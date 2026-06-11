package jan.stefan.hibernate.service;

import jan.stefan.hibernate.dataInDbValidation.DataBaseValidator;
import jan.stefan.hibernate.dto.modelDto.CountryDto;
import jan.stefan.hibernate.dto.modelDto.MyOrderDto;
import jan.stefan.hibernate.exceptions.MyException;
import jan.stefan.hibernate.model.Customer;
import jan.stefan.hibernate.model.MyOrder;
import jan.stefan.hibernate.model.Product;
import jan.stefan.hibernate.repository.repositoryInterfaces.OrderRepository;
import jan.stefan.hibernate.service.mappers.ModelMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class MyOrderService
{
    private final OrderRepository orderRepository;
    private final DataBaseValidator dataBaseValidator;

    public MyOrderDto addOrUpdate(MyOrderDto myOrderDto)
    {
        if (myOrderDto == null)
        {
            throw new MyException("ORDER SERVICE: customerOrderDto object argument is null");
        }

        MyOrder myOrder = ModelMapper.fromMyOrderDtoToMyOrder(myOrderDto);
        Customer customer = dataBaseValidator.customerDbValidator(myOrderDto.getCustomerDto());
        Product product = dataBaseValidator.productDbValidator(myOrderDto.getProductDto());

        myOrder.setCustomer(customer);
        myOrder.setProduct(product);

        return ModelMapper.fromMyOrderToMyOrderDto(orderRepository
                .saveOrUpdate(myOrder)
                .orElseThrow(() -> new MyException("ORDER SERVICE: cannot addOrUpdate() customerOrderDto"))
        );
    }

    public List<MyOrderDto> findAll()
    {
        return orderRepository
                .findAll()
                .stream()
                .map(ModelMapper::fromMyOrderToMyOrderDto)
                .collect(Collectors.toList());
    }

    public void delete(Long id)
    {
        orderRepository.delete(id);
    }

    public MyOrderDto findOneById(Long id)
    {
        return orderRepository
                .findById(id)
                .map(ModelMapper::fromMyOrderToMyOrderDto)
                .orElseThrow(() -> new MyException("ORDER SERVICE: addOrUpdate() cannot find customer order id: " + id));
    }

    public MyOrderDto findOneByNumber(Integer number)
    {
        return orderRepository
                .findOneByNumber(number)
                .map(ModelMapper::fromMyOrderToMyOrderDto)
                .orElseThrow(() -> new MyException("ORDER SERVICE: findOneByNumber() : cannot find order number: " + number));
    }

    public Integer generateOrderNumber()
    {
        return orderRepository
                .findLastOrderNumber()
                .orElseThrow(() -> new MyException("ORDER: generateOrderNumber() : ERROR: "));
    }


    public List<MyOrderDto> getProductsByCustomerAndCountry(CountryDto countryDto, int ageFrom, int ageTo)
    {
        return orderRepository
                .getProductsByCustomersCountry(countryDto, ageFrom, ageTo)
                .stream()
                .map(ModelMapper::fromMyOrderToMyOrderDto)
                .collect(Collectors.toList());

    }





}



















