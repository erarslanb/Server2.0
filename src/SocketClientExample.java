import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketClientExample {

    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException{
        //get the localhost IP address, if server is running on some other IP, you need to use that


        String nl = "%";
        InetAddress host = InetAddress.getLocalHost();
        Socket socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        //for(int i=0; i<5;i++){
        //establish socket connection to server
        socket = new Socket(host.getHostName(), 9876);
        //write to socket using ObjectOutputStream
        oos = new ObjectOutputStream(socket.getOutputStream());
        System.out.println("Sending request to Socket Server");


        //String tsnd ="MENUSEARCH" + nl + "-33" + nl + "151" + nl +"40" + nl + "18" + nl + "13" + nl + "700" + nl + "100";         //food search test


        //String tsnd = "EDITLOCATION" + nl + "1" + nl + "-33" + nl + "151";        //edit location test


        //String tsnd = "GETRESTAURANT" + nl + "1";        //retrieve restaurant test

        //String tsnd = "ADDMEAL" + nl + "20" + nl + "20" + nl + "20" + nl + "800" + nl + "103733921743768629878" + nl + "Dinner";    //add meal test


        //String tsnd = "DAILYADD" + nl + "19" + nl + "22" + nl + "11" + nl + "450" + nl + "103733921743768629878";    //add daily test

        //String tsnd = "USERLOGIN" + nl + "103733921743768629878" + nl + "ali.c.zeybek@gmail.com" + nl + "Ali Can Zeybek"; //user login test

        //String tsnd = "UPDATEUSER" + nl + "123456712345671234567" + nl + "1500" + nl + "50" + nl + "40" + nl + "30"; //update user test

        //String tsnd = "SETCLIENTNUTR" + nl + "123456712345671234567" + nl + "123456712345671234567"; //set client-nutritionist test

        String tsnd = "UPDATEMEAL" + nl + "103733921743768629878" + nl + "1" + nl + "750" + nl + "18" + nl + "18" + nl + "17"; //update user test



        oos.writeObject(tsnd);

        //read the server response message
        ois = new ObjectInputStream(socket.getInputStream());

        //ArrayList<Restaurant> message = (ArrayList) ois.readObject();        //food search test


        //boolean message = (Boolean) ois.readObject();         //edit location test


        //Restaurant message = (Restaurant) ois.readObject();        //retrieve restaurant test

        boolean message = (Boolean) ois.readObject();       //add meal  -- user update  -- meal update -- set client-nutritionist


        //User message = (User) ois.readObject(); // user login test

        System.out.println("Message: " + message);
        //close resources
        ois.close();
        oos.close();
        Thread.sleep(100);
        //}
    }
}