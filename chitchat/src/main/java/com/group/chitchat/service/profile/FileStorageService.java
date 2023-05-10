package com.group.chitchat.service.profile;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

  String saveFile(String userName, MultipartFile file);

}
