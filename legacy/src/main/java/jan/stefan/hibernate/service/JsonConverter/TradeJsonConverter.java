package jan.stefan.hibernate.service.JsonConverter;

import jan.stefan.hibernate.dto.modelDto.TradeDto;
import jan.stefan.hibernate.model.Trade;

public class TradeJsonConverter extends JsonConverter<TradeDto>
{
    public TradeJsonConverter(String jSonFilename)
    {
        super(jSonFilename);
    }
}
