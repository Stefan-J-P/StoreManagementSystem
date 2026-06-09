package jan.stefan.hibernate.service.dataGenerator;

import com.google.gson.Gson;
import jan.stefan.hibernate.dto.modelDto.CountryDto;
import jan.stefan.hibernate.service.JsonConverter.CountryJsonConverter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CountriesDataManager
{
    //final String countries = "countries.json";

    public List<CountryDto> countriesFromJsonToList(String jSonFilename)
    {
        CountryJsonConverter countryJsonConverter = new CountryJsonConverter(jSonFilename);
        //Optional<List<CountryDto>> countriesFromJson = countryJsonConverter.fromJson();
        //List<CountryDto> countryDtos = countryJsonConverter.fromJson();
        //List<CountryDto> countries = new Gson().fromJson(jSonFilename);
        return null;
    }

}
