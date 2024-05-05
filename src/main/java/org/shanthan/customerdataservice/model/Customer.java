package org.shanthan.customerdataservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shanthan.customerdataservice.annotation.DateOfBirth;

import static org.shanthan.customerdataservice.util.CustomerConstants.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer {

    @Size(min = ACC_KEY_LEN, max = ACC_KEY_LEN)
    private String accountKey;

    @Size(min = ACC_NUM_LEN, max = ACC_NUM_LEN)
    private String accountNumber;

    @NotBlank(message = "First name cannot be null or empty")
    @Pattern(regexp = NAME_REGEXP, message = "Invalid first name format")
    private String firstName;

    @NotBlank(message = "Last name cannot be null or empty")
    @Pattern(regexp = NAME_REGEXP, message = "Invalid first name format")
    private String lastName;

    @NotBlank(message = "Email cannot be null or empty")
    @Pattern(regexp = EMAIL_REGEXP, message = "Invalid email format")
    private String email;

    @DateOfBirth
    private String dateOfBirth;

    @NotBlank(message = "SSN cannot be null or empty")
    @Pattern(regexp = SSN_REGEXP, message = "invalid message format")
    private String ssn;

    @NotBlank(message = "Phone number cannot be null or empty")
    @Pattern(regexp = PHONE_REGEXP, message = "invalid phone format")
    private String phoneNumber;

    private Address address;
}
