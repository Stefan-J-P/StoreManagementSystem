package jan.stefan.hibernate.service.JsonConverter;

import jan.stefan.hibernate.dto.modelDto.MyOrderDto;

public class MyOrderJsonConverter extends JsonConverter<MyOrderDto>
{
    public MyOrderJsonConverter(String jSonFilename)
    {
        super(jSonFilename);
    }
}
