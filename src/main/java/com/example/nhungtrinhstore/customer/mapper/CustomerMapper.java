package com.example.nhungtrinhstore.customer.mapper;

import com.example.nhungtrinhstore.customer.dto.CustomerRequest;
import com.example.nhungtrinhstore.customer.dto.CustomerResponse;
import com.example.nhungtrinhstore.customer.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerResponse toResponse(Customer customer) {
        return new CustomerResponse(
            customer.getId(),
            customer.getFullName(),
            customer.getFacebookUrl(),
            customer.getPhoneNumber(),
            customer.getAddress(),
            customer.getCreatedAt()
        );
    }

    public Customer toEntity(CustomerRequest request) {
        return Customer.builder()
            .fullName(request.fullName())
            .facebookUrl(request.facebookUrl())
            .phoneNumber(request.phoneNumber())
            .address(request.address())
            .build();
    }

    public void updateEntity(Customer customer, CustomerRequest request) {
        customer.setFullName(request.fullName());
        customer.setFacebookUrl(request.facebookUrl());
        customer.setPhoneNumber(request.phoneNumber());
        customer.setAddress(request.address());
    }
}
