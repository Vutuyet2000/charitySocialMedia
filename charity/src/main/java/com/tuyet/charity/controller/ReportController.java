package com.tuyet.charity.controller;

import com.tuyet.charity.pojo.Report;
import com.tuyet.charity.pojo.ReportEnum;
import com.tuyet.charity.pojo.User;
import com.tuyet.charity.service.ReportService;
import com.tuyet.charity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ReportController {
    @Autowired
    private ReportService reportService;

    @Autowired
    private UserService userDetailsService;

    @Autowired
    private JavaMailSender emailSender;

    @GetMapping("/report")
    public List<Report> getAllReports(){
        return reportService.getAllReports();
    }

    @PostMapping("/report/{reportedUserId}")
    public ResponseEntity<Object> createReport(@Valid @RequestBody Report report, @PathVariable Integer reportedUserId,OAuth2Authentication auth){
        User reporter = userDetailsService.getUserByUsername(auth.getName());
        //reporterId != reportedUserId
        if(reporter.getUserId()==reportedUserId){
            Map<String, String> msg = new HashMap<>();
            msg.put("error","users cannot report themselves");
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }
        User reportedUser = userDetailsService.getUserById(reportedUserId);
        //kt reportedUser co ton tai hay khong
        if(reportedUser == null){
            Map<String, String> msg = new HashMap<>();
            msg.put("error","this user does not exist");
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }

        //tao report
        report.setReporter(reporter);
        report.setReportedUser(reportedUser);
        reportService.createReport(report);

        //gui mail cho admin
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("nhom9athttt@gmail.com");
        message.setSubject("BÁO CÁO USER VI PHẠM");
        String htmlMsg = "Người dùng " + reporter.getUsername() + " đã báo cáo Người dùng " + reportedUser.getUsername() +
                " với lý do " + report.getContent().getReportEnumContent();
        message.setText(htmlMsg);
        // Send Message
        emailSender.send(message);

        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
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
