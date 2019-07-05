package jan.stefan.hibernate.model.validation;

import jan.stefan.hibernate.model.Category;
import jan.stefan.hibernate.model.Country;

import java.util.HashMap;
import java.util.Map;

public class CategoryValidation implements Validator<Category>
{
    private Map<String, String> errors = new HashMap<>();

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

    @Override
    public boolean hasErrors()
    {
        return !errors.isEmpty();
    }

    private boolean isNameValid(Category category)
    {
        return category.getName() != null && category.getName().matches("^[A-Z ]+$");
    }



}
