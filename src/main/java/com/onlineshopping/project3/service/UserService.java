package com.onlineshopping.project3.service;

import com.onlineshopping.project3.dtos.add.RegisterDto;
import com.onlineshopping.project3.dtos.add.UserAddDTO;
import com.onlineshopping.project3.enums.Role;
import com.onlineshopping.project3.exception.DuplicatePhoneNumberException;
import com.onlineshopping.project3.dtos.get.UserGetDTO;
import com.onlineshopping.project3.model.User;
import com.onlineshopping.project3.repository.UserRepository;
import com.onlineshopping.project3.dtos.updateDTO.UserUpdateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    public UserGetDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElse(null);

        if(user != null)
            return user.toUserGetDTO();
        else
            return null;

    }

    public List<UserGetDTO> getAllUsers() {
        return userRepository.findAll().stream().map(User::toUserGetDTO).toList();
    }

    public UserGetDTO createUser(UserAddDTO userAddDTO, MultipartFile image) throws DuplicatePhoneNumberException {

        if(userAddDTO == null )
            throw new RuntimeException("UserAddDTO is null");


        String fileName = image.getOriginalFilename();

        if(fileName.isEmpty() && userAddDTO.getImageUrl().isEmpty()){
            userAddDTO.setImageUrl("nophoto.jpg");
        }
        else if (!fileName.isEmpty()){
            userAddDTO.setImageUrl(fileName);
            String uploadDir = "src/main/resources/static/images/" + fileName;
            Path uploadPath = Paths.get(uploadDir);

            try{
                Files.copy(image.getInputStream(),uploadPath, StandardCopyOption.REPLACE_EXISTING);
            }catch (IOException ex){
                System.out.println("File saving error "+ ex);
            }
        }

        String encodedPassword = passwordEncoder.encode(userAddDTO.getPassword());

        User user = new User(userAddDTO.getName(), userAddDTO.getAddress(), userAddDTO.getPhone(), userAddDTO.getUsername(), encodedPassword,userAddDTO.getRole(),userAddDTO.getImageUrl());
        if(userRepository.existsByPhone(user.getPhone())) {
            throw new DuplicatePhoneNumberException("Phone number already exists");
        }
        return userRepository.save(user).toUserGetDTO();
    }

    public UserGetDTO updateUser(UserUpdateDTO userUpdateDTO, MultipartFile image){
        Long id = userUpdateDTO.getId();
        if(id == null || id <= 0)
            throw new IllegalArgumentException("id is invalid");

        String fileName = image.getOriginalFilename();
        if (fileName.isEmpty() && userUpdateDTO.getImageUrl().isEmpty()) {
            userUpdateDTO.setImageUrl("nophoto.jpg");
        } else if (!fileName.isEmpty()) {

            userUpdateDTO.setImageUrl(fileName);

            String uploadDir = "src/main/resources/static/images/" + fileName;


            Path uploadPath = Paths.get(uploadDir);

            try {
                Files.copy(image.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                System.out.println("File saving error! " + ex);
            }
        }

        User u = userRepository.findById(id).get();
        u.setName(userUpdateDTO.getName());
        u.setAddress(userUpdateDTO.getAddress());
        u.setPhone(userUpdateDTO.getPhone());
        u.setUsername(userUpdateDTO.getUsername());

        if(!userUpdateDTO.getPassword().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(userUpdateDTO.getPassword());
            u.setPassword(encodedPassword);
        }

        u.setRole(userUpdateDTO.getRole());
        u.setImageUrl(userUpdateDTO.getImageUrl());
        userRepository.save(u);

        return u.toUserGetDTO();
    }


    public void deleteUser(Long id){

        User user = userRepository.findById(id).get();

        if(!user.getImageUrl().isEmpty()) {

            String fileName = user.getImageUrl();

            File file = new File("src/main/resources/static/images/"+  fileName);
            try {
                boolean result = Files.deleteIfExists(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        userRepository.deleteById(id);
    }





    public void register(RegisterDto registerDto) {

        if (registerDto.getUsername() == null || registerDto.getUsername().isBlank() ||
                registerDto.getPassword() == null || registerDto.getPassword().isBlank() ||
                registerDto.getName() == null || registerDto.getName().isBlank() ||
                registerDto.getPhone() == null || registerDto.getPhone().isBlank() ||
                registerDto.getAddress() == null || registerDto.getAddress().isBlank()) {

            throw new IllegalArgumentException("All fields are required.");
        }

        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new IllegalArgumentException("Username already exists.");
        }


        User newUser = new User();
        newUser.setName(registerDto.getName());
        newUser.setUsername(registerDto.getUsername());
        newUser.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        newUser.setRole(Role.CUSTOMER);
        newUser.setImageUrl("nophoto.jpg");
        newUser.setPhone(registerDto.getPhone());
        newUser.setAddress(registerDto.getAddress());

        userRepository.save(newUser);
    }


}
