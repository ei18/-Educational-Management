package com.riwi.educationalManagement.api.dto.response;

import com.riwi.educationalManagement.utils.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAndCourseResponse {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private Role role;
    private List<CourseResponse> courses;
}