package com.dvm.profile.service;

import com.dvm.profile.dto.request.ProfileCreationRequest;
import com.dvm.profile.dto.request.ProfileUpdateRequest;
import com.dvm.profile.dto.response.UserProfileResponse;
import com.dvm.profile.entity.Department;
import com.dvm.profile.entity.UserProfile;
import com.dvm.profile.repository.DepartmentRepository;
import com.dvm.profile.repository.UserProfileRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserProfileService {
    UserProfileRepository userProfileRepository;
    DepartmentRepository departmentRepository;
    ModelMapper modelMapper;
    public UserProfileResponse createProfile(ProfileCreationRequest request) {
//        Department department = departmentRepository.findById(request.getDepartmentId()).orElseThrow(()-> new RuntimeException("Department not found"));

        UserProfile userProfile = modelMapper.map(request, UserProfile.class);
//        userProfile.setDepartment(department);
        userProfile = userProfileRepository.save(userProfile);
        UserProfileResponse response = modelMapper.map(userProfile, UserProfileResponse.class);
//        response.setDepartmentName(department.getName());
        return response;
    }
    public UserProfileResponse updateProfile(String profileId,ProfileUpdateRequest request) {
        UserProfile userProfile = userProfileRepository.findById(profileId).orElseThrow(() -> new RuntimeException("Profile not found"));
        if(request.getFirstName() != null) userProfile.setFirstName(request.getFirstName());
        if(request.getLastName() != null)userProfile.setLastName(request.getLastName());
        if(request.getDob() != null)userProfile.setDob(request.getDob());
        if(request.getCity() != null)userProfile.setCity(request.getCity());
        userProfile = userProfileRepository.save(userProfile);
        return UserProfileResponse.builder()
                .id(userProfile.getId())
                .firstName(userProfile.getFirstName())
                .lastName(userProfile.getLastName())
                .dob(userProfile.getDob())
                .city(userProfile.getCity())
                .build();
    }

    public UserProfileResponse getProfile(String id) {
        UserProfile userProfile = userProfileRepository.findById(id).orElseThrow(() -> new RuntimeException("Profile not found"));
//        return userProfileMapper.toUserProfileReponse(userProfile);
        return UserProfileResponse.builder()
                .id(userProfile.getId())
                .firstName(userProfile.getFirstName())
                .lastName(userProfile.getLastName())
                .dob(userProfile.getDob())
                .city(userProfile.getCity())
                .build();
    }
    public List<UserProfileResponse> getAllProfiles() {
        List<UserProfile> userProfiles = userProfileRepository.findAll();
        return userProfiles.stream().map(userProfile-> UserProfileResponse.builder()
                .id(userProfile.getId())
                .firstName(userProfile.getFirstName())
                .lastName(userProfile.getLastName())
                .dob(userProfile.getDob())
                .city(userProfile.getCity())
                .build()).toList();
    }
    public void deleteProfile(String id) {
        userProfileRepository.deleteById(id);
    }
}
