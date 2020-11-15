package com.validity.monolithstarter.rest;

import com.validity.monolithstarter.service.FindDuplicatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This is the controller class for displaying duplicates and non duplicates
 */
@RestController
public class FindDuplicatesController {

    @Autowired
    private FindDuplicatesService findDuplicatesService;
    /* list of duplicates */
    private List<Map<String, String>> duplicateList;
    /* list of non duplicates */
    private List<Map<String, String>> nonDuplicateList;

    /**
     * This method displays the duplicates in JSON format
     * @return List<Map<String, String>> list of duplicates
     * @throws IOException
     */
    @GetMapping("/getDuplicates")
    public List<Map<String, String>> fetchDuplicates() throws IOException {
        return duplicateList;
    }

    /**
     * This method displays the non duplicates in JSON format
     * @return List<Map<String, String>> list of non duplicates
     * @throws IOException
     */
    @GetMapping("/getNonDuplicates")
    public List<Map<String, String>> fetchNonDuplicates() throws IOException {
        return nonDuplicateList;
    }

    /**
     * This method will be executed after dependency injection is done to process the duplicates
     * @throws IOException
     */
    @PostConstruct
    public void processCSVRecords() throws IOException {
        /* list of duplicates */
        duplicateList = new ArrayList<>();
        /* list of non duplicates */
        nonDuplicateList = new ArrayList<>();
        /* fetching final list */
        List<List<Map<String, String>>> finalList = findDuplicatesService.processDuplicates();
        if(!finalList.isEmpty()) {
            /* fetching duplicates and non duplicates from the final list */
            duplicateList = finalList.get(0);
            nonDuplicateList = finalList.get(1);
        }
    }
}
