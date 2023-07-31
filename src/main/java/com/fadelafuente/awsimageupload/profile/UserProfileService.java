package com.fadelafuente.awsimageupload.profile;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.fadelafuente.awsimageupload.bucket.BucketName;
import com.fadelafuente.awsimageupload.filestore.FileStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.apache.http.entity.ContentType.*;

@Service
public class UserProfileService {
    private final UserProfileDataAccessService userProfileDataAccessService;
    private final FileStore filestore;

    @Autowired
    public UserProfileService(UserProfileDataAccessService userProfileDataAccessService, FileStore filestore) {
        this.userProfileDataAccessService = userProfileDataAccessService;
        this.filestore = filestore;
    }

    List<UserProfile> getUserProfiles() {
        return userProfileDataAccessService.getUserProfiles();
    }

    public void uploadUserProfileImage(UUID userProfileId, MultipartFile file) throws IOException {
        // 1. Check if image is not empty
        if(file.isEmpty()) {
            throw new IllegalStateException("Image not uploaded");
        }

        // 2. Check if image file is an image
        String extension = file.getOriginalFilename().split("\\.")[1];
        if(!Arrays.asList("jpeg", "png", "jpg").contains(extension)) {
            throw new IllegalStateException("file uploaded is not an image [" + file.getContentType() + "]");
        }

        // 3. Check whether the user exists in database
        UserProfile user = userProfileDataAccessService.getUserProfile(userProfileId);

        // 4. Grab some metadata from file if any
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());
        Map<String, String> metadata = objectMetadata.getUserMetadata();

        // 5. Store the image in s3 and update database
        String bucketName = BucketName.PROFILE_IMAGE.getBucketName();
        String filename = userProfileId + "/" + String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());
        filestore.save(bucketName, filename, Optional.of(metadata), file.getInputStream());
        user.setUserProfileImageLink(filename);
    }

    public byte[] downloadUserProfileImage(UUID userProfileId) {
        UserProfile user = userProfileDataAccessService.getUserProfile(userProfileId);
        String bucketName = BucketName.PROFILE_IMAGE.getBucketName();

        return user.getUserProfileImageLink()
                .map(key -> filestore.download(bucketName, key))
                .orElse(new byte[0]);
    }
}
