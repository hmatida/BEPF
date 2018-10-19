package br.com.personal_financee.pf.utility;

import br.com.personal_financee.pf.models.Users;
import br.com.personal_financee.pf.repositories.UserRepository;
import br.com.personal_financee.pf.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public class DetailsFromRequest {

    private JwtTokenUtil jwtTokenUtil;

    private UserRepository userRepository;

    public DetailsFromRequest(){}

    public Users userByRequest(HttpServletRequest request){

        String token = request.getHeader("Authorization");
        String userName = jwtTokenUtil.getUsernameFromToken(token);

        return userRepository.findByLogin(userName);
    }
}
