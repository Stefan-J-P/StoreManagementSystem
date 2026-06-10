package jan.stefan.hibernate.model;
import jan.stefan.hibernate.dto.modelDto.CustomerDto;
import jan.stefan.hibernate.service.JsonConverter.CustomerJsonConverter;
import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "customers")
public class Customer
{
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String surname;
    private String email;
    private Integer age;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "country_id")
    private Country country;

    @OneToMany(mappedBy = "customer")
    private Set<MyOrder> myOrders;

}
