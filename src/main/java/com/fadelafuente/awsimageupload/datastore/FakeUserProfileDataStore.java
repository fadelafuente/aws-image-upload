package com.fadelafuente.awsimageupload.datastore;

import com.fadelafuente.awsimageupload.profile.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class FakeUserProfileDataStore {
    private static final List<UserProfile> USER_PROFILES = new ArrayList<>();

    static {
        USER_PROFILES.add(new UserProfile(UUID.fromString("29f5f4f7-9a59-4229-8f9b-284211389ddf"), "janetjones",null));
        USER_PROFILES.add(new UserProfile(UUID.fromString("36232f7a-e919-4099-8022-012cc9b1f77d"), "antoniojunior",null));
    }

    public List<UserProfile> getUserProfiles() {
        return USER_PROFILES;
    }

    public UserProfile getUserProfile(UUID userProfileId) {
        return USER_PROFILES.stream()
                .filter(userProfile -> userProfile.getUserProfileId().equals(userProfileId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("User does not exist"));
    }
}
