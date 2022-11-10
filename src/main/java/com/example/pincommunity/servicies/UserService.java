package com.example.pincommunity.servicies;

import com.example.pincommunity.models.Member;
import com.example.pincommunity.repositories.MemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public UserService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> optionalMember = memberRepository.getMemberByUsername(username);
        if (!optionalMember.isPresent()) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        Member member = optionalMember.get();
        return new User(member.getUsername(), member.getPassword(), getAuthority(member));
    }

    /**
     * из коллекции ролей получить коллекцию GrantedAuthority
     *
     */
    private Collection<? extends GrantedAuthority> getAuthority(Member member) {
        // т.к. у меня только по одной роли у каждого члена, пока метод выглядит так
        Collection<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(member.getRole()));
        return roles;
    }
}
