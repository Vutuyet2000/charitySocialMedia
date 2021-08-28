package com.tuyet.charity.controller;

import com.tuyet.charity.pojo.Report;
import com.tuyet.charity.pojo.ReportEnum;
import com.tuyet.charity.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping("/report")
    public List<Report> getAllReports(){
        return reportService.getAllReports();
    }

    @PostMapping("/report")
    public void createReport(@RequestBody Report report){
        reportService.createReport(report);
    }
}
