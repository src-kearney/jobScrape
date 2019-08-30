# jobScrape

jobScrape scrapes through Indeed.com's job postings based on given search parameters.

This project was built with Spring Boot and HTMLUnit.

*Still in development. Next additions: More CRUD operations, duplicate check, account entities and authentication config, CORS security.*

    src/main/java

    +- com
        +- employme
            +- EmploymeApplication.java
                +- Controller
                |   +- ScrapeController.java
                +- Model
                |   +- JobPage.java
                |   +- JobPageRepository.java
                +- Service
                |   +- IScrapeService.java
                |   +- ScrapeService.java
                
              
**API REQUEST PATHS**

    --> http://localhost:8080/api/update?query={myQuery}&location={myLocation}&pages={myPages}

Fills database with newly scraped data and returns new data.
Takes 3 query params: String query, String location, int pages.
Returns JSONObject.toString() of job postings that have been added.



    --> http://localhost:8080/api/clear

Clear all job postings that have been added to database.



    --> http://localhost:8080/api/findAll

Returns JSONObject.toString() of all job postings held in database



    --> http://localhost:8080/api/findByQuery?query={myQuery}

Takes 1 query param: query
Returns JSONObject.toString() of all job postings held in database with matching query



    --> http://localhost:8080/api/findByLocation?location={myLocation}

Takes 1 query param: location
Returns JSONObject.toString() of all job postings held in database with matching location



    --> http://localhost:8080/api/findByCompany?company={myCompany}

Takes 1 query param: company
Returns JSONObject.toString() of all job postings held in database with matching company





**EXAMPLE API USAGE**


POST:

    --> http://localhost:8080/api/update?query=Software+Intern+Internship&location=Austin&pages=3

    --> http://localhost:8080/api/update?query=Software+Intern+Internship&location=Seattle&pages=3

    --> http://localhost:8080/api/update?query=Architect&location=Seattle&pages=2

    --> http://localhost:8080/api/update?query=Attorney&location=Boston&pages=1

This fills the database with 3 pages of Software internship postings in Austin and Seattle, 2 pages of Architect positions in Seattle, and 1 page of Attorney positions in Boston.


GET:

    --> http://localhost:8080/api/findAll

Gets JSONObject.toString() of all job postings in the repository



    --> http://localhost:8080/api/findByQuery?query=Software+Intern+Internship

Gets JSONObject.toString() of all job postings in the repository that were added using the query param "Software+Intern+Internship"



    --> http://localhost:8080/api/findByCompany?company=Google

Gets JSONObject.toString() of all job postings in the repository posted by Google



    --> http://localhost:8080/api/findByLocation?location=Seattle

Gets JSONObject.toString() of all job postings with matching location value

