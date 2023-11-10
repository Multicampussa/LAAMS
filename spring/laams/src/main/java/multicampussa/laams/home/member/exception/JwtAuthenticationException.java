package multicampussa.laams.home.member.exception;

//import org.springframework.security.core.AuthenticationException;

import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationException extends AuthenticationException {

    public JwtAuthenticationException(String msg) {
        super(msg);
    }
}