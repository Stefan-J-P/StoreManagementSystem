package jan.stefan.hibernate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StockDto
{
    private Long id;
    private Integer quantity;
    private ShopDto shopDto;
    private ProductDto productDto;
}
