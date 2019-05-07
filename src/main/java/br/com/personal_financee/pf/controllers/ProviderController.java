package br.com.personal_financee.pf.controllers;

import br.com.personal_financee.pf.models.Provider;
import br.com.personal_financee.pf.models.Users;
import br.com.personal_financee.pf.passclasses.PassProvider;
import br.com.personal_financee.pf.repositories.CategoryRepository;
import br.com.personal_financee.pf.repositories.ProviderRepository;
import br.com.personal_financee.pf.repositories.SubCategoryRepository;
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
@RequestMapping("/provider")
public class ProviderController {

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    /**
     * Parâmetro de execução
     * */
    private Provider cadProvider(Provider passProvider, Users users){
        if (providerRepository.findByName(passProvider.getName_provider(), users) != null){
            return null;
        } else {
            passProvider.setUser(users);
            providerRepository.save(passProvider);
            passProvider.setUser(null);
            return passProvider;
        }
    }

    private PassProvider changeProvider(Long id, PassProvider provider, Users users){
        Provider pro = new Provider();
        pro.setId_provider(id);
        pro.setName_provider(provider.getName_provider());
        pro.setUser(users);
        if(provider.getCategory() != null){
            pro.setCategory(categoryRepository.findById(provider.getCategory()).get());
        }
        if(provider.getSubCategory() != null){
            pro.setSubCategory(subCategoryRepository.findById(provider.getSubCategory()).get());
        }
        providerRepository.save(pro);
        provider.setUsers(null);
        return provider;
    }

    public Users userByRequest(HttpServletRequest request){

        String token = request.getHeader("Authorization");
        String userName = jwtTokenUtil.getUsernameFromToken(token);

        return userRepository.findByLogin(userName);
    }




    /**
     * End points
     * */

    @RequestMapping(method = RequestMethod.POST, value = "/cadastrar", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Provider> postProvider(@RequestBody Provider provider, HttpServletRequest request) {
        if (cadProvider(provider, userByRequest(request)) == null){
            return new ResponseEntity<Provider>(provider, HttpStatus.NOT_ACCEPTABLE);
        } else {
            cadProvider(provider, userByRequest(request));
            return new ResponseEntity<Provider>(provider, HttpStatus.CREATED);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Provider>> getAllProviders(HttpServletRequest request){
        return new ResponseEntity<Collection<Provider>>((Collection<Provider>) providerRepository
                .findAllProvidersByUser(userByRequest(request)), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/change", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<PassProvider> changeProvi(@RequestBody PassProvider provider, HttpServletRequest request){
        PassProvider acc = changeProvider(provider.getId_provider(), provider, userByRequest(request));
        return new ResponseEntity<PassProvider>(acc, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Provider> getProviderByid(@PathVariable Long id){
        return new ResponseEntity<Provider>(providerRepository.findById(id).get(), HttpStatus.OK);
    }
}
