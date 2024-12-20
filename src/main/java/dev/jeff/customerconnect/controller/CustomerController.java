package dev.jeff.customerconnect.controller;

import dev.jeff.customerconnect.controller.dto.ApiResponseDto;
import dev.jeff.customerconnect.controller.dto.CreateCustomerDto;
import dev.jeff.customerconnect.controller.dto.PaginationResponseDto;
import dev.jeff.customerconnect.controller.dto.UpdateCustomerDto;
import dev.jeff.customerconnect.entity.CustomerEntity;
import dev.jeff.customerconnect.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto<CustomerEntity>> findAll(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                                  @RequestParam(name = "pageSize", defaultValue = "3") Integer pageSize,
                                                                  @RequestParam(name = "orderBy", defaultValue = "desc") String orderBy,
                                                                  @RequestParam(name = "email", required = false) String email,
                                                                  @RequestParam(name = "cpf", required = false) String cpf
    ) {

        Page<CustomerEntity> pageResp = customerService.findAll(page, pageSize, orderBy, email, cpf);
        return ResponseEntity.ok(new ApiResponseDto<>(
                pageResp.getContent(),
                new PaginationResponseDto(pageResp.getNumber(), pageResp.getSize(), pageResp.getTotalElements(), pageResp.getTotalPages())
        ));
    }

    @GetMapping(path = "/{customerId}")
    public ResponseEntity<CustomerEntity> findById(@PathVariable("customerId") Long id) {
        Optional<CustomerEntity> customer = customerService.findById(id);
        return customer.isPresent() ?
                ResponseEntity.ok(customer.get()) :
                ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CreateCustomerDto body) {
        CustomerEntity customer = customerService.create(body);
        return ResponseEntity.created(URI.create("/customers/" + customer.getId())).build();
    }

    @PutMapping(path = "/{customerId}")
    public ResponseEntity<CustomerEntity> update(@RequestBody UpdateCustomerDto body, @PathVariable("customerId") Long customerId) {
        Optional<CustomerEntity> customer = customerService.update(body, customerId);
        return customer.isPresent() ?
                ResponseEntity.ok(customer.get()) :
                ResponseEntity.notFound().build();
    }

    @DeleteMapping(path = "/{customerId}")
    public ResponseEntity<Void> delete(@PathVariable("customerId") Long customerId) {
        boolean deleted = customerService.deleteById(customerId);
        return deleted ?
                ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }
}

