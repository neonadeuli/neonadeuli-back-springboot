package back.neonadeuli.aop;

import back.neonadeuli.exception.ValidFailureException;
import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Aspect
@Component
public class ValidAspect {

    @Before("execution(* back.neonadeuli..controller..*.*(.., @jakarta.validation.Valid (*), ..))")
    public void checkBindingResult(JoinPoint joinPoint) {
        Arrays.stream(joinPoint.getArgs())
                .filter(BindingResult.class::isInstance)
                .map(BindingResult.class::cast)
                .forEach(this::bindingResultErrorCheck);
    }

    private void bindingResultErrorCheck(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidFailureException(bindingResult);
        }
    }
}
