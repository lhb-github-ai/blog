package com.blog.Controller.admin;

import com.blog.po.Type;
import com.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TypeController {
    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String types(@PageableDefault(size = 3,sort = {"id"},direction = Sort.Direction.DESC)
                                Pageable pageable, Model model) {
        model.addAttribute("page",typeService.listType(pageable));
        return "admin/types";
    }
    @GetMapping("/types/input")
    public  String  input(Model model){
        model.addAttribute("type",new Type());
       return  "admin/type-input";
    }
    @GetMapping("/types/{id}/input")
    public  String editInput(@PathVariable  Long id, Model model){
        model.addAttribute("type",typeService.getType(id));
        return  "admin/type-input";
    }

  @PostMapping("/types")
    public  String post(@Valid  Type type, BindingResult result, RedirectAttributes redirectAttributes){
        Type type1 =typeService.getTypeByname(type.getName());
        if (type1!=null){
            result.rejectValue("name",":nameError","不能添加重复的分类");
        }
        if (result.hasErrors()){
            return "admin/type-input";
        }
      Type t=  typeService.saveType(type);
        if (t==null){
            redirectAttributes.addFlashAttribute("message","添加失败");

        }else {
            redirectAttributes.addFlashAttribute("message","添加成功");

        }
        return "redirect:/admin/types";

    }

    @PostMapping("/types/{id}")
    public  String editPost(@Valid  Type type, BindingResult result,@PathVariable Long id, RedirectAttributes redirectAttributes){
        Type type1 =typeService.getTypeByname(type.getName());
        if (type1!=null){
            result.rejectValue("name",":nameError","不能添加重复的分类");
        }
        if (result.hasErrors()){
            return "admin/type-input";
        }
        Type t=  typeService.updateType(id,type);
        if (t==null){
            redirectAttributes.addFlashAttribute("message","更新失败");

        }else {
            redirectAttributes.addFlashAttribute("message","更新成功");

        }
        return "redirect:/admin/types";

    }
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes redirectAttributes){
        typeService.deleteType(id);
        redirectAttributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }

}
