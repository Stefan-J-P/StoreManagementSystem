package jan.stefan.hibernate.dto.modelDto;

import jan.stefan.hibernate.model.enums.EGuarantee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductDto
{
    private Long id;
    private String name;
    private BigDecimal price;
    private ProducerDto producerDto;
    private TradeDto tradeDto;
    private CategoryDto categoryDto;
    private Set<EGuarantee> eGuarantees;
}
