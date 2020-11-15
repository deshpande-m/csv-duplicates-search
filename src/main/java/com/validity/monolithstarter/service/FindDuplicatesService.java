package com.validity.monolithstarter.service;

import com.validity.monolithstarter.utility.CSVParser;
import org.apache.commons.codec.language.DoubleMetaphone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class is created to find the duplicates and non duplicates in the CSV file
 */
@Service
public class FindDuplicatesService {

    @Autowired
    private CSVParser csvParser;

    /* name of the CSV file */
    @Value("${csv.file.name}")
    private String csvFileName;

    /**
     * This method processes the duplicates and non duplicates in a CSV file
     * @return List<List<Map<String, String>>> list of duplicates and non duplicates
     * @throws IOException
     */
    public List<List<Map<String, String>>> processDuplicates() throws IOException {
        /* list of duplicates */
        List<Map<String, String>> duplicateList = new ArrayList<>();
        /* list of non duplicates */
        List<Map<String, String>> nonDuplicateList = new ArrayList<>();
        /* final list of duplicates and non duplicates */
        List<List<Map<String, String>>> finalList = new ArrayList<>();

        /* creating instance of DoubleMetaphone class */
        DoubleMetaphone doubleMetaphone = new DoubleMetaphone();
        /* fetching values from the CSV Parser class */
        List<Map<String, String>> valuesList = csvParser.readCSV(csvFileName);
        /* fetching headers from the CSV Parser class */
        List<String> headerList = csvParser.getCSVHeader();

        if(!valuesList.isEmpty()) {
            /* calculating the threshold value of the column to mark row as duplicate */
            int columnThreshold = (int) (headerList.size() * 0.6);

            int i=0;
            /* iterating till the list is empty */
            while(!valuesList.isEmpty()) {
                /* fetching the first entry in the list to compare with rest of the entries */
                Map<String, String> compare = valuesList.get(i);
                /* declaring variable to check duplicates */
                boolean isDuplicateFound = false;

                /* iterating the rest of the CSV rows */
                for(int j=i+1; j<valuesList.size(); j++) {
                    /* fetching the CSV rows */
                    Map<String, String> compare1 = valuesList.get(j);
                    int count = 0;
                    /* iterating through the header list to get the values from the map */
                    for(int k=1; k<headerList.size(); k++) {
                        /* comparing the header values of the first CSV row with othe CSV rows to find the duplicates */
                        if(doubleMetaphone.isDoubleMetaphoneEqual(compare.get(headerList.get(k)), compare1.get(headerList.get(k)))){
                            count++;
                        }
                    }
                    /* checking if the count is equals or greater than the threshold */
                    if(count >= columnThreshold) {
                        isDuplicateFound = true;
                        /* adding duplicate row to the list */
                        duplicateList.add(compare1);
                        /* as the row is already marked as duplicate, its safe to remove from the main list */
                        valuesList.remove(compare1);
                    }
                }

                /* to check if any duplicate found */
                if(isDuplicateFound) {
                    /* adding the base row to the duplicates list*/
                    duplicateList.add(compare);
                }else {
                    /* adding the base to non duplicates list */
                    nonDuplicateList.add(compare);
                }
                /* as the processing of the ase row is done, its safe to remove from the main list */
                valuesList.remove(compare);
            }
        }
        /* adding duplicate list to the fial list */
        finalList.add(duplicateList);
        /* adding non duplicate list to the fial list */
        finalList.add(nonDuplicateList);
        return finalList;
    }
}
