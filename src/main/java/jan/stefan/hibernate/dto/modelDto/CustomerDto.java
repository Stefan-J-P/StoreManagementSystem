package jan.stefan.hibernate.dto.modelDto;

import jan.stefan.hibernate.dto.modelDto.CountryDto;
import jan.stefan.hibernate.service.JsonConverter.CustomerJsonConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDto
{
    private Long id;
    private String name;
    private String surname;
    private String email;
    private Integer age;
    private CountryDto countryDto;

}
