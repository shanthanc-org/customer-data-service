package org.shanthan.customerdataservice.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shanthan.customerdataservice.annotation.ValidState;

import static org.shanthan.customerdataservice.util.CustomerConstants.ZIPCODE_REGEXP;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {

    @NotEmpty(message = "Street cannot be null or empty!")
    private String street;

    @NotEmpty(message = "City cannot be null or empty!")
    private String city;

    @NotEmpty(message = "State cannot be null or empty")
    @ValidState
    private String state;

    @NotEmpty(message = "Zipcode cannot be null or empty")
    @Pattern(regexp = ZIPCODE_REGEXP, message = "Invalid zipcode")
    private String zipCode;
}
