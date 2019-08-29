package com.srckearney.employme.Service;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.srckearney.employme.Model.JobPage;
import com.srckearney.employme.Model.JobPageRepository;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ScrapeService implements IScrapeService {

    @Autowired
    private JobPageRepository repository;


    /**
     * Scrapes through Indeed.com search results, populating JobPage objects with
     * each posting's job title, company, page, and description.
     *
     * Returns "No Results Found" if the search results come up empty
     *
     *
     * @param query      Job title
     * @param location   Job location
     * @param pages      Amount of pages of data to output
     * @return           JSONObject.toString() of scraped job data
     */
    public String scrapeJobs(String query, String location, int pages){
        String url = "https://www.indeed.com/jobs?q="+query+"&l="+location;
        WebClient client = new WebClient(BrowserVersion.CHROME);
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiesEnabled(false);

        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);


        try {
            HtmlPage searchPage = client.getPage(url);
            ArrayList<JobPage> jobPages = new ArrayList<>();
            JSONObject jo = new JSONObject();

            for(int x = 0; x<pages; x++) {
                searchPage = client.getPage(url + "&start=" + (10 * x));
                List<HtmlElement> jobDivs = (List<HtmlElement>) searchPage.getByXPath("//div[contains(@class, 'title')]");
                jobDivs.remove(0); //trim extraneous div off first index

                //Loop through each job div, creating an ArrayList of jobPage objects
                for (int i = 0; i <= 8; i++) {
                    HtmlAnchor a = (HtmlAnchor) jobDivs.get(i).getElementsByTagName("a").get(0);
                    DomElement span = (DomElement) jobDivs.get(i).getByXPath("//span[contains(@class, 'company')]").get(i);
                    if(x>0){
                        span = (DomElement) jobDivs.get(i).getByXPath("//span[contains(@class, 'company')]").get(i+1);
                    }
                    String title = a.getTextContent();
                    String company = span.getTextContent();

                    //Set page equal to the job's page, and jobDescription to the description on the job's page
                    HtmlPage page = a.click();
                    HtmlDivision jobDescriptionDiv = (HtmlDivision) page.getByXPath("//*[@id=\"jobDescriptionText\"]").get(0);
                    String jobDescription = jobDescriptionDiv.getTextContent();

                    //Parse out "\n"s in company, title, and jobdescription
                    company = company.substring(9);
                    if (company.substring(0, 1).equals("\n")) {
                        company = company.substring(9);
                    }
                    title = title.substring(13);
                    for (int j = 0; j < jobDescription.length(); j++) {
                        if (jobDescription.substring(j, j + 1).equals("\n")) {
                            jobDescription = jobDescription.substring(0, j) + ' ' + jobDescription.substring(j + 1);
                        }
                    }

                    //Create JobPage object and add the current job's title, company, page, and job description
                    JobPage job = new JobPage(title, company, page.toString(), jobDescription, query, location);
                    jobPages.add(job);
                    repository.save(job);
                }
            }

            //Create JSONObject and fill with jobPages data
            for(int i = 0; i <jobPages.size(); i++){

                Map m = new LinkedHashMap(6);
                m.put("company", jobPages.get(i).getCompany());
                m.put("title", jobPages.get(i).getTitle());
                m.put("page", jobPages.get(i).getPage().toString());
                m.put("description", jobPages.get(i).getJobDescription());
                m.put("location",location);
                m.put("query",query);

                jo.put(i, m);
            }
            return jo.toString();

        }catch(Exception e){
            e.printStackTrace();

            return "No Results Found. Try using broader search criteria or using less pages.";
        }
    }


}
