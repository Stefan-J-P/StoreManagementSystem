package jan.stefan.hibernate.service.JsonConverter;

import jan.stefan.hibernate.dto.modelDto.StockDto;

public class StockJsonConverter extends JsonConverter<StockDto>
{
    public StockJsonConverter(String jSonFilename)
    {
        super(jSonFilename);
    }
}
