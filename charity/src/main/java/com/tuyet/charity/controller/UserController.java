package com.tuyet.charity.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.tuyet.charity.pojo.User;
import com.tuyet.charity.pojo.UserForm;
import com.tuyet.charity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin
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
    public ResponseEntity<Object> createUser(@ModelAttribute UserForm user){
        try {
            //validate user
//            if(result.hasErrors()){
//                Map<String, String> errors = new HashMap<>();
//                result.getAllErrors().forEach((error) -> {
//                    String fieldName = ((FieldError) error).getField();
//                    String errorMessage = error.getDefaultMessage();
//                    errors.put(fieldName, errorMessage);
//                });
//                return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
//            }
            // username, pass ko null
            if(user.getPassword() == null || user.getUsername() == null){
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            //kt khong trung username
            User createdUsername = userDetailsService.getUserByUsername(user.getUsername());
            if(createdUsername != null){
                Map<String, String> msg = new HashMap<>();
                msg.put("error","this username existed");
                return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
            }

            //tao user
            User usr = new User();
            usr.setUsername(user.getUsername());
            usr.setPassword(user.getPassword());
            usr.setEmail(user.getEmail());

            if(user.getAvatar() != null && !user.getAvatar().isEmpty()) {
                Map avatarLink = this.cloudinary.uploader().upload(user.getAvatar().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
//                usr = new User(user.getUsername(), user.getPassword(), user.getEmail(),
//                        (String) avatarLink.get("secure_url"));
                usr.setAvatar((String) avatarLink.get("secure_url"));
            }
            return new ResponseEntity<>(userDetailsService.addUser(usr), HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update-user/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable Integer id, @ModelAttribute UserForm user,
                                             BindingResult result, OAuth2Authentication auth){
        //kt lieu user co phai la admin va owner cua tai khoan
        User ownerProfile = userDetailsService.getUserByUsername(auth.getName());
        if(!auth.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN")) && ownerProfile.getUserId() != id){
            Map<String, String> msg = new HashMap<>();
            msg.put("error","this username does not have permission to update user profile");
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }

        //kt username da ton tai hay chua
        List<User> createdUsername = userDetailsService.getUsers(user.getUsername());
        if(createdUsername.size() == 1 && createdUsername.get(0).getUserId() != id){
            Map<String, String> msg = new HashMap<>();
            msg.put("error","this username existed");
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }

        try {
            User createdUser = userDetailsService.getUserById(id);

            //update user
            createdUser.setUsername(user.getUsername());
            createdUser.setPassword(user.getPassword());
            createdUser.setActive(user.isActive());
            createdUser.setEmail(user.getEmail());
            createdUser.setRole(user.getRole());

            //upload anh moi cua user len server va set lai anh
            Map avatarLink = this.cloudinary.uploader().upload(user.getAvatar().getBytes(),
                    ObjectUtils.asMap("resource_type","auto"));
            createdUser.setAvatar( (String) avatarLink.get("secure_url"));

            return new ResponseEntity<>(userDetailsService.addUser(createdUser), HttpStatus.CREATED);
        }
        catch (EntityNotFoundException ex){
            //user not exist
            //neu user khong ton tai => loi
            Map<String, String> msg = new HashMap<>();
            msg.put("error","this user does not exist");
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }
        catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/current-user")
    public User getAllUser(OAuth2Authentication auth){
//        AbstractAuthenticationToken auth = (AbstractAuthenticationToken)
//                SecurityContextHolder.getContext().getAuthentication();
//        //username khong duoc trung
        User currentUser = userDetailsService.getCurrentUser(auth.getName());
        return currentUser;
    }

    //validation object va tra response thich hop cho nhung phuong thuc co @Valid
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getAllErrors().forEach((error) -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        });
//        return errors;
//    }
}
