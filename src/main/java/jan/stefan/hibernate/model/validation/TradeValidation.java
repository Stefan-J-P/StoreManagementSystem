package jan.stefan.hibernate.model.validation;

import jan.stefan.hibernate.dto.modelDto.CustomerDto;
import jan.stefan.hibernate.dto.modelDto.StockDto;
import jan.stefan.hibernate.dto.modelDto.TradeDto;
import jan.stefan.hibernate.model.validation.generic.Validator;

import java.util.HashMap;
import java.util.Map;

public class TradeValidation implements Validator<TradeDto>
{
    Map<String, String> errors = new HashMap<>();


    @Override
    public Map<String, String> validate(TradeDto tradeDto)
    {
        errors.clear();

        if (!isNameValid(tradeDto))
        {
            errors.put("NAME: " + tradeDto.getName(), "IS NOT VALID: BLOCK LETTERS ONLY");
            return errors;
        }

        return errors;
    }

    @Override
    public boolean hasErrors()
    {
        return false;
    }

    private boolean isNameValid(TradeDto tradeDto)
    {
        return tradeDto.getName() != null && tradeDto.getName().matches("^[A-Z ]+$");
    }
}
