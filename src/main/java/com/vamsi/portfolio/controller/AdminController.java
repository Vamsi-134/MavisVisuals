package com.vamsi.portfolio.controller;
import com.vamsi.portfolio.service.S3Service;
import java.io.IOException;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.vamsi.portfolio.service.CategoryService;
import com.vamsi.portfolio.model.Media;
import com.vamsi.portfolio.service.MediaService;
import com.vamsi.portfolio.dto.DashboardDTO;
@Controller
public class AdminController {
	
	

    @Autowired
    private S3Service s3Service;
    

    @Autowired
    private MediaService mediaService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/admin")
    
    public String admin(Model model) {
    	DashboardDTO dashboard = mediaService.getDashboard();

    	System.out.println("Images = " + dashboard.getTotalImages());
    	System.out.println("Videos = " + dashboard.getTotalVideos());
    	System.out.println("Categories = " + dashboard.getTotalCategories());
    	System.out.println("Media = " + dashboard.getTotalMedia());

    	model.addAttribute("dashboard", dashboard);
    	
    	

        model.addAttribute("mediaList",
                mediaService.getAllMedia());

        model.addAttribute("categoryList",
                categoryService.getAllCategories());

        return "admin";
    }

    @PostMapping("/upload")
    public String upload(

            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("category") String category,
            @RequestParam("type") String type,
            @RequestParam("files") MultipartFile[] files,
            @RequestParam(value = "coverImage", required = false) Boolean coverImage

    ) throws IOException {

    	System.out.println("======================");
    	System.out.println("Title       : " + title);
    	System.out.println("Description : " + description);
    	System.out.println("Category    : " + category);
    	System.out.println("Type        : " + type);
    	System.out.println("Total Files : " + files.length);
    	System.out.println("Cover Image : " + (coverImage != null));
    	System.out.println("======================");

        // Save file
    	for (MultipartFile file : files) {

    	    String fileName = s3Service.uploadFile(file);

    	    Media media = new Media();

    	    media.setTitle(title);
    	    media.setDescription(description);
    	    media.setCategory(category);
    	    media.setType(type);
    	    media.setFilename(fileName);

    	    media.setCoverImage(
    	            coverImage != null && file == files[0]);

    	    mediaService.saveMedia(media);
    	}
        return "redirect:/admin";
    }
    

    @GetMapping("/delete/{id}")
    public String deleteMedia(@PathVariable Integer id) {

        mediaService.deleteMedia(id);

        return "redirect:/admin";
    }
    @GetMapping("/edit/{id}")
    public String editMedia(
            @PathVariable Integer id,
            Model model){

        model.addAttribute(
                "media",
                mediaService.getMedia(id));

        return "edit-media";
    }
    @PostMapping("/update-media")
    public String updateMedia(

    @RequestParam Integer id,
    @RequestParam String title,
    @RequestParam String description,
    @RequestParam String category,
    @RequestParam(required=false)
    Boolean coverImage){

    Media media=
    mediaService.getMedia(id);

    media.setTitle(title);
    media.setDescription(description);
    media.setCategory(category);
    media.setCoverImage(coverImage!=null);

    mediaService.saveMedia(media);

    return "redirect:/admin";

    }

}