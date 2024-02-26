package back.neonadeuli.account.service;

import back.neonadeuli.account.dto.request.LoginRequestDto;
import back.neonadeuli.account.dto.request.SignupRequestDto;
import back.neonadeuli.account.dto.response.LoginResponseDto;
import back.neonadeuli.account.dto.response.SignupResponseDto;
import back.neonadeuli.account.entity.Account;
import back.neonadeuli.account.exception.LoginFailureException;
import back.neonadeuli.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SignupResponseDto signup(SignupRequestDto requestDto) {
        Account saveAccount = accountRepository.save(requestDto.toEntity(passwordEncoder));
        return new SignupResponseDto(saveAccount.getId());
    }

    public LoginResponseDto login(LoginRequestDto requestDto) {
        Account loginAccount = accountRepository.findByLoginId(requestDto.getLoginId())
                .orElseThrow(LoginFailureException::new);

        if (passwordEncoder.matches(requestDto.getPassword(), loginAccount.getPassword())) {
            return new LoginResponseDto(loginAccount.getId());
        }

        throw new LoginFailureException();
    }
}
