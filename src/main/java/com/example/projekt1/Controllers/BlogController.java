package com.example.projekt1.Controllers;

import com.example.projekt1.Managers.*;
import com.example.projekt1.Models.AttachmentDTO;
import com.example.projekt1.Models.Author;
import com.example.projekt1.Models.Comment;
import com.example.projekt1.Models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String processAddingPost(@Valid Post post, Errors errors, int[] id, Model model, @RequestParam("file") MultipartFile file, String tags) throws IOException{
        if(userId == -1){
            return "redirect:/";
        }
        if(errors.hasErrors()){
            model.addAttribute("authors", aum.findAll());
            model.addAttribute("userId", userId);
            return "addPost";
        }
        if(!file.isEmpty()){
            sm.store(file);
            AttachmentDTO attachment = new AttachmentDTO();
            attachment.setFilename(file.getOriginalFilename());
            attachment.setPost_id(post.getId());
            am.addAttachment(attachment, pm);
        }
        List<Integer> authorId = new ArrayList<>();
        authorId.add(userId);
        for(int i: id){
            authorId.add(i);
        }
        List<Integer> tagId = tm.getTagsId(tags);
        pm.addPost(post, authorId, aum, tagId, tm);
        return "redirect:/homePage";
    }

    /*

    @PostMapping("/addComment/{id}")
    public String addComment(@Valid Comment comment, Errors errors, Model model, @PathVariable int id) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException{
        if(!pm.checkPost(id)){
            return "redirect:/error/This post does not exist!";
        }
        if(errors.hasErrors()){
            model.addAttribute("posts", pm.getAllPosts());
            model.addAttribute("pa", pam.getAllPostsAuthors());
            model.addAttribute("authors", aum.getAllAuthors());
            model.addAttribute("comments", cm.getAllComments());
            model.addAttribute("postIdForCommentErrors", id);
            model.addAttribute("attachments", atm.getAllAttachments());
            return "homePage";
        }
        cid++;
        comment.setId(cid);
        comment.setId_post(id);
        cm.addComment(comment);
        cm.save();
        return "redirect:/";
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////DELETING//////////////////
    @GetMapping("/deletePostConfirm/{id}")
    public String deletePostConfirm(Model model, @PathVariable int id){
        if(!pm.checkPost(id)){
            return "redirect:/error/This post does not exist!";
        }
        model.addAttribute("post", pm.getPostById(id));
        model.addAttribute("pa", pam.getAllPostsAuthors());
        model.addAttribute("authors", aum.getAllAuthors());
        model.addAttribute("comments", cm.getAllComments());
        model.addAttribute("attachments", atm.getAllAttachments());
        return "deletePostConfirm";
    }
    @RequestMapping(value = "/deletePost/{id}", method = RequestMethod.GET)
    public String deletePost(@PathVariable int id) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException{
        if(!pm.checkPost(id)){
            return "redirect:/error/This post does not exist!";
        }
        pm.deletePost(id);
        pam.deleteByPostId(id);
        cm.deleteCommentsByPostId(id);
        atm.deleteAttachments(id);
        pm.save();
        pam.save();
        cm.save();
        atm.save();
        return "redirect:/";
    }

    @GetMapping("/deleteCommentConfirm/{id}")
    public String deleteCommentConfirm(Model model, @PathVariable int id){
        if(!cm.checkComment(id)){
            return "redirect:/error/This comment does not exist!";
        }
        model.addAttribute("post", pm.getPostById(cm.getCommentById(id).getId_post()));
        model.addAttribute("pa", pam.getAllPostsAuthors());
        model.addAttribute("authors", aum.getAllAuthors());
        model.addAttribute("comments", cm.getAllComments());
        model.addAttribute("comment", cm.getCommentById(id));
        model.addAttribute("attachments", atm.getAllAttachments());
        return "deleteCommentConfirm";
    }
    @GetMapping("/deleteComment/{id}")
    public String deleteComment(Model model, @PathVariable int id) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException{
        if(!cm.checkComment(id)){
            return "redirect:/error/This comment does not exist!";
        }
        cm.deleteComment(id);
        cm.save();
        return "redirect:/";
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////EDITING////////////////
    @GetMapping("/editPost/{id}")
    public String editPost(Model model, @PathVariable int id){
        if(!pm.checkPost(id)){
            return "redirect:/error/This post does not exist!";
        }
        model.addAttribute("post", pm.getPostById(id));
        model.addAttribute("pa", pam.getAllPostsAuthors());
        model.addAttribute("authors", aum.getAllAuthors());
        model.addAttribute("comments", cm.getAllComments());
        model.addAttribute("attachments", atm.getAllAttachments());
        return "editPost";
    }
    @PostMapping("/editPost/{id}")
    public String processEditPost(@Valid Post post, Errors errors, int[] idA, Model model, @PathVariable int id) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException{
        if(!pm.checkPost(id)){
            return "redirect:/error/This post does not exist!";
        }
        if(errors.hasErrors()){
            model.addAttribute("pa", pam.getAllPostsAuthors());
            model.addAttribute("authors", aum.getAllAuthors());
            model.addAttribute("comments", cm.getAllComments());
            model.addAttribute("attachments", atm.getAllAttachments());
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
        pm.save();
        pam.save();
        atm.save();
        return "redirect:/";
    }

    @GetMapping("/editComment/{id}")
    public String editComment(Model model, @PathVariable int id){
        if(!cm.checkComment(id)){
            return "redirect:/error/This comment does not exist!";
        }
        model.addAttribute("post", pm.getPostById(cm.getCommentById(id).getId_post()));
        model.addAttribute("pa", pam.getAllPostsAuthors());
        model.addAttribute("authors", aum.getAllAuthors());
        model.addAttribute("comments", cm.getAllComments());
        model.addAttribute("comment", cm.getCommentById(id));
        model.addAttribute("attachments", atm.getAllAttachments());
        return "editComment";
    }
    @PostMapping("/editComment/{id}")
    public String processEditComment(@Valid Comment comment, Errors errors, Model model, @PathVariable int id) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException{
        if(!cm.checkComment(id)){
            return "redirect:/error/This comment does not exist!";
        }
        if(errors.hasErrors()){
            model.addAttribute("post", pm.getPostById(cm.getCommentById(id).getId_post()));
            model.addAttribute("pa", pam.getAllPostsAuthors());
            model.addAttribute("authors", aum.getAllAuthors());
            model.addAttribute("comments", cm.getAllComments());
            model.addAttribute("attachments", atm.getAllAttachments());
            return "editComment";
        }
        Comment commentToEdit = cm.getCommentById(id);
        commentToEdit.setComment_content(comment.getComment_content());
        commentToEdit.setUsername(comment.getUsername());
        cm.save();
        return "redirect:/";
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////SEARCHING////////////////////////////
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
        model.addAttribute("postIdForCommentErrors", -1);
        model.addAttribute("comment", new Comment());
        model.addAttribute("attachments", atm.getAllAttachments());
        return "homePage";
    }

    @PostMapping("/searchUser")
    public String searchUser(Model model, String username){
        if(username.length() == 0){
            return "redirect:/";
        }
        model.addAttribute("comments", cm.getCommentsByUsername(username));
        model.addAttribute("i", cm.getCommentsByUsername(username).size());
        return "searchUser";
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////SHOWING/////////////////
    @GetMapping("/userPage/{username}")
    public String userPage(Model model, @PathVariable String username){
        if(!cm.checkCommentsByUsername(username)){
            return "redirect:/error/This user does not exist!";
        }
        model.addAttribute("i", cm.getCommentsByUsername(username).size());
        model.addAttribute("posts", pm.getAllPosts());
        model.addAttribute("comments", cm.getCommentsByUsername(username));
        model.addAttribute("username", username);
        return "userPage";
    }

    @GetMapping("/postPage/{id}")
    public String postPage(Model model, @PathVariable int id){
        if(!pm.checkPost(id)){
            return "redirect:/error/This post does not exist!";
        }
        model.addAttribute("post", pm.getPostById(id));
        model.addAttribute("pa", pam.getAllPostsAuthors());
        model.addAttribute("authors", aum.getAllAuthors());
        model.addAttribute("comments", cm.getAllComments());
        model.addAttribute("attachments", atm.getAllAttachments());
        return "postPage";
    }

    @GetMapping("/authorPage/{id}")
    public String authorPage(Model model, @PathVariable int id){
        if(!aum.checkAuthor(id)){
            return "redirect:/error/This author does not exist!";
        }
        model.addAttribute("author", aum.getAuthorById(id));
        if(pam.getByAuthor(aum.getAuthorById(id)).isEmpty()){
            return "redirect:/error/This author have no posts!";
        }
        model.addAttribute("posts", pm.getPostsByAuthors(pam.getByAuthor(aum.getAuthorById(id))));
        model.addAttribute("comments", cm.getAllComments());
        model.addAttribute("pa", pam.getAllPostsAuthors());
        model.addAttribute("authors", aum.getAllAuthors());
        model.addAttribute("i", pm.getPostsByAuthors(pam.getByAuthor(aum.getAuthorById(id))).size());
        model.addAttribute("attachments", atm.getAllAttachments());
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
    public String addAttachment(@PathVariable int id, @RequestParam("file") MultipartFile file) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException{
        if(!pm.checkPost(id)){
            return "redirect:/error/This post does not exist!";
        }
        if(!file.isEmpty()){
            sm.store(file);
            Attachment attachment = new Attachment();
            attachment.setFilename(file.getOriginalFilename());
            attachment.setId_post(id);
            atm.addAttachment(attachment);
        }
        atm.save();
        return "redirect:/editPost/" + id;
    }

    @GetMapping("/deleteAttachment/{id}/{filename}")
    public String deleteAttachment(@PathVariable int id, @PathVariable String filename) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException{
        if(!pm.checkPost(id)){
            return "redirect:/error/This post does not exist!";
        }
        if(!filename.isEmpty()){
            atm.deleteAttachment(id, filename);
        }
        atm.save();
        return "redirect:/editPost/" + id;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////JSON////////////////////////////
    @GetMapping(value = "/post/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> postJson(@PathVariable int id){
        Post post = pm.getPostById(id);
        return new ResponseEntity<Object>(post, HttpStatus.OK);
    }

    @GetMapping(value = "/posts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> postsJson(@PathVariable int id){
        List<Post> posts = pm.getPostsByAuthors(pam.getByAuthor(aum.getAuthorById(id)));
        return new ResponseEntity<Object>(posts, HttpStatus.OK);
    }

     */
}
