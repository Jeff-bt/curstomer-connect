package dev.jeff.customerconnect.repository;

import dev.jeff.customerconnect.entity.CustomerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    Page<CustomerEntity> findByEmailAndCpf(String email, String cpf, PageRequest pageRequest);

    Page<CustomerEntity> findByEmail(String email, PageRequest pageRequest);

    Page<CustomerEntity> findByCpf(String cpf, PageRequest pageRequest);

}
