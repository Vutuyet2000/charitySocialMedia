package com.tuyet.charity.controller;

import com.tuyet.charity.pojo.Post;
import com.tuyet.charity.pojo.PostAuction;
import com.tuyet.charity.repository.PostCustomRepository;
import com.tuyet.charity.service.PostAuctionService;
import com.tuyet.charity.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private PostAuctionService postAuctionService;

    //lam get /post?page={pageNumber}
    @GetMapping("/post")
    public List<Post> getAllPosts(@RequestParam("page") int currentPage){
        Page<Post> page = postService.getAllPosts(currentPage);
        List<Post> list = page.getContent();
        return list;
    }

    @PostMapping("/post")
    public void createPost(@Valid @RequestBody Post post){
        postService.createPost(post);
    }

    //tao post dau gia
    @PostMapping("/create-post-auction")
    public void createPostAuction(@Valid @RequestBody Post post){
        PostAuction postAuction = new PostAuction();
        postAuction.setPost(post);
        postAuctionService.createPostAuction(postAuction);
    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable(value = "postId") Integer postId){
        try {
            postService.deletePost(postId);
        }
        catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //validation object va tra response thich hop cho nhung phuong thuc co @Valid
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
