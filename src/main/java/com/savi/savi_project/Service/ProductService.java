package com.savi.savi_project.Service;


import com.savi.savi_project.Model.Product;
import com.savi.savi_project.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
public class ProductService  {


    @Autowired
    private  ProductRepository repo;


    public List<Product> getAllProducts() {
        return  repo.findAll();
    }

    public Product getProductById(int id) {
        return  repo.findById(id).get();
    }

//    public Product addProduct(Product product) {
//        return repo.save(product);
//    }

    public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageDate(imageFile.getBytes());
        return  repo.save(product);
    }

    public Product updateProduct(int id, Product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageDate(imageFile.getBytes());
        return  repo.save(product);
    }

    public void deleteProduct(int id) {
        repo.deleteById(id);
    }

    public List<Product> searchProduct(String keyword) {
        return  repo.searchProduct(keyword);

    }
}
