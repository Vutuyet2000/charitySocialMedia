package com.tuyet.charity.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.Serializable;

@Entity
@Table(name = "report")
public class Report implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Integer reportId;
    //test enum gui request se the nao
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "This field is required")
    private ReportEnum content;

    //Many to many user (report)
    @ManyToOne
    @JoinColumn(name="reporter_id")
    private User reporter;

    @ManyToOne
    @JoinColumn(name="reported_user_id")
    private User reportedUser;

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public ReportEnum getContent() {
        return content;
    }

    public void setContent(ReportEnum content) {
        this.content = content;
    }

    public User getReporter() {
        return reporter;
    }

    public void setReporter(User reporter) {
        this.reporter = reporter;
    }

    public User getReportedUser() {
        return reportedUser;
    }

    public void setReportedUser(User reportedUser) {
        this.reportedUser = reportedUser;
    }
}
