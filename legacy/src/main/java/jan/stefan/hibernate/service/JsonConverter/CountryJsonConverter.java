package jan.stefan.hibernate.service.JsonConverter;

import jan.stefan.hibernate.dto.modelDto.CountryDto;

public class CountryJsonConverter extends JsonConverter<CountryDto>
{
    public CountryJsonConverter(String jSonFilename)
    {
        super(jSonFilename);
    }
}
