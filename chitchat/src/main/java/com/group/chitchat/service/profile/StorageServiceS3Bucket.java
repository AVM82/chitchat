package com.group.chitchat.service.profile;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageServiceS3Bucket implements FileStorageService {

  @Override
  public String saveFile(String userName, MultipartFile file) {
    return null;
  }
}
