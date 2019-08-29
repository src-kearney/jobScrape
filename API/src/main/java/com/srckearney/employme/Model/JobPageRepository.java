package com.srckearney.employme.Model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobPageRepository extends CrudRepository<JobPage, Long> {

    List<JobPage> findByquery(@Param("query") String query);
    List<JobPage> findBylocation(@Param("location") String location);
    List<JobPage> findBycompany(@Param("company") String company);
    List<JobPage> findBytitle(@Param("title") String title);


}
