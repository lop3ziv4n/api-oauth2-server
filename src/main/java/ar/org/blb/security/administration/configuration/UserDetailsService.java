package ar.org.blb.security.administration.configuration;

import ar.org.blb.security.administration.model.Role;
import ar.org.blb.security.administration.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return Optional.ofNullable(userRepository.findOneByLoginAndEnabledTrue(login))
                .map(u -> new org.springframework.security.core.userdetails.User(u.getLogin(), u.getPassword(),
                        this.getGrantedAuthorities(u.getRoles())))
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s does not exist!", login)));
    }

    private Collection<? extends GrantedAuthority> getGrantedAuthorities(List<Role> roles) {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Role role : roles) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getCode()));
        }
        return grantedAuthorities;
    }
}
