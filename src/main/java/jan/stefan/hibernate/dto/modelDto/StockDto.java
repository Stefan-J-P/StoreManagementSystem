package jan.stefan.hibernate.dto.modelDto;

import jan.stefan.hibernate.dto.modelDto.ProductDto;
import jan.stefan.hibernate.dto.modelDto.ShopDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
