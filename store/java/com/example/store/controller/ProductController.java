package com.example.store.controller;

import com.example.store.dtos.ProductDto;
import com.example.store.dtos.UserDto;
import com.example.store.entities.Category;
import com.example.store.entities.Product;
import com.example.store.mappers.ProductMapper;
import com.example.store.repositories.CategoryRepository;
import com.example.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductController {


    private final ProductMapper productMapper;


    private final ProductRepository productRepository;


    private final CategoryRepository categoryRepository;

//    @RequestMapping(method = RequestMethod.GET, value = "/product")
//    public ResponseEntity<List<ProductDto>> getProductList(@RequestParam(required = false) Integer categoryId){
//
//        List<ProductDto> products =productRepository.findAll()
//                .stream()
//                .filter(product -> product.getCategory().getId().equals(categoryId))
//                .map(product -> productMapper.productDto(product))
//                .toList();
//
//        return ResponseEntity.ok(products);
//    }

    @RequestMapping(method = RequestMethod.GET, value = "/product")
    public ResponseEntity<List<ProductDto>> getProductList(Integer categoryId){

        List<ProductDto> products =productRepository.findAll()
                .stream()
                .map(product -> productMapper.productDto(product))
                .toList();

        return ResponseEntity.ok(products);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/product/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Integer productId){

        Product product = productRepository.findById(productId).orElse(null);
        if(product == null){
            return ResponseEntity.notFound().build();
        }
        ProductDto productDto = productMapper.productDto(product);

        return ResponseEntity.ok(productDto);
    }

    //create a new resource
    @PostMapping("/product")
    public ResponseEntity<ProductDto> addProduct(
            @RequestBody ProductDto productDto,
            UriComponentsBuilder uriBuilder){
        //this step is needed as we have category object
        Category category =categoryRepository.findById(productDto.getCategoryId()).orElse(null);
        if(category == null){
            return ResponseEntity.badRequest().build();
        }
        Product product = productMapper.toEntity(productDto);
        product.setCategory(category);
        productRepository.save(product);

        URI uri = uriBuilder.path("/product/{id}").buildAndExpand(product.getId()).toUri();

        //this will return the correct id to the postman
        ProductDto productDto1 = productMapper.productDto(product);
        return ResponseEntity.created(uri).body(productDto1);

    }

    //update a resource
    @PutMapping("/product/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @RequestBody ProductDto productDto,
            @PathVariable Integer id
    ){
        Product product = productRepository.findById(id).orElse(null);
        if(product == null){
            return ResponseEntity.notFound().build();
        }

        productMapper.update(productDto, product);
        productRepository.save(product);
        return ResponseEntity.ok(productMapper.productDto(product));
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id){
        Product product = productRepository.findById(id).orElse(null);
        if(product == null){
            return ResponseEntity.notFound().build();
        }
        productRepository.delete(product);
        return ResponseEntity.noContent().build();

    }

}
