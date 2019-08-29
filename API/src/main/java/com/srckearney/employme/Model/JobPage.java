package com.srckearney.employme.Model;

import javax.persistence.*;
import java.lang.*;

@Entity
public class JobPage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title, company, query, location;

    @Column(name="page", length = 2048)
    private String page;

    @Column(name="description", length = 10000)
    private String jobDescription;


    public JobPage() {}


    public JobPage(String title, String company, String page, String jobDescription, String query, String location) {
        this.title = title;
        this.company = company;
        this.page = page;
        this.jobDescription = jobDescription;
        this.query = query;
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public String getCompany() {
        return company;
    }

    public String getPage() {
        return page;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public String getQuery() {
        return query;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    @Override
    public String toString() {
        return "JobPage{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", company='" + company + '\'' +
                ", query='" + query + '\'' +
                ", location='" + location + '\'' +
                ", page='" + page + '\'' +
                ", jobDescription='" + jobDescription + '\'' +
                '}';
    }
}
