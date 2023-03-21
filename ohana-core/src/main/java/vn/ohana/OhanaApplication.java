package vn.ohana;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OhanaApplication {

    public static void main(String[] args) {
        SpringApplication.run(OhanaApplication.class, args);
    }


//    public static void main(String[] args) throws ClassNotFoundException, ServletException, SQLException
//    {
//
//        try
//        {
//            Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:1887/?user=ohana&password=!O$&hana@2023");
//            Statement s = (Statement) conn.createStatement();
////            int result = s.executeUpdate("CREATE DATABASE databasename");
//        }
//
//
//        catch ( Exception e)
//        {
//            e.printStackTrace();
//        }
//    }

}
