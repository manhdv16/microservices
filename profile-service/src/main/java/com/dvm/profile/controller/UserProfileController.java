package com.dvm.profile.controller;

import com.dvm.profile.dto.request.ProfileCreationRequest;
import com.dvm.profile.dto.request.ProfileUpdateRequest;
import com.dvm.profile.dto.response.ApiResponse;
import com.dvm.profile.dto.response.UserProfileResponse;
import com.dvm.profile.service.UserProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserProfileController {
    UserProfileService userProfileService;

    @PostMapping
    ApiResponse<UserProfileResponse> createProfile(@RequestBody ProfileCreationRequest request) {
        return ApiResponse.<UserProfileResponse>builder()
                .result(userProfileService.createProfile(request))
                .build();
    }

    @PutMapping("/{profileId}")
    ApiResponse<UserProfileResponse> updateProfile(@PathVariable String profileId, @RequestBody ProfileUpdateRequest request) {
        return ApiResponse.<UserProfileResponse>builder()
                .result(userProfileService.updateProfile(profileId,request))
                .build();
    }
    @GetMapping("/{profileId}")
    ApiResponse<UserProfileResponse> getProfile(@PathVariable String profileId) {
        return ApiResponse.<UserProfileResponse>builder()
                .result(userProfileService.getProfile(profileId))
                .build();
    }

    @GetMapping
    ApiResponse<List<UserProfileResponse>> getAllProfiles() {
        return ApiResponse.<List<UserProfileResponse>>builder()
                .result(userProfileService.getAllProfiles())
                .build();
    }
    @DeleteMapping("/{profileId}")
    ApiResponse<String> deleterofile(@PathVariable String profileId) {
        userProfileService.deleteProfile(profileId);
        return ApiResponse.<String>builder()
                .result("User profile has been deleted")
                .build();
    }
}
