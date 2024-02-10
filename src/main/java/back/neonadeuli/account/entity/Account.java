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
import jakarta.validation.constraints.Email;
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

    public static final int NAME_MAX_SIZE = 20;
    public static final int NICKNAME_MIN_SIZE = 2;
    public static final int NICKNAME_MAX_SIZE = 30;
    public static final int LOGIN_ID_MIN_SIZE = 4;
    public static final int LOGIN_ID_MAX_SIZE = 30;
    public static final int EMAIL_MAX_SIZE = 320;

    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "picture_id")
    @NotNull
    Picture picture;

    @Column
    @Size(max = NAME_MAX_SIZE)
    private String name;

    @Column
    @NotEmpty
    @Size(min = NICKNAME_MIN_SIZE, max = NICKNAME_MAX_SIZE)
    private String nickname;

    @Column
    @NotEmpty
    @Size(min = LOGIN_ID_MIN_SIZE, max = LOGIN_ID_MAX_SIZE)
    private String loginId;

    @Column(columnDefinition = "char(60)")
    @NotEmpty
    private String password;

    @Column
    @NotEmpty
    @Email
    @Size(max = EMAIL_MAX_SIZE)
    private String email;

    public Account(Picture picture, String name, String nickname, String loginId, String password, String email) {
        this.picture = picture;
        this.name = name;
        this.nickname = nickname;
        this.loginId = loginId;
        this.password = password;
        this.email = email;
    }
}

