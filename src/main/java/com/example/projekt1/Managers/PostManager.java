package com.example.projekt1.Managers;

import com.example.projekt1.Interfaces.PostInterface;
import com.example.projekt1.Interfaces.PostInterfaceCustom;
import com.example.projekt1.Models.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PostManager implements PostInterfaceCustom {

    public PostInterface pi;

    public PostManager(PostInterface pi) throws FileNotFoundException {
        this.pi = pi;
    }


    @Override
    public void addPost(Post post, List<Integer> auhtorId, AuthorManager am, List<Integer> tagId, TagManager tm, MultipartFile file, StorageManager sm, AttachmentManager atm){
        Post postToSave = new Post();
        int id;
        if(pi.findAll().isEmpty()){
            id = 1;
        }
        else{
            id = pi.findAll().get(0).getId();
            for(Post postId: pi.findAll()){
                if(postId.getId() > id){
                    id = postId.getId();
                }
            }
            id++;
        }
        postToSave.setId(id);
        postToSave.setPost_content(post.getPost_content());
        for(int id2: auhtorId){
            postToSave.getAuthors().add(am.findById(id2));
        }
        for(int id2: tagId){
            postToSave.getTags().add(tm.findById(id2));
        }
        pi.save(postToSave);
        if(!file.isEmpty()){
            sm.store(file);
            AttachmentDTO attachment = new AttachmentDTO();
            attachment.setFilename(file.getOriginalFilename());
            attachment.setPost_id(postToSave.getId());
            atm.addAttachment(attachment, this);
        }
    }

    @Override
    public void editPost(Post post, int id, List<Integer> authorId, AuthorManager am, List<Integer> tagId, TagManager tm){
        Post postToSave = pi.findById(id);
        postToSave.setPost_content(post.getPost_content());
        postToSave.getAuthors().clear();
        for(int id2: authorId){
            postToSave.getAuthors().add(am.findById(id2));
        }
        postToSave.getTags().clear();
        for(int id2: tagId){
            postToSave.getTags().add(tm.findById(id2));
        }
        pi.save(postToSave);
    }

    @Override
    public List<Post> findAll(){ return pi.findAll(); }

    @Override
    public Post findById(int id){ return pi.findById(id); }

    @Override
    public void deleteById(int id){ pi.deleteById(id); }

    @Override
    public boolean checkById(int id){
        Post post = pi.findById(id);
        if(post == null){
            return false;
        }
        return true;
    }

    @Override
    public List<Post> getPostsByContent(String contentInput){
        List<Post> posts = pi.findAll();
        List<Post> postsToReturn = new ArrayList<>();
        String[] contents = contentInput.split(" ");
        Pattern pattern;
        Matcher matcher;
        boolean matchFound;
        for(Post post: posts){
            for(String content: contents){
                pattern = Pattern.compile(content, Pattern.CASE_INSENSITIVE);
                matcher = pattern.matcher(post.getPost_content());
                matchFound = matcher.find();
                if(matchFound){
                    postsToReturn.add(post);
                }
            }
        }
        return postsToReturn;
    }

    @Override
    public List<Post> getPostsByAuthors(List<Author> authors){
        List<Post> postsToReturn = new ArrayList<>();
        List<Post> posts = pi.findAll();
        for(Post post: posts){
            boolean flag = false;
            for(Author author: authors){
                for(Author authorP: post.getAuthors()){
                    if(authorP.equals(author)){
                        flag = true;
                    }
                }
            }
            if(flag){
                postsToReturn.add(post);
            }
        }
        return postsToReturn;
    }

    @Override
    public List<Post> getPostsByTags(List<Tag> tags){
        List<Post> postsToReturn = new ArrayList<>();
        List<Post> posts = pi.findAll();
        for(Post post: posts){
            boolean flag = false;
            for(Tag tag: tags){
                for(Tag tagP: post.getTags()){
                    if(tagP.equals(tag)){
                        flag=true;
                    }
                }
            }
            if(flag){
                postsToReturn.add(post);
            }
        }
        return postsToReturn;
    }

    @Override
    public List<Post> getPostsByComments(List<Comment> comments){
        List<Post> postsToReturn = new ArrayList<>();
        for(Comment comment: comments){
            postsToReturn.add(findByComment(comment.getId()));
        }
        return postsToReturn;
    }


    @Override
    public void save(Post post){
        pi.save(post);
    }

    @Override
    public boolean checkIfAuthor(int aid, int pid){
        Post post = pi.findById(pid);
        for(Author author: post.getAuthors()){
            if(author.getId() == aid){
                return true;
            }
        }
        return false;
    }

    @Override
    public Post findByComment(int id){
        for(Post post: pi.findAll()){
            for(Comment comment: post.getComments()){
                if(comment.getId() == id){
                    return post;
                }
            }
        }
        return null;
    }


}
