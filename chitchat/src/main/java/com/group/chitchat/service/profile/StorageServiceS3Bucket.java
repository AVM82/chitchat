package com.group.chitchat.service.profile;

import static org.springframework.http.HttpStatus.CONFLICT;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class StorageServiceS3Bucket implements FileStorageService {

  private AmazonS3 s3client;

  @Value("${s3.access.key}")
  private String accessKey;

  @Value("${s3.secret.key}")
  private String secretKey;

  @Value("${bucket.name}")
  private String bucketName;

  @Override
  public String saveFile(String userName, MultipartFile file) {
    AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

    s3client = AmazonS3ClientBuilder
        .standard()
        .withCredentials(new AWSStaticCredentialsProvider(credentials))
        .withRegion(Regions.EU_CENTRAL_1)
        .build();

    String fileKey = "avatars/" + UUID.randomUUID() + "-" + userName;
    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentType("image/jpeg");
    metadata.setContentLength(file.getSize());
    try {
      PutObjectRequest request = new PutObjectRequest(
          bucketName, fileKey, file.getInputStream(), metadata);

      request.setCannedAcl(CannedAccessControlList.PublicRead);

      s3client.putObject(request);

      log.info("avatar of user {} saved successfully", userName);
      return s3client.getUrl(bucketName, fileKey).toString();

    } catch (IOException ex) {
      log.info("error loading user avatar of user {}", userName);
      throw new ErrorResponseException(CONFLICT, ex);
    }
  }

  @Override
  public void deleteFile(String fileKey) {
    s3client.deleteObject(bucketName, fileKey.replace(
        "s3.bucket.url", ""));
  }
}
