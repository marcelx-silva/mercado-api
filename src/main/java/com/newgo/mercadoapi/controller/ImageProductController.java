package com.newgo.mercadoapi.controller;

import com.newgo.mercadoapi.domain.dto.ImageProductDTO;
import com.newgo.mercadoapi.domain.dto.ProductDTO;
import com.newgo.mercadoapi.service.imageproduct.ImageProductService;
import com.newgo.mercadoapi.service.imageproduct.Storage;
import com.newgo.mercadoapi.service.product.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/image/adm")
public class ImageProductController {

    @Autowired
    ImageProductService imageProductService;
    @Autowired
    Storage storage;
    @Autowired
    ProductService productService;

    @PostMapping ("/save")
    public ResponseEntity<Object> uploadImage(@RequestParam("desc") String imageDescription, @RequestParam("img")  MultipartFile multipartFile){
        storage.saveImage(multipartFile);
        ImageProductDTO imageProductDTO =
                new ImageProductDTO(multipartFile.getOriginalFilename().toLowerCase(),
                        storage.getImagePath(),
                        imageDescription);

        imageProductService.save(imageProductDTO);
        return ResponseEntity.status(HttpStatus.OK).body(imageProductDTO);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Object> saveImageToProduct(@PathVariable("id") UUID productId,ImageProductDTO imageProductDTO){
        Optional<ProductDTO> product = productService.findById(productId);
        if (product.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");

        ProductDTO productDTO = new ProductDTO();
        BeanUtils.copyProperties(product,productDTO);
        productDTO.setImageProductURL(imageProductDTO.getUrl());
        imageProductService.save(imageProductDTO);
        return ResponseEntity.status(HttpStatus.OK).body(imageProductDTO);
    }

}
