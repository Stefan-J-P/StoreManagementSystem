package jan.stefan.hibernate.model;

import jan.stefan.hibernate.model.enums.EPayment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name="payments")
public class Payment
{
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private EPayment payment;

    @OneToMany(mappedBy = "payment")
    private Set<MyOrder> myOrders;

}
