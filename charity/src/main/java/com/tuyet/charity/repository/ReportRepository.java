package com.tuyet.charity.repository;

import com.tuyet.charity.pojo.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report,Integer> {
    List<Report> findAllByOrderByReportIdDesc();
}
