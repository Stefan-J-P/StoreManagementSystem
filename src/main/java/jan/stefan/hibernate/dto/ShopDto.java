package jan.stefan.hibernate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ShopDto
{
    private Long id;
    private String name;
    private CountryDto countryDto;
}
