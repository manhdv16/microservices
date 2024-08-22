package com.dvm.profile.controller;

import com.dvm.profile.dto.request.ProfileCreationRequest;
import com.dvm.profile.dto.response.UserProfileResponse;
import com.dvm.profile.service.UserProfileService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class UserProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserProfileService userProfileService;

    private UserProfileResponse response;
    private ProfileCreationRequest request;

    private LocalDate dob = LocalDate.of(1990, 1, 1);

    @BeforeEach
    void initData() {
        request = ProfileCreationRequest.builder()
                .userId("1")
                .firstName("John")
                .lastName("Doe")
                .dob(dob)
                .city("New York")
                .build();
        response = UserProfileResponse.builder()
                .id("1")
                .firstName("John")
                .lastName("Doe")
                .dob(dob)
                .city("New York")
                .build();
    }
    @Test
    void createProfile() throws Exception{
    // GIVEN
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(request);

        Mockito.when(userProfileService.createProfile(ArgumentMatchers.any()))
                .thenReturn(response);
    // WHEN
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1000)
                )
        ;
    // THEN

    }
    @Test
    void createProfileWithUserNameInvalidFail() throws Exception {
        // GIVEN
        request.setFirstName("Joh");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(request);

        // WHEN THEN
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1001)
        );
    }
}
