package br.com.personal_financee.pf.controllers;

import br.com.personal_financee.pf.models.LaunchPrediction;
import br.com.personal_financee.pf.models.Users;
import br.com.personal_financee.pf.passclasses.PredictionPass;
import br.com.personal_financee.pf.repositories.LaunchesPredictionRepository;
import br.com.personal_financee.pf.repositories.LaunchesRepository;
import br.com.personal_financee.pf.repositories.UserRepository;
import br.com.personal_financee.pf.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/prediction")
public class PredictionController {

    @Autowired
    private LaunchesPredictionRepository launchesPredictionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LaunchesRepository launchesRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * Parâmetro de execução
     * */

    private Users userByRequest(HttpServletRequest request){

        String token = request.getHeader("Authorization");
        String userName = jwtTokenUtil.getUsernameFromToken(token);

        return userRepository.findByLogin(userName);
    }

    private void setTimeDateMin(Calendar date){
        date.set(Calendar.HOUR, date.getActualMinimum(Calendar.HOUR));
        date.set(Calendar.MINUTE, date.getActualMinimum(Calendar.MINUTE));
        date.set(Calendar.SECOND, date.getActualMinimum(Calendar.SECOND));
        date.set(Calendar.MILLISECOND, date.getActualMinimum(Calendar.MILLISECOND));
    }

    private void setTimeDateMax(Calendar date){
        date.set(Calendar.HOUR, date.getActualMaximum(Calendar.HOUR));
        date.set(Calendar.MINUTE, date.getActualMaximum(Calendar.MINUTE));
        date.set(Calendar.SECOND, date.getActualMaximum(Calendar.SECOND));
        date.set(Calendar.MILLISECOND, date.getActualMaximum(Calendar.MILLISECOND));
    }

    private LaunchPrediction saveLaunchePrediction(LaunchPrediction launchPrediction){
        launchesPredictionRepository.save(launchPrediction);
        return launchPrediction;
    }

    private Collection<LaunchPrediction> saveCollectionsLanches(Users user, Collection<LaunchPrediction> launches){

        List<LaunchPrediction> lisLaunches = new ArrayList<>();
        lisLaunches.addAll(launches);
        for (int i = 0; i < lisLaunches.size(); i++) {
            lisLaunches.get(i).setUser(user);
        }

        launchesPredictionRepository.saveAll(lisLaunches);
        return lisLaunches;
    }

    private LaunchPrediction deleteLaunche(Long id){
        LaunchPrediction launche = launchesPredictionRepository.findById(id).get();
        launchesPredictionRepository.delete(launche);
        return launche;
    }

    /**
     * Altera lançameto no banco de dados.
     * */
    private LaunchPrediction changeLaunches(Users user, Long id, LaunchPrediction launch){
        launch.setId_launchPrediction(id);
        launchesPredictionRepository.save(launch);
        return launch;
    }

    private Collection<LaunchPrediction> getPredictionByOperation(Users user, PredictionPass pass){
        int operation = pass.getPeriodo();
        Calendar now = Calendar.getInstance();
        setTimeDateMin(now);
        Calendar after = Calendar.getInstance();

        switch (operation){
            case 0:
                after.add(Calendar.DATE, 10);
                setTimeDateMax(after);
                setTimeDateMin(now);
                return launchesPredictionRepository.getAllPredictionsNotPayByUserAndDataInterval(user, pass.getIsPay(), now, after);
            case 1:
                after.add(Calendar.DATE, 30);
                setTimeDateMax(after);
                setTimeDateMin(now);
                return launchesPredictionRepository.getAllPredictionsNotPayByUserAndDataInterval(user, pass.getIsPay(), now, after);
            case 2:
                now.add(Calendar.MONTH, 1);
                now.set(Calendar.DATE, now.getActualMinimum(Calendar.DATE));
                setTimeDateMin(now);
                after.add(Calendar.MONTH, 1);
                after.set(Calendar.DATE, after.getActualMaximum(Calendar.DATE));
                setTimeDateMax(after);
                return launchesPredictionRepository.getAllPredictionsNotPayByUserAndDataInterval(user, pass.getIsPay(), now, after);
            case 3:
                now.set(Calendar.DATE, now.getActualMinimum(Calendar.DATE));
                setTimeDateMin(now);
                after.set(Calendar.DATE, after.getActualMaximum(Calendar.DATE));
                setTimeDateMax(after);
                return launchesPredictionRepository.getAllPredictionsNotPayByUserAndDataInterval(user, pass.getIsPay(), now, after);
            case 4:
                return launchesPredictionRepository.getAllPredictionsNotPayByUser(user, pass.getIsPay());
            case 5:
                setTimeDateMin(pass.getInitDate());
                setTimeDateMax(pass.getFinalDate());
                return launchesPredictionRepository.getAllPredictionsNotPayByUserAndDataInterval(user, pass.getIsPay(), pass.getInitDate(), pass.getFinalDate());
            default:
                return null;
        }

    }

    /**
     * Teste de print
     * */
    private void printer(Collection<LaunchPrediction> launchPredictions){
        List<LaunchPrediction> lc = new ArrayList<LaunchPrediction>();
        lc.addAll(launchPredictions);

        for (int i = 0; i < lc.size(); i++) {
            System.out.println("Valores -----> "+lc.get(i).getIsPay());
        }
    }


    /**
     * End points
     * */
    @RequestMapping(method = RequestMethod.POST, value = "/cadastros", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<LaunchPrediction>> postMultiplePredictions(HttpServletRequest request, @RequestBody
            Collection<LaunchPrediction> launchesPredictions){
        return new ResponseEntity<Collection<LaunchPrediction>>(
                saveCollectionsLanches(userByRequest(request), launchesPredictions), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/cadastrar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <LaunchPrediction> postCad(HttpServletRequest request,@RequestBody LaunchPrediction launche) {
        launche.setUser(userByRequest(request));
        return new ResponseEntity <LaunchPrediction>(saveLaunchePrediction(launche), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/filter", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public ResponseEntity <Collection<LaunchPrediction>>filterPrediction(HttpServletRequest request, @RequestBody PredictionPass pass) {
        Users user = userByRequest(request);
        return new ResponseEntity <Collection<LaunchPrediction>>(getPredictionByOperation(user, pass), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/change", consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public  ResponseEntity<LaunchPrediction> changePrediction(HttpServletRequest request, @RequestBody LaunchPrediction launche){
        launche.setUser(userByRequest(request));
        LaunchPrediction laun = changeLaunches(launche.getUser(), launche.getId_launchPrediction(), launche);
        return new ResponseEntity<LaunchPrediction>(laun, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/delete/{id}", produces = APPLICATION_JSON_VALUE)
    public  ResponseEntity<LaunchPrediction> deletePrediction(HttpServletRequest request, @PathVariable Long id){
        LaunchPrediction laun = deleteLaunche(id);
        return new ResponseEntity<LaunchPrediction>(laun, HttpStatus.OK);
    }


}
