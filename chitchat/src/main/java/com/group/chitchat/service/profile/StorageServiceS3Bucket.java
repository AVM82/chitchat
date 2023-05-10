package com.group.chitchat.service.profile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import java.io.File;
import java.util.Objects;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class StorageServiceS3Bucket implements FileStorageService {

  private final Environment environment;

  @Override
  public String saveFile(String userName, MultipartFile file) {

    AWSCredentials credentials = new BasicAWSCredentials(
        Objects.requireNonNull(environment.getProperty("aws.access.key")),
        Objects.requireNonNull(environment.getProperty("aws.secret.key"))
    );

    AmazonS3 s3client = AmazonS3ClientBuilder
        .standard()
        .withCredentials(new AWSStaticCredentialsProvider(credentials))
        .withRegion(Regions.EU_CENTRAL_1)
        .build();

    s3client.putObject(
        environment.getProperty("bucket.name"),
        "keyDefaultAvatar",
        new File("database/default.png"));

    return s3client.getUrl(
        environment.getProperty("bucket.name"),
        "keyDefaultAvatar").toString();
  }
}
