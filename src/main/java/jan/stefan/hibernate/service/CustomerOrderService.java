package jan.stefan.hibernate.service;

import jan.stefan.hibernate.dto.CustomerOrderDto;
import jan.stefan.hibernate.exceptions.MyException;
import jan.stefan.hibernate.model.CustomerOrder;
import jan.stefan.hibernate.repository.repositoryInterfaces.CustomerOrderRepository;
import jan.stefan.hibernate.service.mappers.ModelMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CustomerOrderService
{
    private final CustomerOrderRepository customerOrderRepository;

    public CustomerOrderDto addOrUpdate(CustomerOrderDto customerOrderDto)
    {
        if (customerOrderDto == null)
        {
            throw new MyException("CUSTOMER ORDER SERVICE: customerOrderDto object argument is null");
        }

        CustomerOrder customerOrder = ModelMapper.fromCustomerOrderDtoToCustomerOrder(customerOrderDto);
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
