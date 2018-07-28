package br.com.personal_financee.pf.controllers;

import br.com.personal_financee.pf.models.Account;
import br.com.personal_financee.pf.models.LaunchPrediction;
import br.com.personal_financee.pf.models.Launches;
import br.com.personal_financee.pf.passclasses.ExtractFilter;
import br.com.personal_financee.pf.repositories.LaunchesPredictionRepository;
import br.com.personal_financee.pf.repositories.LaunchesRepository;
import br.com.personal_financee.pf.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
//@CrossOrigin("http://localhost:4200")
@RequestMapping("/prediction")
public class PredictionController {

    @Autowired
    private LaunchesPredictionRepository launchesPredictionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LaunchesRepository launchesRepository;

    /**
     * Parâmetro de execução
     * */
    private LaunchPrediction saveLaunchePrediction(LaunchPrediction launchPrediction){
        launchesPredictionRepository.save(launchPrediction);
        return launchPrediction;
    }

    private Collection<LaunchPrediction> saveCollectionsLanches(Collection<LaunchPrediction> launches){
        launchesPredictionRepository.saveAll(launches);
        System.out.println(launches);
        return launches;
    }


    /**
     * End points
     * */
    @RequestMapping(method = RequestMethod.POST, value = "/cadastros", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<LaunchPrediction>> postMultiplePredictions(@RequestBody
            Collection<LaunchPrediction> launchesPredictions){
        System.out.println("Ëntrou...");
        return new ResponseEntity<Collection<LaunchPrediction>>(
                saveCollectionsLanches(launchesPredictions), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/cadastrar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <LaunchPrediction> postCad(@RequestBody LaunchPrediction launche) {
        return new ResponseEntity <LaunchPrediction>(saveLaunchePrediction(launche), HttpStatus.CREATED);
    }


}
