package com.dvm.profile.service;

import com.dvm.event.dto.NotificationEvent;
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
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserProfileService {

    UserProfileRepository userProfileRepository;
    DepartmentRepository departmentRepository;
    ModelMapper modelMapper;
    KafkaTemplate<String, Object> kafkaTemplate;

    public UserProfileResponse createProfile(ProfileCreationRequest request) {
//        Department department = departmentRepository.findById(request.getDepartmentId()).orElseThrow(()-> new RuntimeException("Department not found"));

        UserProfile userProfile = modelMapper.map(request, UserProfile.class);
//        userProfile.setDepartment(department);

        UserProfile userProfile1 = userProfileRepository.save(userProfile);
        NotificationEvent event = NotificationEvent.builder()
                .body("Welcome to the system")
                .subject("User created")
                .recipient(userProfile.getEmail())
                .build();
//         send to kafka
//        kafkaTemplate.send("notification-create-user", event);
        return modelMapper.map(userProfile, UserProfileResponse.class);
//        response.setDepartmentName(department.getName());
    }
    public UserProfileResponse updateProfile(String profileId,ProfileUpdateRequest request) {
        UserProfile userProfile = userProfileRepository.findById(profileId).orElseThrow(() -> new RuntimeException("Profile not found"));

        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.map(request, userProfile);

        userProfile = userProfileRepository.save(userProfile);
        return modelMapper.map(userProfile,UserProfileResponse.class);
    }

    public UserProfileResponse getProfile(String id) {
        UserProfile userProfile = userProfileRepository.findById(id).orElseThrow(() -> new RuntimeException("Profile not found"));
        return modelMapper.map(userProfile, UserProfileResponse.class);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserProfileResponse> getAllProfiles() {
        List<UserProfile> userProfiles = userProfileRepository.findAll();
        return userProfiles.stream().map(userProfile->
                modelMapper.map(userProfile, UserProfileResponse.class)).toList();
    }

    public void deleteProfile(String id) {
        userProfileRepository.deleteById(id);
    }
}
