package back.neonadeuli.post.entity;

import back.neonadeuli.picture.entity.Picture;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "posts_pictures")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostPicture {

    @EmbeddedId
    private PostPictureId id;

    @MapsId("postId")
    @ManyToOne
    @JoinColumn(name = "post_id")
    Post post;

    @MapsId("savedName")
    @ManyToOne
    @JoinColumn(name = "saved_name")
    Picture picture;

    @Embeddable
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class PostPictureId implements Serializable {

        @Column
        private Long postId;

        @Column
        private UUID savedName;
    }

    public PostPicture(Post post, Picture picture) {
        this.id = new PostPictureId();
        this.post = post;
        this.picture = picture;
    }
}
