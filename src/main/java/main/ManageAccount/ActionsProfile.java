package main.ManageAccount;

import javax.swing.plaf.nimbus.State;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

public class ActionsProfile {

    private int id;
    private Connection con;
    private Statement st;

    public ActionsProfile(int id, Connection con) throws SQLException {
        this.id = id;
        this.con = con;
        this.st = con.createStatement();
    }

    public void friends() throws SQLException{
        int option = menuFriends();
        System.out.println("User name you want to manage: ");
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();
        ResultSet rs = st.executeQuery("select id from usuario where name like " + name +  ";");
        if(rs == null && rs.getInt("id") == this.id){
            System.out.println("There is not a user you can manage with name : " + name);
        }else{
            switch (option){
                case 1 :
                    st.executeQuery("insert into amigo(iduser1, iduser2) values(" + Integer.toString(this.id) + ", " + Integer.toString(rs.getInt("id")) + ";");
                    System.out.println("User with name : " + name + " is your friend");
                    break;
                case 2:
                    st.executeQuery("delete from amigo where iduser1 =" + Integer.toString(this.id) + "and iduser2 = " + Integer.toString(rs.getInt("id")) +  ";");
                    System.out.println("User with name : " + name + " is not your friend anymore");
                    break;
                case 3:
                    viewImages(rs.getInt("id"));
                    break;
                case 4:
                    break;
            }
        }
        con.commit();
    }

    public void manageInfo() throws SQLException {
        //puedes cambiar passwd, email, image
        int option = menuInfo();
        switch (option){
            case 1 :
                Scanner sc = new Scanner(System.in);
                System.out.println("Introduce your password: ");

                //leemos el password
                String passwd = sc.nextLine();
                //comprobamos que coincide
                if(!passwd.equals(st.executeQuery("select passwd from usuario where id = " + Integer.toString(this.id)+ ";")))
                    System.err.println("[ERROR], Invalid password");
                else{

                    System.out.println("Introduce your new password: ");
                    String newPsswd1 = sc.nextLine();//leemos el nuevo password

                    System.out.println("Reintroduce your new password: ");
                    String newPsswd2 = sc.nextLine();

                    if(newPsswd1.equals(newPsswd2)){ //comprobamos que coinciden
                        st.executeQuery("update usuario set passwd = " + newPsswd1 + " where id = " + Integer.toString(this.id) + ";");
                    }else {
                        System.err.println("[ERROR], No match");
                    }
                }
                break;
            case 2:
                sc = new Scanner(System.in);
                System.out.println("Introduce your mail: ");
                String mail = sc.nextLine();
                //comprobamos que es una dirección válida
                //TODO
                 st.executeQuery("update usuario set email = " + mail + " where id = " + Integer.toString(this.id) + ";"); //actualizamos el campo email
                break;
            case 3:
                sc = new Scanner(System.in);
                System.out.println("Introduce link of the photo: ");
                String link = sc.nextLine();
                try{
                    Desktop desktop = java.awt.Desktop.getDesktop();
                    URI oURL = new URI(link); //volcamos el link de la i-esima imagen
                    desktop.browse(oURL); // lanzamos el navegador con el link
                } catch (Exception e) {
                    System.err.println("[ERROR], not a proper link");
                }
                st.executeQuery("update usuario set image = " + link + " where id = " + Integer.toString(this.id) + ";"); //actualizamos el campo image
                break;
            case 4:
                break;
        }

    }
    public void unsubscribe() throws SQLException {
        st.executeQuery("delete from usuario where id = " + Integer.toString(id) + ";");
        System.out.println("Your information has been removed from the system");
        con.commit();
    }

    public void viewMyImages() throws  SQLException{
        viewImages(this.id);
    }
    public void viewImages(int id) throws SQLException { //abre la imagenes dado el id de un usuario
        ResultSet rs = st.executeQuery("select image from publicacion where iduser = " + Integer.toString(id) + ";");
        while(rs.next()){
            try { //tratamos de abrir el link a la foto
                Desktop desktop = java.awt.Desktop.getDesktop();
                URI oURL = new URI(rs.getString("image")); //volcamos el link de la i-esima imagen
                desktop.browse(oURL); // lanzamos el navegador con el link
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        con.commit();
    }

    public int menuInfo() {
        System.out.println("Manage information menu");
        System.out.println("------------------------");
        System.out.println();
        System.out.println("1. Change password");
        System.out.println("2. Change email");
        System.out.println("3. Change profile image");
        System.out.println("4. Quit");
        System.out.println("Select option:_ ");
        Scanner sc = new Scanner(System.in);
        String op = sc.nextLine();
        int option = Integer.parseInt(op);
        if(option > 4 || option < 1){
            System.out.println("Select option:_ ");
            sc = new Scanner(System.in);
            op = sc.nextLine();
            option = Integer.parseInt(op);
        }
        return option;
    }

    public int menuFriends()  {
        System.out.println("Manage friends menu");
        System.out.println("------------------------");
        System.out.println();
        System.out.println("1. Add friend");
        System.out.println("2. Remove friend");
        System.out.println("3. View profile");
        System.out.println("4. Quit");
        System.out.println("Select option:_ ");
        Scanner sc = new Scanner(System.in);
        String op = sc.nextLine();
        int option = Integer.parseInt(op);
        if(option > 4 || option < 1){
            System.out.println("Select option:_ ");
            sc = new Scanner(System.in);
            op = sc.nextLine();
            option = Integer.parseInt(op);
        }
        return option;
    }

}
