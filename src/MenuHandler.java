import shared.*;

import java.util.ArrayList;

public class MenuHandler {

    public MenuHandler(){

    }


    public ArrayList<ArrayList<Food>> createMenu(ArrayList<Food> foodList,int cals,int fat,int prot, int carbs) {

        ArrayList<Food> mainCourseList = new ArrayList<Food>();
        ArrayList<Food> appetizerList = new ArrayList<Food>();
        ArrayList<Food> desertList = new ArrayList<Food>();
        ArrayList<Food> drinkList = new ArrayList<Food>();

        for (int i = 0; i < foodList.size(); i++) {

            if (foodList.get(i).getType() == 0) {

                appetizerList.add(foodList.get(i));

            }else if (foodList.get(i).getType() == 1) {

                mainCourseList.add(foodList.get(i));

            }else if (foodList.get(i).getType() == 2) {

                desertList.add(foodList.get(i));

            }else if (foodList.get(i).getType() == 3) {

                drinkList.add(foodList.get(i));

            }


        }
        //add dummy Foods to appetizer and desert to enable combinations without appetizer or desert
        Food dummyAppetizer = new Food("dummyAppetizer", "", 0, 0, 0, 0, 0);
        Food dummyDesert = new Food("dummyDesert", "", 0, 0, 0, 0, 2);
        appetizerList.add(dummyAppetizer);
        desertList.add(dummyDesert);
        ArrayList<ArrayList<Food>> results = new ArrayList<ArrayList<Food>>();
        for(int i = 0; i < mainCourseList.size(); i++){
            if(
                    ((mainCourseList.get(i).getCarbs() < (carbs+carbs/10)) && (mainCourseList.get(i).getCarbs() > (carbs - carbs/10)))
                    &&((mainCourseList.get(i).getProtein() < (prot+prot/10)) && (mainCourseList.get(i).getProtein() > (prot - prot/10)))
                    &&((mainCourseList.get(i).getFat() < (fat+fat/10)) && (mainCourseList.get(i).getFat() > (fat - fat/10)))
                    &&((mainCourseList.get(i).getCalories() < (cals+cals/10)) && (mainCourseList.get(i).getCalories() > (cals - cals/10)))

            ){
                ArrayList<Food> temp = new ArrayList<Food>();
                temp.add(mainCourseList.get(i));
                results.add(temp);
            }else{
                for(int j = 0; j < appetizerList.size(); j++){
                    if(
                            (( (mainCourseList.get(i).getCarbs()+appetizerList.get(j).getCarbs()) < (carbs+carbs/10)) && ((mainCourseList.get(i).getCarbs()+appetizerList.get(j).getCarbs()) > (carbs - carbs/10)))
                                    &&(((mainCourseList.get(i).getProtein()+appetizerList.get(j).getProtein()) < (prot+prot/10)) && ((mainCourseList.get(i).getProtein()+appetizerList.get(j).getProtein()) > (prot - prot/10)))
                                    &&(((mainCourseList.get(i).getFat()+appetizerList.get(j).getFat()) < (fat+fat/10)) && ((mainCourseList.get(i).getFat()+appetizerList.get(j).getFat()) > (fat - fat/10)))
                                    &&(((mainCourseList.get(i).getCalories()+appetizerList.get(j).getCalories()) < (cals+cals/10)) && ((mainCourseList.get(i).getCalories()+appetizerList.get(j).getCalories()) > (cals - cals/10)))

                    ){
                        ArrayList<Food> temp = new ArrayList<Food>();
                        temp.add(mainCourseList.get(i));
                        temp.add(appetizerList.get(j));
                        results.add(temp);
                    }else{
                        for(int k = 0; k < desertList.size(); k++){
                            if(
                                    (( (mainCourseList.get(i).getCarbs()+appetizerList.get(j).getCarbs() + desertList.get(k).getCarbs()) < (carbs+carbs/10)) && ((mainCourseList.get(i).getCarbs()+appetizerList.get(j).getCarbs()  + desertList.get(k).getCarbs()) > (carbs - carbs/10)))
                                            &&(((mainCourseList.get(i).getProtein()+appetizerList.get(j).getProtein()  + desertList.get(k).getProtein()) < (prot+prot/10)) && ((mainCourseList.get(i).getProtein()+appetizerList.get(j).getProtein()  + desertList.get(k).getProtein()) > (prot - prot/10)))
                                            &&(((mainCourseList.get(i).getFat()+appetizerList.get(j).getFat()  + desertList.get(k).getFat()) < (fat+fat/10)) && ((mainCourseList.get(i).getFat()+appetizerList.get(j).getFat() +  + desertList.get(k).getFat()) > (fat - fat/10)))
                                            &&(((mainCourseList.get(i).getCalories()+appetizerList.get(j).getCalories() +  + desertList.get(k).getCalories()) < (cals+cals/10)) && ((mainCourseList.get(i).getCalories()+appetizerList.get(j).getCalories() +  + desertList.get(k).getCalories()) > (cals - cals/10)))

                            ){
                                ArrayList<Food> temp = new ArrayList<Food>();
                                temp.add(mainCourseList.get(i));
                                temp.add(appetizerList.get(j));
                                temp.add(desertList.get(k));
                                results.add(temp);
                            }else{
                                for(int l = 0; l < drinkList.size(); l++){
                                    if(
                                            (( (mainCourseList.get(i).getCarbs()+appetizerList.get(j).getCarbs() + desertList.get(k).getCarbs() + drinkList.get(k).getCarbs()) < (carbs+carbs/10)) && ((mainCourseList.get(i).getCarbs()+appetizerList.get(j).getCarbs()  + desertList.get(k).getCarbs() +  + drinkList.get(k).getCarbs()) > (carbs - carbs/10)))
                                                    &&(((mainCourseList.get(i).getProtein()+appetizerList.get(j).getProtein()  + desertList.get(k).getProtein()  + drinkList.get(k).getProtein()) < (prot+prot/10)) && ((mainCourseList.get(i).getProtein()+appetizerList.get(j).getProtein()  + desertList.get(k).getProtein()  + drinkList.get(k).getProtein()) > (prot - prot/10)))
                                                    &&(((mainCourseList.get(i).getFat()+appetizerList.get(j).getFat()  + desertList.get(k).getFat() +  + drinkList.get(k).getFat()) < (fat+fat/10)) && ((mainCourseList.get(i).getFat()+appetizerList.get(j).getFat() +  + desertList.get(k).getFat() +  + drinkList.get(k).getFat()) > (fat - fat/10)))
                                                    &&(((mainCourseList.get(i).getCalories()+appetizerList.get(j).getCalories() +  + desertList.get(k).getCalories() +  + drinkList.get(k).getFat()) < (cals+cals/10)) && ((mainCourseList.get(i).getCalories()+appetizerList.get(j).getCalories() +  + desertList.get(k).getCalories()  + drinkList.get(k).getCalories() ) > (cals - cals/10)))

                                    ){
                                        ArrayList<Food> temp = new ArrayList<Food>();
                                        temp.add(mainCourseList.get(i));
                                        temp.add(appetizerList.get(j));
                                        temp.add(desertList.get(k));
                                        temp.add(drinkList.get(l));
                                        results.add(temp);
                                    }


                                }
                            }


                        }
                    }



                }
            }

        }
        return results;

    }
}