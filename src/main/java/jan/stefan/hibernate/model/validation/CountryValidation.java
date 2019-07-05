package jan.stefan.hibernate.model.validation;

import jan.stefan.hibernate.dto.CountryDto;

import java.util.HashMap;
import java.util.Map;

public class CountryValidation implements Validator<CountryDto>
{
    Map<String, String> errors = new HashMap<>();

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

    @Override
    public boolean hasErrors()
    {
        return !errors.isEmpty();
    }


    public boolean isNameValid(CountryDto countryDto)
    {
        return countryDto.getName() != null && countryDto.getName().matches("^[A-Z ]+$");
    }
}
