package com.dvm.identityservice.service;

import com.dvm.identityservice.dto.request.ProfileCreationRequest;
import com.dvm.identityservice.dto.request.UserCreationRequest;
import com.dvm.identityservice.dto.response.UserProfileResponse;
import com.dvm.identityservice.dto.response.UserResponse;
import com.dvm.identityservice.entity.Role;
import com.dvm.identityservice.entity.User;
import com.dvm.identityservice.exception.AppException;
import com.dvm.identityservice.exception.ErrorCode;
import com.dvm.identityservice.repository.UserRepository;
import com.dvm.identityservice.repository.httpclient.ProfileClient;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    ModelMapper modelMapper;
    ProfileClient profileClient;

    public UserResponse createUser(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);
        User user = modelMapper.map(request, User.class);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<Role> roles = new HashSet<>();
        roles.add(Role.builder().name("ROLE_USER").build());
        user.setRoles(roles);
        userRepository.save(user);

        var profileCreationRequest = modelMapper.map(request, ProfileCreationRequest.class);
        profileCreationRequest.setUserId(user.getId());

        UserProfileResponse profile = profileClient.createProfile(profileCreationRequest).getResult();
        UserResponse response = modelMapper.map(profile, UserResponse.class);
        return response;
    }

}
