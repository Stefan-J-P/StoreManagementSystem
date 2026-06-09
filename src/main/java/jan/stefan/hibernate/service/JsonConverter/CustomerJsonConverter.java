package jan.stefan.hibernate.service.JsonConverter;

import jan.stefan.hibernate.dto.modelDto.CustomerDto;

import java.util.List;

public class CustomerJsonConverter extends JsonConverter<CustomerDto>
{
    private List<CustomerDto> customerDtos;

    public CustomerJsonConverter(String jSonFilename)
    {
        super(jSonFilename);
    }

/*    public CustomerListJsonConverter(String jSonFilename)
    {

    }*/




}
