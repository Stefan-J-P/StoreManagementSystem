package jan.stefan.hibernate.service.JsonConverter;

import jan.stefan.hibernate.dto.modelDto.ProducerDto;

public class ProducerJsonConverter extends JsonConverter<ProducerDto>
{
    public ProducerJsonConverter(String jSonFilename)
    {
        super(jSonFilename);
    }
}
