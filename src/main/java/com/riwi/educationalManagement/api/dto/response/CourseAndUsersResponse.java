package com.riwi.educationalManagement.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseAndUsersResponse {
    private Long id;
    private String courseName;
    private String description;
    private List<UserInfoResponse> userInfoResponses;
}