package dev.jeff.customerconnect.repository;

import dev.jeff.customerconnect.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Page<Customer> findByEmailAndCpf(String email, String cpf, PageRequest pageable);

    Page<Customer> findByEmail(String email, PageRequest pageable);

    Page<Customer> findByCpf(String cpf, PageRequest pageable);

    //Page<Customer> findAll(PageRequest pageRequest);
}
