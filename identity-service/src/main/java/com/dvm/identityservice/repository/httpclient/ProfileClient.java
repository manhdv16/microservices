package com.dvm.identityservice.repository.httpclient;

import com.dvm.identityservice.configuration.AuthenticationRequestInterceptor;
import com.dvm.identityservice.dto.request.ProfileCreationRequest;
import com.dvm.identityservice.dto.response.ApiResponse;
import com.dvm.identityservice.dto.response.UserProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "profile-service", url = "${app.services.profile}",
            configuration = { AuthenticationRequestInterceptor.class })
public interface ProfileClient {
    @PostMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<UserProfileResponse> createProfile(@RequestBody ProfileCreationRequest request);

}
