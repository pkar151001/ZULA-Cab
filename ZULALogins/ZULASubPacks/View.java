package ZULALogins.ZULASubPacks;
import java.util.*;
import java.sql.*;

public class View{
    public static void AdminView()
    {
        String url="jdbc:mysql://localhost:3306/curr";
        Connection con;
        try 
        {
            //JDBC Connection is established
            con = DriverManager.getConnection(url,"root", "Priyalini@2");
            Statement curr=con.createStatement();
         
            Scanner s= new Scanner(System.in);
            
            //Checking the contribution of each cab 
            System.out.print("\nTo check the contribution of each cab, enter Cab ID:\t");
            int cabID= s.nextInt();
            ResultSet fare=curr.executeQuery("select sum(fare) from history where cabid="+cabID+";");
            fare.next();
            int totalFare = fare.getInt(1);
            ResultSet comm=curr.executeQuery("select sum(comm) from history where cabid="+cabID+";");
            comm.next();
            int totalComm = comm.getInt(1);
            ResultSet count=curr.executeQuery("Select count(*) from history where cabid="+cabID+";");
            count.next();
            int totalCount = count.getInt(1);
            System.out.println("\nTotal Fare: "+totalFare);
            System.out.println("Total Commission: "+totalComm);
            System.out.println("Total Count: "+totalCount);
            System.out.println("\nThe Table:");
            ResultSet currentHistory=curr.executeQuery("Select * from history where cabid="+cabID+";");
            System.out.println("\n\nCustomer ID\tCustomer Name\tSource Location\t\tDestination Location\tFare\tZULA Commission");
            while(currentHistory.next())
            {
            System.out.println(currentHistory.getInt("customerid")+"\t\t"+currentHistory.getString("customername")+"\t\t\t"+currentHistory.getString("source")+"\t\t"+currentHistory.getString("destination")+"\t\t\t"+currentHistory.getInt("fare")+"\t\t"+currentHistory.getInt("comm"));
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void HistoryView()
    {
        String url="jdbc:mysql://localhost:3306/curr";
        Connection con;
        try 
        {
            con = DriverManager.getConnection(url,"root", "Priyalini@2");
            Statement curr=con.createStatement();
            
            //Looking out the overall history of rides
            System.out.println("\nThe Table:");
            ResultSet currentHistory=curr.executeQuery("Select * from history;");
            System.out.println("\nCab ID\t\tDriver Name\tCustomer ID\tCustomer Name\tSource Location\t\tDestination Location\tFare\tZULA Commission");
            while(currentHistory.next())
            {
            System.out.println(currentHistory.getInt("cabid")+"\t\t"+currentHistory.getString("drivername")+"\t\t"+currentHistory.getInt("customerid")+"\t\t"+currentHistory.getString("customername")+"\t\t\t"+currentHistory.getString("source")+"\t\t\t"+currentHistory.getString("destination")+"\t\t"+currentHistory.getInt("fare")+"\t\t\t"+currentHistory.getInt("comm"));
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void DriverView(int cabID)
    {
        String url="jdbc:mysql://localhost:3306/curr";
        try
        {
            Connection con = DriverManager.getConnection(url,"root", "Priyalini@2");
            Statement curr=con.createStatement();
            
            //List of rides undertaken by the particular cab
            ResultSet currentHistory=curr.executeQuery("Select source, destination, customerid,fare,comm from history where cabid='"+cabID+ "';");
            System.out.println("\n\nCustomer ID\tSource Location\t\tDestination Location\tFare\tZULA Commission");
            while(currentHistory.next())
            {
                System.out.println(currentHistory.getInt("customerid")+"\t\t"+"\t"+currentHistory.getString("source")+"\t"+currentHistory.getString("destination")+"\t"+currentHistory.getInt("fare")+"\t"+currentHistory.getInt("comm"));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void CustomerView(int customerID)
    {
        String url="jdbc:mysql://localhost:3306/curr";
        try
        {
            Connection con = DriverManager.getConnection(url,"root", "Priyalini@2");
            Statement curr=con.createStatement();
            
            //List of all transactions of the customer;
            ResultSet currentHistory=curr.executeQuery("Select source, destination, cabid,fare from history where customerid='"+customerID+ "';");
            System.out.println("\n\nCab ID\tSource Location\t\tDestination Location\tFare");
            while(currentHistory.next())
            {
                System.out.println(currentHistory.getInt("cabid")+"\t"+currentHistory.getString("source")+"\t\t\t"+currentHistory.getString("destination")+"\t\t\t"+currentHistory.getInt("fare")+"\t");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        } 
    }
    public static void reset()
    {
        String url="jdbc:mysql://localhost:3306/curr";
        Connection con;
        try 
        {
            con = DriverManager.getConnection(url,"root", "Priyalini@2");
            Statement curr=con.createStatement();
            
            //Resetting all values i.e. Status of cabs and overall history back to original data
            curr.executeUpdate("delete from cabstatus");
            curr.executeUpdate("insert into cabstatus values (1,'D',0,'ready');");
            curr.executeUpdate("insert into cabstatus values (2,'G',0,'ready');");
            curr.executeUpdate("insert into cabstatus values (3,'H',0,'ready');");
            curr.executeUpdate("insert into cabstatus values (4,'A',0,'ready');");
            curr.executeUpdate("delete from history");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
