package jan.stefan.hibernate.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Stocks")
public class Stock
{
    @Id
    @GeneratedValue
    private Long id;
    private Integer quantity;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "product_id")
    private Product product;
}
