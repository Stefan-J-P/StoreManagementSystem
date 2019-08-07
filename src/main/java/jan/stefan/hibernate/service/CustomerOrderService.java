package jan.stefan.hibernate.service;

import jan.stefan.hibernate.dataInDbValidation.DataBaseValidator;
import jan.stefan.hibernate.dto.modelDto.CustomerDto;
import jan.stefan.hibernate.dto.modelDto.CustomerOrderDto;
import jan.stefan.hibernate.dto.modelDto.ProductDto;
import jan.stefan.hibernate.exceptions.MyException;
import jan.stefan.hibernate.model.Customer;
import jan.stefan.hibernate.model.CustomerOrder;
import jan.stefan.hibernate.model.Product;
import jan.stefan.hibernate.repository.repositoryInterfaces.CustomerOrderRepository;
import jan.stefan.hibernate.repository.repositoryInterfaces.CustomerRepository;
import jan.stefan.hibernate.repository.repositoryInterfaces.PaymentRepository;
import jan.stefan.hibernate.repository.repositoryInterfaces.ProductRepository;
import jan.stefan.hibernate.service.mappers.ModelMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CustomerOrderService
{
    private final CustomerOrderRepository customerOrderRepository;
    private final DataBaseValidator dataBaseValidator;

    public CustomerOrderDto addOrUpdate(CustomerOrderDto customerOrderDto)
    {
        if (customerOrderDto == null)
        {
            throw new MyException("CUSTOMER ORDER SERVICE: customerOrderDto object argument is null");
        }

        CustomerOrder customerOrder = ModelMapper.fromCustomerOrderDtoToCustomerOrder(customerOrderDto);
        Customer customer = dataBaseValidator.customerDbValidator(customerOrderDto.getCustomerDto());
        Product product = dataBaseValidator.productDbValidator(customerOrderDto.getProductDto());

        customerOrder.setCustomer(customer);
        customerOrder.setProduct(product);

        return ModelMapper.fromCustomerOrderToCustomerOrderDto(customerOrderRepository
                .saveOrUpdate(customerOrder)
                .orElseThrow(() -> new MyException("CUSTOMER ORDER SERVICE: cannot addOrUpdate() customerOrderDto"))
        );
    }

    public List<CustomerOrderDto> findAll()
    {
        return customerOrderRepository
                .findAll()
                .stream()
                .map(ModelMapper::fromCustomerOrderToCustomerOrderDto)
                .collect(Collectors.toList());
    }

    public void delete(Long id)
    {
        customerOrderRepository.delete(id);
    }

    public CustomerOrderDto findOneById(Long id)
    {
        return customerOrderRepository
                .findById(id)
                .map(ModelMapper::fromCustomerOrderToCustomerOrderDto)
                .orElseThrow(() -> new MyException("CUSTOMER ORDER SERVICE: addOrUpdate() cannot find customer order id: " + id));
    }





}



















