package br.com.personal_financee.pf.controllers;

import br.com.personal_financee.pf.models.Users;
import br.com.personal_financee.pf.passclasses.SimpleUser;
import br.com.personal_financee.pf.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;


    /**
     * Parâmetro de execução
     * */
    private Users cadUser(Users user){
        if (userRepository.findByLogin(user.getLogin()) != null){
            return null;
        } else {
            userRepository.save(user);
            return user;
        }
    }

    private Users changeUser(Long id, Users user){
        user.setId_usuario(id);
        userRepository.save(user);
        return user;
    }




    /**
     * End points
     * */

    @RequestMapping(method = RequestMethod.POST, value = "/cadastrar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Users> postCadUser(@RequestBody Users userPass) {
        if (cadUser(userPass) == null){
            return new ResponseEntity<Users>(userPass, HttpStatus.NOT_ACCEPTABLE);
        } else {
            cadUser(userPass);
            return new ResponseEntity<Users>(userPass, HttpStatus.CREATED);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Users>> getAllUsers(){
        return new ResponseEntity<Collection<Users>>((Collection<Users>) userRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/change", consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<Users> alteraCliServico(@RequestBody Users usr){
        Users user = changeUser(usr.getId_usuario(), usr);
        return new ResponseEntity<Users>(user, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/allsimple", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<SimpleUser>> getAllSimpleUsers(){
        return new ResponseEntity<Collection<SimpleUser>>((Collection<SimpleUser>) userRepository.listSimpleUsers(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Users> getUserById(@PathVariable Long id){
        Users usr = new Users();
                usr=userRepository.findById(id).get();
                usr.setPassword(null);
        return new ResponseEntity<Users>(usr, HttpStatus.OK);
    }

}
