import shared.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

public class Server {

    //static ServerSocket variable
    private static ServerSocket server;
    //socket server port on which it will listen
    private static int port = 9876;

    public static void main(String args[]) throws IOException, ClassNotFoundException, SQLException {
        String nl = "%";
        String usrlogin = "USERLOGIN";
        String restlogin = "RESTAURANTLOGIN";
        String restsign = "RESTAURANTLOGIN";
        String menuSearch = "MENUSEARCH";
        String foodDelete = "FOODDELETE";
        String foodADD = "FOODADD";
        String editLocation = "EDITLOCATION";
        String getRestaurant = "GETRESTAURANT";
        String addMeal = "ADDMEAL";
        String addDaily = "DAILYADD";
        String updateUser = "UPDATEUSER";
        String updateMeal = "UPDATEMEAL";
        String setClientNutr = "SETCLIENTNUTR";

        //create the socket server object
        DBAdmin dba;
        server = new ServerSocket(port);
        dba = new DBAdmin();
        //keep listens indefinitely until receives 'exit' call or program terminates
        while(true){
            System.out.println("Waiting for the client request");
            //creating socket and waiting for client connection
            Socket socket = server.accept();
            //read from socket to ObjectInputStream object
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            //convert ObjectInputStream object to String
            String message = (String) ois.readObject();
            System.out.println("Message Received: " + message);
            //create ObjectOutputStream object
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            //write object to Socket
            //parse start
            String[] arguments = message.split(nl);
            if(arguments[0].equals(usrlogin)) {
                if(dba.testUserLogin(arguments[1], arguments[3],arguments[2])){
                    User toSend = dba.retrieveUserInfo(arguments[1]);

                    oos.writeObject(toSend);
                    //send user info done
                }
                else {
                    String toSend = "FALSE";
                    oos.writeObject(toSend);
                    //send error done
                }
            }else if(arguments[0].equals(restlogin)){
                if(dba.testRestaurantLogin(arguments[1],arguments[2])){
                    //send rest info done
                    //String toSend = dba.retrieveRestaurantInfo(arguments[1]);
                    //oos.writeObject(toSend);
                }
                else{
                    String toSend = "FALSE";
                    oos.writeObject(toSend);
                    //send error done
                }
            }else if(arguments[0].equals(restsign)) {
                Restaurant result = dba.createRestaurant(arguments[1], arguments[2], arguments[3]);
                oos.writeObject(result);

            }else if(arguments[0].equals(menuSearch)){
                double x  = Integer.parseInt(arguments[1]);
                double y  = Integer.parseInt(arguments[2]);
                int carb = Integer.parseInt(arguments[3]);
                int protein = Integer.parseInt(arguments[4]);
                int fat = Integer.parseInt(arguments[5]);
                int calories = Integer.parseInt(arguments[6]);
                int radius = Integer.parseInt(arguments[7]);


                ArrayList<Restaurant> sendRestaurants = dba.retrieveMenuInfo(calories,carb,fat,protein,x,y,radius);

                if(sendRestaurants.size() >0){
                    oos.writeObject(sendRestaurants);
                }


            }else if(arguments[0].equals(foodDelete)){
                String email = arguments[1];
                String foodName = arguments[2];

                boolean result = dba.deleteFood(email, foodName);

                oos.writeObject(result);

            }else if(arguments[0].equals(editLocation)) {
                String email = arguments[1];
                double x  = Integer.parseInt(arguments[2]);
                double y  = Integer.parseInt(arguments[3]);

                boolean result = dba.editRestaurantLocation(email, x, y);

                oos.writeObject(result);

            }else if(arguments[0].equals(getRestaurant)){
                String email = arguments[1];

                Restaurant resultRest = dba.retrieveRestaurantInfo(email);

                oos.writeObject(resultRest);

            }else if(arguments[0].equals(foodADD)){
                String restEmail = arguments[1];
                String foodName = arguments[2];
                int calories = Integer.parseInt(arguments[3]);
                int carbs = Integer.parseInt(arguments[4]);
                int prot = Integer.parseInt(arguments[5]);
                int fat = Integer.parseInt(arguments[6]);
                double price = Double.parseDouble(arguments[7]);
                int type = Integer.parseInt(arguments[8]);

                boolean result = dba.addMenuItem(restEmail, foodName, calories,
                                            carbs, prot, fat, price, type);

                oos.writeObject(result);

            }else if(arguments[0].equals(addMeal)){
                int carbs = Integer.parseInt(arguments[1]);
                int fat = Integer.parseInt(arguments[2]);
                int prot = Integer.parseInt(arguments[3]);
                int calories = Integer.parseInt(arguments[4]);

                String id = arguments[5];
                String mealname = arguments[6];

                boolean result = dba.addMeal(carbs, fat, prot, calories, id, mealname);

                oos.writeObject(result);

            }else if(arguments[0].equals(addDaily)){
                int carbs = Integer.parseInt(arguments[1]);
                int fat = Integer.parseInt(arguments[2]);
                int prot = Integer.parseInt(arguments[3]);
                int calories = Integer.parseInt(arguments[4]);

                String id = arguments[5];

                boolean result = dba.addMealToDaily(carbs, fat, prot, calories, id);

                oos.writeObject(result);

            }else if(arguments[0].equals(updateUser)){
                String id = arguments[1];
                int calories = Integer.parseInt(arguments[2]);
                int carbs = Integer.parseInt(arguments[3]);
                int fat = Integer.parseInt(arguments[4]);
                int protein = Integer.parseInt(arguments[5]);

                boolean result = dba.updateUser(id, calories, carbs, fat, protein);

                oos.writeObject(result);
            }else if(arguments[0].equals(updateMeal)){
                String userID = arguments[1];
                String mealName = arguments[2];
                int calories = Integer.parseInt(arguments[3]);
                int carbs = Integer.parseInt(arguments[4]);
                int fat = Integer.parseInt(arguments[5]);
                int protein = Integer.parseInt(arguments[6]);

                boolean result = dba.updateMeal(userID, mealName, calories, carbs, fat, protein);

                oos.writeObject(result);
            }
            else if(arguments[0].equals(setClientNutr)){
                String client_id = arguments[1];
                String nutr_id = arguments[1];


                boolean result = dba.setClientNutr(client_id, nutr_id);

                oos.writeObject(result);
            }


            else{
                System.out.println("wtf");
            }

            //parse end
            if(message.equals("3")){
                dba.testUserLogin("23123","Allllli","anan@anan.com");
                dba.createRestaurant("kazıkcıhusrem@ananisikicez.com","benimsifremcoksaglam","husreminanasikmeyeri");
                dba.addMenuItem("kazıkcıhusrem@ananisikicez.com","Adana Kebap",1250,100,100,213,-1, 0);
                dba.deleteFood("kazıkcıhusrem@ananisikicez.com","Kebap");
                //System.out.println(dba.addMeal(123,123,123,123,23123,"randomMeal"));
                //System.out.println(dba.addMealToDaily(44,44,44,44,23123));
                System.out.println(dba.retrieveUserInfo("23123"));

            }
            //close resources
            ois.close();
            oos.close();
            socket.close();
            //terminate the server if client sends exit request
            if(message.equalsIgnoreCase("exit")) break;
        }
        System.out.println("Shutting down Socket server!!");
        //close the ServerSocket object
        server.close();
    }

}