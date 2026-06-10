package jan.stefan.hibernate.dto.modelDto.statModelDto;

import jan.stefan.hibernate.model.*;
import lombok.*;
import net.bytebuddy.asm.Advice;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductByCustomersCountryDto
{
    // MY ORDER DATA
    private Long myOrderId;
    private Integer orderNumber;
    private LocalDateTime dateTime;
    private Integer quantity;
    private BigDecimal discount;

    private Customer customer;
    private Product product;
    private Payment payment;

    // CUSTOMER DATA
    private Long customerId;
    private String name;
    private String surname;
    private String email;
    private Integer age;
    private Country country;

    // PRODUCT DATA
    private Long productId;
    private String productName;
    private BigDecimal price;


    // COUNTRY DATA
    private Long countryId;
    private String countryName;



}
