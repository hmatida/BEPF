package br.com.personal_financee.pf.controllers;

import br.com.personal_financee.pf.models.Account;
import br.com.personal_financee.pf.models.Launches;
import br.com.personal_financee.pf.models.TypeOfLaunch;
import br.com.personal_financee.pf.models.Users;
import br.com.personal_financee.pf.models.report_models.PieDataModel;
import br.com.personal_financee.pf.models.report_models.PredictionDashboardDataModel;
import br.com.personal_financee.pf.models.report_models.PrepareRecipeAndExpenses;
import br.com.personal_financee.pf.models.report_models.RecipeAndExpenses;
import br.com.personal_financee.pf.passclasses.ExtractFilter;
import br.com.personal_financee.pf.repositories.AccountRepository;
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
import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private LaunchesRepository launchesRepository;

    @Autowired
    private LaunchesPredictionRepository launchesPredictionRepository;


    /**
     * Lógica dos negócios
     * */


    /**
     * Utilities
     * */
    public Users userByRequest(HttpServletRequest request){

        String token = request.getHeader("Authorization");
        String userName = jwtTokenUtil.getUsernameFromToken(token);

        return userRepository.findByLogin(userName);
    }

    //Preparação dos dados.
    private Collection<RecipeAndExpenses> prepareAmount(Users user){
        Calendar now = Calendar.getInstance();
        Calendar dtFinal = Calendar.getInstance();
        dtFinal = prepareMax(dtFinal);
        Calendar dtInicial = Calendar.getInstance();
        dtInicial = prepareMax(dtInicial);
        List<PrepareRecipeAndExpenses> prepareRecipeAndExpenses = new ArrayList<>();
        List<RecipeAndExpenses> recipeAndExpenses = new ArrayList<>();
        int value = 0;
        for (int i = 0; i<12; i++) {
            dtFinal.add(Calendar.MONTH, (-1) * value);
            dtFinal.set(Calendar.DAY_OF_MONTH, dtFinal.getActualMaximum(Calendar.DAY_OF_MONTH));
//            System.out.println("Dt final: " + dtFinal.get(Calendar.DAY_OF_MONTH) + "/" + dtFinal.get(Calendar.MONTH) + "/" + dtFinal.get(Calendar.YEAR));
            dtInicial.add(Calendar.MONTH, (-1) * value);
            dtInicial.set(Calendar.DAY_OF_MONTH, dtInicial.getActualMinimum(Calendar.DAY_OF_MONTH));
            dtInicial.add(Calendar.DAY_OF_MONTH, -1);
            prepareRecipeAndExpenses.addAll(launchesRepository.getLaunchesByAccountForSumRecipeAndExpenses(user, dtInicial, dtFinal));
            value = 1;

            if (prepareRecipeAndExpenses.size() > 0 ){
                RecipeAndExpenses rec = new RecipeAndExpenses();
                Integer v = dtFinal.get(Calendar.MONTH);
                v = v + 1;
                rec.setDate(v+"/"+dtFinal.get(Calendar.YEAR));
                verificaOperacao(prepareRecipeAndExpenses.get(0), rec);
                if (prepareRecipeAndExpenses.size() > 1){
                    verificaOperacao(prepareRecipeAndExpenses.get(1), rec);
                }
                recipeAndExpenses.add(rec);
            }
            prepareRecipeAndExpenses.clear();

        }
        return recipeAndExpenses;
    }

    private void verificaOperacao(PrepareRecipeAndExpenses prepareRecipeAndExpenses, RecipeAndExpenses recipeAndExpenses){
        if (prepareRecipeAndExpenses.getTypeOfLaunch().getDescricao().equals("Entrada")){
            recipeAndExpenses.setRecipe(prepareRecipeAndExpenses.getSumValue());
        } else if (prepareRecipeAndExpenses.getTypeOfLaunch().getDescricao().equals("Saída")){
            recipeAndExpenses.setExpenses(prepareRecipeAndExpenses.getSumValue());
        }
    }

    private List<PieDataModel>dataPieChartExpCategory(Users users){

        List<PieDataModel> datas = new ArrayList<>();

        Calendar dtFinal = Calendar.getInstance();
        dtFinal = prepareMax(dtFinal);
        Calendar dtInicial = Calendar.getInstance();
        dtInicial = prepareMax(dtInicial);

        dtFinal.set(Calendar.DAY_OF_MONTH, dtFinal.getActualMaximum(Calendar.DAY_OF_MONTH));
        dtInicial.add(Calendar.MONTH, -1);
        dtInicial.set(Calendar.DAY_OF_MONTH, dtInicial.getActualMaximum(Calendar.DAY_OF_MONTH));


        datas.addAll(launchesRepository.getLaunchesByAccountForSumExpensesByCategory(users, dtInicial, dtFinal));
        for (int i=0; i<datas.size(); i++){
            Random randCol = new Random();

            datas.get(i).setColor(String.format("#%06X", randCol.nextInt(0xFFFFFF+1)));
        }

        return datas;
    }

    private Collection<PieDataModel> dataPieChart(Users users){
        List<PieDataModel> pieDataModels = new ArrayList<>();

        Calendar dtFinal = Calendar.getInstance();
        dtFinal = prepareMax(dtFinal);
        Calendar dtInicial = Calendar.getInstance();
        dtInicial = prepareMax(dtInicial);

        dtFinal.set(Calendar.DAY_OF_MONTH, dtFinal.getActualMaximum(Calendar.DAY_OF_MONTH));
        dtInicial.add(Calendar.MONTH, -1);
        dtInicial.set(Calendar.DAY_OF_MONTH, dtInicial.getActualMaximum(Calendar.DAY_OF_MONTH));


        pieDataModels.addAll(launchesRepository.getLaunchesByAccountForSumExpenses(users, dtInicial, dtFinal));
        for (int i=0; i<pieDataModels.size(); i++){
            Random randCol = new Random();

            pieDataModels.get(i).setColor(String.format("#%06X", randCol.nextInt(0xFFFFFF+1)));
        }
        return pieDataModels;
    }

    private Collection<PieDataModel> dataPieChartRecept(Users users){
        List<PieDataModel> pieDataModels = new ArrayList<>();

        Calendar dtFinal = Calendar.getInstance();
        dtFinal = prepareMax(dtFinal);
        Calendar dtInicial = Calendar.getInstance();
        dtInicial = prepareMax(dtInicial);

        dtFinal.set(Calendar.DAY_OF_MONTH, dtFinal.getActualMaximum(Calendar.DAY_OF_MONTH));
        dtInicial.add(Calendar.MONTH, -1);
        dtInicial.set(Calendar.DAY_OF_MONTH, dtInicial.getActualMaximum(Calendar.DAY_OF_MONTH));


        pieDataModels.addAll(launchesRepository.getLaunchesByAccountForSumRecepts(users, dtInicial, dtFinal));
        for (int i=0; i<pieDataModels.size(); i++){
            Random randCol = new Random();

            pieDataModels.get(i).setColor(String.format("#%06X", randCol.nextInt(0xFFFFFF+1)));
        }
        return pieDataModels;
    }

    private List<PieDataModel> dataPieChartRecCategory(Users users){
        List<PieDataModel> pieDataModels = new ArrayList<>();

        Calendar dtFinal = Calendar.getInstance();
        dtFinal = prepareMax(dtFinal);
        Calendar dtInicial = Calendar.getInstance();
        dtInicial = prepareMax(dtInicial);

        dtFinal.set(Calendar.DAY_OF_MONTH, dtFinal.getActualMaximum(Calendar.DAY_OF_MONTH));
        dtInicial.add(Calendar.MONTH, -1);
        dtInicial.set(Calendar.DAY_OF_MONTH, dtInicial.getActualMaximum(Calendar.DAY_OF_MONTH));


        pieDataModels.addAll(launchesRepository.getLaunchesByAccountForSumReceByCategory(users, dtInicial, dtFinal));
        for (int i=0; i<pieDataModels.size(); i++){
            Random randCol = new Random();

            pieDataModels.get(i).setColor(String.format("#%06X", randCol.nextInt(0xFFFFFF+1)));
        }
        return pieDataModels;
    }

    private Collection<PredictionDashboardDataModel> getAllPredictionsVecidos(Users users){
        Calendar dtFinal = Calendar.getInstance();
        dtFinal = prepareMax(dtFinal);
        dtFinal.add(Calendar.DAY_OF_MONTH, 30);
        return launchesPredictionRepository.getAllPredictionsVencidosByUser(users, dtFinal);
    }

    private Calendar prepareMax(Calendar calendar){
        calendar.set(Calendar.HOUR, calendar.getActualMaximum(Calendar.HOUR));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
        return calendar;
    }
    private Calendar prepareMin(Calendar calendar){
        calendar.set(Calendar.HOUR, calendar.getActualMinimum(Calendar.HOUR));
        calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
        return calendar;
    }

    /**
     * End points
     * */
    @RequestMapping(method = RequestMethod.GET, value = "/barchart", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity  <Collection<RecipeAndExpenses>> barChart(HttpServletRequest request){
        return new ResponseEntity<Collection<RecipeAndExpenses>>(prepareAmount(userByRequest(request)), HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/piechart", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity  <Collection<PieDataModel>> pieChart(HttpServletRequest request){
        return new ResponseEntity<Collection<PieDataModel>>(dataPieChart(userByRequest(request)), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/piechartrecepts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity  <Collection<PieDataModel>> pieChart2(HttpServletRequest request){
        return new ResponseEntity<Collection<PieDataModel>>(dataPieChartRecept(userByRequest(request)), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/piechartcategoryexp", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity  <Collection<PieDataModel>> piechartcategoryexp(HttpServletRequest request){
        return new ResponseEntity<Collection<PieDataModel>>(dataPieChartExpCategory(userByRequest(request)), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/piechartcategoryrecp", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity  <Collection<PieDataModel>> piechartcategoryrecpt(HttpServletRequest request){
        return new ResponseEntity<Collection<PieDataModel>>(dataPieChartRecCategory(userByRequest(request)), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/allpredvcdo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity  <Collection<PredictionDashboardDataModel>> allPredicionForDashboard(HttpServletRequest request){
        return new ResponseEntity<Collection<PredictionDashboardDataModel>>(getAllPredictionsVecidos(userByRequest(request)), HttpStatus.OK);
    }

}
