import java.util.*;
import ZULALogins.*;

public class Home
{
    public static void main(String[] args)
    {
        System.out.println("-----ZULA Taxi - Main Menu-----");
        Scanner s= new Scanner(System.in);
        boolean b=true;
        while(b)
        {
            System.out.println("\nEnter Your Choice: ");
            System.out.println("\n1. Driver Login");
            System.out.println("2. Customer Login");
            System.out.println("3. ZULA Admin");
            System.out.println("4. Exit");
            System.out.print("\n\nThe Option:\t");
            int n=s.nextInt();
            s.nextLine();
            switch(n)
            {
                case 1: DriverLogin.Driver();
                        break;
                case 2: CustomerLogin.Customer();
                        break;
                case 3: AdminLogin.Admin();
                        break;
                case 4: b=false;
                        break;
                default:System.out.println("Wrong Choice....");
                        break;
            }
            if(b==true)System.out.println("\n\nReturning back to Home Menu....");
        }
        s.close();
    }
}