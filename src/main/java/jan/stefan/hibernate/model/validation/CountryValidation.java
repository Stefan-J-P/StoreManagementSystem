package jan.stefan.hibernate.model.validation;

import jan.stefan.hibernate.dto.modelDto.CountryDto;
import jan.stefan.hibernate.model.Country;
import jan.stefan.hibernate.model.validation.generic.AbstractValidator;
import jan.stefan.hibernate.model.validation.generic.Validator;

import java.util.HashMap;
import java.util.Map;

public class CountryValidation extends AbstractValidator<CountryDto>
{

    @Override
    public Map<String, String> validate(CountryDto countryDto)
    {
        errors.clear();

        if (!isNameValid(countryDto))
        {
            errors.put("NAME: " + countryDto.getName(), "IS NOT VALID: Block letters only");
            return errors;
        }

        return errors;
    }



    public boolean isNameValid(CountryDto countryDto)
    {
        return countryDto.getName() != null && countryDto.getName().matches("^[A-Z ]+$");
    }
}
