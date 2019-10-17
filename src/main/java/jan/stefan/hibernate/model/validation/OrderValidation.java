package jan.stefan.hibernate.model.validation;

import jan.stefan.hibernate.dto.modelDto.MyOrderDto;
import jan.stefan.hibernate.model.validation.generic.Validator;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class OrderValidation implements Validator<MyOrderDto>
{
    Map<String, String> errors = new HashMap<>();

    @Override
    public Map<String, String> validate(MyOrderDto myOrderDto)
    {
        errors.clear();


        return null;
    }

    @Override
    public boolean hasErrors()
    {
        return !errors.isEmpty();
    }

    public boolean isNameValid(MyOrderDto myOrderDto)
    {
        return myOrderDto.getCustomerDto().getName() != null && myOrderDto.getCustomerDto().getName().matches("^[A-Z ]+$");
    }

    public boolean isSurnameValid(MyOrderDto myOrderDto)
    {
        return myOrderDto.getCustomerDto().getSurname() != null && myOrderDto.getCustomerDto().getSurname().matches("^[A-Z ]+$");
    }

    public boolean isDateValid(MyOrderDto myOrderDto)
    {
        return myOrderDto.getDateTime() != null && myOrderDto.getDateTime().isAfter(LocalDateTime.now());
    }




}
