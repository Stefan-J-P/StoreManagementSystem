package jan.stefan.hibernate.model.validation;

import jan.stefan.hibernate.dto.modelDto.CustomerOrderDto;
import jan.stefan.hibernate.model.validation.generic.Validator;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class CustomerOrderValidation implements Validator<CustomerOrderDto>
{
    Map<String, String> errors = new HashMap<>();

    @Override
    public Map<String, String> validate(CustomerOrderDto customerOrderDto)
    {
        errors.clear();


        return null;
    }

    @Override
    public boolean hasErrors()
    {
        return !errors.isEmpty();
    }

    public boolean isNameValid(CustomerOrderDto customerOrderDto)
    {
        return customerOrderDto.getCustomerDto().getName() != null && customerOrderDto.getCustomerDto().getName().matches("^[A-Z ]+$");
    }

    public boolean isSurnameValid(CustomerOrderDto customerOrderDto)
    {
        return customerOrderDto.getCustomerDto().getSurname() != null && customerOrderDto.getCustomerDto().getSurname().matches("^[A-Z ]+$");
    }

    public boolean isDateValid(CustomerOrderDto customerOrderDto)
    {
        return customerOrderDto.getDateTime() != null && customerOrderDto.getDateTime().isAfter(LocalDateTime.now());
    }




}
