package com.onlineshopping.project3.service;

import com.onlineshopping.project3.addDTO.ProductAddDTO;
import com.onlineshopping.project3.exception.ErrorMessages;
import com.onlineshopping.project3.exception.ResourceNotFoundException;
import com.onlineshopping.project3.getDTO.ProductGetDTO;
import com.onlineshopping.project3.model.Product;
import com.onlineshopping.project3.repository.ProductRepository;
import com.onlineshopping.project3.updateDTO.ProductUpdateDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductGetDTO getProductById(Long id) {

        Product product =  productRepository.findById(id)
                .orElse(null);

        if(product != null)
            return product.toProductGetDTO();
        else
            return null;

    }

    public List<ProductGetDTO> getAllProducts() {
        return productRepository.findAll().stream().map(Product::toProductGetDTO).toList();
    }


    public ProductGetDTO createProduct(ProductAddDTO productAddDTO, MultipartFile file) {

        String fileName = file.getOriginalFilename();

        if(fileName.isEmpty() && productAddDTO.getImageUrl().isEmpty()){
            productAddDTO.setImageUrl("nophoto.jpg");
        }
        else if (!fileName.isEmpty()) {
            productAddDTO.setImageUrl(fileName);
            String uploadDir = "target/classes/images/" + fileName;
            Path uploadPath = Paths.get(uploadDir);

            try{
                Files.copy(file.getInputStream(),uploadPath, StandardCopyOption.REPLACE_EXISTING);
            }
            catch (IOException ex) {
                System.out.println("File saving error : "+ ex.getMessage());
            }
        }
        Product product = new Product(productAddDTO.getName(),productAddDTO.getSupplier(),productAddDTO.getPrice(),productAddDTO.getImageUrl());
        productRepository.save(product);
        return product.toProductGetDTO();
    }

    public ProductGetDTO updateProduct(ProductUpdateDTO product,MultipartFile file) {
        Long id = product.getId();

        if(id == null || id <= 0)
            throw new IllegalArgumentException("id is invalid");

        String fileName = file.getOriginalFilename();

        if(fileName.isEmpty() && product.getImageUrl().isEmpty()){
            product.setImageUrl("nophoto.jpg");
        }
        else if (!fileName.isEmpty()) {
            product.setImageUrl(fileName);
            String uploadDir = "target/classes/images/" + fileName;
            Path uploadPath = Paths.get(uploadDir);

            try{
                Files.copy(file.getInputStream(),uploadPath, StandardCopyOption.REPLACE_EXISTING);
            }
            catch (IOException ex) {
                System.out.println("File saving error : "+ ex.getMessage());
            }
        }
        Product p = productRepository.findById(id).get();
        p.setName(product.getName());
        p.setPrice(product.getPrice());
        p.setSupplier(product.getSupplier());
        p.setImageUrl(product.getImageUrl());
        productRepository.save(p);
        return p.toProductGetDTO();
    }

    public void deleteProduct(Long id) {
        // Delete image if exists
        Product product = productRepository.findById(id).get();

        if(!product.getImageUrl().isEmpty()) {

            String fileName = product.getImageUrl();

            File file = new File("target/classes/static/images/"+  fileName);
            try {
                boolean result = Files.deleteIfExists(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        productRepository.deleteById(id);
    }

    public List<ProductGetDTO> searchProductsByNameService(String name){
        List<Product> repoProducts = productRepository.searchProductsByName(name);
        return repoProducts
                .stream()
                .map(Product::toProductGetDTO)
                .toList();
    }
}
