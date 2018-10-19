package br.com.personal_financee.pf.controllers;


import br.com.personal_financee.pf.models.Account;
import br.com.personal_financee.pf.models.Launches;
import br.com.personal_financee.pf.models.Users;
import br.com.personal_financee.pf.repositories.*;
import br.com.personal_financee.pf.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    private final Path rootLocation = Paths.get("/Users/hernanematida/Documents/Projetos em andamento/pf/BEPF/src/" +
                                                "main/java/br/com/personal_financee/pf/models/atachs");

    private String fileName = null;
    private String unsTorageName = null;



    /*
     * Parâmetro de execução
     * */

    /**
     * Grava o arquivo anexo em rootLocation.
     * @return void
     * */
    private void storeAtachment(MultipartFile file, String fileName){
        String[] extension = file.getContentType().split("/");
        System.out.println(file.getContentType());
        try {
            if (fileName != null) {
                Files.copy(file.getInputStream(), this.rootLocation.resolve(fileName + "." + extension[extension.length - 1]));
            } else {
                Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
            }
            this.fileName = "."+extension[extension.length-1];
            this.unsTorageName = file.getOriginalFilename();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteAtachemt(String fileName){
        try {
            Path file = rootLocation.resolve(fileName);
            Files.delete(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Resource loadFileStored(String fileName) throws MalformedURLException {
        Path file = rootLocation.resolve(fileName);
        Resource resource = new UrlResource(file.toUri());
        if (resource.exists() || resource.isReadable()){
            return resource;
        } else {
            return null;
        }
    }

    private void changeStoredName(String fileNameUnchanged, String fileNewNameToChange) throws IOException {
        System.out.println(this.rootLocation.getRoot());
        Files.move(rootLocation.resolve(fileNameUnchanged), this.rootLocation.resolve(fileNewNameToChange));
    }

    /**
    * Persiste lançamento no banco de dados.
     * 16/10/2018 -> Adicionado o salvamento de anexo (file) pertinente ao lançamento.
    * **/
    private Launches cadLaunches(Launches launches) throws IOException {
        adjustLauncheBalance(launches.getUser(), launches);
        launchesRepository.save(launches);
        if (this.fileName != null){
            changeStoredName(this.unsTorageName, launches.getId_launch().toString()+this.fileName);
            launches.setAtach(launches.getId_launch().toString()+this.fileName);
            launchesRepository.save(launches);
            this.fileName = null;
            this.unsTorageName = null;
        }
        return launches;
    }

    private void saveAccount(Account account){
        accountRepository.save(account);
    }

    /**
    * Altera lançameto no banco de dados.
    * */
    private Launches changeLaunches(Users user, Long id, Launches launches){
        launches.setId_launch(id);
        launchesRepository.save(launches);
        recalcBalance(user, launches.getAccount());
        Launches lastLaunch = launchesRepository.getLastLaunchByAccount(user, launches.getAccount());
        lastLaunch.getAccount().setBalance(lastLaunch.getBalance());
        accountRepository.save(lastLaunch.getAccount());
        return launches;
    }

    /**
     * Deleta lançamento no banco baseado no Id
     * */

    private Launches deleteLaunche(Users user, Long idLaunche){
        Launches launches = launchesRepository.findById(idLaunche).get();
        launchesRepository.delete(launches);
        recalcBalance(user, launches.getAccount());
        Launches lastLaunch = launchesRepository.getLastLaunchByAccount(user, launches.getAccount());
        lastLaunch.getAccount().setBalance(lastLaunch.getBalance());
        accountRepository.save(lastLaunch.getAccount());
        return launches;
    }

    /**
     * Verifica de há lançamentos referente a conta.
     * */
    private boolean verifyExistsLanchInAccount(Users user, Account account){
        System.out.println(launchesRepository.verifyExistsLaunch(user, account));
        if(launchesRepository.verifyExistsLaunch(user, account) == null){
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
    private Launches getLastLaunche(Users user, Launches launches){
        return launchesRepository.getLastLaunchByAccountAndData(user, launches.getAccount(),
                launches.getDt());
    }

    private void recalcBalance(Users user, Account account){
        List<Launches> launchesList= new ArrayList<>();
        launchesList.addAll(launchesRepository.getAllLaunchesByAccount(user, account));
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
    private void calcBalance(Users user, Launches launches, int operation){
        Launches lastLaunch = launchesRepository.getLastLaunchByAccount(user, launches.getAccount());
        if (operation == 0){
            if (lastLaunch.getDt().after(launches.getDt())){
                launchesRepository.save(launches);
                recalcBalance(user, launches.getAccount());

            } else{
                launches.setBalance(lastLaunch.getBalance()+launches.getPay_value());
            }
        } else {
            if (lastLaunch.getDt().after(launches.getDt())){
                launchesRepository.save(launches);
                recalcBalance(user, launches.getAccount());

            } else {
                launches.setBalance(lastLaunch.getBalance()-launches.getPay_value());
            }
        }
        lastLaunch = launchesRepository.getLastLaunchByAccount(user, launches.getAccount());
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
    private void adjustLauncheBalance (Users user, Launches launches){

        //Condicionante de entrada.
        if (launches.getTypeOfLaunch().getDescricao().equals("Entrada")){
            if (verifyExistsLanchInAccount(user, launches.getAccount()) == false){
                launches.setBalance(launches.getAccount().getStartBalance()+launches.getPay_value());
                launches.getAccount().setBalance(launches.getBalance());
            }else{
                calcBalance(user, launches, 0);
            }
        } else { //Condicionante se não for entrada
            if (verifyExistsLanchInAccount(user, launches.getAccount()) == false){
                launches.setBalance(launches.getAccount().getStartBalance()-launches.getPay_value());
                launches.getAccount().setBalance(launches.getBalance());
            }else{
                calcBalance(user, launches, 1);
            }
        }
        saveAccount(launches.getAccount());
    }

    public Users userByRequest(HttpServletRequest request){

        String token = request.getHeader("Authorization");
        String userName = jwtTokenUtil.getUsernameFromToken(token);

        return userRepository.findByLogin(userName);
    }


    /**
     * End points
     * */

    @RequestMapping(method = RequestMethod.POST, value = "/cadastrar", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Launches> postLaunches(HttpServletRequest request, @RequestBody Launches launches) {
        setClasses(launches);
        setTimeDate(launches.getDt());
        launches.setUser(userByRequest(request));
        try {
            Launches lc = this.cadLaunches(launches);
            return new ResponseEntity<Launches>(lc, HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * Teste de upload de arquivo.
     * */
    @RequestMapping(method = RequestMethod.POST, value = "/upload")
    public ResponseEntity<String> uploadTeste(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        if (!file.isEmpty()){
            try {
                this.storeAtachment(file, null);
                String response = "Upload enviado com sucesso!";
                System.out.println(file.getOriginalFilename());
                return new ResponseEntity<String>(response, HttpStatus.OK);
            } catch (Exception e){
                String response = e.getMessage();
                return new ResponseEntity<String>(response, HttpStatus.NOT_ACCEPTABLE);
            }

        }
        return new ResponseEntity<String>("Deu certo", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/change", consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public  ResponseEntity<Launches> changeLaunches(HttpServletRequest request, @RequestBody Launches launches){
        launches.setUser(userByRequest(request));
        Launches laun = changeLaunches(launches.getUser(), launches.getId_launch(), launches);
        return new ResponseEntity<Launches>(laun, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Launches> getLauncheById(HttpServletRequest request, @PathVariable Long id){
        return new ResponseEntity<Launches>(launchesRepository.findById(id).get(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "delete/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity <Launches> deleteLauncheById(HttpServletRequest request, @PathVariable Long id){
        Users user = userByRequest(request);
        return new ResponseEntity<Launches>(deleteLaunche(user, id), HttpStatus.OK);
    }

    @GetMapping("file/{id}")
    @ResponseBody
    public ResponseEntity<Resource> getAtachFile(@PathVariable Long id) throws IOException {
        Launches laun = launchesRepository.findById(id).get();
        Resource resource = loadFileStored(laun.getAtach());
        System.out.println("teste.....");
        System.out.println(resource.getFilename());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"%s\""+resource.getFilename())
                .body(resource);
    }

}
