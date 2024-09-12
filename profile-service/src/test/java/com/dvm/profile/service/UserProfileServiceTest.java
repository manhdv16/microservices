package com.dvm.profile.service;

import com.dvm.profile.dto.request.ProfileCreationRequest;
import com.dvm.profile.dto.response.UserProfileResponse;
import com.dvm.profile.entity.UserProfile;
import com.dvm.profile.repository.UserProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest // load context spring khi chay test
//@TestPropertySource("/test.yml") // load file cau hinh test
public class UserProfileServiceTest {
    @Autowired
    private UserProfileService userProfileService;

    @MockBean
    private UserProfileRepository userProfileRepository;

    private UserProfileResponse userResponse;
    private ProfileCreationRequest request;
    private UserProfile userProfile;

    @BeforeEach
    void initData() {
        LocalDate dob = LocalDate.of(1990, 1, 1);
        request = ProfileCreationRequest.builder()
                .userId("1")
                .firstName("John")
                .lastName("Doe")
                .email("manh11yopmail.com")
                .dob(dob)
                .city("New York")
                .build();
        userResponse = UserProfileResponse.builder()
                .id("1")
                .firstName("John")
                .lastName("Doe")
                .dob(dob)
                .email("manh11yopmail.com")
                .city("New York")
                .build();
        userProfile = UserProfile.builder()
                .id("1")
                .firstName("John")
                .lastName("Doe")
                .dob(dob)
                .email("manh11yopmail.com")
                .city("New York")
                .build();
    }
    @Test
    void createProfile() {
        // GIVEN : du doan du lieu dau vao va ket qua mong muon
        when(userProfileRepository.save(any())).thenReturn(userProfile);
        // WHEN : thuc hien hanh dong can test
        var response = userProfileService.createProfile(request);
        // THEN : so sanh ket qua mong muon voi ket qua thuc te
        assertThat(response.getEmail().equals(userResponse.getEmail()));

    }

    @Test
    void deleteProfile() {
        // GIVEN : du doan du lieu dau vao va ket qua mong muon
        // if deletebyid return void, then in the when method, we use
        doNothing().when(userProfileRepository).deleteById("id");
        // WHEN : thuc hien hanh dong can test
        userProfileService.deleteProfile("id");
        // THEN : so sanh ket qua mong muon voi ket qua thuc te
        // kiem tra phuong thuc co duoc goi chinh xac 1 lan hay khong
        verify(userProfileRepository, times(1)).deleteById("id");
    }
}
