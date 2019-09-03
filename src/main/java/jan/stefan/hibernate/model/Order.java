package jan.stefan.hibernate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "orders")
public class Order
{
    @Id
    @GeneratedValue
    private Long id;
    private Integer orderNumber;
    private LocalDateTime dateTime;
    private Integer quantity;
    private BigDecimal discount;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "payment_id")
    private Payment payment;



}
