package org.shanthan.customerdataservice.model;

import lombok.*;

@Value
@Builder
public class CustomerAccDataResponse {
    String accountKey;
    String accountNumber;
}
