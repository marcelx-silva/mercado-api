package com.newgo.mercadoapi.controller;

import com.newgo.mercadoapi.domain.dto.ImageProductDTO;
import com.newgo.mercadoapi.domain.dto.ProductDTO;
import com.newgo.mercadoapi.domain.model.ImageProduct;
import com.newgo.mercadoapi.domain.model.Product;
import com.newgo.mercadoapi.service.imageproduct.ImageProductService;
import com.newgo.mercadoapi.service.imageproduct.Storage;
import com.newgo.mercadoapi.service.product.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/product")

public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    Storage storage;
    @Autowired
    ImageProductService imageProductService;


    @PostMapping("/adm/save")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> save(@RequestBody ProductDTO productDTO) {
        productService.save(productDTO);
        return ResponseEntity.ok().body(productDTO);
    }

    @PostMapping("/adm/save/product/image")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> saveWithImage(@RequestPart("img") MultipartFile multipartFile,
                                        @RequestParam String descImg,
                                        @RequestParam Boolean statusProd,
                                        @RequestParam String nameProd,
                                        @RequestParam String descProd,
                                        @RequestParam int quantProd) {

        storage.saveImage(multipartFile);
        ImageProductDTO imageProductDTO =
                new ImageProductDTO(multipartFile.getOriginalFilename().toLowerCase(),
                        storage.getImagePath(), descImg);
        imageProductService.save(imageProductDTO);


        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(nameProd);
        productDTO.setDescription(descProd);
        productDTO.setStatus(statusProd);
        productDTO.setQuantity(quantProd);
        productDTO.setImageProductURL(storage.getImagePath());


        productService.save(productDTO);
        return ResponseEntity.ok().body(productService.findByName(nameProd));
    }

    @GetMapping("/adm/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getAllProdutos() {
        return ResponseEntity
                .ok()
                .body(productService.findAll());
    }

    @GetMapping("/user/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getOnlyActivatedProdutos() {
        return ResponseEntity
                .ok()
                .body(productService.findAllByAtivadoIsTrue());
    }

    @GetMapping("/adm/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getOneById(@PathVariable("id") UUID uuid) {
        Optional<ProductDTO> productDTO = productService.findById(uuid);
        if (productDTO.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product does not exist");

        return ResponseEntity.ok().body(productDTO);
    }

    @GetMapping("/findByName")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getOneByName(@RequestParam(value = "name") String name) {
        Optional<ProductDTO> productDTO = productService.findByName(name);
        if (productDTO.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product does not exist");

        return ResponseEntity.ok().body(productDTO);
    }

    @DeleteMapping("/adm/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> deleteProdutoById(@PathVariable("id") UUID uuid) {
        Optional<ProductDTO> productDTO = productService.findById(uuid);
        if (productDTO.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product does not exist");

        productService.deleteById(uuid);
        return ResponseEntity.ok().body(productDTO);
    }

    @DeleteMapping("/adm/delete")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> deleteProdutoById(@RequestParam(value = "name") String name) {
        Optional<ProductDTO> productDTO = productService.findByName(name);
        if (productDTO.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product does not exist");

        productService.deleteByName(name);
        return ResponseEntity.ok().body(productDTO);
    }

    @PutMapping("/adm/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updateProdutoById(@PathVariable("id") UUID uuid, @RequestBody ProductDTO productDTO) {
        Optional<ProductDTO> product = productService.findById(uuid);
        if (product.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product does not exist");

        productService.updateProduct(uuid, productDTO);
        return ResponseEntity.ok().body(productService.findById(uuid));
    }

    @PutMapping("/adm/update/{id}/status")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updateProductStatus(@PathVariable("id") UUID uuid) {
        Optional<ProductDTO> product = productService.findById(uuid);
        if (product.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product does not exist");

        productService.updateProductStatus(uuid);
        return ResponseEntity.ok().body(productService.findById(uuid));
    }
}
