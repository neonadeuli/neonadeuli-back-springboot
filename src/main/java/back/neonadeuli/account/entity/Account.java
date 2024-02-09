package back.neonadeuli.account.entity;

import back.neonadeuli.picture.entity.Picture;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "accounts")
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "oauth_type_code", discriminatorType = DiscriminatorType.STRING)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "picture_id")
    @NotNull
    Picture picture;

    @Column
    @Size(max = 20)
    private String name;

    @Column
    @NotEmpty
    @Size(max = 30)
    private String nickname;

    @Column
    @NotEmpty
    @Size(max = 30)
    private String loginId;

    @Column
    @NotEmpty
    @Size(max = 320)
    private String email;
}

