package jan.stefan.hibernate.model.validation;

import jan.stefan.hibernate.dto.modelDto.ProducerDto;
import jan.stefan.hibernate.model.validation.generic.Validator;

import java.util.HashMap;
import java.util.Map;

public class ProducerValidation implements Validator<ProducerDto>
{

    private Map<String, String> errors = new HashMap<>();


    @Override
    public Map<String, String> validate(ProducerDto producerDto)
    {
        errors.clear();

        if (!isNameValid(producerDto))
        {
            errors.put("PRODUCER NAME: " + producerDto.getName(), "IS NOT VALID: Block letters only");
        }

        if (!isCountryNameValid(producerDto))
        {
            errors.put("COUNTRY NAME: " + producerDto.getCountryDto().getName(), "IS NOT VALID: Block letters only");
        }

        if (!isTradeNameValid(producerDto))
        {
            errors.put("TRADE NAME: " + producerDto.getTradeDto().getName(), "IS NOT VALID: Block letters only");
        }

        return errors;
    }

    public boolean hasErrors()
    {
        return !errors.isEmpty();
    }

    private boolean isNameValid(ProducerDto producerDto)
    {
        return producerDto.getName() != null && producerDto.getName().matches("^[A-Z ]+$");
    }

    private boolean isCountryNameValid(ProducerDto producerDto)
    {
        return producerDto.getCountryDto().getName() != null && producerDto.getCountryDto().getName().matches("^[A-Z ]+$");
    }

    private boolean isTradeNameValid(ProducerDto producerDto)
    {
        return producerDto.getTradeDto().getName() != null && producerDto.getTradeDto().getName().matches("^[A-Z ]+$");
    }
}
