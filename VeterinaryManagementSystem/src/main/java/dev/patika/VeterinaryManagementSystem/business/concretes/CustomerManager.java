package dev.patika.VeterinaryManagementSystem.business.concretes;

import dev.patika.VeterinaryManagementSystem.business.abstracts.ICustomerService;
import dev.patika.VeterinaryManagementSystem.core.result.Result;
import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.core.utilities.ResultInfo;
import dev.patika.VeterinaryManagementSystem.dao.CustomerRepository;
import dev.patika.VeterinaryManagementSystem.dto.converter.CustomerConverter;
import dev.patika.VeterinaryManagementSystem.dto.request.customer.CustomerSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.customer.CustomerUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.CustomerResponse;
import dev.patika.VeterinaryManagementSystem.entities.Customer;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerManager implements ICustomerService {

    private final CustomerRepository customerRepo;
    private final CustomerConverter customerConverter;

    public CustomerManager(CustomerRepository customerRepo, CustomerConverter customerConverter) {
        this.customerRepo = customerRepo;
        this.customerConverter = customerConverter;
    }

    @Override
    // Müşteriyi kaydeder
    public ResultData<CustomerResponse> saveCustomer(CustomerSaveRequest customerSaveRequest) {
        // Kaydedilecek müşteri nesnesini oluşturur
        Customer saveCustomer = this.customerConverter.convertToCustomer(customerSaveRequest);
        // Müşterinin var olup olmadığını kontrol eder
        checkCustomerExistence(saveCustomer);
        // Müşteriyi veritabanına kaydeder
        this.customerRepo.save(saveCustomer);
        // Başarıyla kaydedilen müşteri bilgilerini döner
        return ResultInfo.success(this.customerConverter.toCustomerResponse(saveCustomer));
    }

    @Override
    // Müşteriyi günceller
    public ResultData<CustomerResponse> updateCustomer(CustomerUpdateRequest customerUpdateRequest) {
        // Güncellenecek müşterinin var olup olmadığını kontrol eder
        findCustomerById(customerUpdateRequest.getId());
        // Güncellenmiş müşteri nesnesini oluşturur
        Customer updatedCustomer = this.customerConverter.convertToUpdatedCustomer(customerUpdateRequest);
        // Güncellenmiş müşteriyi veritabanına kaydeder
        updatedCustomer.setId(customerUpdateRequest.getId());
        this.customerRepo.save(updatedCustomer);
        // Başarıyla güncellenen müşteri bilgilerini döner
        return ResultInfo.success(this.customerConverter.toCustomerResponse(updatedCustomer));
    }

    // ID'ye göre müşteriyi bulur
    public Customer findCustomerId(Long customerId) {
        return this.customerRepo.findById(customerId).orElseThrow(()
                -> new EntityNotFoundException("ID " + customerId + " olan müşteri bulunamadı"));
    }

    @Override
    // ID'ye göre müşteriyi bulur
    public ResultData<CustomerResponse> findCustomerById(Long id) {
        Customer customer = findCustomerId(id);
        return ResultInfo.success(this.customerConverter.toCustomerResponse(customer));
    }

    @Override
    // İsimle müşterileri bulur
    public ResultData<List<CustomerResponse>> findCustomerByName(String name) {
        List<Customer> customers = customerRepo.findByName(name);
        if (customers.isEmpty()) {
            throw new EntityNotFoundException("İsim " + name + " olan müşteri bulunamadı.");
        }
        List<CustomerResponse> customerResponses = customers.stream()
                .map(this.customerConverter::toCustomerResponse).collect(Collectors.toList());
        return ResultInfo.success(customerResponses);
    }

    @Override
    // Tüm müşterileri listeler
    public ResultData<List<CustomerResponse>> findAllCustomers() {
        List<Customer> allCustomers = this.customerRepo.findAll();
        List<CustomerResponse> customerResponses = allCustomers.stream()
                .map(this.customerConverter::toCustomerResponse).collect(Collectors.toList());
        return ResultInfo.success(customerResponses);
    }

    @Override
    // ID'ye göre müşteriyi siler
    public Result deleteCustomer(Long id) {
        Customer customer = findCustomerId(id);
        this.customerRepo.delete(customer);
        return ResultInfo.ok();
    }

    // Müşterinin var olup olmadığını kontrol eder
    private void checkCustomerExistence(Customer customer) {
        if (customerRepo.existsByMail(customer.getMail())) {
            throw new IllegalArgumentException("Email adresi " + customer.getMail() + " zaten kayıtlı.");
        }
        if (customerRepo.existsByPhone(customer.getPhone())) {
            throw new IllegalArgumentException("Telefon numarası " + customer.getPhone() + " zaten kayıtlı.");
        }
    }
}
