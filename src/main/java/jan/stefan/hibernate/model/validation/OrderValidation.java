package jan.stefan.hibernate.model.validation;

import jan.stefan.hibernate.dto.modelDto.OrderDto;
import jan.stefan.hibernate.model.validation.generic.Validator;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class OrderValidation implements Validator<OrderDto>
{
    Map<String, String> errors = new HashMap<>();

    @Override
    public Map<String, String> validate(OrderDto orderDto)
    {
        errors.clear();


        return null;
    }

    @Override
    public boolean hasErrors()
    {
        return !errors.isEmpty();
    }

    public boolean isNameValid(OrderDto orderDto)
    {
        return orderDto.getCustomerDto().getName() != null && orderDto.getCustomerDto().getName().matches("^[A-Z ]+$");
    }

    public boolean isSurnameValid(OrderDto orderDto)
    {
        return orderDto.getCustomerDto().getSurname() != null && orderDto.getCustomerDto().getSurname().matches("^[A-Z ]+$");
    }

    public boolean isDateValid(OrderDto orderDto)
    {
        return orderDto.getDateTime() != null && orderDto.getDateTime().isAfter(LocalDateTime.now());
    }




}
