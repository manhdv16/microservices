package com.dvm.profile.controller;

import com.dvm.profile.dto.request.DepartmentCreationRequest;
import com.dvm.profile.dto.response.ApiResponse;
import com.dvm.profile.dto.response.DepartmentResponse;
import com.dvm.profile.service.DepartmentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/departments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DepartmentController {

    DepartmentService departmentService;

    @PostMapping
    ApiResponse<DepartmentResponse> createDepartment(@RequestBody DepartmentCreationRequest request) {
        return ApiResponse.<DepartmentResponse>builder()
                .result(departmentService.createDepartment(request))
                .message("Department has been created")
                .build();
    }
}
