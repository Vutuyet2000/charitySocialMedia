package com.tuyet.charity.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.tuyet.charity.pojo.*;
import com.tuyet.charity.service.PostAuctionService;
import com.tuyet.charity.service.PostService;
import com.tuyet.charity.service.UserService;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private UserService userDetailsService;

    @Autowired
    private PostAuctionService postAuctionService;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private PostPagination postPagination;

    @Autowired
    private PostAuction postAuction;

    @Autowired
    private Post post;

    @GetMapping("/posts")
    public PostPagination getAllPosts(@RequestParam(value = "page", defaultValue = "1") int currentPage){
        Page<Post> page = postService.getAllPosts(currentPage);
        List<Post> list = page.getContent();
        int count = postService.getAmountAllPosts(); //so luong posts trong db

        postPagination.setCount(count);
        postPagination.setListPost(list);

        return postPagination;
    }

    @PostMapping(value = "/post", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> createPost(@Valid @ModelAttribute PostForm postReq,
                                             OAuth2Authentication auth,
                                             BindingResult result){
        //validate post?? tai sao khong return lai error???
        if(result.hasErrors()){
            Map<String, String> errors = new HashMap<>();
            result.getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        //tao post
        post.setContent(postReq.getContent());
        post.setCreatedDate(postReq.getCreatedDate());

        post.setHashTag(postReq.getHashTag());
        //upload image
        try {
            Map avatarLink = this.cloudinary.uploader().upload(postReq.getImage().getBytes(),
                    ObjectUtils.asMap("resource_type","auto"));
            post.setImage( (String) avatarLink.get("secure_url"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //lay user tao post
        User ownerPost = userDetailsService.getCurrentUser(auth.getName());
        post.setOwnerPost(ownerPost);
        //tao post
        Post createdPost = postService.createPost(post);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    //tao post dau gia
    @PostMapping(value = "/post/create-post-auction", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.APPLICATION_JSON_VALUE})
    public Post createPostAuction(@Valid @ModelAttribute PostForm postReq, OAuth2Authentication auth){
        //create post
        post.setContent(postReq.getContent());
        post.setCreatedDate(postReq.getCreatedDate());

        post.setHashTag(postReq.getHashTag());
        //upload image
        try {
            Map avatarLink = this.cloudinary.uploader().upload(postReq.getImage().getBytes(),
                    ObjectUtils.asMap("resource_type","auto"));
            post.setImage( (String) avatarLink.get("secure_url"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //lay user tao post
        User ownerPost = userDetailsService.getCurrentUser(auth.getName());
        post.setOwnerPost(ownerPost);

        //tao post Auction
        postAuction.setPost(post);
        postAuctionService.createPostAuction(postAuction);

        return post;
    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<Object> deletePost(@PathVariable(value = "postId") Integer postId, OAuth2Authentication auth){
        //kt lieu user co phai la admin va owner cua tai khoan
        Post createdPost = postService.getPost(postId);
        if(!auth.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN")) &&
                !createdPost.getOwnerPost().getUsername().equals(auth.getName())){
            Map<String, String> msg = new HashMap<>();
            msg.put("error","this username does not have permission to update user profile");
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }
        //delete post
        try {
            postService.deletePost(postId);
        }
        catch (NoSuchElementException ex){
            Map<String, String> msg = new HashMap<>();
            msg.put("error","this post does not exist");
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //PATCH => update content, hashtag,...
    @PatchMapping(value = "/post/{postId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> partialUpdatePost(@PathVariable(value = "postId") Integer postId,
                                                    @ModelAttribute PostForm update, OAuth2Authentication auth){
        try {
            Post createdPost = postService.getPost(postId);

            //kt lieu user co phai la admin va owner cua tai khoan
            if(!auth.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN")) &&
                    !createdPost.getOwnerPost().getUsername().equals(auth.getName())){
                Map<String, String> msg = new HashMap<>();
                msg.put("error","this username does not have permission to update user profile");
                return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
            }

            createdPost.setCreatedDate(update.getCreatedDate());
            //update content
            if(update.getContent()!=null) {
                createdPost.setContent(update.getContent());
            }
            //update hashtag
            if(update.getHashTag()!=null){
                createdPost.setHashTag(update.getHashTag());
            }
            //update image
            if(update.getImage()!=null){
                try {
                    Map avatarLink = this.cloudinary.uploader().upload(update.getImage().getBytes(),
                            ObjectUtils.asMap("resource_type","auto"));
                    createdPost.setImage( (String) avatarLink.get("secure_url"));
                } catch (IOException e) {
                    Map<String, String> msg = new HashMap<>();
                    msg.put("error","Upload image failed");
                    return new ResponseEntity<>(msg, HttpStatus.CONFLICT);
                }
            }
            postService.createPost(createdPost);
            return new ResponseEntity<>(createdPost, HttpStatus.OK);
        }
        catch (NoSuchElementException ex){
            Map<String, String> msg = new HashMap<>();
            msg.put("error","This post doesn not exist");
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }
    }

    //show comments
    @GetMapping("/post/{postId}/show-comments")
    public ResponseEntity<Object> showComments(@PathVariable(value = "postId") Integer postId){
        try{
            Post createdPost = postService.getPost(postId);
            return new ResponseEntity<>(createdPost, HttpStatus.OK);
        }
        catch(NoSuchElementException ex){
            Map<String, String> msg = new HashMap<>();
            msg.put("error","This post doesn not exist");
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }
    }

    //choose winner => inactive post auciton => send mail to winner
    @GetMapping("/post/{postId}/choose-winner")
    public ResponseEntity<Object> chooseWinner(@PathVariable(value = "postId") Integer postId,
                                    @RequestParam(value = "winner-id", defaultValue = "0") Integer winnerId,
                                    @RequestParam(value = "cost", defaultValue = "0") BigDecimal cost,
                                               OAuth2Authentication auth){
        try {
            Post createdPost = postService.getPost(postId);
            String winnerEmail = userDetailsService.getUserById(winnerId).getEmail();
            //check owner post AND owner post is not winner
            if(!auth.getName().equals(createdPost.getOwnerPost().getUsername())||
                    winnerId==createdPost.getOwnerPost().getUserId()){
                Map<String, String> msg = new HashMap<>();
                msg.put("error","Bad request");
                return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
            }
            //check if post is post auction
            if(createdPost.getPostAuction()==null){
                Map<String, String> msg = new HashMap<>();
                msg.put("error","This post is not post auction");
                return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
            }
            //inactive post auction
            createdPost.getPostAuction().setActive(false);
            createdPost.getPostAuction().setWinner(winnerId);
            createdPost.getPostAuction().setCost(cost);
            // Create a MIME MailMessage
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,"utf-8");
            helper.setTo(winnerEmail);
            helper.setSubject("THẮNG ĐẤU GIÁ");
            String htmlMsg ="<html>" +
                    "<body>" +
                    "<p>Chúc mừng bạn đã chiến thắng trong bài đấu giá của người dùng <strong>"+createdPost.getOwnerPost().getUsername()+"</strong></p>" +
                    "<p>Với số tiền: "+cost+"</p><p>Bạn có thể quẹt <strong>mã QR</strong> dưới đây để trả tiền</p>" +
                    "<img src=\"https://res.cloudinary.com/diacstfxz/image/upload/v1630305753/momo_ijkeg7.png\" style=\"width:40%\"/>" +
                    "</body>" +
                    "</html>";
            message.setContent(htmlMsg, "text/html; charset=UTF-8");
            // Send Message
            emailSender.send(message);
            //save
            return new ResponseEntity<>(postService.createPost(createdPost),HttpStatus.OK);
        } catch (MessagingException e) {
            Map<String, String> msg = new HashMap<>();
            msg.put("error","Send email failed");
            return new ResponseEntity<>(msg, HttpStatus.CONFLICT);
        } catch (NoSuchElementException ex){
            Map<String, String> msg = new HashMap<>();
            msg.put("error","This post doesn not exist");
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        } catch (EntityNotFoundException e){
            Map<String, String> msg = new HashMap<>();
            msg.put("error","This user doesn not exist");
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }
    }

    //active auction for post
    //inactive auction

}
