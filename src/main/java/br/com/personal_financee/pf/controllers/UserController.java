package br.com.personal_financee.pf.controllers;

import br.com.personal_financee.pf.models.ProfileEnum;
import br.com.personal_financee.pf.models.Users;
import br.com.personal_financee.pf.passclasses.SimpleUser;
import br.com.personal_financee.pf.repositories.CategoryRepository;
import br.com.personal_financee.pf.repositories.SubCategoryRepository;
import br.com.personal_financee.pf.repositories.UserRepository;
import br.com.personal_financee.pf.security.JwtTokenUtil;
import br.com.personal_financee.pf.utility.SendMail;
import br.com.personal_financee.pf.utility.UserNew;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;


    /**
     * Parâmetro de execução
     * */
    private Users cadUser(Users user){
        if (userRepository.findByLogin(user.getLogin()) != null){
            return null;
        } else {
            userRepository.save(user);

            new Thread() {
                public void run(){
                    categoryRepository.saveAll(UserNew.cadCategories(user));
                    subCategoryRepository.saveAll(UserNew.cadSubCategories(user));
                }
            }.start();


            user.setPassword(null);

            new Thread() {
                public void run(){
                    SendMail.enviaEmail(user.getEmail());
                }
            }.start();
            return user;
        }
    }

    private Users changeUser(Long id, Users user){
        user.setId_usuario(id);
        userRepository.save(user);
        return user;
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
    public ResponseEntity<Users> postCadUser(@RequestBody Users userPass) {
        userPass.setPassword(passwordEncoder.encode(userPass.getPassword()));
        if (cadUser(userPass) == null){
            return new ResponseEntity<Users>(userPass, HttpStatus.NOT_ACCEPTABLE);
        } else {
            cadUser(userPass);
            return new ResponseEntity<Users>(userPass, HttpStatus.CREATED);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/cadnewuser", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Users> cadNewUser(@RequestBody Users userPass) {
        userPass.setPassword(passwordEncoder.encode(userPass.getPassword()));
        userPass.setProfileEnum(ProfileEnum.ROLE_CUSTOMER);
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

    @RequestMapping(method = RequestMethod.PUT, value = "/changeuserbycustomer", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Users> putUserFromCustomer(HttpServletRequest request, @RequestBody Users userReq){
        Users urs = userRepository.findById(userByRequest(request).getId_usuario()).get();
        userReq.setLogin(urs.getLogin());
        userReq.setProfileEnum(urs.getProfileEnum());
        if(userReq.getPassword() == null){
            userReq.setPassword(urs.getPassword());
        }
        userReq.setPassword(passwordEncoder.encode(userReq.getPassword()));
        userReq = changeUser(urs.getId_usuario(), userReq);
        userReq.setPassword(null);
        return new ResponseEntity<Users>(userReq, HttpStatus.OK);
    }

}
