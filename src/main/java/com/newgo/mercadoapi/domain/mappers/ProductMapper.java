package com.newgo.mercadoapi.domain.mappers;

import com.newgo.mercadoapi.domain.dto.product.ProductDTO;
import com.newgo.mercadoapi.domain.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper implements ObjectDTOMapper<Product, ProductDTO> {

    @Override
    public ProductDTO toDTO(Product object) {
        if(object==null)
            return null;

        ProductDTO productDTO = new ProductDTO();
        productDTO.setUuid(object.getUuid());
        productDTO.setName(object.getName());
        productDTO.setDescription(object.getDescription());
        productDTO.setStatus(object.getStatus());
        productDTO.setQuantity(object.getQuantity());
        productDTO.setPrice(object.getPrice());
        productDTO.setImageProductURL(object.getImageProduct()!=null ? object.getImageProduct().getUrl() : null);

        return productDTO;
    }
}
