package multicampussa.laams.home.member.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private JwtTokenProvider jwtTokenProvider;

    // 토큰이 유효한지 검사
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) req;
        String path = httpRequest.getRequestURI();
        String token = jwtTokenProvider.resolveToken(httpRequest);


        if (!path.startsWith("/api/v1/member/login") &&
                !path.startsWith("/api/v1/member/signup") &&
                !path.startsWith("/api/v1/member/findid") &&
                !path.startsWith("/api/v1/member/findpassword") &&
                !path.startsWith("/api/v1/member/sendemail") &&
                !path.startsWith("/api/v1/member/refresh") &&
                !path.startsWith("/api/v1/member/encodedpassword") &&
                !path.startsWith("/csrf") &&
                !path.startsWith("/webjars") &&
                !path.startsWith("/assets") &&
                !path.startsWith("/images") &&
                !path.startsWith("/error") &&
                !path.startsWith("/swagger-ui") &&
                !path.startsWith("/swagger-resources") &&
                !path.startsWith("/v3/api-docs") &&
                !path.startsWith("/v2/api-docs") &&
                !path.startsWith("/api/v1/examinee") &&
                !path.startsWith("/ws/chat") &&
                !path.startsWith("/")) {


            if (token != null) {
                if (jwtTokenProvider.getAuthority(token).equals("ROLE_EXAMINEE")) {
                    if (!path.startsWith("ap1/v1/examinee/attendance")) {
                        sendErrorResponse((HttpServletResponse) res, "관리자만 접근 가능합니다.", "a001");
                        return;
                    }
                }

                try {
                    jwtTokenProvider.validateToken(token);
                } catch (Exception e) {
                    // 토큰이 유효하지 않을 때
                    sendErrorResponse((HttpServletResponse) res, "액세스 토큰이 만료되었거나 유효하지 않습니다.", "l002");
                    return; // 추가 처리 중지
                }

                Authentication auth = jwtTokenProvider.getAuthentication(token);
                if (auth != null) {
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } else {
                // 토큰이 유효하지 않을 때
                sendErrorResponse((HttpServletResponse) res, "로그인이 필요한 서비스입니다.", "l001");
                return; // 추가 처리 중지
            }
        }
        filterChain.doFilter(req, res);
    }

    private void sendErrorResponse(HttpServletResponse response, String message, String code) throws IOException {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message", message);
        errorResponse.put("code", code);

//        response.setStatus(code);
        response.setContentType("application/json; charset=utf8");

        ObjectMapper objectMapper = new ObjectMapper();
        String responseBody = objectMapper.writeValueAsString(errorResponse);

        response.getWriter().write(responseBody);
    }
}
