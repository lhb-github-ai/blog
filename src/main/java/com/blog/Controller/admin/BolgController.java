package com.blog.Controller.admin;

import com.blog.po.Blog;
import com.blog.po.User;
import com.blog.service.BlogService;
import com.blog.service.TagService;
import com.blog.service.TypeService;
import com.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/admin")
public class BolgController {

    private static  final  String INPUT="admin/blogs-input.html";
    private static  final  String LIST="admin/blogs";
    private static  final  String REDIRECT_LIST="redirect:/admin/blogs";



    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

      @GetMapping("/blogs")
     public String list(@PageableDefault(size = 2,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model){
          model.addAttribute("types",typeService.listType());
          model.addAttribute("page",blogService.listBlog(pageable,blog));
          return LIST;

     }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 2,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return "admin/blogs :: blogList";

    }
    @GetMapping("/blogs/input")
    public String input(Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
          model.addAttribute("blog" ,new Blog());
          return  INPUT;
    }

    private  void setTypeAndTag(Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
    }

    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        setTypeAndTag(model);
        Blog blog=blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog" , blog);
        return  INPUT;
    }
    @PostMapping("/blogs")
    public  String post(Blog blog, RedirectAttributes redirectAttributes ,HttpSession httpSession){
          blog.setUser((User) httpSession.getAttribute("user"));
          blog.setType(typeService.getType(blog.getType().getId()));
          blog.setTags(tagService.listTag(blog.getTagIds()));
          Blog b;
           if (blog.getId()==null){
               b=blogService.saveBlog(blog);
           }else {
               b=blogService.updateBlog(blog.getId(),blog);
           }
           if(b==null){
               redirectAttributes.addFlashAttribute("message","操作失败");
           }else {
               redirectAttributes.addFlashAttribute("message","操作成功");
           }
          return REDIRECT_LIST;
    }
    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes redirectAttributes){
        blogService.deleteBlog(id);
        redirectAttributes.addFlashAttribute("message","删除成功");
        return REDIRECT_LIST;
    }

}
