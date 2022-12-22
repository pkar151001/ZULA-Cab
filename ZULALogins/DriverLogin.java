package ZULALogins;

import ZULALogins.ZULASubPacks.*;
import java.sql.*;
import java.util.*;

import ZULALogins.ZULASubPacks.View;
public class DriverLogin {
    public static void Driver()
    {
        String url="jdbc:mysql://localhost:3306/zula";
        Scanner s= new Scanner(System.in);
        System.out.println("\n-----Login for Driver-----");
        System.out.print("\nEnter your User Name: \t");
        String driverName= s.nextLine();
        System.out.print("Enter your Pass Code: \t");
        int cabPass=s.nextInt();
        try
        {
            Connection con=DriverManager.getConnection(url,"root", "Priyalini@2");
            Statement zula=con.createStatement();
            ResultSet verify=zula.executeQuery("select * from cabd where name='"+driverName+"';");
            boolean b=false;
            while(verify.next())
            {
                if(verify.getInt("pass")==cabPass && verify.getString("name").compareTo(driverName)==0) 
                {
                    System.out.println("\nLogged in Successfully !!!");
                    System.out.println("\n-----Driver Menu-----");
                    System.out.println("\nView history of rides: ");
                    View.DriverView(verify.getInt("id"));
                    b=true;
                    break;
                }
            }
            if(b==false)
                System.out.println("\nWrong Driver Name / Passcode\n\nRe-login Again with right credentials");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }       
}
