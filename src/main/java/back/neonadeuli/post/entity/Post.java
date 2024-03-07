package back.neonadeuli.post.entity;

import back.neonadeuli.account.entity.Account;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.geo.Point;

@Entity
@Table(name = "posts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "visibility_code", columnDefinition = "varchar(20)")
    @Enumerated(EnumType.STRING)
    @NotNull
    private Visibility visibility;

    @ManyToOne
    @JoinColumn(name = "account_id")
    @NotNull
    private Account account;

    @Column(name = "location_available")
    @NotNull
    private Boolean locationAvailable;

    @Column
    @NotNull
    private String content;

    @Column
    private Point location;

    @Column(name = "h3_index")
    private Long h3Index;
}
