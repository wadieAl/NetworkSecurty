/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

public class UsersItems {

    private int ID;
    private String UserNam;
    private String UserPass;
    private boolean NetWork_IP;
    private boolean Alwod_IPs;
    private boolean Close_Port;
    private boolean Sites;
    private boolean Programs;
    private boolean Users;


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUserNam() {
        return UserNam;
    }

    public void setUserNam(String UserNam) {
        this.UserNam = UserNam;
    }

    public String getUserPass() {
        return UserPass;
    }

    public void setUserPass(String UserPass) {
        this.UserPass = UserPass;
    }

    public boolean isNetWork_IP() {
        return NetWork_IP;
    }

    public void setNetWork_IP(boolean NetWork_IP) {
        this.NetWork_IP = NetWork_IP;
    }

    public boolean isAlwod_IPs() {
        return Alwod_IPs;
    }

    public void setAlwod_IPs(boolean Alwod_IPs) {
        this.Alwod_IPs = Alwod_IPs;
    }

    public boolean isClose_Port() {
        return Close_Port;
    }

    public void setClose_Port(boolean Close_Port) {
        this.Close_Port = Close_Port;
    }

    public boolean isSites() {
        return Sites;
    }

    public void setSites(boolean Sites) {
        this.Sites = Sites;
    }

    public boolean isPrograms() {
        return Programs;
    }

    public void setPrograms(boolean Programs) {
        this.Programs = Programs;
    }

    public boolean isUsers() {
        return Users;
    }

    public void setUsers(boolean Users) {
        this.Users = Users;
    }





}
