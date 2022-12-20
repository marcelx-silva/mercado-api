package com.newgo.mercadoapi.service.imageproduct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class Storage {
    @Value("${root.directory}")
    private String root;
    @Value("${directory.images}")
    private String imagesDir;
    private String imagePath;

    public void saveImage(MultipartFile file){
        this.save(imagesDir,file);
    }

    private void save(String directory, MultipartFile file){
        Path path = Paths.get(root,directory);
        Path filePath = path.resolve(Objects.requireNonNull(file.getOriginalFilename()));
        imagePath = filePath.toString();
        try{
            Files.createDirectories(path);
            file.transferTo(filePath.toFile());
        }catch (IOException e){
            throw new RuntimeException();
        }
    }

    public String getImagePath(){
        return imagePath;
    }
}
