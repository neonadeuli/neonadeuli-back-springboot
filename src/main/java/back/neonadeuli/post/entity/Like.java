package back.neonadeuli.post.entity;

import back.neonadeuli.account.entity.Account;
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
@Table(name = "likes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like {

    @EmbeddedId
    private PostAccountId id;

    @MapsId("postId")
    @ManyToOne
    @JoinColumn(name = "post_id")
    @NotNull
    private Post post;

    @MapsId("accountId")
    @ManyToOne
    @JoinColumn(name = "account_id")
    @NotNull
    private Account account;

    @Column
    @NotNull
    private Boolean status;

    @Embeddable
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class PostAccountId implements Serializable {

        @Column
        @NotNull
        private Long postId;

        @Column
        @NotNull
        private Long accountId;
    }
}
