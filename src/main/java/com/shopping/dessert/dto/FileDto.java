package com.shopping.dessert.dto;

import com.shopping.dessert.entity.FileEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileDto {

    public Long fileId;

    public String originalName;
    public String savedName;

    public Long productId;

    public static FileDto of(FileEntity fileEntity){
        return FileDto
                .builder()
                .fileId(fileEntity.getFileId())
                .originalName(fileEntity.getOriginalName())
                .savedName(fileEntity.getSavedName())
                .productId(fileEntity.getProduct().getProductId())
                .build();
    }

}
