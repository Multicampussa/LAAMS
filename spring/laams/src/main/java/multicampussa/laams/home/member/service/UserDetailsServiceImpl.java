package multicampussa.laams.home.member.service;

import lombok.RequiredArgsConstructor;
import multicampussa.laams.home.member.domain.Member;
import multicampussa.laams.home.member.repository.MemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    // 유저의 정보를 가져오는 함수
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        if (memberRepository.existsById(id)) {
            multicampussa.laams.home.member.domain.Member member = memberRepository.findById(id)
                    .orElseThrow(() -> new UsernameNotFoundException(id + "이라는 아이디를 찾을 수 없습니다."));

            // 권한 설정
            String authority = "ROLE_DIRECTOR";

            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(authority));
            return new org.springframework.security.core.userdetails.User(member.getId(), member.getPw(), authorities);
        } else if (memberRepository.existsById(id)) {
            multicampussa.laams.home.member.domain.Member member = memberRepository.findById(id)
                    .orElseThrow(() -> new UsernameNotFoundException(id + "이라는 아이디를 찾을 수 없습니다."));

            // 권한 설정
            String authority = "ROLE_MANAGER";

            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(authority));
            return new org.springframework.security.core.userdetails.User(member.getId(), member.getPw(), authorities);
        } else {
            Member member = memberRepository.findById(id)
                    .orElseThrow(() -> new UsernameNotFoundException(id + "이라는 아이디를 찾을 수 없습니다."));

            // 권한 설정
            String authority = "ROLE_CENTER_MANAGER";

            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(authority));
            return new org.springframework.security.core.userdetails.User(member.getId(), member.getPw(), authorities);
        }
    }
}
