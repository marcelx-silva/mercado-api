package com.newgo.mercadoapi.service.product;

import com.newgo.mercadoapi.domain.dto.product.ProductDTO;
import com.newgo.mercadoapi.domain.mappers.ObjectDTOMapper;
import com.newgo.mercadoapi.domain.model.Category;
import com.newgo.mercadoapi.domain.model.Product;
import com.newgo.mercadoapi.repository.CategoryRepository;
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
    ObjectDTOMapper<Product,ProductDTO> converterDTO;
    @Autowired
    ImageProductService imageProductService;
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    @Transactional
    public void save(ProductDTO productDTO) {
        Optional<Category> category = categoryRepository.findByName(productDTO.getCategory());
        if (category.isEmpty())
            return;


        Product product = modelMapper.map(productDTO, Product.class);
        product.setCategory(category.get());
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

    @Override
    public Set<ProductDTO> findProductsBetween(Double min, Double max) {
      Set<ProductDTO> products = new HashSet<>();
      productRepository.findByPriceBetween(min, max)
              .stream()
              .filter(Product::getStatus)
              .forEach(product -> products.add(converterDTO.toDTO(product)));

      return products;
    }

    @Override
    public Set<ProductDTO> searchByKeyWord(String keyWord) {
        Set<ProductDTO> products = new HashSet<>();
        System.out.println("Esta vazio: "+(productRepository.searchByKeyWord(keyWord).isEmpty() ? "sim" : "nao"));
        productRepository.searchByKeyWord(keyWord).forEach(product -> products.add(converterDTO.toDTO(product)));
        return products;
    }
}
