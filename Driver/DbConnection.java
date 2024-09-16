package Driver;
import java.sql.*;



public class DbConnection {
    
    public Connection con= null;
    public Connection getConnection () throws ClassNotFoundException{      
      try {   
        con = DriverManager.getConnection( "jdbc:mysql://localhost:3306/nsecurty?useUnicode=true&characterEncoding=utf8", "root", "");
      }  
      catch(SQLException e){             
        System.out.println(e.getMessage());    
      }    
      
        return con;
}
}