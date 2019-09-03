package jan.stefan.hibernate.service;

import jan.stefan.hibernate.dataInDbValidation.DataBaseValidator;
import jan.stefan.hibernate.dto.modelDto.OrderDto;
import jan.stefan.hibernate.exceptions.MyException;
import jan.stefan.hibernate.model.Customer;
import jan.stefan.hibernate.model.Order;
import jan.stefan.hibernate.model.Product;
import jan.stefan.hibernate.repository.repositoryInterfaces.OrderRepository;
import jan.stefan.hibernate.service.mappers.ModelMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class OrderService
{
    private final OrderRepository orderRepository;
    private final DataBaseValidator dataBaseValidator;

    public OrderDto addOrUpdate(OrderDto orderDto)
    {
        if (orderDto == null)
        {
            throw new MyException("CUSTOMER ORDER SERVICE: customerOrderDto object argument is null");
        }

        Order order = ModelMapper.fromCustomerOrderDtoToCustomerOrder(orderDto);
        Customer customer = dataBaseValidator.customerDbValidator(orderDto.getCustomerDto());
        Product product = dataBaseValidator.productDbValidator(orderDto.getProductDto());

        order.setCustomer(customer);
        order.setProduct(product);

        return ModelMapper.fromCustomerOrderToCustomerOrderDto(orderRepository
                .saveOrUpdate(order)
                .orElseThrow(() -> new MyException("CUSTOMER ORDER SERVICE: cannot addOrUpdate() customerOrderDto"))
        );
    }

    public List<OrderDto> findAll()
    {
        return orderRepository
                .findAll()
                .stream()
                .map(ModelMapper::fromCustomerOrderToCustomerOrderDto)
                .collect(Collectors.toList());
    }

    public void delete(Long id)
    {
        orderRepository.delete(id);
    }

    public OrderDto findOneById(Long id)
    {
        return orderRepository
                .findById(id)
                .map(ModelMapper::fromCustomerOrderToCustomerOrderDto)
                .orElseThrow(() -> new MyException("CUSTOMER ORDER SERVICE: addOrUpdate() cannot find customer order id: " + id));
    }

    // DLACZEGO NIE WIDZI TEJ METODY ???
    public OrderDto findOneByNumber(Integer number)
    {
        return orderRepository
                .findOneByNumber(number)
                .map(ModelMapper::fromCustomerOrderToCustomerOrderDto)
                .orElseThrow(() -> new MyException("CUSTOMER ORDER SERVICE: addOrUpdate() cannot find customer order id: " + number));
    }






}



















