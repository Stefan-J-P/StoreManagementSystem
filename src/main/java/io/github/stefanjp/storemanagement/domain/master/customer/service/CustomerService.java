package io.github.stefanjp.storemanagement.domain.master.customer.service;

import io.github.stefanjp.storemanagement.domain.master.customer.dto.CustomerCreateRequest;
import io.github.stefanjp.storemanagement.domain.master.customer.dto.CustomerResponse;
import io.github.stefanjp.storemanagement.domain.master.customer.entity.Customer;
import io.github.stefanjp.storemanagement.domain.master.customer.repository.CustomerRepository;
import io.github.stefanjp.storemanagement.domain.reference.country.entity.Country;
import io.github.stefanjp.storemanagement.domain.reference.country.repository.CountryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CountryRepository countryRepository;

    @Transactional
    public CustomerResponse create(CustomerCreateRequest request) {
        Country country = countryRepository.findById(request.countryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Country not found"));

        Customer customer = Customer.builder()
                .firstName(request.firstName().trim())
                .lastName(request.lastName().trim())
                .email(request.email().trim())
                .age(request.age())
                .country(country)
                .build();

        Customer saved = customerRepository.save(customer);
        return toResponse(saved);
    }

    public List<CustomerResponse> findAll() {
        return customerRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public CustomerResponse findById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        return toResponse(customer);
    }

    private CustomerResponse toResponse(Customer customer) {
        Country country = customer.getCountry();
        return new CustomerResponse(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getAge(),
                country.getId(),
                country.getName()
        );
    }
}

