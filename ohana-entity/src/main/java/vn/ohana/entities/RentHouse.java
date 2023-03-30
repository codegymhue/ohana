package vn.ohana.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "rent_house")
public class RentHouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "price", precision = 14)
    private BigDecimal price;

    @Column(name = "capacity")
    private Long capacity;

    @Column(name = "area")
    private Long area;

    @OneToOne
    @JoinColumn(name = "gender_id", foreignKey = @ForeignKey(name = "fk_rent_house_gender"))
    private Gender gender;

    @Column(name = "status")
    private boolean status;

    public RentHouse(Long id) {
        this.id = id;
    }
}