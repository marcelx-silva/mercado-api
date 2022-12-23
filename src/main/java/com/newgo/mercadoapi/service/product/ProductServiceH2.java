package com.newgo.mercadoapi.service.product;

import com.newgo.mercadoapi.domain.dto.ProductDTO;
import com.newgo.mercadoapi.domain.mappers.ConverterDTO;
import com.newgo.mercadoapi.domain.model.Product;
import com.newgo.mercadoapi.repository.ProductRepository;
import com.newgo.mercadoapi.service.imageproduct.ImageProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class ProductServiceH2 implements ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    ConverterDTO<Product,ProductDTO> converterDTO;
    @Autowired
    ImageProductService imageProductService;

    @Override
    @Transactional
    public void save(ProductDTO productDTO) {
        Product product = modelMapper.map(productDTO, Product.class);
        product.setImageProduct(imageProductService.findByURL(productDTO.getImageProductURL()));
        productRepository.save(product);
    }

    @Override
    public Set<ProductDTO> findAll() {
        Set<ProductDTO> productDTOSet = new HashSet<>();
        productRepository.findAll().forEach(product -> productDTOSet.add(converterDTO.toDTO(product)));
        return productDTOSet;
    }

    @Override
    public Optional<ProductDTO> findById(UUID uuid) {
        Optional<Product> product = productRepository.findById(uuid);
        return Optional.ofNullable(converterDTO.toDTO(product.get()));
    }

    @Override
    public Optional<ProductDTO> findByName(String name) {
        Optional<Product> product = productRepository.findProductByName(name);
        return Optional.ofNullable(converterDTO.toDTO(product.get()));
    }

    @Override
    @Transactional
    public void deleteById(UUID uuid) {
        productRepository.deleteById(uuid);
    }

    @Override
    @Transactional
    public void deleteByName(String name) {
        productRepository.deleteProductByName(name);
    }

    @Override
    public Set<ProductDTO> findAllByAtivadoIsTrue() {
        Set<ProductDTO> productDTOSet = new HashSet<>();
        productRepository.findAllByStatusIsTrue().
                forEach(product -> productDTOSet.
                        add(converterDTO.toDTO(product)));
        return productDTOSet;
    }

    @Override
    @Transactional
    public void updateProduct(UUID uuid, ProductDTO productDTO) {
        productRepository.updateProduct(uuid,productDTO);
    }

    @Override
    @Transactional
    public void updateProductStatus(UUID uuid) {
        Optional<Product> product = productRepository.findById(uuid);

        if (product.isEmpty())
            throw new RuntimeException();

        if (product.get().getStatus()){
            productRepository.setProductStatus(uuid, false);
            return;
        }
        productRepository.setProductStatus(uuid, true);
    }
}
