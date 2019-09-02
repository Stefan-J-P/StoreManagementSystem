package jan.stefan.hibernate.model;


import jan.stefan.hibernate.model.enums.EGuarantee;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product
{
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private BigDecimal price;

    @ElementCollection
    @CollectionTable(name = "guarantees", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "guarantee")
    @Enumerated(EnumType.STRING)
    private Set<EGuarantee> guaranteeComponents;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "Producer")
    private Producer producer;

    @OneToMany(mappedBy = "product")
    private Set<Order> orders;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "trade_id")
    private Trade trade;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product")
    private Set<Stock> stocks;

}
