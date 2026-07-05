package com.savi.savi_project.Controller;

import com.savi.savi_project.Model.Product;
import com.savi.savi_project.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id) {
        Product product = service.getProductById(id);
        if (product != null)
            return new ResponseEntity<>(product, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

//    @PostMapping("/product")
//    public  Product addProduct(@RequestBody Product product){
//        return  service.addProduct(product);
//    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product,
                                        @RequestPart MultipartFile imageFile) {
        System.out.println(product);
        try {
            System.out.println(product);
            Product product1 = service.addProduct(product, imageFile);
            return new ResponseEntity<>(product1, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId) {
        Product product = service.getProductById(productId);
        byte[] imageFile = product.getImageDate();
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(product.getImageType()))
                .body(imageFile);
    }

    @PutMapping("/product/{id}")
    public  ResponseEntity<String> updateProduct(@PathVariable int id ,@RequestPart Product product,
                                                 @RequestPart MultipartFile imageFile ) throws IOException {
        Product product1 =  service.updateProduct(id , product , imageFile);
        if (product1 != null)
            return  new ResponseEntity<>( " updated " , HttpStatus.OK);
        else
            return  new ResponseEntity<>( "failed to update " , HttpStatus.BAD_REQUEST);
    }


    @DeleteMapping("/product/{id}")
    public  ResponseEntity<String> deleteProduct(@PathVariable Integer id){
        Product product = service.getProductById(id);
        if (product != null) {
             service.deleteProduct(id);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }else
            return new ResponseEntity<>("No Product Found ", HttpStatus.NOT_FOUND);

    }

     @GetMapping("/products/search")
    public  ResponseEntity<List<Product> >  searchProduct(@RequestParam String keyword){
         System.out.println("Searching with " + keyword);
        List<Product> product = service.searchProduct(keyword);
        return  new ResponseEntity<>(product , HttpStatus.OK);
     }

}

