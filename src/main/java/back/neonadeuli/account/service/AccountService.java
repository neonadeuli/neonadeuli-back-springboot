package back.neonadeuli.account.service;

import back.neonadeuli.account.entity.Account;
import back.neonadeuli.account.model.dto.request.LoginRequestDto;
import back.neonadeuli.account.model.dto.request.SignupRequestDto;
import back.neonadeuli.account.model.dto.response.SignupResponseDto;
import back.neonadeuli.account.repository.AccountRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public SignupResponseDto signup(SignupRequestDto requestDto) {
        Account saveAccount = accountRepository.save(requestDto.toEntity(passwordEncoder));
        return new SignupResponseDto(saveAccount.getId());
    }

    public void doLogin(LoginRequestDto requestDto, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(
                requestDto.getLoginId(),
                requestDto.getPassword());

        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        HttpSession session = request.getSession(true);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());
    }

    public void doLogout(HttpSession session) {
        SecurityContextHolder.clearContext();

        if (Objects.nonNull(session)) {
            session.invalidate();
        }
    }
}
