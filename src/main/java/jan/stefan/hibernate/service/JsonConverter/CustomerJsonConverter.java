package jan.stefan.hibernate.service.JsonConverter;

import jan.stefan.hibernate.dto.modelDto.CustomerDto;

public class CustomerJsonConverter extends JsonConverter<CustomerDto>
{
    public CustomerJsonConverter(String jSonFilename)
    {
        super(jSonFilename);
    }


}
