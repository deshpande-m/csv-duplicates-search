package com.validity.monolithstarter.utility;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

/**
 * This class reads the CSV file and process it
 */
@Service
public class CSVParser {

    /*list to store the CSV header values */
    List<String> headerList = new ArrayList<String>();

    /**
     * This method returns the header list of the CSV
     * @return List<String> list of header values
     */
    public List<String> getCSVHeader() {
        return headerList;
    }

    /**
     * This method reads the CSV file and process its content
     * @return List<Map<String, String>> list of map of header and its value
     * @throws IOException
     */
    public List<Map<String, String>> readCSV(String csvFileName) throws IOException {
        /* list to store the header and value */
        List<Map<String, String>> valuesList = new ArrayList<Map<String, String>>();
        /* name and path of the CSV file */
        String csvFile = "./test-files/"+csvFileName;
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";

        try {
            /* creating buffered reader instance */
            br = new BufferedReader(new FileReader(csvFile));
            /* iterating while loop for fetching headers */
            while ((line = br.readLine()) != null) {
                /* getting header values */
                String[] header = line.split(cvsSplitBy);
                /* iterating through the header length */
                for(int i=0; i<header.length; i++) {
                    /* storing the headers in the list */
                    headerList.add(header[i]);
                }
                break;
            }

            /* iterating while loop for fetching values */
            while ((line = br.readLine()) != null) {
                /* splitting the row values based on , and ""*/
                String[] values = line.split(cvsSplitBy);
                /* declaring linked hash map to maintain the insertion order */
                Map<String, String> obj = new LinkedHashMap<String, String>();
                for(int i=0; i<values.length; i++) {
                    obj.put(headerList.get(i), values[i].replace("\"", ""));
                }
                /* adding the values and header map to the list */
                valuesList.add(obj);
            }
        }catch (FileNotFoundException e) {
            System.err.println("File not found");
        }catch (InvalidObjectException e) {
            e.printStackTrace();
        }finally {
            /* checking if buffered reader is not null */
            if (br != null) {
                try {
                    /* closing the buffered reader connection */
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        /* return the values list */
        return valuesList;
    }
}
