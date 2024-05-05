package org.shanthan.customerdataservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class DefaultSuccessResponse {

    String accountKey;
    String message;
}
