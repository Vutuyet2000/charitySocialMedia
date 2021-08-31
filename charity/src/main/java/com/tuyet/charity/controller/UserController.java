package com.tuyet.charity.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.tuyet.charity.pojo.User;
import com.tuyet.charity.pojo.UserForm;
import com.tuyet.charity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private UserService userDetailsService;

    @Autowired
    private Cloudinary cloudinary;

//    @PostMapping("/sign-up")
//    public void createUser(@Valid @RequestBody User user){
//        userDetailsService.addUser(user);
//    }

    //dinh dang lai json tra ve cua modelAttribute
    //multipart user
    @PostMapping(value = "/sign-up", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.APPLICATION_JSON_VALUE})
    public void createUser(@Valid @ModelAttribute UserForm user){
        try {
            Map avatarLink = this.cloudinary.uploader().upload(user.getAvatar().getBytes(),
                    ObjectUtils.asMap("resource_type","auto"));
            User usr = new User(user.getUsername(), user.getPassword(), user.getEmail(),
                    (String) avatarLink.get("secure_url"));
            userDetailsService.addUser(usr);
        } catch (IOException e) {
            System.err.println("Upload image: " + e.getMessage());
        }
    }

    @GetMapping("/users/current-user")
    public User getAllUser(){
        AbstractAuthenticationToken auth = (AbstractAuthenticationToken)
                SecurityContextHolder.getContext().getAuthentication();
//        UserDetails details = (UserDetails) auth.getDetails();
        //username khong duoc trung
        User currentUser = userDetailsService.getCurrentUser(auth.getName());
//        return userDetailsService.getAllUsers();
        return currentUser;
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
