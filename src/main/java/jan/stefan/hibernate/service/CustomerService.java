package jan.stefan.hibernate.service;

import jan.stefan.hibernate.dataInDbValidation.DataBaseValidator;
import jan.stefan.hibernate.dto.modelDto.CustomerDto;
import jan.stefan.hibernate.dto.modelDto.MyErrorDto;
import jan.stefan.hibernate.exceptions.MyException;
import jan.stefan.hibernate.model.Country;
import jan.stefan.hibernate.model.Customer;
import jan.stefan.hibernate.model.validation.CustomerValidation;
import jan.stefan.hibernate.repository.repositoryInterfaces.CustomerRepository;
import jan.stefan.hibernate.service.mappers.ModelMapper;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CustomerService
{
    private final CustomerRepository customerRepository;
    private final DataBaseValidator dataBaseValidator;

    public CustomerDto addOrUpdate(CustomerDto customerDto)
    {
        if (customerDto == null)
        {
            throw new MyException("CUSTOMER SERVICE: addOrUpdate() : Customer object argument is null");
        }

        Customer customer = ModelMapper.fromCustomerDtoToCustomer(customerDto);
        //Customer customerDb = dataBaseValidator.customerDbValidator(customerDto);
        Country result = dataBaseValidator.countryDbValidator(customerDto.getCountryDto());
        //customer.setName(customerDb.getName());
        //customer.setSurname(customerDb.getSurname());
        //customer.setAge(customerDb.getAge());
        //customer.setEmail(customerDb.getEmail());
        customer.setCountry(result);

        return ModelMapper.fromCustomerToCustomerDto(customerRepository
                .saveOrUpdate(customer)
                .orElseThrow(()-> new MyException("CUSTOMER SERVICE: Cannot addOrUpdate() customer")
        ));
    }

    public List<CustomerDto> findAll()
    {
        return customerRepository
                .findAll()
                .stream()
                .map(ModelMapper::fromCustomerToCustomerDto)
                .collect(Collectors.toList());
    }

    public void delete(Long id)
    {
        customerRepository.delete(id);
    }


    public CustomerDto findOneById(Long id)
    {
        if (id == null)
        {
            throw new MyException("CUSTOMER SERVICE: findOneById() : ID argument is null");
        }
        return customerRepository
                .findById(id)
                .map(ModelMapper::fromCustomerToCustomerDto)
                .orElseThrow(() -> new MyException("CUSTOMER SERVICE: findOneById() : Cannot find id: " + id));
    }

    public CustomerDto findOneByEmail (String email)
    {
        if (email == null)
        {
            throw new MyException("CUSTOMER SERVICE: findOneByEmail() : email argument is null");
        }
        return customerRepository
                .findOneByEmail(email)
                .map(ModelMapper::fromCustomerToCustomerDto)
                .orElseThrow(() -> new MyException("CUSTOMER SERVICE: findOneByEmail() : Cannot find email: " + email));

    }


}
