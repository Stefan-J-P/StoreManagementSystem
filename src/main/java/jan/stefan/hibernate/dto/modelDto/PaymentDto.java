package jan.stefan.hibernate.dto.modelDto;

import jan.stefan.hibernate.model.enums.EPayment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PaymentDto
{
    private Long id;
    private EPayment ePayment;

}
