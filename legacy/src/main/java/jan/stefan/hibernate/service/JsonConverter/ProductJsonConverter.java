package jan.stefan.hibernate.service.JsonConverter;

import jan.stefan.hibernate.dto.modelDto.ProductDto;

public class ProductJsonConverter extends JsonConverter<ProductDto>
{
    public ProductJsonConverter(String jSonFilename)
    {
        super(jSonFilename);
    }
}
