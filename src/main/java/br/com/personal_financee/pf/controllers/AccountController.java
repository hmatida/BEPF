package br.com.personal_financee.pf.controllers;


import br.com.personal_financee.pf.models.Account;
import br.com.personal_financee.pf.models.Users;
import br.com.personal_financee.pf.passclasses.SimpleUser;
import br.com.personal_financee.pf.repositories.AccountRepository;
import br.com.personal_financee.pf.repositories.UserRepository;
import br.com.personal_financee.pf.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    /**
     * Parâmetro de execução
     * */
    private Account cadAccount(Account account){
        if (accountRepository.findByName(account.getName_account()) != null){
            return null;
        } else {
            accountRepository.save(account);
            return account;
        }
    }

    private Account changeUserSave(Long id, Account account){
        account.setId_account(id);
        accountRepository.save(account);
        return account;
    }

    public Users userByRequest(HttpServletRequest request){

        String token = request.getHeader("Authorization");
        String userName = jwtTokenUtil.getUsernameFromToken(token);

        return userRepository.findByLogin(userName);
    }


    /**
     * End points
     * */

    @RequestMapping(method = RequestMethod.POST, value = "/cadastrar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> postAccount(HttpServletRequest request, @RequestBody Account account) {

        account.setUser(userByRequest(request));
        if (cadAccount(account) == null){
            return new ResponseEntity<Account>(account, HttpStatus.NOT_ACCEPTABLE);
        } else {
            cadAccount(account);
            return new ResponseEntity<Account>(account, HttpStatus.CREATED);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Account>> getAllAccouts(HttpServletRequest request){
        return new ResponseEntity<Collection<Account>>((Collection<Account>) accountRepository
                .geAllAccountsByUser(userByRequest(request)), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/change", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<Account> changeAccount(HttpServletRequest request, @RequestBody Account account){
        account.setUser(userByRequest(request));
        Account acc = changeUserSave(account.getId_account(), account);
        return new ResponseEntity<Account>(acc, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> getAccountByid(@PathVariable Long id){
        return new ResponseEntity<Account>(accountRepository.findById(id).get(), HttpStatus.OK);
    }


}
