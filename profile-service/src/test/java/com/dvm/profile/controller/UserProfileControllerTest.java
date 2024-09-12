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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.time.LocalDate;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc // tao mock request cho controller test
public class UserProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean // tao mock cho service
    private UserProfileService userProfileService;

    private UserProfileResponse response;
    private ProfileCreationRequest request;

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
        response = UserProfileResponse.builder()
                .id("1")
                .firstName("John")
                .lastName("Doe")
                .dob(dob)
                .email("manh11yopmail.com")
                .city("New York")
                .build();
    }
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void createProfile_validRequest_success() throws Exception{
    // GIVEN
        ObjectMapper objectMapper = new ObjectMapper(); // dung de chuyen doi object thanh json
        objectMapper.registerModule(new JavaTimeModule());// dung de xu ly LocalDate
        String content = objectMapper.writeValueAsString(request); // chuyen doi object thanh json

        Mockito.when(userProfileService.createProfile(ArgumentMatchers.any()))
                .thenReturn(response); // mock service tra ve response khi goi createProfile
    // WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders
                .post("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1000)
                )
        ; // kiem tra response tra ve khi goi api /users

    }
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void createProfile_withEmailInvalidFail() throws Exception {
        // GIVEN
        request.setEmail("invalid-email");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(request);

        // WHEN THEN
        mockMvc.perform(MockMvcRequestBuilders
                .post("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1008)
        );
    }
}
