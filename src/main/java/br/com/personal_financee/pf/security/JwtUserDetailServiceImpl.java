package br.com.personal_financee.pf.security;

import br.com.personal_financee.pf.models.Users;
import br.com.personal_financee.pf.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        Users user = userRepository.findByLogin(login);
        if (user == null){
            throw new UsernameNotFoundException(String.format("User not found with login '%s'", login));
        } else {
            return JwtUserFactory.create(user);
        }
    }
}
