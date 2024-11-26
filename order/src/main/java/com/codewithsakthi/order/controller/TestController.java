package com.codewithsakthi.order.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "working";
    }


    /**
     * 
spring.servlet.multipart.enabled=true
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
     * 
     * @param file
     * @return
     * @throws Exception
     */

    @PostMapping("/upload")
    public ResponseEntity<List<String>> handleFileUpload(@RequestParam("file") MultipartFile file) throws Exception {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            //log error here
            throw new Exception("Failed to read file");
        }
        processFile(lines);
        return ResponseEntity.ok(lines); // Return the list of lines as the response
    }

    public void processFile(List<String> lines) throws Exception{
        List<String> model = new ArrayList<>();
        //Service
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
        for(String row: lines){
            String[] columns = row.split(",");
            if("Vendor Upload".equalsIgnoreCase(columns[0])){
                //Header row
                String todayDateString = formatter.format(LocalDate.now());//get date from date utils
                String dateFromfile = String.valueOf(columns[1]);
                if(dateFromfile == null){
                    throw new Exception("Date not present in file");
                }
                dateFromfile = dateFromfile.length() !=8 ? "0".concat(dateFromfile) : dateFromfile;
                if(dateFromfile.equalsIgnoreCase(todayDateString)){
                    //date validation successful
                } else {
                    throw new Exception("Date is not today");
                }
            } else if("ZZZZZ".equalsIgnoreCase(columns[0])){
                //Footer row
                if(columns[1] == null){
                    throw new Exception("Count not present");
                }
                Integer count = 0;
                try{
                    count = Integer.valueOf(columns[1]);
                } catch (NumberFormatException e){
                    throw new Exception("Count is not a number");
                }
                if(model.size() != count){
                    throw new Exception("Count mismatch in the file");
                } else {
                    //log file count validation successful
                }
            } else {
                //build modelto send to tuxedo
                model.add(columns[0]);
            }
        }
    }
    
    
}
