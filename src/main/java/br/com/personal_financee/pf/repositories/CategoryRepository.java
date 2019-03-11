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

    //Retorna todas as categorias dado o usuário somente visíveis
    @Query("SELECT ca FROM Category ca WHERE ca.users=:user and ca.visible=0 ORDER BY ca.typeOfLaunch")
    public List<Category> findAllCategoriesByUser(@Param("user") Users user);

    //Retorna todas as categorias dado o usuário todos
    @Query("SELECT ca FROM Category ca WHERE ca.users=:user ORDER BY ca.typeOfLaunch")
    public List<Category> findAllCategoriesByUserAll(@Param("user") Users user);

    //Retorna uma lista de categorias que contem o nome
    @Query("SELECT ca FROM Category ca WHERE ca.users=:user AND ca.name_category LIKE :name ORDER BY ca.typeOfLaunch")
    public List<Category> findAllCategoriesByUserAndName(@Param("user") Users user, @Param("name")String name_category);
}
