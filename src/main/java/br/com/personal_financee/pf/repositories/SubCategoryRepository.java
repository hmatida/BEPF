package br.com.personal_financee.pf.repositories;

import br.com.personal_financee.pf.models.Category;
import br.com.personal_financee.pf.models.SubCategory;
import br.com.personal_financee.pf.models.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface SubCategoryRepository extends CrudRepository<SubCategory, Long> {

    //Buscar subcategorias por id de categoria
    @Query("SELECT subcat FROM SubCategory subcat WHERE subcat.category.id_category =:id_category")
    public Collection<SubCategory> findSubCategoriesByIdCategory(@Param("id_category") Long id_category);

    //Retorna todas as categorias dado o usuário
    @Query("SELECT subcat FROM SubCategory subcat WHERE subcat.users=:user ORDER BY subcat.category.typeOfLaunch")
    public List<SubCategory> getAllSubCatecoryByUser(@Param("user") Users user);
}
