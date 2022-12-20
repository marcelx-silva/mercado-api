package com.newgo.mercadoapi.service.imageproduct;

import com.newgo.mercadoapi.domain.dto.ImageProductDTO;
import com.newgo.mercadoapi.domain.model.ImageProduct;
import com.newgo.mercadoapi.repository.ImageProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ImageProductServiceH2 implements ImageProductService{

    @Autowired
    ImageProductRepository imageProductRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    @Transactional
    public void save(ImageProductDTO imageProductDTO) {
        System.out.println(imageProductDTO.getName());
        System.out.println(imageProductDTO.getUrl());
        ImageProduct imageProduct = modelMapper.map(imageProductDTO,ImageProduct.class);
        System.out.println(imageProduct.getUuid());
        imageProductRepository.save(imageProduct);
    }

    @Override
    public Optional<ImageProduct> findByName(String name) {
        return imageProductRepository.findImageProductByName(name);
    }

    @Override
    @Transactional
    public void deleteByName(String name) {
        imageProductRepository.deleteImageProductByName(name);
    }

    @Override
    public ImageProduct findByURL(String URL) {
        return imageProductRepository.findImageProductByUrl(URL);
    }
}
