package dev.jeff.customerconnect.service;

import dev.jeff.customerconnect.dto.CustomerRequestDto;
import dev.jeff.customerconnect.dto.CustomerResponseDto;
import dev.jeff.customerconnect.entity.Customer;
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

    public Page<Customer> findAll(Integer page, Integer pageSize, String sorter, String email, String cpf) {
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
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        return new CustomerResponseDto(customer);
    }

    public CustomerResponseDto create(CustomerRequestDto cutomerDto) {
        Customer customer = new Customer(cutomerDto.getName(), cutomerDto.getCpf(), cutomerDto.getEmail(), cutomerDto.getPhone());
        return new CustomerResponseDto(customerRepository.save(customer));
    }


    public CustomerResponseDto update(CustomerRequestDto body, Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        customer.setName(body.getName());
        customer.setCpf(body.getCpf());
        customer.setEmail(body.getEmail());
        customer.setPhone(body.getPhone());
        return new CustomerResponseDto(customerRepository.save(customer));
    }

    public void delete(Long id) {
        customerRepository.deleteById(id);
    }
}
