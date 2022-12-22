package ZULALogins;

import ZULALogins.ZULASubPacks.*;
import java.sql.*;
import java.util.*;

import ZULALogins.ZULASubPacks.View;

public class CustomerLogin {
    public static void Customer()
    {
        String url="jdbc:mysql://localhost:3306/zula";
        Scanner s= new Scanner(System.in);
        System.out.println("\n-----Login for Customer-----");
        System.out.print("\nEnter your User Name: \t");
        String customerName= s.nextLine();
        System.out.print("Enter your Pass Code: \t");
        int customerPass=s.nextInt();
        try{
            Connection con=DriverManager.getConnection(url,"root", "Priyalini@2");
            Statement zula=con.createStatement();
            ResultSet verify=zula.executeQuery("select * from customers where name='"+customerName+"';");
            int customerID=0;
            boolean b=false;
            while(verify.next())
            {
                if(verify.getInt("pass")==customerPass) 
                {
                    b=true;
                    customerID=verify.getInt("id");
                    break;
                }
            }
            if(b==false)
            {
                System.out.println("\nWrong Customer Name / Passcode");
                System.out.println("\nDo you want to register? (No: 0 / Yes: 1)");
                int n=s.nextInt();
                s.nextLine();
                if(n==1)
                {
                    System.out.print("\nEnter UserName:\t");
                    String newName= s.nextLine();
                    int newPass=0;
                    boolean a=true;
                    while(a)
                    {
                        System.out.print("Enter Pass Code: \t");
                        newPass=s.nextInt();
                        System.out.print("Re-Enter Pass Code: \t");
                        int verifyPass=s.nextInt();
                        if(newPass==verifyPass)a=false;
                        else System.out.println("\nPasswords doesn`t match\n");
                    }
                    System.out.print("Enter Age:\t");
                    int age=s.nextInt();
                    ResultSet totalRows=zula.executeQuery("select count(*) from customers;");
                    totalRows.next();
                    int rows = totalRows.getInt(1);
                    rows++;
                    zula.executeUpdate("insert into customers values ("+rows+",'"+newName+"',"+newPass+","+age+")");
                    System.out.println("\nDetails Added Successfully!\n\nYou can re-login again....");
                }
            }
            else
            {
                System.out.println("\nLogged in Successfully !!!");
                boolean c=true;
                while(c)
                {
                    System.out.println("\n----Customer Menu----");
                    System.out.println("\n1. Register for a ride");
                    System.out.println("2. View history of travel");
                    System.out.print("\nChoose your option:\t");
                    int choice= s.nextInt();
                    s.nextLine();
                    switch(choice)
                    {
                        case 1: System.out.println("\nEnter the Source");
                                String source=s.nextLine();
                                System.out.println("Enter the Destination");
                                String dest=s.nextLine();
                                CabHail.calculations(source, dest, customerID);
                                break;
                        case 2: View.CustomerView(customerID);
                                break;
                        default: System.out.println("\nWrong Choice");
                    }
                    System.out.println("\nWanna More? (No: 0 / Yes: 1)");
                    int x= s.nextInt();
                    if(x==0)c=false;
                } 
            }  
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }       
    
}



