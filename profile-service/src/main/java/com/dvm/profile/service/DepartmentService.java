package com.dvm.profile.service;

import com.dvm.profile.dto.request.DepartmentCreationRequest;
import com.dvm.profile.dto.response.DepartmentResponse;
import com.dvm.profile.entity.Department;
import com.dvm.profile.repository.DepartmentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DepartmentService {
    DepartmentRepository departmentRepository;
    ModelMapper modelMapper = new ModelMapper();
    public DepartmentResponse createDepartment(DepartmentCreationRequest request) {
        Department department = modelMapper.map(request, Department.class);
        departmentRepository.save(department);
        DepartmentResponse response = modelMapper.map(department, DepartmentResponse.class);
        return response;
    }
}
