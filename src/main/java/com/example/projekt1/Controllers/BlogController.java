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
import java.util.List;


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
        if(!pm.checkPost(id)){
            return "redirect:/error/This post does not exist!";
        }
        model.addAttribute("post", pm.getPostById(id));
        model.addAttribute("pa", pam.getAllPostsAuthors());
        model.addAttribute("authors", aum.getAllAuthors());
        model.addAttribute("comments", cm.getAllComments());
        return "deletePostConfirm";
    }
    @RequestMapping(value = "/deletePost/{id}", method = RequestMethod.GET)
    public String deletePost(@PathVariable int id){
        if(!pm.checkPost(id)){
            return "redirect:/error/This post does not exist!";
        }
        pm.deletePost(id);
        pam.deleteByPostId(id);
        cm.deleteCommentsByPostId(id);
        return "redirect:/";
    }
    @GetMapping("/editPost/{id}")
    public String editPost(Model model, @PathVariable int id){
        if(!pm.checkPost(id)){
            return "redirect:/error/This post does not exist!";
        }
        model.addAttribute("post", pm.getPostById(id));
        model.addAttribute("pa", pam.getAllPostsAuthors());
        model.addAttribute("authors", aum.getAllAuthors());
        model.addAttribute("comments", cm.getAllComments());
        return "editPost";
    }
    @PostMapping("/editPost/{id}")
    public String processEditPost(@Valid Post post, Errors errors, int[] idA, Model model, @PathVariable int id){
        if(!pm.checkPost(id)){
            return "redirect:/error/This post does not exist!";
        }
        if(errors.hasErrors()){
            model.addAttribute("authors", aum.getAllAuthors());
            return "editPost";
        }
        Post postToEdit = pm.getPostById(id);
        postToEdit.setPost_content(post.getPost_content());
        postToEdit.setTags(post.getTags());
        pam.deleteByPostId(id);
        if(idA.length != 0){
            for(int i: idA){
                Posts_Authors par = new Posts_Authors();
                par.setId_author(i);
                par.setId_post(id);
                pam.addP_A(par);
            }
        }
        return "redirect:/";
    }
    @GetMapping("/error/{message}")
    public String errorMessage(Model model, @PathVariable String message){
        model.addAttribute("message", message);
        return "errorMessage";
    }
    @PostMapping("/searchPost")
    public String searchPost(Model model, String type, String pattern){
        if(pattern.length() == 0){
            return "redirect:/";
        }
        if(type.equals("content")){
            if(pm.getPostsByContent(pattern).isEmpty()){
                return "redirect:/error/No matches found!";
            }
            model.addAttribute("posts", pm.getPostsByContent(pattern));
        }
        if(type.equals("author")){
            if(pm.getPostsByAuthors(pam.getByAuthors(aum.getAuthorsByUsername(pattern))).isEmpty()){
                return "redirect:/error/No matches found!";
            }
            model.addAttribute("posts", pm.getPostsByAuthors(pam.getByAuthors(aum.getAuthorsByUsername(pattern))));
        }
        if(type.equals("tags")){
            if(pm.getPostsByTags(pattern).isEmpty()){
                return "redirect:/error/No matches found!";
            }
            model.addAttribute("posts", pm.getPostsByTags(pattern));
        }
        model.addAttribute("pa", pam.getAllPostsAuthors());
        model.addAttribute("authors", aum.getAllAuthors());
        model.addAttribute("comments", cm.getAllComments());
        return "homePage";
    }
}
