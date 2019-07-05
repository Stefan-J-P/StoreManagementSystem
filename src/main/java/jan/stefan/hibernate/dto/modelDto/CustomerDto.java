package jan.stefan.hibernate.dto.modelDto;

import jan.stefan.hibernate.dto.modelDto.CountryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
