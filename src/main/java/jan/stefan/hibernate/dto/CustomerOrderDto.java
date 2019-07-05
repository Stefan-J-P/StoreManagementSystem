package jan.stefan.hibernate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerOrderDto
{
    private Long id;
    private LocalDateTime dateTime;
    private Integer quantity;
    private BigDecimal discount;

    private CustomerDto customerDto;
    private ProductDto productDto;
    private PaymentDto paymentDto;
}
