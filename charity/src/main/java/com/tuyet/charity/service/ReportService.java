package com.tuyet.charity.service;

import com.tuyet.charity.pojo.Report;

import java.util.List;

public interface ReportService {
    List<Report> getAllReports();
    void createReport(Report report);
}
