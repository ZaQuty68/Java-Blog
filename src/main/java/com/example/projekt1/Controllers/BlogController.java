package com.example.projekt1.Controllers;

import com.example.projekt1.Managers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class BlogController {

    @Autowired
    AttachmentManager atm;
    @Autowired
    AuthorManager aum;
    @Autowired
    CommentManager cm;
    @Autowired
    Posts_AuthorsManager pam;
    @Autowired
    PostManager pm;


    @GetMapping("/")
    public String homePage(Model model){
        model.addAttribute("posts", pm.getAllPosts());
        model.addAttribute("pa", pam.getAllPostsAuthors());
        model.addAttribute("authors", aum.getAllAuthors());
        model.addAttribute("comments", cm.getAllComments());
        model.addAttribute("attachments", atm.getAllAttachments());
        return "homePage";
    }
}
