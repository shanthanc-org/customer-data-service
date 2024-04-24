package org.shanthan.customerdataservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, String> {

    List<CustomerEntity> findByLastName(String lastName);

    List<CustomerEntity> findByFirstName(String firstName);

    List<CustomerEntity> findByEmail(String email);

    List<CustomerEntity> findByPhoneNumber(String phoneNumber);

    CustomerEntity findByAccountKey(String accountKey);

    CustomerEntity findByAccountNumber(String accountNumber);
}
