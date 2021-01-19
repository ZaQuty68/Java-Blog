package com.example.projekt1.Controllers;

import com.example.projekt1.Managers.*;
import com.example.projekt1.Models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Controller
public class BlogController {
    @Autowired
    AttachmentManager am;
    @Autowired
    AuthorManager aum;
    @Autowired
    CommentManager cm;
    @Autowired
    PostManager pm;
    @Autowired
    TagManager tm;
    @Autowired
    StorageManager sm;

    private int userId = -1;




    /////////////////////////////////////////////////////////////////////////////////////////LOGIN
    @GetMapping("/")
    public String login(){
        userId = -1;
        return "login";
    }

    @PostMapping("/")
    public String processLogin(Model model, String username, String password){
        userId = aum.logIn(username, password);
        if(userId == -1){
            model.addAttribute("loginError", true);
            return "login";
        }
        return "redirect:/homePage";
    }

    ////////////////////////////////////////////////////////////////////////////////////////REGISTER
    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("author", new Author());
        return "register";
    }
    @PostMapping("/register")
    public String processRegister(@Valid Author author, Errors errors, Model model){
        if(errors.hasErrors()){
            return "register";
        }
        if(aum.checkByUsername(author.getUsername())){
            model.addAttribute("registerError", true);
            return "register";
        }
        aum.addAuthor(author);
        return "redirect:/";
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////HOME PAGE/////////////////////////////////////////
    @GetMapping("/homePage")
    public String homePage(Model model){
        if(userId == -1){
            return "redirect:/";
        }
        model.addAttribute("posts", pm.findAll());
        model.addAttribute("authors", aum.findAll());
        model.addAttribute("comment", new Comment());
        model.addAttribute("postIdForCommentErrors", -1);
        model.addAttribute("userId", userId);
        return "homePage";
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////ADDING///////////////////////////////////////////////////////
    @GetMapping("/addPost")
    public String addPost(Model model){
        if(userId == -1){
            return "redirect:/";
        }
        model.addAttribute("post", new Post());
        model.addAttribute("authors", aum.findAll());
        model.addAttribute("userId", userId);
        return "addPost";
    }
    @PostMapping("/addPost")
    public String processAddingPost(@Valid Post post, Errors errors, int[] id, Model model, @RequestParam("file") MultipartFile file, String tag1, String tag2, String tag3, String tag4){
        if(userId == -1){
            return "redirect:/";
        }
        if(errors.hasErrors()){
            model.addAttribute("authors", aum.findAll());
            model.addAttribute("userId", userId);
            return "addPost";
        }
        List<Integer> authorId = new ArrayList<>();
        authorId.add(userId);
        if(id != null){
            for(int i: id){
                authorId.add(i);
            }
        }
        List<Integer> tagId = tm.getTagsId(tag1, tag2, tag3, tag4);
        pm.addPost(post, authorId, aum, tagId, tm, file, sm, am);
        return "redirect:/homePage";
    }

    @PostMapping("/addComment/{id}")
    public String addComment(@Valid Comment comment, Errors errors, Model model, @PathVariable int id){
        if(userId == -1){
            return "redirect:/";
        }
        if(!pm.checkById(id)){
            return "redirect:/error/This post does not exist!";
        }
        if(errors.hasErrors()){
            model.addAttribute("posts", pm.findAll());
            model.addAttribute("authors", aum.findAll());
            model.addAttribute("postIdForCommentErrors", id);
            model.addAttribute("userId", userId);
            return "homePage";
        }
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setComment_content(comment.getComment_content());
        commentDTO.setAuthor_id(userId);
        commentDTO.setPost_id(id);
        cm.addComment(commentDTO, pm, aum);
        return "redirect:/postPage/" + id;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////DELETING//////////////////
    @GetMapping("/deletePostConfirm/{id}")
    public String deletePostConfirm(Model model, @PathVariable int id){
        if(userId == -1){
            return "redirect:/";
        }
        if(!pm.checkById(id)){
            return "redirect:/error/This post does not exist!";
        }
        if(userId != 1 && !pm.checkIfAuthor(userId, id)){
            return "redirect:/error/You dont have permissions to delete this post!";
        }
        model.addAttribute("post", pm.findById(id));
        model.addAttribute("authors", aum.findAll());;
        return "deletePostConfirm";
    }
    @GetMapping("/deletePost/{id}")
    public String deletePost(@PathVariable int id){
        if(userId == -1){
            return "redirect:/";
        }
        if(!pm.checkById(id)){
            return "redirect:/error/This post does not exist!";
        }
        if(userId != 1 && !pm.checkIfAuthor(userId, id)){
            return "redirect:/error/You dont have permissions to delete this post!";
        }
        pm.deleteById(id);
        return "redirect:/homePage";
    }

    @GetMapping("/deleteCommentConfirm/{id}")
    public String deleteCommentConfirm(Model model, @PathVariable int id){
        if(userId == -1){
            return "redirect:/";
        }
        if(!cm.checkById(id)){
            return "redirect:/error/This comment does not exist!";
        }
        if(userId != 1 && !cm.checkIfAuthor(userId, id, aum)){
            return "redirect:/error/You dont have permissions to delete this comment!";
        }
        model.addAttribute("post", pm.findByComment(id));
        model.addAttribute("authors", aum.findAll());
        model.addAttribute("comment", cm.findById(id));
        return "deleteCommentConfirm";
    }

    @GetMapping("/deleteComment/{id}")
    public String deleteComment(@PathVariable int id){
        if(userId == -1){
            return "redirect:/";
        }
        if(!cm.checkById(id)){
            return "redirect:/error/This comment does not exist!";
        }
        if(userId != 1 && !cm.checkIfAuthor(userId, id, aum)){
            return "redirect:/error/You dont have permissions to delete this comment!";
        }
        int pid = pm.findByComment(id).getId();
        cm.deleteById(id);
        return "redirect:/postPage/" + pid;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////EDITING////////////////
    @GetMapping("/editPost/{id}")
    public String editPost(Model model, @PathVariable int id){
        if(userId == -1){
            return "redirect:/";
        }
        if(!pm.checkById(id)){
            return "redirect:/error/This post does not exist!";
        }
        if(userId != 1 && !pm.checkIfAuthor(userId, id)){
            return "redirect:/error/You dont have permissions to edit this post!";
        }
        model.addAttribute("post", pm.findById(id));
        model.addAttribute("authors", aum.findAll());
        model.addAttribute("userId", userId);
        return "editPost";
    }
    @PostMapping("/editPost/{id}")
    public String processEditPost(@Valid Post post, Errors errors, int[] idA, Model model, @PathVariable int id,  String tag1, String tag2, String tag3, String tag4){
        if(userId == -1){
            return "redirect:/";
        }
        if(!pm.checkById(id)){
            return "redirect:/error/This post does not exist!";
        }
        if(userId != 1 && !pm.checkIfAuthor(userId, id)){
            return "redirect:/error/You dont have permissions to edit this post!";
        }
        if(errors.hasErrors()){
            model.addAttribute("post", pm.findById(id));
            model.addAttribute("authors", aum.findAll());
            model.addAttribute("userId", userId);
            return "editPost";
        }
        List<Integer> authorId = new ArrayList<>();
        authorId.add(userId);
        if(idA != null){
            for(int i: idA){
                authorId.add(i);
            }
        }
        List<Integer> tagId = tm.getTagsId(tag1, tag2, tag3, tag4);
        pm.editPost(post, id, authorId, aum, tagId, tm);
        return "redirect:/postPage/" + id;
    }

    @GetMapping("/editComment/{id}")
    public String editComment(Model model, @PathVariable int id){
        if(userId == -1){
            return "redirect:/";
        }
        if(!cm.checkById(id)){
            return "redirect:/error/This comment does not exist!";
        }
        if(userId != 1 && !cm.checkIfAuthor(userId, id, aum)){
            return "redirect:/error/You dont have permissions to edit this comment!";
        }
        model.addAttribute("post", pm.findByComment(id));
        model.addAttribute("authors", aum.findAll());
        model.addAttribute("comment", cm.findById(id));
        return "editComment";
    }
    @PostMapping("/editComment/{id}")
    public String processEditComment(@Valid Comment comment, Errors errors, Model model, @PathVariable int id){
        if(userId == -1){
            return "redirect:/";
        }
        if(!cm.checkById(id)){
            return "redirect:/error/This comment does not exist!";
        }
        if(userId != 1 && !cm.checkIfAuthor(userId, id, aum)){
            return "redirect:/error/You dont have permissions to edit this comment!";
        }
        if(errors.hasErrors()){
            model.addAttribute("post", pm.findByComment(id));
            model.addAttribute("authors", aum.findAll());
            return "editComment";
        }
        cm.editComment(comment, id);
        return "redirect:/postPage/" + pm.findByComment(id).getId();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////SEARCHING////////////////////////////
    @PostMapping("/searchPost")
    public String searchPost(Model model, String type, String pattern){
        if(userId == -1){
            return "redirect:/";
        }
        if(pattern.length() == 0){
            return "redirect:/homePage";
        }
        if(type.equals("content")){
            if(pm.getPostsByContent(pattern).isEmpty()){
                return "redirect:/error/No matches found!";
            }
            model.addAttribute("posts", pm.getPostsByContent(pattern));
        }

        if(type.equals("author")){
            if(pm.getPostsByAuthors(aum.getAuthorsByUsername(pattern)).isEmpty()){
                return "redirect:/error/No matches found!";
            }
            model.addAttribute("posts", pm.getPostsByAuthors(aum.getAuthorsByUsername(pattern)));
        }
        if(type.equals("tags")){
            if(pm.getPostsByTags(tm.getTagsByTitle(pattern)).isEmpty()){
                return "redirect:/error/No matches found!";
            }
            model.addAttribute("posts", pm.getPostsByTags(tm.getTagsByTitle(pattern)));
        }
        model.addAttribute("authors", aum.findAll());
        model.addAttribute("comment", new Comment());
        model.addAttribute("postIdForCommentErrors", -1);
        model.addAttribute("userId", userId);
        return "homePage";
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////SHOWING/////////////////
    @GetMapping("/postPage/{id}")
    public String postPage(Model model, @PathVariable int id){
        if(userId == -1){
            return "redirect:/";
        }
        if(!pm.checkById(id)){
            return "redirect:/error/This post does not exist!";
        }
        model.addAttribute("post", pm.findById(id));
        model.addAttribute("authors", aum.findAll());
        model.addAttribute("comment", new Comment());
        model.addAttribute("postIdForCommentErrors", -1);
        model.addAttribute("userId", userId);
        return "postPage";
    }

    @GetMapping("/authorPage/{id}")
    public String authorPage(Model model, @PathVariable int id){
        if(userId == -1){
            return "redirect:/";
        }
        if(!aum.checkById(id)){
            return "redirect:/error/This author does not exist!";
        }
        List<Author> authors = new ArrayList<>();
        authors.add(aum.findById(id));
        if(pm.getPostsByAuthors(authors).isEmpty()){
            return "redirect:/error/This author have no posts!";
        }
        model.addAttribute("author", aum.findById(id));
        model.addAttribute("posts", pm.getPostsByAuthors(authors));
        model.addAttribute("authors", aum.findAll());
        model.addAttribute("i", pm.getPostsByAuthors(authors).size());
        model.addAttribute("comment", new Comment());
        model.addAttribute("postIdForCommentErrors", -1);
        model.addAttribute("userId", userId);
        model.addAttribute("postsC", pm.getPostsByComments(aum.findById(id).getComments()));
        model.addAttribute("j", pm.getPostsByComments(aum.findById(id).getComments()).size());
        return "authorPage";
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////HANDLING ERRORS/////////////////////////
    @GetMapping("/error/{message}")
    public String errorMessage(Model model, @PathVariable String message){
        model.addAttribute("message", message);
        return "errorMessage";
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////HANDLING FILES///////////////////////
    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename){
        Resource file = sm.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/addAttachment/{id}")
    public String addAttachment(@PathVariable int id, @RequestParam("file") MultipartFile file){
        if(userId == -1){
            return "redirect:/";
        }
        if(!pm.checkById(id)){
            return "redirect:/error/This post does not exist!";
        }
        if(!file.isEmpty()){
            sm.store(file);
            AttachmentDTO attachment = new AttachmentDTO();
            attachment.setFilename(file.getOriginalFilename());
            attachment.setPost_id(id);
            am.addAttachment(attachment, pm);
        }
        return "redirect:/editPost/" + id;
    }

    @GetMapping("/deleteAttachment/{id}/{aid}")
    public String deleteAttachment(@PathVariable int id, @PathVariable int aid){
        if(userId == -1){
            return "redirect:/";
        }
        if(!pm.checkById(id)){
            return "redirect:/error/This post does not exist!";
        }
        am.deleteById(aid);
        return "redirect:/editPost/" + id;
    }
}
