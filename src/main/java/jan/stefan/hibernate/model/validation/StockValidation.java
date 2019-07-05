package jan.stefan.hibernate.model.validation;

import jan.stefan.hibernate.dto.modelDto.StockDto;

import java.util.HashMap;
import java.util.Map;

public class StockValidation implements Validator<StockDto>
{
    Map<String, String> errors = new HashMap<>();

    @Override
    public Map<String, String> validate(StockDto stockDto)
    {
        //errors.clear();




        return null;
    }

    @Override
    public boolean hasErrors()
    {
        return !errors.isEmpty();
    }






}
