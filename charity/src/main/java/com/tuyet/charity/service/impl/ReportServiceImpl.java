package com.tuyet.charity.service.impl;

import com.tuyet.charity.pojo.Report;
import com.tuyet.charity.repository.ReportRepository;
import com.tuyet.charity.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Override
    public List<Report> getAllReports() {
//        return reportRepository.findAll();
        return reportRepository.findAllByOrderByReportIdDesc();
    }

    @Override
    public void createReport(Report report) {
        reportRepository.save(report);
    }
}
