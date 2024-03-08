package back.neonadeuli.account.model.dto.request;

import static back.neonadeuli.account.entity.Account.LOGIN_ID_MAX_SIZE;
import static back.neonadeuli.account.entity.Account.LOGIN_ID_MIN_SIZE;
import static back.neonadeuli.account.entity.Account.NICKNAME_MAX_SIZE;
import static back.neonadeuli.account.entity.Account.NICKNAME_MIN_SIZE;

import back.neonadeuli.account.entity.Account;
import back.neonadeuli.picture.entity.Picture;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignupRequestDto {

    private String name;

    @NotEmpty
    @Size(min = NICKNAME_MIN_SIZE, max = NICKNAME_MAX_SIZE)
    private String nickname;

    @NotEmpty
    @Size(min = LOGIN_ID_MIN_SIZE, max = LOGIN_ID_MAX_SIZE)
    private String loginId;

    @NotEmpty
    private String password;

    @Email
    @NotEmpty
    @Size(max = Account.EMAIL_MAX_SIZE)
    private String email;

    public Account toEntity(PasswordEncoder passwordEncoder) {
        return new Account(Picture.DEFAULT_PICTURE, getName(), getNickname(), getLoginId(),
                passwordEncoder.encode(password), getEmail());
    }
}
