package vn.ohana.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", length = 60, nullable = false)
    private String title;

    public Category(Long id) {
        this.id = id;
    }

    @Column(name = "status", columnDefinition = "VARCHAR(10) DEFAULT 'SHOW'")
    @Enumerated(EnumType.STRING)
    private StatusCategory status;
}