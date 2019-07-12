package jan.stefan.hibernate.model.validation;

import jan.stefan.hibernate.model.Category;
import jan.stefan.hibernate.model.validation.generic.AbstractValidator;
import jan.stefan.hibernate.model.validation.generic.Validator;

import java.util.HashMap;
import java.util.Map;

public class CategoryValidation extends AbstractValidator<Category>
{

    @Override
    public Map<String, String> validate(Category category)
    {
        errors.clear();

        if (category == null)
        {
            errors.put("CATEGORY", "NULL");
            return errors;
        }

        if (!isNameValid(category))
        {
            errors.put("CATEGORY NAME: " + category.getName(), "IS NOT VALID");
        }
        return errors;
    }


    private boolean isNameValid(Category category)
    {
        return category.getName() != null && category.getName().matches("^[A-Z ]+$");
    }



}
