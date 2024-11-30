package dev.jeff.customerconnect.controller;

import dev.jeff.customerconnect.controller.dto.ApiResponse;
import dev.jeff.customerconnect.controller.dto.CustomerRequestDto;
import dev.jeff.customerconnect.controller.dto.CustomerResponseDto;
import dev.jeff.customerconnect.controller.dto.PaginationResponse;
import dev.jeff.customerconnect.entity.CustomerEntity;
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
    public ResponseEntity<ApiResponse<CustomerEntity>> findAll(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                               @RequestParam(name = "pageSize", defaultValue = "3") Integer pageSize,
                                                               @RequestParam(name = "orderBy", defaultValue = "desc") String orderBy,
                                                               @RequestParam(name = "email", required = false) String email,
                                                               @RequestParam(name = "cpf", required = false) String cpf
    ) {

        Page<CustomerEntity> pageResp = customerService.findAll(page, pageSize, orderBy, email, cpf);
        return ResponseEntity.ok(new ApiResponse<>(
                pageResp.getContent(),
                new PaginationResponse(pageResp.getNumber(), pageResp.getSize(), pageResp.getTotalElements(), pageResp.getTotalPages())
        ));
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

