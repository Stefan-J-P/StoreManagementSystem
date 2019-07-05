package jan.stefan.hibernate.dto.modelDto;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProducerDto
{
    private Long id;
    private String name;
    private CountryDto countryDto;
    private TradeDto tradeDto;
}
