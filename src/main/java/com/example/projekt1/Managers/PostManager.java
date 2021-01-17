package com.example.projekt1.Managers;

import com.example.projekt1.Interfaces.PostInterface;
import com.example.projekt1.Interfaces.PostInterfaceCustom;
import com.example.projekt1.Models.Author;
import com.example.projekt1.Models.Post;
import org.springframework.stereotype.Service;

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
    public void addPost(Post post, List<Integer> auhtorId, AuthorManager am, List<Integer> tagId, TagManager ti){
        Post postToSave = new Post();
        postToSave.setId(post.getId());
        postToSave.setPost_content(post.getPost_content());
        for(int id: auhtorId){
            postToSave.getAuthors().add(am.findById(id));
        }
        for(int id: tagId){
            postToSave.getTags().add(ti.findById(id));
        }
        pi.save(postToSave);
    }

    @Override
    public List<Post> findAll(){ return pi.findAll(); }

    @Override
    public Post findById(int id){ return pi.findById(id); }

    @Override
    public void deletePost(int id){ pi.deleteById(id); }

    @Override
    public boolean checkPost(int id){
        List<Post> posts = pi.findAll();
        for(Post post: posts){
            if(post.getId() == id){
                return true;
            }
        }
        return false;
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
    public List<Post> getPostsByTags(String tagsInput){
        List<Post> postsToReturn = new ArrayList<>();
        /*String[] tags = tagsInput.split(" ");
        Pattern pattern;
        Matcher matcher;
        boolean matchFound;
        for(Post post: posts){
            for(String tag: tags){
                pattern = Pattern.compile(tag, Pattern.CASE_INSENSITIVE);
                matcher = pattern.matcher(post.getTags());
                matchFound = matcher.find();
                if(matchFound){
                    postsToReturn.add(post);
                }
            }
        }*/
        return postsToReturn;
    }


    @Override
    public void save(Post post){
        pi.save(post);
    }
}
