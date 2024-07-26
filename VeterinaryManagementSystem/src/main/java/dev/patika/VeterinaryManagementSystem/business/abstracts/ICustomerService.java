package dev.patika.VeterinaryManagementSystem.business.abstracts;

import dev.patika.VeterinaryManagementSystem.core.result.Result;
import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.dto.request.customer.CustomerSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.customer.CustomerUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.CustomerResponse;
import dev.patika.VeterinaryManagementSystem.entities.Customer;
import org.springframework.data.domain.Page;
import java.util.List;

public interface ICustomerService {

    ResultData<CustomerResponse> saveCustomer(CustomerSaveRequest customerSaveRequest);

    ResultData<CustomerResponse> updateCustomer(CustomerUpdateRequest customerUpdateRequest);

    ResultData<CustomerResponse> findCustomerById(Long id);

    ResultData<List<CustomerResponse>> findCustomerByName(String name);

    ResultData<List<CustomerResponse>> findAllCustomers();

    Result deleteCustomer(Long id);
}
