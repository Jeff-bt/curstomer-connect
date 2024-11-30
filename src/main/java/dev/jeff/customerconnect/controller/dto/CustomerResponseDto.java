package dev.jeff.customerconnect.controller.dto;

import dev.jeff.customerconnect.entity.CustomerEntity;

import java.time.LocalDateTime;

public class CustomerResponseDto {
    private Long id;
    private String name;
    private String cpf;
    private String email;
    private String phone;
    private LocalDateTime created;
    private LocalDateTime updated;

    public CustomerResponseDto(CustomerEntity entity) {
        this.id = entity.getId();
        this.name = entity.getFullName();
        this.cpf = entity.getCpf();
        this.email = entity.getEmail();
        this.phone = entity.getPhoneNumber();
        this.created = entity.getCreatedAt();
        this.updated = entity.getUpdatedAt();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }
}
