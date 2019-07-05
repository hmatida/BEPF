package br.com.personal_financee.pf.controllers;

import br.com.personal_financee.pf.models.Account;
import br.com.personal_financee.pf.models.Launches;
import br.com.personal_financee.pf.models.Users;
import br.com.personal_financee.pf.passclasses.ExtractFilter;
import br.com.personal_financee.pf.repositories.AccountRepository;
import br.com.personal_financee.pf.repositories.LaunchesRepository;
import br.com.personal_financee.pf.repositories.UserRepository;
import br.com.personal_financee.pf.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Collection;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/extract")
public class ExtractController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private LaunchesRepository launchesRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * Parâmetro de execução
     * */

    private Account findPrincipalAccount(Users user){
        return accountRepository.principalAccountByUser(user);
    }

    private void setTimeDate(Calendar date){
        date.set(Calendar.HOUR, date.getMinimum(Calendar.HOUR));
        date.set(Calendar.MINUTE, date.getMinimum(Calendar.MINUTE));
        date.set(Calendar.SECOND, date.getMinimum(Calendar.SECOND));
        date.set(Calendar.MILLISECOND, date.getMinimum(Calendar.MILLISECOND));
    }

    public Users userByRequest(HttpServletRequest request){

        String token = request.getHeader("Authorization");
        String userName = jwtTokenUtil.getUsernameFromToken(token);

        return userRepository.findByLogin(userName);
    }



    private Collection<Launches> verifyOperation(Users user, ExtractFilter filter){
        Calendar now = Calendar.getInstance();
        Calendar dt = Calendar.getInstance();
        setTimeDate(now);
        setTimeDate(dt);
        switch (filter.getOperation()){
            case 0: //Últimos 10 dias
                dt.add(Calendar.DATE, -10);
                return launchesRepository.findLauchesByDtInterval(user, filter.getAccount(), dt, now);

            case 1: //Últimos 30 dias
                dt.add(Calendar.DATE, -30);
                return launchesRepository.findLauchesByDtInterval(user, filter.getAccount(), dt, now);
            case 2: // Últimos 60 dias
                dt.add(Calendar.DATE, -60);
                return launchesRepository.findLauchesByDtInterval(user, filter.getAccount(), dt, now);
            case 3: //Mês passado
                dt.add(Calendar.MONTH, -1);
                dt.set(Calendar.DAY_OF_MONTH, dt.getActualMinimum(Calendar.DATE));
                now.add(Calendar.MONTH, -1);
                now.set(Calendar.DAY_OF_MONTH, now.getActualMaximum(Calendar.DAY_OF_MONTH));
                return launchesRepository.findLauchesByDtInterval(user, filter.getAccount(), dt, now);
            case 4: //Mês atual
                dt.add(Calendar.MONTH, -1);
                dt.set(Calendar.DATE, dt.getActualMaximum(Calendar.DATE));
                now.set(Calendar.DATE, now.getActualMaximum(Calendar.DATE));
                return launchesRepository.findLauchesByDtInterval(user, filter.getAccount(), dt, now);
            case 5: // Personalizar
                return launchesRepository.findLauchesByDtInterval(user, filter.getAccount(), filter.getInitDate(), filter.getFinalDate());
            default: return null;
        }
    }


    /**
     * End points
     * */

//    @RequestMapping(method = RequestMethod.GET, value = "/getIn", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Collection<Launches>> getInExtract(){
//        Calendar now = Calendar.getInstance();
//        Calendar dtFinal = Calendar.getInstance();
//        dtFinal.add(Calendar.DATE, -10);
//        return new ResponseEntity<Collection<Launches>>((Collection<Launches>)
//                launchesRepository.findLauchesByDtInterval(dtFinal, now), HttpStatus.OK);
//    }

    @RequestMapping(method = RequestMethod.GET, value = "/get_principal", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> getPrincipalAccount(HttpServletRequest request){
        return new ResponseEntity<Account>(findPrincipalAccount(userByRequest(request)), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/filter", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Launches>> postFilter(HttpServletRequest request, @RequestBody ExtractFilter filter) {
        if(filter.getAccount() == null){
            filter.setAccount(findPrincipalAccount(userByRequest(request)));
        }
        return new ResponseEntity<Collection<Launches>>((Collection<Launches>)
                    verifyOperation(userByRequest(request), filter), HttpStatus.OK);
    }

}
