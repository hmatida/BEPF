package br.com.personal_financee.pf.controllers;


import br.com.personal_financee.pf.models.Account;
import br.com.personal_financee.pf.models.Launches;
import br.com.personal_financee.pf.repositories.AccountRepository;
import br.com.personal_financee.pf.repositories.LaunchesRepository;
import br.com.personal_financee.pf.repositories.ProviderRepository;
import br.com.personal_financee.pf.repositories.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/launches")
public class LauchesController {

    @Autowired
    private LaunchesRepository launchesRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private AccountRepository accountRepository;

    /*
     * Parâmetro de execução
     * */

    /**
    * Persiste lançamento no banco de dados.
    * **/
    private Launches cadLaunches(Launches launches){
        adjustLauncheBalance(launches);
        launchesRepository.save(launches);
        return launches;
    }

    private void saveAccount(Account account){
        accountRepository.save(account);
    }

    /**
    * Altera lançameto no banco de dados.
    * */
    private Launches changeLaunches(Long id, Launches launches){
        launches.setId_launch(id);
        launchesRepository.save(launches);
        recalcBalance(launches.getAccount());
        Launches lastLaunch = launchesRepository.getLastLaunchByAccount(launches.getAccount());
        lastLaunch.getAccount().setBalance(lastLaunch.getBalance());
        accountRepository.save(lastLaunch.getAccount());
        return launches;
    }

    /**
     * Deleta lançamento no banco baseado no Id
     * */

    private Launches deleteLaunche(Long idLaunche){
        Launches launches = launchesRepository.findById(idLaunche).get();
        launchesRepository.delete(launches);
        recalcBalance(launches.getAccount());
        Launches lastLaunch = launchesRepository.getLastLaunchByAccount(launches.getAccount());
        lastLaunch.getAccount().setBalance(lastLaunch.getBalance());
        accountRepository.save(lastLaunch.getAccount());
        return launches;
    }

    /**
     * Verifica de há lançamentos referente a conta.
     * */
    private boolean verifyExistsLanchInAccount(Account account){
        System.out.println(launchesRepository.verifyExistsLaunch(account));
        if(launchesRepository.verifyExistsLaunch(account) == null){
            return false;
        } else
            return true;
    }

    private void setTimeDate(Calendar date){
        date.set(Calendar.HOUR, date.getActualMinimum(Calendar.HOUR));
        date.set(Calendar.MINUTE, date.getActualMinimum(Calendar.MINUTE));
        date.set(Calendar.SECOND, date.getActualMinimum(Calendar.SECOND));
        date.set(Calendar.MILLISECOND, date.getActualMinimum(Calendar.MILLISECOND));
    }

    /**
    * Retorna o último lançamento efetuado à partir da data referente a conta
    * implícita na classe Launches.
    * */
    private Launches getLastLaunche(Launches launches){
        return launchesRepository.getLastLaunchByAccountAndData(launches.getAccount(),
                launches.getDt());
    }

    private void recalcBalance(Account account){
        List<Launches> launchesList= new ArrayList<>();
        launchesList.addAll(launchesRepository.getAllLaunchesByAccount(account));
        for (int i = 0; i < launchesList.size(); i++){
            if (i == 0){
                if (launchesList.get(i).getTypeOfLaunch().getDescricao().equals("Entrada")) {
                    launchesList.get(i).setBalance(launchesList.get(i).getAccount().getStartBalance()
                            + launchesList.get(i).getPay_value());
                } else if(launchesList.get(i).getTypeOfLaunch().getDescricao().equals("Saída")){
                    launchesList.get(i).setBalance(launchesList.get(i).getAccount().getStartBalance()
                            - launchesList.get(i).getPay_value());
                }
            } else {
                if (launchesList.get(i).getTypeOfLaunch().getDescricao().equals("Entrada")) {
                    launchesList.get(i).setBalance(launchesList.get(i-1).getBalance()
                            + launchesList.get(i).getPay_value());
                } else if(launchesList.get(i).getTypeOfLaunch().getDescricao().equals("Saída")){
                    launchesList.get(i).setBalance(launchesList.get(i-1).getBalance()
                            - launchesList.get(i).getPay_value());
                }
            }
        }

    }

    /**
     * Calcula e ajusta o saldo da conta.
     * Se operation = 0 -> soma
     * operation = 1 -> subtrai
     * */
    private void calcBalance(Launches launches, int operation){
        Launches lastLaunch = launchesRepository.getLastLaunchByAccount(launches.getAccount());
        if (operation == 0){
            if (lastLaunch.getDt().after(launches.getDt())){
                launchesRepository.save(launches);
                recalcBalance(launches.getAccount());

            } else{
                launches.setBalance(lastLaunch.getBalance()+launches.getPay_value());
            }
        } else {
            if (lastLaunch.getDt().after(launches.getDt())){
                launchesRepository.save(launches);
                recalcBalance(launches.getAccount());

            } else {
                launches.setBalance(lastLaunch.getBalance()-launches.getPay_value());
            }
        }
        lastLaunch = launchesRepository.getLastLaunchByAccount(launches.getAccount());
        lastLaunch.getAccount().setBalance(lastLaunch.getBalance());
        accountRepository.save(lastLaunch.getAccount());
    }

    /**
     * Seta todas as classes em Launches passsado por parâmetro
     * */
    private void setClasses(Launches launches){

        launches.setAccount(accountRepository.findById(launches.getAccount().getId_account()).get());
    }


    /**
     *Efetua o ajuste do saldo da conta de acordo com o lançamento.
     * */
    private void adjustLauncheBalance (Launches launches){

        //Condicionante de entrada.
        if (launches.getTypeOfLaunch().getDescricao().equals("Entrada")){
            if (verifyExistsLanchInAccount(launches.getAccount()) == false){
                launches.setBalance(launches.getAccount().getStartBalance()+launches.getPay_value());
                launches.getAccount().setBalance(launches.getBalance());
            }else{
                calcBalance(launches, 0);
            }
        } else { //Condicionante se não for entrada
            if (verifyExistsLanchInAccount(launches.getAccount()) == false){
                launches.setBalance(launches.getAccount().getStartBalance()-launches.getPay_value());
                launches.getAccount().setBalance(launches.getBalance());
            }else{
                calcBalance(launches, 1);
            }
        }
        saveAccount(launches.getAccount());
    }


    /**
     * End points
     * */

    @RequestMapping(method = RequestMethod.POST, value = "/cadastrar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Launches> postLaunches(@RequestBody Launches launches) {
        setClasses(launches);
        setTimeDate(launches.getDt());
        return new ResponseEntity<Launches>(this.cadLaunches(launches), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Launches>> getAllLaunches(){
        return new ResponseEntity<Collection<Launches>>((Collection<Launches>) launchesRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/change", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<Launches> changeLaunches(@RequestBody Launches launches){
        Launches laun = changeLaunches(launches.getId_launch(), launches);
        return new ResponseEntity<Launches>(laun, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Launches> getLauncheById(@PathVariable Long id){
        return new ResponseEntity<Launches>(launchesRepository.findById(id).get(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <Launches> deleteLauncheById(@PathVariable Long id){
        return new ResponseEntity<Launches>(deleteLaunche(id), HttpStatus.OK);
    }

}
