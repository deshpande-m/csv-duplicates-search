# CSV Duplicate Records Search
## Introduction
The goal of this assignment is to parse a given CSV file to find duplicate and non-duplicate records. 

The goal is to find not just exact duplicates but also records that are likely to be a duplicate entry with different spelling, missing data, small differences, etc.

## Pre-requisites
1. Java should be configured and installed on the machine.
2. File normal.csv/advanced.csv should be inside the test-files folder under the main directory and should be populated with test data to see the results.
3. To use a different file, change the name of the file in the application.yml file.

## Technology Stack
1. Spring Boot MVC
2. Maven

## Features
1. Double Metaphone algorithm is used to implement phonetic search for fetching near duplicate records from csv file.
2. A CSV row is marked as duplicate when atleast 60% of the columns are matching with another CSV row.
3. Output is returned as a JSON array.

## Running the application
1. Download the project and open it in any IDE. 
2. Run the MonolithStarterApp module.
    1. Go to http://localhost:8080/getDuplicates to fetch duplicate records. 
    2. Go to http://localhost:8080/getNonDuplicates to fetch non duplicate records.
