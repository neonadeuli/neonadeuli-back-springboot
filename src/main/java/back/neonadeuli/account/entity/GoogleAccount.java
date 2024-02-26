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
@Table(name = "google_accounts")
@Getter
@DiscriminatorValue(value = "GOOGLE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GoogleAccount extends Account {

    @Column(name = "account_code")
    @NotEmpty
    private String code;
}
