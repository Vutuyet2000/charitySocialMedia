package com.tuyet.charity.controller;

import com.tuyet.charity.pojo.Comment;
import com.tuyet.charity.pojo.Notification;
import com.tuyet.charity.pojo.Post;
import com.tuyet.charity.pojo.User;
import com.tuyet.charity.service.CommentService;
import com.tuyet.charity.service.NotificationService;
import com.tuyet.charity.service.PostService;
import com.tuyet.charity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userDetailsService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private Notification notification;

//    @GetMapping("/comments")
//    public List<Comment> getAllCommentsPost(@RequestParam(value = "post-id") Integer postId){
//        return commentService.getAllCommentsPost(postId);
//    }

    @RequestMapping(method = {RequestMethod.PATCH, RequestMethod.PUT}, value = "/comments/{commentId}")
    public ResponseEntity<Object> updateCommentPost(@PathVariable Integer commentId, @RequestBody Comment commentReq, OAuth2Authentication auth){
        Comment comment = commentService.getCommentById(commentId);
        //kt owner comment
        if(!auth.getName().equals(comment.getUser().getUsername())){
            Map<String, String> msg = new HashMap<>();
            msg.put("error","This person has no permission");
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }
        //update comment
        comment.setCreatedDate(commentReq.getCreatedDate());
        comment.setContent(commentReq.getContent());
        return new ResponseEntity<>(comment,HttpStatus.OK);
    }

    @PostMapping("comments/")
    public ResponseEntity<Object> createComment(@RequestParam(value = "post-id") Integer postId,
                                                @Valid @RequestBody Comment comment, OAuth2Authentication auth){
        Post createdPost = postService.getPost(postId);
        User owner = userDetailsService.getUserByUsername(auth.getName());
        //tao comment
        comment.setPost(createdPost);
        comment.setUser(owner);
        Comment createdComment=commentService.createComment(comment);

        //tao notification
        if(!auth.getName().equals(createdPost.getOwnerPost().getUsername())){
            notification.setPost(createdPost);
            notification.setUser(createdPost.getOwnerPost());
            notification.setType(2);
            notificationService.createNotification(notification);
        }

        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @DeleteMapping("/comments/{commentId}")
    public void deleteComment(@PathVariable Integer commentId){
        //bat comment khong ton tai
        Comment createdComment = commentService.getCommentById(commentId);
        commentService.deleteComment(createdComment);
    }

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
