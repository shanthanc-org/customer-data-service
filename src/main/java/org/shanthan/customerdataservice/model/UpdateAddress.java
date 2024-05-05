package org.shanthan.customerdataservice.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static org.shanthan.customerdataservice.util.CustomerConstants.ACC_KEY_REGEXP;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateAddress {

    @NotEmpty(message = "Account key cannot be null or empty!")
    @Size(min = 9, max = 9)
    @Pattern(regexp = ACC_KEY_REGEXP, message = "Invalid acct key!")
    private String accountKey;

    private Address address;
}
