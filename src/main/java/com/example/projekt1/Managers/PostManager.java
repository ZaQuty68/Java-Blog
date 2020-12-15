package com.example.projekt1.Managers;

import com.example.projekt1.Interfaces.PostInterface;
import com.example.projekt1.Models.Author;
import com.example.projekt1.Models.Post;
import com.example.projekt1.Models.Posts_Authors;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PostManager implements PostInterface {

    private static List<Post> posts;

    public PostManager() throws FileNotFoundException {
        Reader reader = new BufferedReader(new FileReader("src/main/java/com/example/projekt1/csv/Posts.csv"));
        CsvToBean<Post> csvReader = new CsvToBeanBuilder(reader)
                .withType(Post.class).withSeparator(',').withIgnoreQuotations(false)
                .withIgnoreLeadingWhiteSpace(true).build();
        posts = csvReader.parse();
    }

    @Override
    public void save() throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        try(Writer writer = Files.newBufferedWriter(Paths.get("src/main/java/com/example/projekt1/csv/Posts.csv"));){
            StatefulBeanToCsv<Post> beanToCsv = new StatefulBeanToCsvBuilder(writer).withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER).build();
            beanToCsv.write(posts);
        }
    }


    @Override
    public void addPost(Post post){
        posts.add(post);
    }

    @Override
    public List<Post> getAllPosts(){ return posts; }

    @Override
    public Post getPostById(int id){
        Post postToReturn = null;
        for (Post post: posts){
            if (post.getId() == id){
                postToReturn = post;
            }
        }
        return postToReturn;
    }

    @Override
    public void deletePost(int id){
        Post postToRemove = null;
        for (Post post: posts){
            if (post.getId() == id){
                postToRemove = post;
            }
        }
        if (postToRemove != null){
            posts.remove(postToRemove);
        }
    }

    @Override
    public boolean checkPost(int id){
        for (Post post: posts){
            if (post.getId() == id){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Post> getPostsByContent(String contentInput){
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
        String[] tags = tagsInput.split(" ");
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
        }
        return postsToReturn;
    }

    @Override
    public List<Post> getPostsByAuthors(List<Posts_Authors> pa){
        List<Post> postsToReturn = new ArrayList<>();
        for(Posts_Authors par: pa){
            for(Post post: posts){
                if(post.getId() == par.getId_post()){
                    postsToReturn.add(post);
                }
            }
        }
        return postsToReturn;
    }
}
