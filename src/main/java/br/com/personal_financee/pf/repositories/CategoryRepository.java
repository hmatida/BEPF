package br.com.personal_financee.pf.repositories;

import br.com.personal_financee.pf.models.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
