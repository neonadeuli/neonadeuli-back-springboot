package back.neonadeuli.account.model.authonticate;

import back.neonadeuli.account.entity.Account;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AccountDetail implements UserDetails {

    private final String loginId;
    private final String password;
    private final Set<GrantedAuthority> authorities;

    public AccountDetail(Account account) {
        this.loginId = account.getLoginId();
        this.password = account.getPassword();
        this.authorities = Collections.emptySet();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return loginId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
