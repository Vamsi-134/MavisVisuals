package com.vamsi.portfolio.mapper;

import com.vamsi.portfolio.dto.MediaDTO;
import com.vamsi.portfolio.model.Media;

public class MediaMapper {

    private MediaMapper() {
    }

    public static MediaDTO toDTO(Media media) {

        MediaDTO dto = new MediaDTO();

        dto.setId(media.getId());
        dto.setTitle(media.getTitle());
        dto.setDescription(media.getDescription());
        dto.setCategory(media.getCategory());
        dto.setType(media.getType());
        dto.setFilename(media.getFilename());
        dto.setCoverImage(media.isCoverImage());

        return dto;
    }
}