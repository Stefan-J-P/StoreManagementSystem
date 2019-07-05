package jan.stefan.hibernate.model.validation;

import jan.stefan.hibernate.dto.ProductDto;
import jan.stefan.hibernate.model.Product;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ProductValidation implements Validator<ProductDto>
{
    Map<String, String> errors = new HashMap<>();

    @Override
    public Map<String, String> validate(ProductDto productDto)
    {

        errors.clear();

        if (!isNameValid(productDto))
        {
            errors.put("NAME: " + productDto.getName(), "IS NOT VALID: Block letters only");
            return errors;
        }

        if (!isPriceValid(productDto))
        {
            errors.put("PRICE: " + productDto.getPrice(), "IS NOT VALID: Value must be greater than zero");
            return errors;
        }
        return errors;

    }

    @Override
    public boolean hasErrors()
    {
        return !errors.isEmpty();
    }

    public boolean isNameValid(ProductDto productDto)
    {
        return productDto.getName() != null && productDto.getName().matches("^[A-Z ]+$");
    }

    public boolean isPriceValid(ProductDto productDto)
    {
        return productDto.getPrice() != null && productDto.getPrice().compareTo(BigDecimal.ZERO) > 0;
    }
}
