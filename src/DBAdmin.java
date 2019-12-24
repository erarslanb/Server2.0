import shared.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;


public class DBAdmin {

    //private static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
    private static final String DB_URL = "jdbc:mariadb://192.168.1.107/diner";
    //private static final String DB_URL = "jdbc:mysql://localhost:8889/diner?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=Turkey";
    private final String pass = "q1w2e3r4t5";
    //private final String pass = "root";
    private final String user = "root";
    private Connection cn;
    private Statement st;
    public String nl = "%";

    private MenuHandler menuHandler = new MenuHandler();

    public DBAdmin(){
        try {
            /*try {
                Class.forName(JDBC_DRIVER);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }*/
            this.cn = DriverManager.getConnection(this.DB_URL, this.user,this.pass);
            this.st = cn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean testUserLogin(String id,String username, String email) throws SQLException {
        //works
        PreparedStatement stmt=cn.prepareStatement("select * from User where user_id = ? ");
        stmt.setString(1, id);
        ResultSet rs=stmt.executeQuery();
        if(rs.next()){
            return true;
        }
        else{
            stmt=cn.prepareStatement("insert into User values(?,?,?,?,?,?,?,?)");
            stmt.setString(1,id);
            stmt.setString(2,username);
            stmt.setString(3,email);
            stmt.setNull(4, Types.INTEGER);
            stmt.setNull(5, Types.INTEGER);
            stmt.setNull(6, Types.INTEGER);
            stmt.setNull(7, Types.INTEGER);
            stmt.setInt(8,0);
            int i=stmt.executeUpdate();
            if(i ==1){
                return true;
            }
            else return false;
        }

    }

    public boolean updateUser(String id, int calories, int carbs, int fat, int protein) throws SQLException{
        PreparedStatement stmt = cn.prepareStatement("update User set calories = ?, carbs = ?, fat = ?, protein = ? where user_id = ?");

        stmt.setInt(1, calories);
        stmt.setInt(2, carbs);
        stmt.setInt(3, fat);
        stmt.setInt(4, protein);
        stmt.setString(5, id);

        int i = stmt.executeUpdate();
        if (i == 1) {
            return true;
        }
        return false;
    }

    public boolean updateMeal(String userID, int mealID, int calories, int carbs, int fat, int protein) throws SQLException{
        PreparedStatement stmt = cn.prepareStatement("update Meals set calories = ?, carbs = ?, fat = ?, protein = ? where user_id = ? and meal_id = ?");

        stmt.setInt(1, calories);
        stmt.setInt(2, carbs);
        stmt.setInt(3, fat);
        stmt.setInt(4, protein);
        stmt.setString(5, userID);
        stmt.setInt(6, mealID);


        int i = stmt.executeUpdate();
        if (i == 1) {
            return true;
        }
        return false;
    }

    public boolean setClientNutr(String clientID, String nutrID) throws SQLException{
        PreparedStatement stmt = cn.prepareStatement("insert into client values(?,?)");

        stmt.setString(1, clientID);
        stmt.setString(2, nutrID);

        int i = stmt.executeUpdate();
        if (i == 1) {
            return true;
        }
        return false;
    }

    public boolean testRestaurantLogin(String email, String pass) throws SQLException {
        //works
        PreparedStatement stmt=cn.prepareStatement("select * from Restaurant where rest_email = ? and rest_password = ? ");
        stmt.setString(1,email);
        stmt.setString(2,pass);
        ResultSet rs=stmt.executeQuery();
        if(rs.next()){
            return true;
        }
        else return false;

    }

    public boolean createRestaurant(String email,String pass,String restName ) throws SQLException {
        //works
        PreparedStatement ifStmt = cn.prepareStatement("select * from Restaurant where rest_email = ?");
        ifStmt.setString(1,email);
        ResultSet ifrs = ifStmt.executeQuery();
        if(ifrs.next()){
            return false;
        }
        else{
            PreparedStatement  stmt=cn.prepareStatement("insert into Restaurant values(?,?,?,?,?)");
            PreparedStatement MenuStmt = cn.prepareStatement("insert into Menu values (?,?)");
            stmt.setString(1,restName);
            stmt.setString(2,email);
            stmt.setString(3,pass);
            stmt.setNull(4,Types.DOUBLE);
            stmt.setNull(5,Types.DOUBLE);
            int i=stmt.executeUpdate();

            if(i ==1){
                stmt = cn.prepareStatement("insert into Menu(rest_email) values (?)");
                stmt.setString(1,email);
                int j = stmt.executeUpdate();
                if(j == 1) {
                    return true;
                }
                else{
                    stmt = cn.prepareStatement("delete from Restaurant whre rest_email = ?");
                    stmt.setString(1,email);
                    stmt.executeQuery();
                    return false;

                }
            }
            else return false;

        }

    }

    public boolean addMeal(int carb, int fat, int protein, int cals, String id, String mealName) throws SQLException {

        PreparedStatement stmt = cn.prepareStatement("insert into Meals(user_id,meal_name,calories,carbs,fat,protein) values" +
                " (?,?,?,?,?,?) ");
        stmt.setString(1,id);
        stmt.setString(2,mealName);
        stmt.setInt(3,cals);
        stmt.setInt(4,carb);
        stmt.setInt(5,fat);
        stmt.setInt(6,protein);
        int i = stmt.executeUpdate();
        if(i == 1){
            return true;
        }
        return false;
    }


    public boolean addMealToDaily(int carb, int fat, int protein, int cals, String id) throws SQLException {


        PreparedStatement st =  cn.prepareStatement("SELECT * from Daily where user_id = ?");

        st.setString(1, id);

        ResultSet rs = st.executeQuery();

        if(rs.next()){

            PreparedStatement stmt = cn.prepareStatement("UPDATE Daily SET calories = ?, carbs = ?, fat = ?, protein = ?" +
                            " WHERE user_id = ?");

            stmt.setString(5, id);

            int newcal = rs.getInt("calories") + cals;
            int newcarb = rs.getInt("carbs") + carb;
            int newfat = rs.getInt("fat") + fat;
            int newprot = rs.getInt("protein") + protein;

            stmt.setInt(1, newcal);
            stmt.setInt(2, newcarb);
            stmt.setInt(3, newfat);
            stmt.setInt(4, newprot);

            int i = stmt.executeUpdate();
            if (i == 1) {
                return true;
            }
            return false;

        }else {

            PreparedStatement stmt = cn.prepareStatement("INSERT into Daily(user_id,calories,carbs,fat,protein,daily_date) values" +
                    " (?,?,?,?,?,?) ");
            stmt.setString(1, id);
            stmt.setInt(2, cals);
            stmt.setInt(3, carb);
            stmt.setInt(4, fat);
            stmt.setInt(5, protein);
            Date utilDate = new Date();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            stmt.setDate(6, sqlDate);
            int i = stmt.executeUpdate();
            if (i == 1) {
                return true;
            }
            return false;
        }
    }


    public User retrieveUserInfo(String id) throws SQLException {
        //works


        int totalcal = 0;
        int totalcarbs = 0;
        int totalfat = 0;
        int totalprot = 0;
        int dailycal = 0;
        int dailycarbs = 0;
        int dailyfat = 0;
        int dailyprot = 0;

        PreparedStatement stmt = cn.prepareStatement("select * from Meals where user_id  = ?");
        stmt.setString(1,id);

        ResultSet rs = stmt.executeQuery();

        PreparedStatement stmt2 = cn.prepareStatement("select * from User where user_id = ?");
        stmt2.setString(1,id);
        ResultSet rs2 = stmt2.executeQuery();

        PreparedStatement stmt3 = cn.prepareStatement("select * from Daily where user_id = ? and daily_date = ?");
        Date utilDate = new Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        stmt3.setString(1,id);
        stmt3.setDate(2,sqlDate);
        ResultSet rs3 = stmt3.executeQuery();

        ArrayList<Meal> meals  = new ArrayList<Meal>();


        while(rs.next()){

            String mealname = rs.getString("meal_name");
            String user_id = rs.getString("user_id");
            int meal_id = rs.getInt("meal_id");
            int calories = rs.getInt("calories");
            int fat = rs.getInt("fat");
            int protein = rs.getInt("protein");
            int carbs = rs.getInt("carbs");

            Meal m = new Meal(mealname, user_id, meal_id, calories, fat, protein, carbs);
            meals.add(m);
        }

        if(rs2.next()){
            totalcal = rs2.getInt("calories");
            totalcarbs = rs2.getInt("carbs");
            totalfat = rs2.getInt("fat");
            totalprot = rs2.getInt("protein");
        }


        if(rs2.next() && rs3.next()){

            dailycal = rs3.getInt("calories");
            dailycarbs = rs3.getInt("carbs");
            dailyfat = rs3.getInt("fat");
            dailyprot = rs3.getInt("protein");

        }
        User u = new User(id, totalcal, totalfat, totalprot, totalcarbs, dailycal, dailyfat, dailyprot, dailycarbs, meals);

        return u;


    }

    public ArrayList<Restaurant> retrieveMenuInfo(int calories, int carbs, int fat, int protein,double lat,double longt, double radius)
            throws SQLException {
        //works!!!
        //TODO: needs some cleanup

        //get restaurants in radius

        PreparedStatement stmt1 = cn.prepareStatement(
                "Select rest_email, rest_name, location_lat, location_long, (" +
                "6371 * acos(" +
                " cos(radians(?))" +
                " * cos(radians(location_lat))" +
                " * cos(radians(location_long) - radians(?))" +
                " + sin(radians(?))" +
                " * sin(radians(location_lat))" +
                " )"  +
                " ) AS distance" +
                " from Restaurant" +
                " HAVING distance < ?" +
                " ORDER BY distance");

        stmt1.setDouble(1,lat);
        stmt1.setDouble(2,longt);
        stmt1.setDouble(3,lat);
        stmt1.setDouble(4,radius);
        ResultSet rs1 = stmt1.executeQuery();
        ResultSet rs2 = stmt1.executeQuery();

        ArrayList<String> restEmails = new ArrayList<String>();

        while (rs1.next()){
            restEmails.add(rs1.getString("rest_email"));
        }


        //get menu and foods for each restaurant

        ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();


        PreparedStatement stmt2 = cn.prepareStatement(
                "Select * from Food where menu_id in " +
                        "(Select menu_id from Menu where rest_email = ?)");

        while(rs2.next()){
            String name = rs2.getString("rest_name");
            String email = rs2.getString("rest_email");
            double latitude = rs2.getDouble("location_lat");
            double longitude = rs2.getDouble("location_long");

            ArrayList<Food> menu = new ArrayList<Food>();

            ArrayList<ArrayList<Food>> menus;

            stmt2.setString(1, rs2.getString("rest_email"));

            ResultSet temprs = stmt2.executeQuery();

            while(temprs.next()){
                String foodname = temprs.getString("food_name");
                int food_id = temprs.getInt("food_id");
                String rest_email = temprs.getString("rest_email");
                int cal = temprs.getInt("calories");
                int carb = temprs.getInt("carb_val");
                int prot = temprs.getInt("prot_val");
                int fats = temprs.getInt("fat_val");


                Food f = new Food(foodname, food_id, rest_email, cal, fats, prot, carb, 1);

                menu.add(f);

            }

            menus = menuHandler.createMenu(menu, calories, fat, protein, carbs);

            Restaurant rs = new Restaurant(name, email, latitude, longitude, menu, menus);
            restaurants.add(rs);

        }


        return restaurants;
    }

    public Restaurant retrieveRestaurantInfo(String email) throws SQLException {

        PreparedStatement stmt = cn.prepareStatement("Select rest_name, location_lat, location_long from Restaurant where rest_email = ?");

        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();

        String restname = "";
        double lat = 0;
        double longt = 0;
        ArrayList<Food> menu = new ArrayList<Food>();


        while(rs.next()) {
            restname = rs.getString("rest_name");
            lat = rs.getDouble("location_lat");
            longt = rs.getDouble("location_long");

            PreparedStatement stmt2 = cn.prepareStatement(
                    "Select * from Food where menu_id in " +
                            "(Select menu_id from Menu where rest_email = ?)");

            stmt2.setString(1, email);

            ResultSet rs2 = stmt2.executeQuery();

            while (rs2.next()) {
                String foodname = rs2.getString("food_name");
                int food_id = rs2.getInt("food_id");
                String rest_email = rs2.getString("rest_email");
                int cal = rs2.getInt("calories");
                int carb = rs2.getInt("carb_val");
                int prot = rs2.getInt("prot_val");
                int fats = rs2.getInt("fat_val");


                Food f = new Food(foodname, food_id, rest_email, cal, fats, prot, carb, 1);

                menu.add(f);

            }

        }
        Restaurant rest = new Restaurant(restname, email, lat, longt, menu, null);

        return rest;
    }

    public boolean addMenuItem(String email, String foodName, int calories,int carb,int protein,int fat, double price) throws SQLException {
        if(checkIfMenuItemExists(email,foodName)){
            return false;
        }
        else{
            //works
            PreparedStatement stmt = cn.prepareStatement("insert into Food(menu_id,food_name,calories,carb_val," +
                    "prot_val,fat_val,price) values (?,?,?,?,?,?,?)");
            stmt.setString(2,foodName);
            stmt.setInt(3,calories);
            stmt.setInt(4,carb);
            stmt.setInt(5,protein);
            stmt.setInt(6,fat);
            if(price < 0){
                stmt.setNull(7,Types.DOUBLE);
            }else {
                stmt.setDouble(7,price);
            }

            PreparedStatement stmt2 = cn.prepareStatement("select * from Menu where rest_email = ?");
            stmt2.setString(1,email);
            ResultSet rs = stmt2.executeQuery();
            if(rs.next()){
                int menuID;
                menuID = rs.getInt(1);
                stmt.setInt(1,menuID);
                int i = stmt.executeUpdate();
                if(i == 1){
                    return true;
                }else return false;
            }else return false;

        }
    }

    public boolean checkIfMenuItemExists(String email, String name) throws SQLException {
        //works
        PreparedStatement stmt = cn.prepareStatement("select * from Menu where rest_email = ?");
        stmt.setString(1,email);
        ResultSet rs = stmt.executeQuery();
        if(rs.next()){
            int menuID = rs.getInt(1);
            stmt = cn.prepareStatement("select * from Food where menu_id = ? and food_name = ?");
            stmt.setString(2,name);
            stmt.setInt(1,menuID);
            rs = stmt.executeQuery();
            if(rs.next()){
                return true;
            }
        }
        return false;
    }

    public boolean deleteFood(String email,String foodName) throws SQLException {
        //works
        PreparedStatement  stmt=cn.prepareStatement("delete from Food where food_name = ? and  menu_id = ?");
        PreparedStatement  stmt2 = cn.prepareStatement("select * from Menu where rest_email = ?");
        stmt2.setString(1,email);
        ResultSet rs=stmt2.executeQuery();
        if(rs.next()){
            int menuID = rs.getInt(1);
            stmt.setString(1,foodName);
            stmt.setInt(2,menuID);

            int i = stmt.executeUpdate();
            if(i == 1){
                return true;
            }
            else{return false;}
        }else{return false;}

    }

    public boolean editRestaurantLocation(String rest_email, double lat, double longt) throws SQLException {

        PreparedStatement stmt = cn.prepareStatement("UPDATE Restaurant SET location_lat = ?, location_long = ? " +
                                                        "WHERE rest_email = ?");

        stmt.setDouble(1, lat);
        stmt.setDouble(2, longt);
        stmt.setString(3, rest_email);

        int res = stmt.executeUpdate();

        if(res == 1)
            return true;
        else
            return false;
    }

    public void deneme() throws SQLException {
        PreparedStatement stmt=cn.prepareStatement("select * from users where username = ?");
        stmt.setString(1,"ali");
        ResultSet rs=stmt.executeQuery();
        while(rs.next()){
            System.out.println(rs.getString(1)+" "+rs.getString(2));
        }


    }




}
