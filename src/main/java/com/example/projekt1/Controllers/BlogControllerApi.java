package com.example.projekt1.Controllers;

import com.example.projekt1.Interfaces.AuthorIterface;
import com.example.projekt1.Interfaces.CommentInterface;
import com.example.projekt1.Interfaces.PostInterface;
import com.example.projekt1.Managers.*;
import com.example.projekt1.Models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BlogControllerApi {
/*
    @Autowired
    AuthorManager aum;
    @Autowired
    PostManager pm;
    @Autowired
    CommentManager cm;
    @Autowired
    AttachmentManager am;
    @Autowired
    TagManager ti;


    @GetMapping("/api/authors")
    public List<Author> getAuthors() { return aum.findAll(); }

    @GetMapping("/api/authors/{username}")
    public List<Author> getAuthorsByUsername(@PathVariable String username) { return aum.findAllByUsername(username);}

    @PostMapping("/api/authors")
    public Author addAuthor(@RequestBody Author author){
        aum.addAuthor(author);
        return aum.findById(author.getId());
    }

    @GetMapping("/api/tags")
    public List<Tag> getTags() { return ti.findAll(); }

    @PostMapping("/api/tags")
    public Tag addTag(@RequestBody Tag tag){
        ti.addTag(tag);
        return ti.findById(tag.getId());
    }

    @GetMapping("/api/posts")
    public List<Post> getPosts() { return pm.findAll(); }

    @PostMapping("/api/posts")
    public Post addPost(@RequestBody Post post){
        ArrayList<Integer> authorId = new ArrayList<>();
        authorId.add(1);
        authorId.add(2);
        ArrayList<Integer> tagId = new ArrayList<>();
        tagId.add(1);
        tagId.add(2);
        pm.addPost(post, authorId, aum, tagId, ti);
        return pm.findById(post.getId());
    }

    @GetMapping("/api/comments")
    public List<Comment> getComments(){ return cm.findAll(); }

    @PostMapping("/api/comments")
    public Comment addComment(@RequestBody CommentDTO comment){
        cm.addComment(comment, pm, aum);
        return cm.findById(comment.getId());
    }

    @GetMapping("/api/attachments")
    public List<Attachment> getAttachments() {return am.findAll(); }

    @PostMapping("/api/attachments")
    public Attachment addAttachment(@RequestBody AttachmentDTO attachment){
        am.addAttachment(attachment, pm);
        return am.findById(attachment.getId());
    }

 */
}
