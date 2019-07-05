package jan.stefan.hibernate.model;


import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "trades")
public class Trade
{
    @Id
    @GeneratedValue
    private Long id;
    private String tradeName;

    @OneToMany(mappedBy = "trade", cascade = CascadeType.PERSIST)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Producer> producers;
}
