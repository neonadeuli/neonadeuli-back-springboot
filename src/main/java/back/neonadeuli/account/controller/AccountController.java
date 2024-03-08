package back.neonadeuli.account.controller;

import back.neonadeuli.account.exception.LoginFailureException;
import back.neonadeuli.account.model.dto.request.LoginRequestDto;
import back.neonadeuli.account.model.dto.request.SignupRequestDto;
import back.neonadeuli.account.model.dto.response.SignupResponseDto;
import back.neonadeuli.account.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@RequestBody @Valid SignupRequestDto requestDto,
                                                    BindingResult bindingResult) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(accountService.signup(requestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody @Valid LoginRequestDto requestDto, BindingResult bindingResult,
                                      HttpServletRequest request) {

        try {
            accountService.doLogin(requestDto, request);
            return ResponseEntity.ok().build();
        } catch (BadCredentialsException e) {
            throw new LoginFailureException();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        accountService.doLogout(session);
        return ResponseEntity.ok().build();
    }
}
