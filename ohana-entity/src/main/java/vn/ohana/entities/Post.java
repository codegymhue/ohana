package vn.ohana.entities;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "post", indexes = {
        @Index(name = "fk_rent_house_idx", columnList = "rent_house_id"),
        @Index(name = "fk_user_idx", columnList = "user_id"),
        @Index(name = "fk_category_idx", columnList = "category_id")
})
@TypeDef(
<<<<<<< HEAD
        typeClass = JsonType.class,
        defaultForType = Location.class
)


public class Post {
=======
        name = "location",
        typeClass = JsonType.class)
@TypeDef(
        name = "utilities",
        typeClass = JsonType.class)
public class Post extends BaseEntity {
>>>>>>> main
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "rent_house_id", foreignKey = @ForeignKey(name = "fk_post_rent_house"))
    private RentHouse rentHouse;
    @Column(name = "rent_house_id", insertable = false, updatable = false)
    private Long rentHouseId;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "slug", length = 45)
    private String slug;

    @Column(name = "description_content")
    private String descriptionContent;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_post_user"))
    private User user;

    @Type(type = "location")
    @Column(name = "location", nullable = false, columnDefinition = "JSON")
    private Location location;

    @Column(name = "thumbnail_id", length = 45)
    private String thumbnailId;

    @OneToMany(mappedBy = "post")
    private List<PostMedia> postMedia;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "fk_post_category"))
    private Category category;

    @Column(name = "category_id", insertable = false, updatable = false)
    private Long categoryId;

    @Type(type = "utilities")
    @Column(name = "utilities", nullable = false, columnDefinition = "JSON")
    private Set<Long> utilities;

    @Column(name = "status", nullable = false)
    private StatusPost status;

    public Post setCategoryId(Long categoryId) {
        this.category = new Category(this.categoryId = categoryId);
        return this;
    }

    public Post setRentHouseId(Long rentHouseId) {
        this.rentHouse = new RentHouse(this.rentHouseId = rentHouseId);
        return this;
    }
}