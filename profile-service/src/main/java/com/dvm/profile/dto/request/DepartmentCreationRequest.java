package com.dvm.profile.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentCreationRequest {
    @NotBlank(message = "Department name is required")
    private String name;
}
