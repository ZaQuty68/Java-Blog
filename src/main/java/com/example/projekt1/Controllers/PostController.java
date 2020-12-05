package com.example.projekt1.Controllers;

import com.example.projekt1.Managers.PostManager;
import com.example.projekt1.Models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class PostController {

    @Autowired
    PostManager pm;

    @GetMapping("/")
    public String homePage(Model model){
        model.addAttribute("posts", pm.getAllPosts());
        return "homePage";
    }
}
