package com.shopping.dessert.service;

import com.shopping.dessert.dto.FileDto;
import com.shopping.dessert.entity.FileEntity;
import com.shopping.dessert.entity.ProductEntity;
import com.shopping.dessert.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    private final String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
    private final String uploadPath = Paths.get("C:","uploads").toString();

    private final String getRandomString(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    @Transactional
    public void uploadFile(MultipartFile[] multipartFiles, ProductEntity productEntity){

        if (multipartFiles[0].getSize() < 0){
            return;
        }

        List<FileDto> savedFiles = new ArrayList<>();

        File dir = new File(uploadPath);
        if (!dir.exists()){
            dir.mkdirs();
        }

        for (MultipartFile file : multipartFiles){
            try{
                // 파일 확장자
                final String extension = FilenameUtils.getExtension(file.getOriginalFilename());
                // 저장할 파일 명
                final String saveName = getRandomString() + "." + extension;

                // 업로드
                File target = new File(uploadPath,saveName);
                file.transferTo(target);

                FileEntity fileEntity = FileEntity
                        .builder()
                        .filePath(uploadPath)
                        .originalName(file.getOriginalFilename())
                        .savedName(saveName)
                        .product(productEntity)
                        .build();

                fileRepository.save(fileEntity);

                savedFiles.add(FileDto.of(fileEntity));


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Transactional
    public void deleteFile(ProductEntity product){
        List<FileEntity> fileEntities = fileRepository.findByProduct(product);

        if(fileEntities.isEmpty()){
            return;
        }

        for(FileEntity fileEntity : fileEntities){
            File file = new File(fileEntity.getFilePath() + File.separator + fileEntity.getSavedName());
            boolean result = file.delete();
        }

    }
}
