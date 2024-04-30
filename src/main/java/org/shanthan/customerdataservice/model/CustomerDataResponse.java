package org.shanthan.customerdataservice.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CustomerDataResponse {

    String message;
}
