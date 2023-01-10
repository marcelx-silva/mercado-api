package com.newgo.mercadoapi.controller;

import com.newgo.mercadoapi.domain.dto.product.ProductDTO;
import com.newgo.mercadoapi.service.imageproduct.ImageProductService;
import com.newgo.mercadoapi.service.imageproduct.Storage;
import com.newgo.mercadoapi.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


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


    @PostMapping("/managed-products")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(value = "hasRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity<Object> save(@RequestBody ProductDTO productDTO) {
        productService.save(productDTO);
        return ResponseEntity.ok().body(productDTO);
    }

    @GetMapping("/managed-products/products/all")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(value = "hasRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity<Object> getAllProdutos(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(),pageable.getSort());
        return ResponseEntity
                .ok()
                .body(productService.findAll(pageable));
    }

    @GetMapping("/products/all")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMINISTRATOR','ROLE_CUSTOMER')")
    public ResponseEntity<Object> getOnlyActivatedProdutos(Pageable pageable) {
        return ResponseEntity
                .ok()
                .body(productService.findAllByAtivadoIsTrue(pageable));
    }

    @GetMapping("/managed-products/products/{productId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(value = "hasRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity<Object> getOneById(@PathVariable("productId") UUID uuid) {
        Optional<ProductDTO> productDTO = productService.findById(uuid);
        if (productDTO.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product does not exist");

        return ResponseEntity.ok().body(productDTO);
    }

    @GetMapping({"/product"})
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMINISTRATOR','ROLE_CUSTOMER')")
    public ResponseEntity<Object> getOneByName(@RequestParam(value = "name") String name) {
        Optional<ProductDTO> productDTO = productService.findByName(name);
        if (productDTO.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product does not exist");

        return ResponseEntity.ok().body(productDTO);
    }

    @DeleteMapping("/managed-products/product/{productId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(value = "hasRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity<Object> deleteProdutoById(@PathVariable("productId") UUID uuid) {
        Optional<ProductDTO> productDTO = productService.findById(uuid);
        if (productDTO.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product does not exist");

        productService.deleteById(uuid);
        return ResponseEntity.ok().body(productDTO);
    }

    @GetMapping("/products/price")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(value = "hasAnyRole('ROLE_ADMINISTRATOR','ROLE_CUSTOMER')")
    public ResponseEntity<Object> findProductsPriceBetween(
            @RequestParam(value = "min" , defaultValue = "0.0") Double min,
            @RequestParam(value = "max", defaultValue = "999999.9") Double max,
            Pageable pageable){
        return ResponseEntity.ok().body(productService.findProductsBetween(min, max, pageable));
    }

    @DeleteMapping("/managed-products/product")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(value = "hasRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity<Object> deleteProdutoByName(@RequestParam(value = "name") String name) {
        Optional<ProductDTO> productDTO = productService.findByName(name);
        if (productDTO.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product does not exist");

        productService.deleteByName(name);
        return ResponseEntity.ok().body(productDTO);
    }

    @PutMapping("/managed-products/product/{productId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(value = "hasRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity<Object> updateProdutoById(@PathVariable("productId") UUID uuid, @RequestBody ProductDTO productDTO) {
        Optional<ProductDTO> product = productService.findById(uuid);
        if (product.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product does not exist");

        productService.updateProduct(uuid, productDTO);
        return ResponseEntity.ok().body(productService.findById(uuid));
    }

    @PutMapping("/managed-products/product/{productId}/status")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(value = "hasRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity<Object> updateProductStatus(@PathVariable("productId") UUID uuid) {
        Optional<ProductDTO> product = productService.findById(uuid);
        if (product.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product does not exist");

        productService.updateProductStatus(uuid);
        return ResponseEntity.ok().body(productService.findById(uuid));
    }
}
