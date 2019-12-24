import java.sql.SQLException;

public class InputParser {
    public InputParser(){

    }

    String nl = " ";
    String usrlogin = "USERLOGIN";
    String restlogin = "RESTAURANTLOGIN";
    String restsign = "RESTAURANTLOGIN";
    String menuSearch = "MENUSEARCH";
    String foodDelete = "FOODDELETE";
    String foodADD = "FOODADD";

    public String parseString(String s) throws SQLException, ClassNotFoundException {

        DBAdmin dba = new DBAdmin();
        String[] arguments = s.split(" ");
        if(arguments == null){
            System.out.println("wtf");
        }else {

            if(arguments[0].equals(usrlogin)) {
                if(dba.testUserLogin(arguments[1],arguments[2],arguments[3])){
                    //send user info
                }
                else {
                    //send error
                }
            }

            else if(arguments[0].equals(restlogin)){
                if(dba.testRestaurantLogin(arguments[1],arguments[2])){
                    //send rest info
                }
                else{
                    //send error
                }
            }
            else if(arguments[0].equals(restsign)){
                if(dba.createRestaurant(arguments[1],arguments[2],arguments[3])){
                    //send res info
                }else {
                    //send error info
                }
            }
            else if(arguments[0].equals(menuSearch)){
                double x  = Integer.parseInt(arguments[1]);
                double y  = Integer.parseInt(arguments[2]);
                int carb = Integer.parseInt(arguments[3]);
                int protein = Integer.parseInt(arguments[4]);
                int fat = Integer.parseInt(arguments[5]);
                int calories = Integer.parseInt(arguments[6]);
                int radius = Integer.parseInt(arguments[7]);
                //String menus = dba.retrieveMenuInfo(calories,carb,fat,protein,x,y,radius);

            }

            else if(arguments[0].equals(foodDelete)){
                    if(dba.deleteFood(arguments[1],arguments[2])){
                        //allisgood
                    }
                    else {
                        ///send err msg
                    }
            }

        }
        return "";

    }
}
