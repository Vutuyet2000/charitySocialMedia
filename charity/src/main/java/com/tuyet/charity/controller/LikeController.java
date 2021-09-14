package com.tuyet.charity.controller;

import com.fasterxml.classmate.AnnotationConfiguration;
import com.tuyet.charity.pojo.Like;
import com.tuyet.charity.pojo.Notification;
import com.tuyet.charity.pojo.Post;
import com.tuyet.charity.pojo.User;
import com.tuyet.charity.repository.LikeRepository;
import com.tuyet.charity.service.LikeService;
import com.tuyet.charity.service.NotificationService;
import com.tuyet.charity.service.PostService;
import com.tuyet.charity.service.UserService;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class LikeController {
    @Autowired
    private LikeService likeService;

    @Autowired
    private UserService userDetailsService;

    @Autowired
    private PostService postService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private Like like;

    @Autowired
    private Notification notification;

    @GetMapping("/likes")
    public List<Like> getAllLikesPost(@RequestParam("post-id") Integer postId){
        return likeService.getAllLikesPost(postId);
    }

    //tao like khi user like post
    @PostMapping("/likes")
    public ResponseEntity<Object> createPostLike(@RequestParam(value = "post-id", defaultValue = "0") Integer postId, OAuth2Authentication auth){
        //kt post id co duoc truyen
        if(postId == 0){
            Map<String, String> msg = new HashMap<>();
            msg.put("error","post id is required");
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }
        try{
            //lay user
            User liker = userDetailsService.getUserByUsername(auth.getName());
            Post post = postService.getPost(postId);
            //create new like
            like.setPost(post);
            like.setUser(liker);

            likeService.createLike(like);

            //tao notification, neu owner post like bai viet cua chinh minh thi khong tao notification
            if(!auth.getName().equals(post.getOwnerPost().getUsername())){
                notification.setPost(post);
                notification.setUser(post.getOwnerPost());
                notification.setType(1);
                notificationService.createNotification(notification);
            }

            return new ResponseEntity<>(null, HttpStatus.CREATED);
        }catch (EntityNotFoundException e){
            Map<String, String> msg = new HashMap<>();
            msg.put("error","this object does not exist");
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }
    }

    //delete like
    @DeleteMapping("/likes")
    public ResponseEntity<Object> deletePostLike(@RequestParam(value = "post-id") Integer postId
            , OAuth2Authentication auth){
        //lay like cua user id?
        Post createdPost = postService.getPost(postId);
        //tim like cua user
        for(Like ul : createdPost.getLikes()){
            if(ul.getUser().getUsername().equals(auth.getName())){
                like.setId(ul.getId());
                like.setUser(ul.getUser());
                like.setPost(ul.getPost());
            }
        }
        //kt lieu user co phai la admin va owner cua like
        if(!auth.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN")) &&
                like.getId()==null){
            Map<String, String> msg = new HashMap<>();
            msg.put("error","this username does not have permission to update user profile");
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }
        //xoa like
        likeService.deleteLike(createdPost, like.getId());

        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
