package com.vamsi.portfolio.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vamsi.portfolio.dto.CategoryDTO;
import com.vamsi.portfolio.dto.MediaDTO;
import com.vamsi.portfolio.mapper.MediaMapper;
import com.vamsi.portfolio.model.Media;
import com.vamsi.portfolio.repository.MediaRepository;

@Service
public class MediaService {

    @Autowired
    private MediaRepository mediaRepository;

    public void saveMedia(Media media) {

        if (media.isCoverImage()) {

            List<Media> mediaList =
                    mediaRepository.findByCategory(media.getCategory());

            for (Media item : mediaList) {

                if (item.isCoverImage()) {

                    item.setCoverImage(false);
                    mediaRepository.save(item);
                }
            }
        }

        mediaRepository.save(media);
    }

    public List<MediaDTO> getAllMedia() {

        List<Media> mediaList = mediaRepository.findAll();

        return mediaList.stream()
                .map(MediaMapper::toDTO)
                .toList();
    }

    public List<CategoryDTO> getCategoryCards() {

        List<CategoryDTO> categoryList = new ArrayList<>();

        List<String> categories = List.of(
                "Edited Images",
                "Edited Videos",
                "Color Grading",
                "Thumbnails"
        );

        for (String category : categories) {

            // First try to find the cover image
            Media media = mediaRepository.findByCoverImageTrue()
                    .stream()
                    .filter(m -> m.getCategory().equals(category))
                    .findFirst()
                    .orElse(null);

            // If no cover image, use the first uploaded image
            if (media == null) {
                media = mediaRepository.findFirstByCategory(category);
            }

            // Skip category if no media exists
            if (media == null) {
                continue;
            }

            CategoryDTO dto = new CategoryDTO();

            dto.setCategory(category);
            dto.setCoverImage(media.getFilename());
            dto.setType(media.getType());
            dto.setTotalItems(mediaRepository.countByCategory(category));

            categoryList.add(dto);
        }

        return categoryList;
    }
    public List<MediaDTO> getMediaByCategory(String category) {

        return mediaRepository.findByCategory(category)
                .stream()
                .map(MediaMapper::toDTO)
                .toList();
    }
}