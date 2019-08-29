package com.srckearney.employme.Controller;

import com.srckearney.employme.Model.JobPage;
import com.srckearney.employme.Model.JobPageRepository;
import com.srckearney.employme.Service.IScrapeService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.lang.*;
import java.util.*;

@RestController
public class ScrapeController {

    @Autowired
    private JobPageRepository repository;


    @Autowired
    private IScrapeService scrapeService;


    /**
     * Handles get requests for the path:
     * "localhost:8080/api/update?query={query}&location={location}&pages={pages}"
     * Fills database with newly scraped data and returns new data.
     * Returns "No Results Found" if the search results come up empty.
     *
     *
     * @param query      Job title
     * @param location   Job location
     * @param pages      Amount of pages of data to output
     * @return           JSONObject.toString() of scraped job data
     */
    @PostMapping(path="/api/update",produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String scrapeQuery(@RequestParam(defaultValue = "software+intern+internship",required = false) String query, String location, int pages) {
        //Implement boolean on scrapeJobs for post/get
        return scrapeService.scrapeJobs(query,location,pages);
    }

    @PostMapping(path="/api/clear",produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String clear(@RequestParam(defaultValue = "software+intern+internship",required = false) String query, String location, int pages) {

        repository.deleteAll();
        return "Cleared repository";
    }


    /**
     * getAll() function
     *
     * @return JSONObject.toString() of entire table
     */
    @GetMapping(path="/api/findAll",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getAll(){
        JSONObject jo = new JSONObject();
        Iterable<JobPage> repo = repository.findAll();

        int i = 0;
        for(JobPage jp : repo){
            Map m = new LinkedHashMap(6);
            m.put("company", jp.getCompany());
            m.put("title", jp.getTitle());
            m.put("page", jp.getPage());
            m.put("description", jp.getJobDescription());
            m.put("location",jp.getLocation());
            m.put("query",jp.getQuery());

            jo.put(i, m);
            i++;
        }
        return jo.toString();
    }


    /**
     * Finds all JobPage objects in repository with given query value
     *
     * @param query
     * @return JSONObject.toString()
     */
    @GetMapping(path="/api/findByQuery",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getByQuery(@RequestParam String query){
        List<JobPage> jobPages = repository.findByquery(query);
        JSONObject jo = new JSONObject();

        for(int i = 0; i <jobPages.size(); i++){

            Map m = new LinkedHashMap(4);
            m.put("company", jobPages.get(i).getCompany());
            m.put("title", jobPages.get(i).getTitle());
            m.put("page", jobPages.get(i).getPage().toString());
            m.put("description", jobPages.get(i).getJobDescription());
            m.put("location",jobPages.get(i).getLocation());
            m.put("query",jobPages.get(i).getQuery());

            jo.put(i, m);
        }

        return jo.toString();
    }


    /**
     * Finds all JobPage objects in repository with given location value
     *
     * @param location
     * @return JSONObject.toString()
     */
    @GetMapping(path="/api/findByLocation")
    @ResponseBody
    public String getByLocation(@RequestParam String location){
        List<JobPage> jobPages = repository.findBylocation(location);
        JSONObject jo = new JSONObject();

        for(int i = 0; i <jobPages.size(); i++){

            Map m = new LinkedHashMap(6);
            m.put("company", jobPages.get(i).getCompany());
            m.put("title", jobPages.get(i).getTitle());
            m.put("page", jobPages.get(i).getPage().toString());
            m.put("description", jobPages.get(i).getJobDescription());
            m.put("location",jobPages.get(i).getLocation());
            m.put("query",jobPages.get(i).getQuery());

            jo.put(i, m);
        }

        return jo.toString();
    }


    /**
     * Finds all JobPage objects in repository with given company value
     *
     * @param company
     * @return JSONObject.toString()
     */
    @GetMapping(path="/api/findByCompany")
    @ResponseBody
    public String getByCompany(@RequestParam String company){
        List<JobPage> jobPages = repository.findBycompany(company);
        JSONObject jo = new JSONObject();

        for(int i = 0; i <jobPages.size(); i++){

            Map m = new LinkedHashMap(4);
            m.put("company", jobPages.get(i).getCompany());
            m.put("title", jobPages.get(i).getTitle());
            m.put("page", jobPages.get(i).getPage().toString());
            m.put("description", jobPages.get(i).getJobDescription());
            m.put("location",jobPages.get(i).getLocation());
            m.put("query",jobPages.get(i).getQuery());

            jo.put(i, m);
        }

        return jo.toString();
    }

}