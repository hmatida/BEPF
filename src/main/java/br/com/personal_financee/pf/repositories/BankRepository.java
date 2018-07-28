package br.com.personal_financee.pf.repositories;

import br.com.personal_financee.pf.models.Bank;
import org.springframework.data.repository.CrudRepository;

public interface BankRepository extends CrudRepository<Bank, Long> {
}
