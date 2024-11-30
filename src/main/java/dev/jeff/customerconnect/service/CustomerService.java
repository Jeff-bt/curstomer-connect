package dev.jeff.customerconnect.service;

import dev.jeff.customerconnect.controller.dto.CreateCustomerDto;
import dev.jeff.customerconnect.controller.dto.UpdateCustomerDto;
import dev.jeff.customerconnect.entity.CustomerEntity;
import dev.jeff.customerconnect.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.hasText;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Page<CustomerEntity> findAll(Integer page, Integer pageSize, String sorter, String email, String cpf) {
        var pageRequest = getPageRequest(page, pageSize, sorter);

        return findWithFilter(email, cpf, pageRequest);

    }

    private Page<CustomerEntity> findWithFilter(String email, String cpf, PageRequest pageRequest) {
        if (!isNull(email) && hasText(cpf)) {
            return customerRepository.findByEmailAndCpf(email, cpf, pageRequest);
        }

        if (hasText(email)) {
            return customerRepository.findByEmail(email, pageRequest);
        }
        if (hasText(cpf)) {
            return customerRepository.findByCpf(cpf, pageRequest);
        }

        return customerRepository.findAll(pageRequest);
    }

    private PageRequest getPageRequest(Integer page, Integer pageSize, String sorter) {
        var direction = Sort.Direction.DESC;
        if (sorter.equalsIgnoreCase("asc")) {
            direction = Sort.Direction.ASC;
        }
        return PageRequest.of(page, pageSize, direction, "created");
    }

    public Optional<CustomerEntity> findById(Long customerId) {
        return customerRepository.findById(customerId);
    }

    public CustomerEntity create(CreateCustomerDto dto) {
        CustomerEntity customerEntity = new CustomerEntity(dto.fullName(), dto.cpf(), dto.email(), dto.phoneNumber());
        return customerRepository.save(customerEntity);
    }


    public Optional<CustomerEntity> update(UpdateCustomerDto body, Long id) {

        var customer = customerRepository.findById(id);

        if (customer.isPresent()) {
            customer.get().setFullName(body.fullName());
            customer.get().setEmail(body.email());
            customer.get().setPhoneNumber(body.phoneNumber());

            customerRepository.save(customer.get());
        }

        return customer;
    }

    public boolean deleteById(Long id) {
        boolean exists = customerRepository.existsById(id);

        if (exists) {
            customerRepository.deleteById(id);
        }

        return exists;
    }
}
