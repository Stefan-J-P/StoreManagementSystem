package jan.stefan.hibernate.model.validation;

import jan.stefan.hibernate.dto.modelDto.CategoryDto;
import jan.stefan.hibernate.model.Category;
import jan.stefan.hibernate.model.validation.generic.AbstractValidator;
import jan.stefan.hibernate.model.validation.generic.Validator;

import java.util.HashMap;
import java.util.Map;

public class CategoryValidation implements Validator<CategoryDto>
{
    private Map<String, String> errors = new HashMap<>();

    @Override
    public Map<String, String> validate(CategoryDto categoryDto)
    {
        errors.clear();

        if (categoryDto == null)
        {
            errors.put("CATEGORY", "NULL");
            return errors;
        }

        if (!isNameValid(categoryDto))
        {
            errors.put("CATEGORY NAME: " + categoryDto.getName(), "IS NOT VALID");
        }
        return errors;
    }

    @Override
    public boolean hasErrors()
    {
        return false;
    }

    private boolean isNameValid(CategoryDto categoryDto)
    {
        return categoryDto.getName() != null && categoryDto.getName().matches("^[A-Z ]+$");
    }



}
