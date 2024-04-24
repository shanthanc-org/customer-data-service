package org.shanthan.customerdataservice.repository;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.util.ObjectUtils;

import java.time.Instant;

import static org.shanthan.customerdataservice.util.CustomerConstants.EMAIL_REGEXP;
import static org.shanthan.customerdataservice.util.CustomerConstants.PHONE_REGEXP;
import static org.shanthan.customerdataservice.util.CustomerUtil.*;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "customer")
public class CustomerEntity {

  @NotEmpty
  @Size(min = 9, max = 9)
  @Id
  private String accountKey;

  @NotEmpty
  @Size(min = 12, max = 12)
  private String accountNumber;

  @NotEmpty
  @Size(min = 3, max = 50)
  private String firstName;

  @NotEmpty
  @Size(min = 3, max = 50)
  private String lastName;

  @NotEmpty
  @Pattern(regexp = EMAIL_REGEXP)
  private String email;

  @NotEmpty
  @Pattern(regexp = PHONE_REGEXP)
  private String phoneNumber;

  @NotEmpty
  private String address;

  @CreatedDate
  private Instant createdAt;

  @LastModifiedDate
  private Instant updatedAt;

  @PrePersist
  protected void onCreate() {
    if (ObjectUtils.isEmpty(accountKey) || ObjectUtils.isEmpty(accountNumber)) {
      this.accountKey = generateAccountKey();
      this.accountNumber = generateAccountNumber();
    }
    this.createdAt = Instant.now();
  }

  @PreUpdate
  protected void onUpdate() {
    this.updatedAt = Instant.now();
  }

}