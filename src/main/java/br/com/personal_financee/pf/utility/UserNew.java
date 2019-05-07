package br.com.personal_financee.pf.utility;

import br.com.personal_financee.pf.models.Category;
import br.com.personal_financee.pf.models.SubCategory;
import br.com.personal_financee.pf.models.TypeOfLaunch;
import br.com.personal_financee.pf.models.Users;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserNew {

    public static Collection<Category> cadCategories(Users users){

        Collection<Category> categories = new ArrayList<>();

        Category cat1 = new Category();
        cat1.setName_category("Salários");
        cat1.setTypeOfLaunch(TypeOfLaunch.C);
        cat1.setUsers(users);

        ((ArrayList<Category>) categories).add(cat1);

        Category cat2 = new Category();
        cat2.setName_category("Financeiro");
        cat2.setTypeOfLaunch(TypeOfLaunch.C);
        cat2.setUsers(users);

        ((ArrayList<Category>) categories).add(cat2);

        Category cat3 = new Category();
        cat3.setName_category("Investimentos");
        cat3.setTypeOfLaunch(TypeOfLaunch.C);
        cat3.setUsers(users);

        ((ArrayList<Category>) categories).add(cat3);

        Category cat4 = new Category();
        cat4.setName_category("Restituições");
        cat4.setTypeOfLaunch(TypeOfLaunch.C);
        cat4.setUsers(users);

        ((ArrayList<Category>) categories).add(cat4);

        Category cat5 = new Category();
        cat5.setName_category("Comercial");
        cat5.setTypeOfLaunch(TypeOfLaunch.C);
        cat5.setUsers(users);

        ((ArrayList<Category>) categories).add(cat5);

        Category cat6 = new Category();
        cat6.setName_category("Alimentação");
        cat6.setTypeOfLaunch(TypeOfLaunch.S);
        cat6.setUsers(users);

        ((ArrayList<Category>) categories).add(cat6);

        Category cat7 = new Category();
        cat7.setName_category("Financeiro");
        cat7.setTypeOfLaunch(TypeOfLaunch.S);
        cat7.setUsers(users);

        ((ArrayList<Category>) categories).add(cat7);

        Category cat8 = new Category();
        cat8.setName_category("Lazer");
        cat8.setTypeOfLaunch(TypeOfLaunch.S);
        cat8.setUsers(users);

        ((ArrayList<Category>) categories).add(cat8);

        Category cat9 = new Category();
        cat9.setName_category("Despesas de casa");
        cat9.setTypeOfLaunch(TypeOfLaunch.S);
        cat9.setUsers(users);

        ((ArrayList<Category>) categories).add(cat9);

        Category cat10 = new Category();
        cat10.setName_category("Saúde");
        cat10.setTypeOfLaunch(TypeOfLaunch.S);
        cat10.setUsers(users);

        ((ArrayList<Category>) categories).add(cat10);

        Category cat11 = new Category();
        cat11.setName_category("Vestuário");
        cat11.setTypeOfLaunch(TypeOfLaunch.S);
        cat11.setUsers(users);

        ((ArrayList<Category>) categories).add(cat11);

        Category cat12 = new Category();
        cat12.setName_category("Transporte");
        cat12.setTypeOfLaunch(TypeOfLaunch.S);
        cat12.setUsers(users);

        ((ArrayList<Category>) categories).add(cat12);

        Category cat13 = new Category();
        cat13.setName_category("Transferências S");
        cat13.setTypeOfLaunch(TypeOfLaunch.S);
        cat13.setUsers(users);
        cat13.setVisible(1);

        ((ArrayList<Category>) categories).add(cat13);

        Category cat14 = new Category();
        cat14.setName_category("Transferências E");
        cat14.setTypeOfLaunch(TypeOfLaunch.C);
        cat14.setUsers(users);
        cat14.setVisible(1);

        ((ArrayList<Category>) categories).add(cat14);

        return categories;
    }

    public static Collection<SubCategory> cadSubCategories(Users users, List<Category> categories) {

        Collection<SubCategory> subCategories = new ArrayList<>();

        Category cat = new Category();

        //Salários
        for (int i = 0; i< categories.size(); i++) {
            if (categories.get(i).getName_category().equals("Salários")){
                cat=categories.get(i);
                break;
            }
        }
        SubCategory sub1 = new SubCategory();
        sub1.setCategory(cat);
        sub1.setSubCategoryName("Salário normal");
        sub1.setUsers(users);
        ((ArrayList<SubCategory>) subCategories).add(sub1);

        SubCategory sub2 = new SubCategory();
        sub2.setCategory(cat);
        sub2.setSubCategoryName("Adto. de Salário");
        sub2.setUsers(users);
        ((ArrayList<SubCategory>) subCategories).add(sub2);

        SubCategory sub3 = new SubCategory();
        sub3.setCategory(cat);
        sub3.setSubCategoryName("Férias");
        sub3.setUsers(users);
        ((ArrayList<SubCategory>) subCategories).add(sub3);

        SubCategory sub4 = new SubCategory();
        sub4.setCategory(cat);
        sub4.setSubCategoryName("13 salário");
        sub4.setUsers(users);
        ((ArrayList<SubCategory>) subCategories).add(sub4);

        //Financeiro
        Category cat2 = new Category();
        for (int i = 0; i< categories.size(); i++) {
            if (categories.get(i).getName_category().equals("Financeiro") && categories.get(i).getTypeOfLaunch().equals(TypeOfLaunch.C)){
                cat2=categories.get(i);
                break;
            }
        }
        SubCategory sub5 = new SubCategory();
        sub5.setCategory(cat2);
        sub5.setSubCategoryName("Juros");
        sub5.setUsers(users);
        ((ArrayList<SubCategory>) subCategories).add(sub5);

        SubCategory sub6 = new SubCategory();
        sub6.setCategory(cat2);
        sub6.setSubCategoryName("Ressarcimento");
        sub6.setUsers(users);
        ((ArrayList<SubCategory>) subCategories).add(sub6);

        //Investimentos
        Category cat3 = new Category();
        for (int i = 0; i< categories.size(); i++) {
            if (categories.get(i).getName_category().equals("Investimentos")){
                cat3=categories.get(i);
                break;
            }
        }
        SubCategory sub7 = new SubCategory();
        sub7.setCategory(cat3);
        sub7.setSubCategoryName("Proventos");
        sub7.setUsers(users);
        ((ArrayList<SubCategory>) subCategories).add(sub7);

        SubCategory sub8 = new SubCategory();
        sub8.setCategory(cat3);
        sub8.setSubCategoryName("Juros");
        sub8.setUsers(users);
        ((ArrayList<SubCategory>) subCategories).add(sub8);

        SubCategory sub9 = new SubCategory();
        sub9.setCategory(cat3);
        sub9.setSubCategoryName("Ganho de capital");
        sub9.setUsers(users);
        ((ArrayList<SubCategory>) subCategories).add(sub9);

        //Restituições
        Category cat4 = new Category();
        for (int i = 0; i< categories.size(); i++) {
            if (categories.get(i).getName_category().equals("Restituições")){
                cat4=categories.get(i);
                break;
            }
        }
        SubCategory sub10 = new SubCategory();
        sub10.setCategory(cat4);
        sub10.setSubCategoryName("Imposto de renda");
        sub10.setUsers(users);
        ((ArrayList<SubCategory>) subCategories).add(sub10);

        //Comercial
        Category cat5 = new Category();
        for (int i = 0; i< categories.size(); i++) {
            if (categories.get(i).getName_category().equals("Comercial")){
                cat5=categories.get(i);
                break;
            }
        }
        SubCategory sub11 = new SubCategory();
        sub11.setCategory(cat5);
        sub11.setSubCategoryName("Aluguéis");
        sub11.setUsers(users);
        ((ArrayList<SubCategory>) subCategories).add(sub11);

        SubCategory sub12 = new SubCategory();
        sub12.setCategory(cat5);
        sub12.setSubCategoryName("Prestação de serviços");
        sub12.setUsers(users);
        ((ArrayList<SubCategory>) subCategories).add(sub12);

        SubCategory sub13 = new SubCategory();
        sub13.setCategory(cat5);
        sub13.setSubCategoryName("Outros");
        sub13.setUsers(users);
        ((ArrayList<SubCategory>) subCategories).add(sub13);

        //Alimentação
        Category cat6 = new Category();
        for (int i = 0; i< categories.size(); i++) {
            if (categories.get(i).getName_category().equals("Alimentação")){
                cat6=categories.get(i);
                break;
            }
        }
        SubCategory sub14 = new SubCategory();
        sub14.setCategory(cat6);
        sub14.setSubCategoryName("Supermercados e padarias");
        sub14.setUsers(users);
        ((ArrayList<SubCategory>) subCategories).add(sub14);

        SubCategory sub15 = new SubCategory();
        sub15.setCategory(cat6);
        sub15.setSubCategoryName("Restaurantes");
        sub15.setUsers(users);
        ((ArrayList<SubCategory>) subCategories).add(sub15);

        //Financeiro
        Category cat7 = new Category();
        for (int i = 0; i< categories.size(); i++) {
            if (categories.get(i).getName_category().equals("Financeiro") && categories.get(i).getTypeOfLaunch().equals(TypeOfLaunch.S)){
                cat7=categories.get(i);
                break;
            }
        }
        SubCategory sub16 = new SubCategory();
        sub16.setCategory(cat7);
        sub16.setSubCategoryName("Juros");
        sub16.setUsers(users);
        ((ArrayList<SubCategory>) subCategories).add(sub16);

        SubCategory sub17 = new SubCategory();
        sub17.setCategory(cat7);
        sub17.setSubCategoryName("Taxas bancárias");
        sub17.setUsers(users);
        ((ArrayList<SubCategory>) subCategories).add(sub17);

        SubCategory sub18 = new SubCategory();
        sub18.setCategory(cat7);
        sub18.setSubCategoryName("Anuidades");
        sub18.setUsers(users);
        ((ArrayList<SubCategory>) subCategories).add(sub18);


        //Lazer
        Category cat8 = new Category();
        for (int i = 0; i< categories.size(); i++) {
            if (categories.get(i).getName_category().equals("Lazer")){
                cat8=categories.get(i);
                break;
            }
        }
        SubCategory sub19 = new SubCategory();
        sub19.setCategory(cat8);
        sub19.setSubCategoryName("Bares");
        sub19.setUsers(users);
        ((ArrayList<SubCategory>) subCategories).add(sub19);

        SubCategory sub20 = new SubCategory();
        sub20.setCategory(cat8);
        sub20.setSubCategoryName("Cinema");
        sub20.setUsers(users);
        ((ArrayList<SubCategory>) subCategories).add(sub20);

        SubCategory sub21 = new SubCategory();
        sub21.setCategory(cat8);
        sub21.setSubCategoryName("Férias");
        sub21.setUsers(users);
        ((ArrayList<SubCategory>) subCategories).add(sub21);


        //Despesas de casa
        Category cat9 = new Category();
        for (int i = 0; i< categories.size(); i++) {
            if (categories.get(i).getName_category().equals("Despesas de casa")){
                cat9=categories.get(i);
                break;
            }
        }
        SubCategory sub22 = new SubCategory();
        sub22.setCategory(cat9);
        sub22.setSubCategoryName("Água");
        sub22.setUsers(users);
        ((ArrayList<SubCategory>) subCategories).add(sub22);

        SubCategory sub23 = new SubCategory();
        sub23.setCategory(cat9);
        sub23.setSubCategoryName("Luz");
        sub23.setUsers(users);
        ((ArrayList<SubCategory>) subCategories).add(sub23);

        SubCategory sub24 = new SubCategory();
        sub24.setCategory(cat9);
        sub24.setSubCategoryName("Telefone e internet");
        sub24.setUsers(users);
        ((ArrayList<SubCategory>) subCategories).add(sub24);

        SubCategory sub25 = new SubCategory();
        sub25.setCategory(cat9);
        sub25.setSubCategoryName("Taxa condomínio");
        sub25.setUsers(users);
        ((ArrayList<SubCategory>) subCategories).add(sub25);

        SubCategory sub26 = new SubCategory();
        sub26.setCategory(cat9);
        sub26.setSubCategoryName("Outros");
        sub26.setUsers(users);
        ((ArrayList<SubCategory>) subCategories).add(sub26);

        //Saúde - 10
        Category cat10 = new Category();
        for (int i = 0; i< categories.size(); i++) {
            if (categories.get(i).getName_category().equals("Saúde")){
                cat10=categories.get(i);
                break;
            }
        }
        SubCategory sub27 = new SubCategory();
        sub27.setCategory(cat10);
        sub27.setSubCategoryName("Medicamentos");
        sub27.setUsers(users);
        ((ArrayList<SubCategory>) subCategories).add(sub27);

        SubCategory sub28 = new SubCategory();
        sub28.setCategory(cat10);
        sub28.setSubCategoryName("Plano de saúde");
        sub28.setUsers(users);
        ((ArrayList<SubCategory>) subCategories).add(sub27);

        SubCategory sub29 = new SubCategory();
        sub29.setCategory(cat10);
        sub29.setSubCategoryName("Outros");
        sub29.setUsers(users);
        ((ArrayList<SubCategory>) subCategories).add(sub29);

        //Vestuário - 11
        Category cat11 = new Category();
        for (int i = 0; i< categories.size(); i++) {
            if (categories.get(i).getName_category().equals("Vestuário")){
                cat11=categories.get(i);
                break;
            }
        }
        SubCategory sub30 = new SubCategory();
        sub30.setCategory(cat11);
        sub30.setSubCategoryName("Roupas e acessórios");
        sub30.setUsers(users);
        ((ArrayList<SubCategory>) subCategories).add(sub30);

        SubCategory sub31 = new SubCategory();
        sub31.setCategory(cat11);
        sub31.setSubCategoryName("Calçados");
        sub31.setUsers(users);
        ((ArrayList<SubCategory>) subCategories).add(sub31);


        //Transporte - 12
        Category cat12 = new Category();
        for (int i = 0; i< categories.size(); i++) {
            if (categories.get(i).getName_category().equals("Transporte")){
                cat12=categories.get(i);
                break;
            }
        }
        SubCategory sub32 = new SubCategory();
        sub32.setCategory(cat12);
        sub32.setSubCategoryName("IPVA e licenciamento");
        sub32.setUsers(users);
        ((ArrayList<SubCategory>) subCategories).add(sub32);

        SubCategory sub33 = new SubCategory();
        sub33.setCategory(cat12);
        sub33.setSubCategoryName("Manutenção");
        sub33.setUsers(users);
        ((ArrayList<SubCategory>) subCategories).add(sub33);

        SubCategory sub34 = new SubCategory();
        sub34.setCategory(cat12);
        sub34.setSubCategoryName("Prestação");
        sub34.setUsers(users);
        ((ArrayList<SubCategory>) subCategories).add(sub34);

        SubCategory sub35 = new SubCategory();
        sub35.setCategory(cat12);
        sub35.setSubCategoryName("Combustível");
        sub35.setUsers(users);
        ((ArrayList<SubCategory>) subCategories).add(sub35);

        SubCategory sub36 = new SubCategory();
        sub36.setCategory(cat12);
        sub36.setSubCategoryName("Prestação");
        sub36.setUsers(users);
        ((ArrayList<SubCategory>) subCategories).add(sub36);

        //Transferência
        SubCategory sub37 = new SubCategory();
        Category cat13 = new Category();
        for (int i = 0; i< categories.size(); i++) {
            if (categories.get(i).getName_category().equals("Transferências S") && categories.get(i).getTypeOfLaunch().equals(TypeOfLaunch.S)){
                cat13=categories.get(i);
                break;
            }
        }
        sub37.setCategory(cat13);
        sub37.setSubCategoryName("Transferência S");
        sub37.setUsers(users);
        sub37.setVisible(1);
        ((ArrayList<SubCategory>) subCategories).add(sub37);

        SubCategory sub38 = new SubCategory();
        Category cat14 = new Category();
        for (int i = 0; i< categories.size(); i++) {
            if (categories.get(i).getName_category().equals("Transferências E") && categories.get(i).getTypeOfLaunch().equals(TypeOfLaunch.C)){
                cat14=categories.get(i);
                break;
            }
        }
        sub38.setCategory(cat14);
        sub38.setSubCategoryName("Transferência E");
        sub38.setUsers(users);
        sub38.setVisible(1);
        ((ArrayList<SubCategory>) subCategories).add(sub38);


        return subCategories;
    }
}
