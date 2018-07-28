package br.com.personal_financee.pf.controllers;

import br.com.personal_financee.pf.models.SubCategory;
import br.com.personal_financee.pf.repositories.LaunchesPredictionRepository;
import br.com.personal_financee.pf.repositories.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/prevision")
public class PrevisonController {

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private LaunchesPredictionRepository launchesPredictionRepository;

    /**
     * Parâmetros de execução
     * */


    /**
     * End points
     * */

}
