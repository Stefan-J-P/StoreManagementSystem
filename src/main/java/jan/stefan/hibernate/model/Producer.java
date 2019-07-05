package jan.stefan.hibernate.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "producers")
public class Producer
{
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "country_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Country country;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "trade_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Trade trade;

    @OneToMany(mappedBy = "producer")
    private Set<Product> products;

}
