package back.neonadeuli.account.model.dto.request;

import static back.neonadeuli.account.entity.Account.LOGIN_ID_MAX_SIZE;
import static back.neonadeuli.account.entity.Account.LOGIN_ID_MIN_SIZE;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginRequestDto {

    @NotEmpty
    @Size(min = LOGIN_ID_MIN_SIZE, max = LOGIN_ID_MAX_SIZE)
    private String loginId;

    @NotEmpty
    private String password;
}
