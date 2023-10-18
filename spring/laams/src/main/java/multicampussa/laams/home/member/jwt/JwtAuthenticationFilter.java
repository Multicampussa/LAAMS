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

//        if (path.startsWith("/admin") && !path.startsWith("/admin/member/login")){
//            if (!jwtTokenProvider.getIsManager(token)) {
//                throw new NotAdminException("관리자만 접근 가능합니다.");
//            }
//        }

        if (!path.startsWith("/member/login") &&
                !path.startsWith("/member/signup") &&
                !path.startsWith("/member/findid") &&
                !path.startsWith("/member/findpassword") &&
                !path.startsWith("/")) {


            if (token != null) {
                try {
                    jwtTokenProvider.validateToken(token);
                } catch (Exception e) {
                    // 토큰이 유효하지 않을 때
                    sendErrorResponse((HttpServletResponse) res, "토큰이 만료되었거나 유효하지 않습니다.", HttpServletResponse.SC_UNAUTHORIZED);
                    return; // 추가 처리 중지
                }

                Authentication auth = jwtTokenProvider.getAuthentication(token);
                if (auth != null) {
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } else {
                // 토큰이 유효하지 않을 때
                sendErrorResponse((HttpServletResponse) res, "로그인이 필요한 서비스입니다.", HttpServletResponse.SC_UNAUTHORIZED);
                return; // 추가 처리 중지
            }
        }
        filterChain.doFilter(req, res);
    }

    private void sendErrorResponse(HttpServletResponse response, String message, int status) throws IOException {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message", message);
        errorResponse.put("status", status);

        response.setStatus(status);
        response.setContentType("application/json; charset=utf8");

        ObjectMapper objectMapper = new ObjectMapper();
        String responseBody = objectMapper.writeValueAsString(errorResponse);

        response.getWriter().write(responseBody);
    }
}
