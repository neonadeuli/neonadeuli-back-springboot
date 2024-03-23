package back.neonadeuli.post.entity;

import static jakarta.persistence.CascadeType.DETACH;
import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REFRESH;

import back.neonadeuli.account.entity.Account;
import back.neonadeuli.location.entity.Location;
import back.neonadeuli.picture.entity.Picture;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column
    @NotNull
    private Boolean locationAvailable;

    @Column
    @NotNull
    private String content;

    @OneToOne(cascade = {PERSIST, MERGE, REFRESH, DETACH})
    @JoinColumn(name = "location_id")
    Location location;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = {PERSIST, MERGE, REFRESH, DETACH})
    List<PostPicture> postPictures = new ArrayList<>();

    public Post(Visibility visibility, Account account, Boolean locationAvailable, String content, Location location) {
        this.visibility = visibility;
        this.account = account;
        this.locationAvailable = locationAvailable;
        this.content = content;
        this.location = location;
    }

    public void addPictures(List<Picture> pictures) {
        List<PostPicture> convertPostPictures = pictures.stream()
                .filter(Objects::nonNull)
                .map(picture -> new PostPicture(this, picture))
                .toList();

        postPictures.addAll(convertPostPictures);
    }
}
