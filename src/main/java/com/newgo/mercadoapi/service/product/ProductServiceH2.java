package com.newgo.mercadoapi.service.product;

import com.newgo.mercadoapi.domain.dto.product.ProductDTO;
import com.newgo.mercadoapi.domain.mappers.ObjectDTOMapper;
import com.newgo.mercadoapi.domain.model.Product;
import com.newgo.mercadoapi.repository.ProductRepository;
import com.newgo.mercadoapi.service.imageproduct.ImageProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceH2 implements ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    ObjectDTOMapper<Product,ProductDTO> converterDTO;
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
    public Page<ProductDTO> findAll(Pageable pageable) {
        return productRepository.findAll(pageable).map(converterDTO::toDTO);
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
    public Page<ProductDTO> findAllByAtivadoIsTrue(Pageable pageable) {
        return productRepository.findAllByStatusIsTrue(pageable)
                .map(converterDTO::toDTO);
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

    @Override
    public Page<ProductDTO> findProductsBetween(Double min, Double max, Pageable pageable) {
      return (Page<ProductDTO>) productRepository.findByPriceBetween(min, max, pageable)
              .filter(Product::getStatus)
              .map(converterDTO::toDTO);
    }
}
