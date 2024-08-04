package com.backend.api.domain.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {

    private String internalCode;
    private String message;
    private String detail;
}
