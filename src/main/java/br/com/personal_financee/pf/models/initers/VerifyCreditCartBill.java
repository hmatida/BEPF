package br.com.personal_financee.pf.models.initers;

import br.com.personal_financee.pf.models.Account;
import br.com.personal_financee.pf.models.TypeOfAccount;
import br.com.personal_financee.pf.models.Users;
import br.com.personal_financee.pf.repositories.AccountRepository;
import br.com.personal_financee.pf.repositories.LaunchesPredictionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class VerifyCreditCartBill {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private LaunchesPredictionRepository launchesPredictionRepository;

    public void launchBillToPrevision(Users user){

        List<Account> accounts = new ArrayList<Account>();

        accounts.addAll(accountRepository.getAllAccountsByUserAndTpAccount(user, TypeOfAccount.CC));

    }
}
