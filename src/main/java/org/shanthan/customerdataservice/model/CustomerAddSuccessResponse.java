package org.shanthan.customerdataservice.model;

import lombok.*;

@Value
@Builder
public class CustomerAddSuccessResponse {
    String accountKey;
    String accountNumber;
}
