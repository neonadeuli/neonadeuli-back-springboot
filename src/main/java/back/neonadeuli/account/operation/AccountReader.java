package back.neonadeuli.account.operation;

import back.neonadeuli.account.entity.Account;
import back.neonadeuli.account.model.authonticate.AccountDetail;
import back.neonadeuli.account.repository.AccountRepository;
import back.neonadeuli.annotation.Reader;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

@Reader
@RequiredArgsConstructor
public class AccountReader {

    private final AccountRepository accountRepository;

    public Account getLoginAccountReference() {
        AccountDetail accountDetail = getAccountDetail();
        return accountRepository.getReferenceById(accountDetail.getAccountId());
    }

    private AccountDetail getAccountDetail() {
        return (AccountDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
