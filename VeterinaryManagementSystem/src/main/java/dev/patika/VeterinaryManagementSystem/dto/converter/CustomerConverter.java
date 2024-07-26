package dev.patika.VeterinaryManagementSystem.dto.converter;

import dev.patika.VeterinaryManagementSystem.dto.request.customer.CustomerSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.customer.CustomerUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.CustomerResponse;
import dev.patika.VeterinaryManagementSystem.entities.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerConverter {

    public Customer convertToCustomer(CustomerSaveRequest customerSaveRequest) {
        if (customerSaveRequest == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.setName(customerSaveRequest.getName());
        customer.setPhone(customerSaveRequest.getPhone());
        customer.setMail(customerSaveRequest.getMail());
        customer.setAddress(customerSaveRequest.getAddress());
        customer.setCity(customerSaveRequest.getCity());
        return customer;
    }

    public Customer convertToUpdatedCustomer(CustomerUpdateRequest customerUpdateRequest) {
        if (customerUpdateRequest == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.setId(customerUpdateRequest.getId());
        customer.setName(customerUpdateRequest.getName());
        customer.setPhone(customerUpdateRequest.getPhone());
        customer.setMail(customerUpdateRequest.getMail());
        customer.setAddress(customerUpdateRequest.getAddress());
        customer.setCity(customerUpdateRequest.getCity());
        return customer;
    }

    public CustomerResponse toCustomerResponse(Customer customer) {
        if (customer == null) {
            return null;
        }
        CustomerResponse response = new CustomerResponse();
        response.setId(customer.getId());
        response.setName(customer.getName());
        response.setPhone(customer.getPhone());
        response.setMail(customer.getMail());
        response.setAddress(customer.getAddress());
        response.setCity(customer.getCity());
        return response;
    }
}
