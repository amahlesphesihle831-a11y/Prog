/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package regristrationloginapp;

/**
 *
 * @author Amahle Hlongwane
 */
public class RegristrationloginApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // hardcoded user for now
    private static String correctUser = "admin";
    private static String correctPass = "1234";

    public static boolean loginUser(String user, String pass) {
        if (user.equals(correctUser) && pass.equals(correctPass)) {
            return true;
        }
        return false;
        
        
        // TODO code application logic here
    }
    
}
