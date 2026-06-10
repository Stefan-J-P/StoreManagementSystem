package jan.stefan.hibernate.service.JsonConverter;

import jan.stefan.hibernate.dto.modelDto.CustomerDto;

import java.util.List;

public class CustomerListJson extends JsonConverter<List<CustomerDto>>
{
    public CustomerListJson(String jSonFilename)
    {
        super(jSonFilename);
    }
}
