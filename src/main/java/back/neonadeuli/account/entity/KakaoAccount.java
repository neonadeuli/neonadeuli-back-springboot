package back.neonadeuli.account.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "kakao_accounts")
@Getter
@DiscriminatorValue(value = "KAKAO")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoAccount extends Account {

    @Column(name = "account_code")
    @NotEmpty
    private String code;
}
