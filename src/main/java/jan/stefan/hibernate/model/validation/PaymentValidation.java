package jan.stefan.hibernate.model.validation;

import jan.stefan.hibernate.model.Payment;
import jan.stefan.hibernate.model.validation.generic.Validator;

import java.util.Map;

public class PaymentValidation implements Validator<Payment>
{
    @Override
    public Map<String, String> validate(Payment payment)
    {
        return null;
    }

    @Override
    public boolean hasErrors()
    {
        return false;
    }
}
