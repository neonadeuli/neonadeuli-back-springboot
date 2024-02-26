package back.neonadeuli.account.controller;

import back.neonadeuli.account.dto.request.LoginRequestDto;
import back.neonadeuli.account.dto.request.SignupRequestDto;
import back.neonadeuli.account.dto.response.LoginResponseDto;
import back.neonadeuli.account.dto.response.SignupResponseDto;
import back.neonadeuli.account.service.AccountService;
import back.neonadeuli.exception.ValidFailureException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

        if (bindingResult.hasErrors()) {
            throw new ValidFailureException(bindingResult);
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(accountService.signup(requestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto requestDto,
                                                  BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ValidFailureException(bindingResult);
        }

        return ResponseEntity.ok(accountService.login(requestDto));
    }
}
