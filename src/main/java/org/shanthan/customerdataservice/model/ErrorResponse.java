package org.shanthan.customerdataservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Value;

import java.util.Map;

@Value
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {
    String errorMessage;
    Map<String, String> errorFields;
}
