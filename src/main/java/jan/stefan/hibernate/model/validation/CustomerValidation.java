package jan.stefan.hibernate.model.validation;

import jan.stefan.hibernate.dto.modelDto.CustomerDto;

import java.util.HashMap;
import java.util.Map;

public class CustomerValidation implements Validator<CustomerDto>
{
    private Map<String, String> errors = new HashMap<>();

    @Override
    public Map<String, String> validate(CustomerDto customerDto)
    {
        errors.clear();

        if (!isNameValid(customerDto))
        {
            errors.put("NAME: " + customerDto.getName(), "IS NOT VALID: BLOCK LETTERS ONLY");
            return errors;
        }

        if (!isSurNameValid(customerDto))
        {
            errors.put("SURNAME: " + customerDto.getSurname(), "IS NOT VALID: BLOCK LETTERS ONLY");
            return errors;
        }

        if (!isAgeValid(customerDto))
        {
            errors.put("AGE: " + customerDto.getAge(), "IS NOT CORRECT");
            return errors;
        }

        return errors;
    }

    @Override
    public boolean hasErrors()
    {
        return !errors.isEmpty();
    }

    private boolean isNameValid(CustomerDto customerDto)
    {
        return customerDto.getName() != null && customerDto.getName().matches("^[A-Z ]+$");
    }

    private boolean isSurNameValid(CustomerDto customerDto)
    {
        return customerDto.getSurname() != null && customerDto.getSurname().matches("^[A-Z ]+$");
    }

    private boolean isAgeValid(CustomerDto customerDto)
    {
        return customerDto.getAge() != null && customerDto.getAge() >= 18;
    }




}
