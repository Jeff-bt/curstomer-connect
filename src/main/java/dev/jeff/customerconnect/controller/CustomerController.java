package dev.jeff.customerconnect.controller;

import dev.jeff.customerconnect.dto.CustomerRequestDto;
import dev.jeff.customerconnect.dto.CustomerResponseDto;
import dev.jeff.customerconnect.entity.Customer;
import dev.jeff.customerconnect.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<Page<Customer>> findAll(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                  @RequestParam(name = "pageSize", defaultValue = "3") Integer pageSize,
                                                  @RequestParam(name = "sorter", defaultValue = "Desc") String sorter,
                                                  @RequestParam(name = "email", required = false) String email,
                                                  @RequestParam(name = "cpf", required = false) String cpf
    ) {

        Page<Customer> customers = customerService.findAll(page, pageSize, sorter, email, cpf);
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> findById(@PathVariable Long id) {
        CustomerResponseDto customerDto = customerService.findById(id);
        return ResponseEntity.ok(customerDto);
    }

    @PostMapping
    public ResponseEntity<CustomerResponseDto> create(@RequestBody CustomerRequestDto body) {
        CustomerResponseDto customerDto = customerService.create(body);
        return ResponseEntity.ok(customerDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> update(@RequestBody CustomerRequestDto body, @PathVariable Long id) {
        CustomerResponseDto customerDto = customerService.update(body, id);
        return ResponseEntity.ok(customerDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

