package ZULALogins;
import ZULALogins.ZULASubPacks.*;
import java.util.*;

public class AdminLogin {
    public static void Admin()
    {
        Scanner s= new Scanner(System.in);
        System.out.println("\n-----Login for Admin-----");
        
        //Getting username and password from user
        System.out.print("\nEnter User Name: \t");
        String adminName= s.nextLine();
        System.out.print("Enter Pass Code: \t");
        int adminID=s.nextInt();
        
        //Checking if it matches
        if(adminName.compareTo("Priyavarthan N")==0 && adminID==1510)
        {
            System.out.println("\nLogged in Successfully !!!");
            boolean c=true;
            while(c)
            {
                System.out.println("\n----Admin Menu----");
                System.out.println("\n1.View contribution of each cab");
                System.out.println("2.View the History");
                System.out.println("3.Reset to Original Data");
                System.out.print("\nChoose your option:\t");
                int choice= s.nextInt();
                s.nextLine();
                switch(choice)
                    {
                        case 1: View.AdminView();
                                break;
                        case 2: View.HistoryView();
                                break;
                        case 3: View.reset();
                                System.out.println("\nReset has been performed successfully!!!");
                                break;
                        default: System.out.println("Wrong Choice");
                    }
                System.out.println("\nWanna More? (No: 0 / Yes: 1)");
                int x= s.nextInt();
                if(x==0)c=false;
            }      
        }
        else
        {
            System.out.println("Wrong Driver Name / Passcode\nRelogin Again with right credentials");
        }
    }       
}
