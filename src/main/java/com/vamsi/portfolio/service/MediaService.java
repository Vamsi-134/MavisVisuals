package com.vamsi.portfolio.service;
import java.util.Optional;
import com.vamsi.portfolio.service.S3Service;
import java.util.ArrayList;
import java.util.List;
import com.vamsi.portfolio.dto.DashboardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vamsi.portfolio.model.Category;
import com.vamsi.portfolio.dto.CategoryDTO;
import com.vamsi.portfolio.dto.MediaDTO;
import com.vamsi.portfolio.mapper.MediaMapper;
import com.vamsi.portfolio.model.Media;
import com.vamsi.portfolio.repository.MediaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
@Service
public class MediaService {
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private S3Service s3Service;

    @Autowired
    private MediaRepository mediaRepository;

    public void saveMedia(Media media) {

        // If user selected cover image
        if (media.isCoverImage()) {

            List<Media> mediaList =
                    mediaRepository.findByCategory(media.getCategory());

            for (Media item : mediaList) {

                if (item.isCoverImage()) {

                    item.setCoverImage(false);
                    mediaRepository.save(item);
                }
            }

        } else {

            // If no cover image exists in this category,
            // make the first uploaded media the cover automatically.

            List<Media> mediaList =
                    mediaRepository.findByCategory(media.getCategory());

            boolean coverExists = false;

            for (Media item : mediaList) {

                if (item.isCoverImage()) {

                    coverExists = true;
                    break;
                }
            }

            if (!coverExists) {

                media.setCoverImage(true);

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

        List<Category> categories = categoryService.getAllCategories();

        for (Category categoryObj : categories) {

            String category = categoryObj.getName();

            Media media = mediaRepository.findByCoverImageTrue()
                    .stream()
                    .filter(m -> m.getCategory().equals(category))
                    .findFirst()
                    .orElse(null);

            if (media == null) {
                media = mediaRepository.findFirstByCategory(category);
            }

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
    public void deleteMedia(Integer id) {

        Optional<Media> mediaOptional = mediaRepository.findById(id);

        if (mediaOptional.isPresent()) {

            Media media = mediaOptional.get();

            // Delete file from S3
            s3Service.deleteFile(media.getFilename());

            // Delete record from MySQL
            mediaRepository.delete(media);
        }
    }
    public Media getMedia(Integer id){

        return mediaRepository.findById(id).orElse(null);

    }
    public Page<MediaDTO> getMediaByCategory(String category, int page) {

        return mediaRepository
                .findByCategory(category, PageRequest.of(page, 9))
                .map(MediaMapper::toDTO);

    }
    public DashboardDTO getDashboard() {

        DashboardDTO dto = new DashboardDTO();

        dto.setTotalImages(
                mediaRepository.countByType("Image"));

        dto.setTotalVideos(
                mediaRepository.countByType("Video"));

        dto.setTotalMedia(
                mediaRepository.count());

        dto.setTotalCategories(
                categoryService.getAllCategories().size());

        return dto;
    }
}