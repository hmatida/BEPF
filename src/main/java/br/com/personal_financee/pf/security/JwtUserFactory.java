package br.com.personal_financee.pf.security;

import br.com.personal_financee.pf.models.ProfileEnum;
import br.com.personal_financee.pf.models.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(Users user){
        return new JwtUser(user.getId_usuario(), user.getLogin(), user.getPassword(),
                mapToGrantedAuthorities(user.getProfileEnum())
                );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(ProfileEnum profileEnum){
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(profileEnum.toString()));
        return authorities;
    }
}
