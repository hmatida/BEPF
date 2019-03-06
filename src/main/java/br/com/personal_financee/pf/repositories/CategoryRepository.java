package br.com.personal_financee.pf.repositories;

import br.com.personal_financee.pf.models.Account;
import br.com.personal_financee.pf.models.Category;
import br.com.personal_financee.pf.models.Launches;
import br.com.personal_financee.pf.models.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Calendar;
import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    //Retorna todas as categorias dado o usuário
    @Query("SELECT ca FROM Category ca WHERE ca.users=:user ORDER BY ca.typeOfLaunch")
    public List<Category> findAllCategoriesByUser(@Param("user") Users user);
}
