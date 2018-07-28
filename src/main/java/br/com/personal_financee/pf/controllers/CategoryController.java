package br.com.personal_financee.pf.controllers;


import br.com.personal_financee.pf.models.Account;
import br.com.personal_financee.pf.models.Category;
import br.com.personal_financee.pf.models.SubCategory;
import br.com.personal_financee.pf.passclasses.PassSubCategory;
import br.com.personal_financee.pf.repositories.CategoryRepository;
import br.com.personal_financee.pf.repositories.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    /**
     * Parâmetro de execução
     * */
    private Category cadCategory(Category category){
        categoryRepository.save(category);
        return category;
    }

    private SubCategory cadSubCategory(SubCategory subCategory){
        subCategoryRepository.save(subCategory);
        return subCategory;
    }

    private Category changeCategory(Long id, Category category){
        category.setId_category(id);
        categoryRepository.save(category);
        return category;
    }

    private SubCategory changeSubCategory(Long id, SubCategory subCategory){
        subCategory.setId_subCategory(id);
        subCategoryRepository.save(subCategory);
        return subCategory;
    }


    /**
     * End points
     * */

    @RequestMapping(method = RequestMethod.POST, value = "/cadastrar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> postCategory(@RequestBody Category category) {

            return new ResponseEntity<Category>(this.cadCategory(category), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Category>> getAllCategories(){
        return new ResponseEntity<Collection<Category>>((Collection<Category>) categoryRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/change", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<Category> changeCategory(@RequestBody Category category){
        Category cat = changeCategory(category.getId_category(), category);
        return new ResponseEntity<Category>(cat, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id){
        return new ResponseEntity<Category>(categoryRepository.findById(id).get(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/cadastrarsubcategory", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SubCategory> postSubCategory(@RequestBody SubCategory subCategory) {

        return new ResponseEntity<SubCategory>(cadSubCategory(subCategory), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/changesubcategory", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<SubCategory> putSubCategory(@RequestBody SubCategory subCategory){
        SubCategory cat = changeSubCategory(subCategory.getId_subCategory(), subCategory);
        return new ResponseEntity<SubCategory>(cat, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "subcategories/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<SubCategory>> getAllSubCategoryByIdCategory(@PathVariable Long id){
        return new ResponseEntity<Collection<SubCategory>>((Collection<SubCategory>)
                subCategoryRepository.findSubCategoriesByIdCategory(id), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/allsubcategory", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<SubCategory>> getAllSubCategories(){
        return new ResponseEntity<Collection<SubCategory>>((Collection<SubCategory>) subCategoryRepository.findAll(), HttpStatus.OK);
    }
}
