package com.blog.Controller;


import com.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ArchivesShowController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/archives")
    public String archivesS(Model model){
     model.addAttribute("archiveMap",blogService.archiveBlog());
     model.addAttribute("blogcount",blogService.countBlog());

        return "archives";
    }
}
