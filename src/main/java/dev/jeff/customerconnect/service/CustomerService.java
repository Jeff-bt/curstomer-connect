package dev.jeff.customerconnect.service;

import dev.jeff.customerconnect.controller.dto.CustomerRequestDto;
import dev.jeff.customerconnect.controller.dto.CustomerResponseDto;
import dev.jeff.customerconnect.entity.CustomerEntity;
import dev.jeff.customerconnect.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

        //return customers.stream().map(customer -> new CustomerResponseDto(customer)).toList();
    }

    private PageRequest getPageRequest(Integer page, Integer pageSize, String sorter) {
        var direction = Sort.Direction.DESC;
        if (sorter.equalsIgnoreCase("asc")) {
            direction = Sort.Direction.ASC;
        }
        return PageRequest.of(page, pageSize, direction, "created");
    }

    public CustomerResponseDto findById(Long id) {
        CustomerEntity customerEntity = customerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        return new CustomerResponseDto(customerEntity);
    }

    public CustomerResponseDto create(CustomerRequestDto cutomerDto) {
        CustomerEntity customerEntity = new CustomerEntity(cutomerDto.getName(), cutomerDto.getCpf(), cutomerDto.getEmail(), cutomerDto.getPhone());
        return new CustomerResponseDto(customerRepository.save(customerEntity));
    }


    public CustomerResponseDto update(CustomerRequestDto body, Long id) {
        CustomerEntity customerEntity = customerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        customerEntity.setFullName(body.getName());
        customerEntity.setCpf(body.getCpf());
        customerEntity.setEmail(body.getEmail());
        customerEntity.setPhoneNumber(body.getPhone());
        return new CustomerResponseDto(customerRepository.save(customerEntity));
    }

    public void delete(Long id) {
        customerRepository.deleteById(id);
    }
}
