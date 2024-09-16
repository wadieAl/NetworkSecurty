
package DAO;
import Driver.DbConnection;
import Entities.UsersItems;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class UserDao {
    
    public boolean checkusernameandpassword (String Un,String Pass ) {
        
       String sql= "SELECT `UserName`, `Userpass` FROM `users` WHERE UserName='"+Un+"' and UserPass='"+Pass+"'";
       
       boolean IsValid=false;
       DbConnection con=new DbConnection();
       Statement statement=null;
        try { 
       Connection connect=con.getConnection();
       statement=connect.createStatement();        
            ResultSet rs=statement.executeQuery(sql);           
            String UserNam="";
            String Password="";
            while(rs.next())
            {
            IsValid=true;
            }       
        }    
      catch(SQLException | ClassNotFoundException ex){  
          JOptionPane.showMessageDialog(null, "قم بتشغيل قاعدة البيانات");
      }
      
       return IsValid;       
 }  
    

  public ArrayList<UsersItems> getall(UsersItems item) throws ClassNotFoundException {
    ArrayList<UsersItems> Item = new ArrayList<>();
        DbConnection ConGetall = new DbConnection();
        Connection ConnectGetall = ConGetall.getConnection();
        Statement statement = null;
        String sql = "SELECT `UserName`, `Userpass`, `NetWork_IP`, `Alwod_IPs`, `Close_Port`, `Sites`, `Programs`, `Users` FROM `users`"
                + " WHERE `ID` = '" + item.getID() + "' ";
        try {
            statement = ConnectGetall.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {                
                item.setUserNam(rs.getString("UserName"));
                item.setUserPass(rs.getString("Userpass"));
                item.setNetWork_IP(rs.getBoolean("NetWork_IP"));
                item.setAlwod_IPs(rs.getBoolean("Alwod_IPs"));
                item.setClose_Port(rs.getBoolean("Close_Port"));
                item.setSites(rs.getBoolean("Sites"));
                item.setPrograms(rs.getBoolean("Programs"));
                item.setUsers(rs.getBoolean("Users")); 
                Item.add(item);
            }       
        } catch (SQLException ex) {
            System.out.print(ex);
        } finally {
            try {
                ConnectGetall.close();
                statement.close();
            } catch (SQLException ex) {
                System.out.print(ex);
            }
        }

        return Item;
    }
  
  public ArrayList<UsersItems> getUsers(UsersItems item) throws ClassNotFoundException {
    ArrayList<UsersItems> Item = new ArrayList<>();
        DbConnection ConGetall = new DbConnection();
        Connection ConnectGetall = ConGetall.getConnection();
        Statement statement = null;
        String sql = "SELECT `UserName`, `Userpass`, `NetWork_IP`, `Alwod_IPs`, `Close_Port`, `Sites`, `Programs`, `Users` FROM `users`"
                + " WHERE `UserName` = '" + item.getUserNam()+ "' ";
        try {
            statement = ConnectGetall.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {                
                item.setNetWork_IP(rs.getBoolean("NetWork_IP"));
                item.setAlwod_IPs(rs.getBoolean("Alwod_IPs"));
                item.setClose_Port(rs.getBoolean("Close_Port"));
                item.setSites(rs.getBoolean("Sites"));
                item.setPrograms(rs.getBoolean("Programs"));
                item.setUsers(rs.getBoolean("Users")); 
                Item.add(item);
            }       
        } catch (SQLException ex) {
            System.out.print(ex);
        } finally {
            try {
                ConnectGetall.close();
                statement.close();
            } catch (SQLException ex) {
                System.out.print(ex);
            }
        }

        return Item;
    }
        
        public int InsertItems(UsersItems item) throws ClassNotFoundException {
        int affected = 0;     
        DbConnection ConInsertItems = new DbConnection();
        Connection ConnectInsertItems = ConInsertItems.getConnection();
        Statement statement = null;
        String sql = "INSERT INTO `users`( `UserName`, `Userpass`, `NetWork_IP`, `Alwod_IPs`, `Close_Port`, `Sites`, `Programs`, `Users`)"
                + " VALUES ('"+item.getUserNam()+"','"+item.getUserPass()+"',"
                + "'"+item.isNetWork_IP()+"','"+item.isAlwod_IPs()+"',"
                + "'"+item.isClose_Port()+"','"+item.isSites()+"',"
                + "'"+item.isPrograms()+"','"+item.isUsers()+"')";
        try {
            statement = ConnectInsertItems.createStatement();
            affected = statement.executeUpdate(sql);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }finally {
            try {
                ConnectInsertItems.close();
                statement.close();
            } catch (SQLException ex) {
                System.out.print(ex);
            }
        }
        return affected;
    }
        
    public int updateCusts(UsersItems item) throws ClassNotFoundException
    {
        DbConnection ConUpdateItems = new DbConnection();
        Connection ConnectUpdateItems = ConUpdateItems.getConnection();
        Statement statement = null;
        String sql = "UPDATE `users` SET `UserName`='"+item.getUserNam()+"',`Userpass`='"+item.getUserPass()+"',"
                + "`NetWork_IP`='"+item.isNetWork_IP()+"',`Alwod_IPs`='"+item.isAlwod_IPs()+"',`Close_Port`='"+item.isClose_Port()+"',"
                + "`Sites`='"+item.isSites()+"',`Programs`='"+item.isPrograms()+"',`Users`='"+item.isUsers()+"' WHERE `ID`='"+item.getID()+"'";
                  int affected = 0;
        try {
            statement = ConnectUpdateItems.createStatement();
            affected = statement.executeUpdate(sql);

        } catch (SQLException ex) {
            System.out.print(ex);
        }

        return affected;
            }
        
    
}
