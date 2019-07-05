package jan.stefan.hibernate.dto.modelDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TradeDto
{
    private Long id;
    private String name;
}
