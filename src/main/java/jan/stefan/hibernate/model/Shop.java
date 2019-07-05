package jan.stefan.hibernate.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "shops")
public class Shop
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

    @OneToMany(mappedBy = "shop")
    private Set<Stock> stocks;
}
