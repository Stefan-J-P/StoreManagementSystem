package jan.stefan.hibernate.service.JsonConverter;

import jan.stefan.hibernate.dto.modelDto.PaymentDto;

public class PaymentJsonConverter extends JsonConverter<PaymentDto>
{
    public PaymentJsonConverter(String jSonFilename)
    {
        super(jSonFilename);
    }
}
