package multicampussa.laams.home.member.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import multicampussa.laams.centerManager.domain.CenterManager;
import multicampussa.laams.director.domain.director.Director;
import multicampussa.laams.home.member.exception.JwtAuthenticationException;
import multicampussa.laams.home.member.repository.MemberDirectorRepository;
import multicampussa.laams.home.member.repository.MemberManagerRepository;
import multicampussa.laams.home.member.service.UserDetailsServiceImpl;
import multicampussa.laams.centerManager.domain.CenterManagerRepository;
import multicampussa.laams.manager.domain.center.Center;
import multicampussa.laams.manager.domain.center.CenterRepository;
import multicampussa.laams.manager.domain.manager.Manager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final UserDetailsServiceImpl userDetailsService;
    private final MemberDirectorRepository memberDirectorRepository;
    private final MemberManagerRepository memberManagerRepository;
    private final CenterManagerRepository centerManagerRepository;
    private final CenterRepository centerRepository;

    private String secretKey = "s1s2a3f4y@"; // 비밀키
    private long validityInMilliseconds = 3600000; // 1 hour

    public String createAccessToken(String id, String authority, Long memberNo) {

        // email과 권한 정보 claims에 담기
        Claims claims = Jwts.claims().setSubject(id);
        claims.put("memberNo", memberNo);
        claims.put("authority", authority);
        if (authority.equals("ROLE_DIRECTOR")) {
            Center center = centerRepository.findByDirectorId(id);
            if (center != null) {
                claims.put("centerNo", center.getNo());
                claims.put("region", center.getRegion());
            } else {
                throw new IllegalArgumentException("센터가 배정되지 않은 감독관입니다.");
            }
        }

        // 만료 시간 계산
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now) // 토큰 발급 시간
                .setExpiration(validity) // 만료 시간
                .signWith(SignatureAlgorithm.HS256, secretKey) // 비밀키를 HS256 방식으로 암호화
                .compact();
    }

    public String createAccessTokenForExamExaminee(String code, String birth) {

        // 수험번호와 권한 정보 claims에 담기
        Claims claims = Jwts.claims().setSubject(code);
        claims.put("birth", birth);
        claims.put("authority", "ROLE_EXAMINEE");

        // 만료 시간 계산
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now) // 토큰 발급 시간
                .setExpiration(validity) // 만료 시간
                .signWith(SignatureAlgorithm.HS256, secretKey) // 비밀키를 HS256 방식으로 암호화
                .compact();
    }

    public Authentication getAuthentication(String token) {
        // 토큰을 통해 유저 정보 가져오기
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getId(token));
        // JWT에서는 비밀번호 정보가 필요없기 때문에 빈 문자열 넣음
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰을 복호화하여 claims를 뽑아내는 과정
    // 리프레시 토큰의 claims에는 아이디만 있음
    public String getEmail(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // 토큰으로 MemberNo 추출
    public Long getMemberNo(String token) {
        return Long.valueOf(Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("memberNo").toString());
    }

    // 토큰으로 ID 추출
    public String getId(String token) {
        // token의 claims 안에 id는 sub 필드로 저장되어 있다. 따라서 getSubject()로 아이디 추출.
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject().toString();
    }

    public String getAuthority(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("authority").toString();
    }

    public Long getCenterNo(String token) {
        return Long.valueOf(Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("centerNo").toString());
    }

    public String getRegion(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("region").toString();
    }

    // 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            // parseClaimsJws : 파싱된 서명이 유효한지, 만료되진 않았는지
            // 유효성 검사에 통과하면 true를 반환한다.
            // 유효성 검사에 통과하지 못하면 예외발생
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("JWT token is expired or invalid");
        }
    }

    // authorization 안에 있는 bearer(token) 반환.
    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.replace("Bearer ", "");
        }
        return null;
    }

    // 리프레시 토큰 발급
    public String createRefreshToken(String id) {
        // 리프레시 토큰의 claims에는 권한 정보를 담지 않는다.
        // 이유는 유효기간이 길어서, 탈취당할 경우 관리자 권한이 악용될 가능성이 높기 때문
        Claims claims = Jwts.claims().setSubject(id);
        Date now = new Date();

        // 리프레시 토큰 유효기간 (우선 일주일 잡음)
        Date validity = new Date(now.getTime() + 7 * 24 * 60 * 60 * 1000);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now) // 토큰 발급 시간
                .setExpiration(validity) // 만료 시간
                .signWith(SignatureAlgorithm.HS256, secretKey) // 비밀키를 HS256 방식으로 암호화
                .compact();
    }

    // 리프레시 토큰을 가지고 액세스 토큰 재발급
    public String refreshAccessToken(String refreshToken) {
        String id = getId(refreshToken);

        String storedRefreshToken = "";
        String authority = "";
        Long memberNo = null;
        // 발급된 리프레시 토큰에 담겨있는 ID로 DB에 저장된 리프레시 토큰 받아오기.
        if (memberDirectorRepository.existsById(id)) {
            Director director = memberDirectorRepository.findById(id).get();
            storedRefreshToken = director.getRefreshToken();
            memberNo = director.getNo();
            authority = "ROLE_DIRECTOR";
        } else if (memberManagerRepository.existsById(id)) {
            Manager manager = memberManagerRepository.findById(id).get();
            storedRefreshToken = manager.getRefreshToken();
            memberNo = manager.getNo();
            authority = "ROLE_MANAGER";
        } else if (centerManagerRepository.existsById(id)) {
            CenterManager centerManager = centerManagerRepository.findById(id).get();
            storedRefreshToken = centerManager.getRefreshToken();
            memberNo = centerManager.getNo();
            authority = "ROLE_CENTER_MANAGER";
        } else {
            throw new IllegalArgumentException("아이디가 존재하지 않습니다.");
        }

        // 권한 정보 (액세스 토큰을 발급받기 위함)

        if (storedRefreshToken == null || !refreshToken.equals(storedRefreshToken)) {
            throw new JwtAuthenticationException("리프레시 토큰이 유효하지 않습니다.");
        }

        if (isTokenExpired(refreshToken)) {
            throw new JwtAuthenticationException("리프레시 토큰이 만료되었습니다.");
        }

        return createAccessToken(id, authority, memberNo);
    }

    // 토큰 만료 여부
    public boolean isTokenExpired(String token) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return claims.getExpiration().before(new Date());
    }

    public Date getTokenExpireTime(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getExpiration();
    }
}