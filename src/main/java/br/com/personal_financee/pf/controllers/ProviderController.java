package br.com.personal_financee.pf.controllers;

import br.com.personal_financee.pf.models.Provider;
import br.com.personal_financee.pf.passclasses.PassProvider;
import br.com.personal_financee.pf.repositories.CategoryRepository;
import br.com.personal_financee.pf.repositories.ProviderRepository;
import br.com.personal_financee.pf.repositories.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    /**
     * Parâmetro de execução
     * */
    private Provider cadProvider(Provider passProvider){
        if (providerRepository.findByName(passProvider.getName_provider()) != null){
            return null;
        } else {
            providerRepository.save(passProvider);
            return passProvider;
        }
    }

    private PassProvider changeProvider(Long id, PassProvider provider){
        Provider pro = new Provider();
        pro.setId_provider(id);
        pro.setName_provider(provider.getName_provider());
        if(provider.getCategory() != null){
            pro.setCategory(categoryRepository.findById(provider.getCategory()).get());
        }
        if(provider.getSubCategory() != null){
            pro.setSubCategory(subCategoryRepository.findById(provider.getSubCategory()).get());
        }
        providerRepository.save(pro);
        return provider;
    }




    /**
     * End points
     * */

    @RequestMapping(method = RequestMethod.POST, value = "/cadastrar", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Provider> postProvider(@RequestBody Provider provider) {
        if (cadProvider(provider) == null){
            return new ResponseEntity<Provider>(provider, HttpStatus.NOT_ACCEPTABLE);
        } else {
            cadProvider(provider);
            return new ResponseEntity<Provider>(provider, HttpStatus.CREATED);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Provider>> getAllProviders(){
        return new ResponseEntity<Collection<Provider>>((Collection<Provider>) providerRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/change", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<PassProvider> changeProvi(@RequestBody PassProvider provider){
        PassProvider acc = changeProvider(provider.getId_provider(), provider);
        return new ResponseEntity<PassProvider>(acc, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Provider> getProviderByid(@PathVariable Long id){
        return new ResponseEntity<Provider>(providerRepository.findById(id).get(), HttpStatus.OK);
    }
}
