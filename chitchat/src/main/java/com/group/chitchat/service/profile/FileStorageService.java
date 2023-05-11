package com.group.chitchat.service.profile;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FileStorageService {

  String saveFile(String userName, MultipartFile file);

  void deleteFile(String fileKey);
}
