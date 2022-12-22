package ZULALogins.ZULASubPacks;
import java.sql.*;

public class CabHail
{
    static private Statement ConnectionEstablishment (String domainName)
    {
        String url="jdbc:mysql://localhost:3306/"+domainName;
        try 
        {
            Connection con=DriverManager.getConnection(url,"root", "Priyalini@2");
            return con.createStatement();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return null;
        }
    }
    public static void calculations(String source, String dest, int customerID)
    {
         // Connection established between Databases curr and zula with the program

         Statement curr=ConnectionEstablishment("curr");
         Statement zula=ConnectionEstablishment("zula");

        try
        {
        //Updating status of cabs (at rest to ready),(booked to at rest)

        curr.executeUpdate("Update cabstatus set rest='ready' where rest='at rest';");
        curr.executeUpdate("Update cabstatus set rest='at rest' where rest='booked';");

        //Checking Current Status of Cabs

        System.out.println("\nCurrent Status of Drivers:");
        System.out.println("ID\tCurrent Location\tTotal rides\tCurrent Status");
        ResultSet currentStatus=curr.executeQuery("Select * from cabstatus;");
        while(currentStatus.next())
        {
            System.out.println(currentStatus.getInt("id")+"\t"+currentStatus.getString("currloc")+"\t\t\t"+currentStatus.getInt("rides")+"\t\t"+currentStatus.getString("rest"));
        }

        //Getting Distance of Source from Origin

        ResultSet sourceDist=zula.executeQuery("Select org_dist from location where name='"+ source+"';");
        int sourcedist=0;
        while(sourceDist.next())
        sourcedist=sourceDist.getInt("org_dist");

        //Getting Distance of Destination from Origin

        ResultSet DestDist=zula.executeQuery("Select org_dist from location where name='"+ dest+"';");
        int destdist=0;
        while(DestDist.next())
        destdist=DestDist.getInt("org_dist");

        //The initialization Part
        int min_dist=100;
        int min_rides=1000;
        int cab=0;
        int cabdist=0;

        //Getting details of cabs which are in ready state 

        ResultSet availableCabs=curr.executeQuery("Select * from cabstatus where rest='ready';");
            
        while(availableCabs.next())

            {
                //Getting Distance of cab from Origin

                ResultSet cabDist=zula.executeQuery("Select org_dist from location where name='"+ availableCabs.getString("currloc")+"';");
                while(cabDist.next())
                cabdist=cabDist.getInt("org_dist");
                
                //Finding which cab is nearer to the source by finding the minimum difference betwwen source and cab 
                //Also applying the condition of minimum rides

                if(Math.abs(cabdist-sourcedist) < min_dist || (Math.abs(cabdist-sourcedist)==min_dist && min_rides>=availableCabs.getInt("rides")))
                {
                    min_dist=Math.abs(cabdist-sourcedist);
                    cab=availableCabs.getInt("id");
                    min_rides=availableCabs.getInt("rides");
                }

            }
        System.out.println("\nCab to pick up at source: "+ cab +"\n");

        String driverName="",customerName="";
        ResultSet driName=zula.executeQuery("select * from cabd where id="+cab+";");
        while(driName.next())
            driverName=driName.getString("name");
        ResultSet custName=zula.executeQuery("select * from customerS where id="+customerID+";");
        while(custName.next())
            customerName=custName.getString("name");
        
        //Fare Calculation
        int fare=Math.abs(sourcedist-destdist)*10;
        int commission=(int) (fare*0.3);

        //Printing All Results
        System.out.println("Customer ID: "+customerID);
        System.out.println("Customer Name: "+customerName);
        System.out.println("\nCab ID: "+cab);
        System.out.println("Driver Name: "+driverName);
        System.out.println("\nSource Location:"+ source);
        System.out.println("Destination Location: "+dest);
        System.out.println("\nTotal Distance: "+Math.abs(sourcedist-destdist));
        System.out.println("Total Fare: "+fare);
        System.out.println("Zula Commission: "+commission);

        //Updating status of cab which is chosen

        curr.executeUpdate("Update cabstatus set rest='booked' where id="+cab+";");
        curr.executeUpdate("Update cabstatus set rides="+(min_rides+1)+" where id="+cab+";");
        curr.executeUpdate("Update cabstatus set currloc='"+dest+"' where id="+cab+";");

        //Final Status after updations
        
        ResultSet rs6=curr.executeQuery("Select * from cabstatus;");
        System.out.println("Current Status: ");
        System.out.println("ID\tCurrent Location\tTotal rides\tCurrent Status");
        while(rs6.next())
        {
            System.out.println(rs6.getInt("id")+"\t"+rs6.getString("currloc")+"\t\t\t"+rs6.getInt("rides")+"\t\t"+rs6.getString("rest"));
        }

        //Updation of Travel History
        curr.executeUpdate("insert into history values( "+cab+",'"+driverName+"',"+customerID+",'"+customerName+"','"+source+"','"+dest+"',"+fare+","+commission+");");
        
        //Displaying Travel History

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}