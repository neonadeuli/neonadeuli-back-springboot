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
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "posts_pictures")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostPictures {

    @EmbeddedId
    private PostPictureId id;

    @MapsId("postId")
    @ManyToOne
    @JoinColumn(name = "post_id")
    Post post;

    @MapsId("pictureId")
    @ManyToOne
    @JoinColumn(name = "picture_id")
    Picture picture;

    @Embeddable
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class PostPictureId implements Serializable {
        
        @Column
        @NotNull
        private Long postId;

        @Column
        @NotNull
        private Long pictureId;
    }
}


