package jan.stefan.hibernate.service.JsonConverter;

import jan.stefan.hibernate.dto.modelDto.ShopDto;

public class ShopJsonConverter extends JsonConverter<ShopDto>
{
    public ShopJsonConverter(String jSonFilename)
    {
        super(jSonFilename);
    }
}
