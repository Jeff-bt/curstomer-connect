package dev.jeff.customerconnect.dto;

import dev.jeff.customerconnect.entity.Customer;

import java.sql.Timestamp;

public class CustomerResponseDto {
    private Long id;
    private String name;
    private String cpf;
    private String email;
    private String phone;
    private Timestamp created;
    private Timestamp updated;

    public CustomerResponseDto(Customer entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.cpf = entity.getCpf();
        this.email = entity.getEmail();
        this.phone = entity.getPhone();
        this.created = entity.getCreated();
        this.updated = entity.getUpdated();
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

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }
}
