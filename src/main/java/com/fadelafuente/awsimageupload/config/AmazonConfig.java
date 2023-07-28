package com.fadelafuente.awsimageupload.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.finspacedata.model.AwsCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.logging.Logger;

@Configuration
public class AmazonConfig {
    private String access_key;
    private String access_secret;

    @Bean
    public AmazonS3 s3() {
        String file = "developer_accessKeys.csv";
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(file));
            fileReader.readLine();
            String row = fileReader.readLine();
            String[] access = row.split(",");
            access_key = access[0];
            access_secret = access[1];
        } catch (Exception e) {
            e.printStackTrace();
        }

        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(
                access_key,
                access_secret
        );

        return AmazonS3ClientBuilder
                .standard()
                .withRegion("us-east-2")
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }
}
