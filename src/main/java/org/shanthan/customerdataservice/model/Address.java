package org.shanthan.customerdataservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shanthan.customerdataservice.annotation.ValidState;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {

    private String street;
    private String city;
    @ValidState
    private String state;
    private String zipCode;
}
