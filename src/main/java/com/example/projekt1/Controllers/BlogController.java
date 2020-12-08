package com.example.projekt1.Controllers;

import com.example.projekt1.Managers.*;
import com.example.projekt1.Models.Post;
import com.example.projekt1.Models.Posts_Authors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
public class BlogController {
    private int pid=150;

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

    @GetMapping("/addPost")
    public String addPost(Model model){
        model.addAttribute("post", new Post());
        model.addAttribute("authors", aum.getAllAuthors());
        return "addPost";
    }
    @PostMapping("/addPost")
    public String processAddingPost(@Valid Post post, Errors errors, int[] id, Model model){
        if(errors.hasErrors()){
            model.addAttribute("authors", aum.getAllAuthors());
            return "addPost";
        }
        pid++;
        post.setId(pid);
        pm.addPost(post);
        if(id.length != 0){
            for(int i: id){
                Posts_Authors par = new Posts_Authors();
                par.setId_author(i);
                par.setId_post(pid);
                pam.addP_A(par);
            }
        }
        return "redirect:/";
    }
    @GetMapping("/deletePostConfirm/{id}")
    public String deletePostConfirm(Model model, @PathVariable int id){
        model.addAttribute("post", pm.getPostById(id));
        model.addAttribute("pa", pam.getAllPostsAuthors());
        model.addAttribute("authors", aum.getAllAuthors());
        model.addAttribute("comments", cm.getAllComments());
        model.addAttribute("attachments", atm.getAllAttachments());
        return "deletePostConfirm";
    }
    @RequestMapping(value = "/deletePost/{id}", method = RequestMethod.GET)
    public String deletePost(@PathVariable int id){
        pm.deletePost(id);
        return "redirect:/";
    }
}
