package dev.patika.VeterinaryManagementSystem.api;

import dev.patika.VeterinaryManagementSystem.business.abstracts.ICustomerService;
import dev.patika.VeterinaryManagementSystem.core.result.Result;
import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.dto.request.customer.CustomerSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.customer.CustomerUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.AnimalResponse;
import dev.patika.VeterinaryManagementSystem.dto.response.CustomerResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/customer")
public class CustomerController {

    private final ICustomerService customerService;

    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<CustomerResponse> saveCustomer(@Valid @RequestBody CustomerSaveRequest customerSaveRequest) {
        return this.customerService.saveCustomer(customerSaveRequest);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CustomerResponse> findCustomerById(@PathVariable("id") Long id) {
        return this.customerService.findCustomerById(id);
    }
    // İsimle hayvan bulmak için
    @GetMapping("/name/{customerName}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<CustomerResponse>> findCustomerByName(@PathVariable("customerName") String name) {
        return this.customerService.findCustomerByName(name);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<CustomerResponse>> findAllCustomers() {
        return this.customerService.findAllCustomers();
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CustomerResponse> updateCustomer(@Valid @RequestBody CustomerUpdateRequest customerUpdateRequest) {
        return this.customerService.updateCustomer(customerUpdateRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result deleteCustomer(@PathVariable("id") Long id) {
        return this.customerService.deleteCustomer(id);
    }
}
