package com.example.projekt1.Interfaces;

import com.example.projekt1.Managers.AttachmentManager;
import com.example.projekt1.Managers.AuthorManager;
import com.example.projekt1.Managers.StorageManager;
import com.example.projekt1.Managers.TagManager;
import com.example.projekt1.Models.Author;
import com.example.projekt1.Models.Comment;
import com.example.projekt1.Models.Post;
import com.example.projekt1.Models.Tag;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostInterfaceCustom {

    void addPost(Post post, List<Integer> authorId, AuthorManager am, List<Integer> tagId, TagManager ti, MultipartFile file, StorageManager sm, AttachmentManager atm);

    List<Post> findAll();

    Post findById(int id);

    void deleteById(int id);

    boolean checkById(int id);

    List<Post> getPostsByContent(String pattern);

    List<Post> getPostsByAuthors(List<Author> authors);

    List<Post> getPostsByTags(List<Tag> tags);

    List<Post> getPostsByComments(List<Comment> comments);

    void save(Post post);

    boolean checkIfAuthor(int aid, int pid);

    Post findByComment(int id);

    void editPost(Post post, int id, List<Integer> authorId, AuthorManager am, List<Integer> tagId, TagManager tm);
}
