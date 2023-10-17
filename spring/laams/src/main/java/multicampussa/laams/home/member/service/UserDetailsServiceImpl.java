package multicampussa.laams.home.member.service;

import lombok.RequiredArgsConstructor;
import multicampussa.laams.director.domain.Director;
import multicampussa.laams.home.member.repository.DirectorRepository;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import multicampussa.laams.home.member.repository.ManagerRepository;
import multicampussa.laams.manager.domain.Manager;
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

    private final DirectorRepository directorRepository;
    private final ManagerRepository managerRepository;

    // 유저의 정보를 가져오는 함수
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        if (directorRepository.existsById(id)) {
            Director director = directorRepository.findById(id)
                    .orElseThrow(() -> new UsernameNotFoundException(id + "이라는 아이디를 찾을 수 없습니다."));

            // 권한 설정
            String authority = "ROLE_DIRECTOR";

            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(authority));
            return new org.springframework.security.core.userdetails.User(director.getId(), director.getPw(), authorities);
        } else {
            Manager manager = managerRepository.findById(id)
                    .orElseThrow(() -> new UsernameNotFoundException(id + "이라는 아이디를 찾을 수 없습니다."));

            // 권한 설정
            String authority = "ROLE_MANAGER";

            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(authority));
            return new org.springframework.security.core.userdetails.User(manager.getId(), manager.getPw(), authorities);
        }
    }
}
