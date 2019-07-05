package jan.stefan.hibernate.service;

import jan.stefan.hibernate.dto.CustomerDto;
import jan.stefan.hibernate.dto.MyErrorDto;
import jan.stefan.hibernate.exceptions.MyException;
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
    private final ScannerService scannerService;
    private final MyErrorService myErrorService;
    private final CustomerValidation customerValidation;


    public CustomerDto addOrUpdate(CustomerDto customerDto)
    {
        if (customerDto == null)
        {
            throw new MyException("CUSTOMER SERVICE: Customer object argument is null");
        }

        Customer customer = ModelMapper.fromCustomerDtoToCustomer(customerDto);
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
        return customerRepository
                .findById(id)
                .map(ModelMapper::fromCustomerToCustomerDto)
                .orElseThrow(() -> new MyException("CUSTOMER SERVICE: findOneById() : Cannot find id: " + id));
    }


    public CustomerDto addNewCustomer()
    {
        CustomerDto customerDto = new CustomerDto();

        customerDto.setName(scannerService.getString("Enter customer's name:"));
        customerDto.setSurname(scannerService.getString("Enter customer's surname:"));
        customerDto.setEmail(scannerService.getString("Enter customer's email:"));
        customerDto.setAge(scannerService.getInt("Enter customer's age:"));

        Map<String, String> customerErrors = customerValidation.validate(customerDto);

        if (!customerValidation.hasErrors())
        {
            addOrUpdate(customerDto);
        }
        else
        {
            customerErrors.forEach((k, v) -> System.out.println(k + " " + v));
            myErrorService.addOrUpdateOneMyError(MyErrorDto.builder()
                    .message("Error while inserting Customer into the table")
                    .dateTime(LocalDateTime.now())
                    .build());
        }
        return customerDto;
    }







}
