package jan.stefan.hibernate.model.validation;

import jan.stefan.hibernate.dto.CustomerDto;
import jan.stefan.hibernate.dto.ShopDto;

import java.util.HashMap;
import java.util.Map;

public class ShopValidation implements Validator<ShopDto>
{

    private Map<String, String> errors = new HashMap<>();

    @Override
    public Map<String, String> validate(ShopDto shopDto)
    {
        errors.clear();


        if (!isNameValid(shopDto))
        {
            errors.put("NAME:" + shopDto.getName(), "SHOP NAME IS NOT VALID: Block letters only");
            return errors;
        }

        if (!isCountryNameValid(shopDto))
        {
            errors.put("COUNTRY NAME: " + shopDto.getName(), "COUNTRY NAME IS NOT VALID: Block letters only");
        }

        return errors;
    }

    @Override
    public boolean hasErrors()
    {
        return !errors.isEmpty();
    }

    private boolean isNameValid(ShopDto shopDto)
    {
        return shopDto.getName() != null && shopDto.getName().matches("^[A-Z ]+$");
    }

    private boolean isCountryNameValid(ShopDto shopDto)
    {
        return shopDto.getCountryDto().getName() != null && shopDto.getCountryDto().getName().matches("^[A-Z ]+$");
    }






}
