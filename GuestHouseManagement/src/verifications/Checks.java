package verifications;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;

public class Checks 
{
    //new password Verifier method, it should be called in order to verify the password for newly created account. or for to change passowrd
    public static boolean newPasswordCheck(String password1, String password2)
    {
        if(password1.equals(password2))
            return true;
        else 
            return false;
    }  
    
    //this method is to check for the correct input of First Name as it can contain spaces;
    public static boolean alphaCheckIgnoreSpace(String name)
    {
        boolean check = true;
        for(int i = 0; i < name.length(); i++)
        {
            if(!Character.isLetter(name.charAt(i)) && name.charAt(i) != ' ')
            {
                check = false;
                break;
            }
        }
        return check;
    }
    
    //this method is to check for the correct input of Last Name as it can contain spaces;
    public static boolean alphaCheck(String name)
    {
        boolean check = true;
        for(int i = 0; i < name.length(); i++)
        {
            if(!Character.isLetter(name.charAt(i)))
            {
                check = false;
                break;
            }
        }
        return check;
    }
    
    //this method is to check for the correct input of First Name as it can contain spaces;
    public static boolean cnicCheck(String cnic)
    {
        boolean check = true;   //0123456789 10 11 12 13 14
                                //37301-7256 8  3  0  -  3
        if(cnic.length() == 15 && cnic.charAt(5) == '-' && cnic.charAt(13) == '-')
        {
            for(int i = 0; i < cnic.length(); i++)
            {
                if(!Character.isDigit(cnic.charAt(i)) && i != 5 && i != 13)
                {
                    check = false;
                    break;
                }
            }
        }
        else
            check = false;
        
        return check;
    }
    
    //check the correct pattern of mobile number
    public static boolean mobCheck(String num)
    {
        boolean check = true;
        
        for(int i = 0; i < num.length(); i++)
        {
            if(num.length() != 11 || !Character.isDigit(num.charAt(i)))
            {
                check = false;
                break;
            }
        }
        
        return check;
    }
    
    public static String getCurrentdate()
    {
        LocalDate today = LocalDate.now();
        
        int days = today.getDayOfMonth();
        int month = today.getMonthValue();
        int year = today.getYear();
        
        String date = year + "-" + month + "-" + days;
        
        return date;
    }
    
    public static Boolean checkLeavingDate(String date)
    {
        boolean check = false;
        
        String date1[] = date.split("-");
        
        LocalDate today = LocalDate.now();
        
        int days = today.getDayOfMonth();
        int month = today.getMonthValue();
        int year = today.getYear();
        
        if(!date.equals(today.toString()))
        {
            if(!(Integer.parseInt(date1[0]) < year))
            {
                if(!(Integer.parseInt(date1[1]) < month))
                {
                    if(!(Integer.parseInt(date1[2]) <= days))
                    {
                       check = true;
                    }
                }
            }
        }
        
        return check;
    }
    
    public static boolean ageCheck(int bYear, int bMonth, int bDay)
    {
        boolean check = true;
        
        LocalDate today = LocalDate.now();               //Today's date
        LocalDate birthday = LocalDate.of(bYear, bMonth, bDay);  //Birth date

        Period period = Period.between(birthday, today);

        //Now access the values as below
        int days = period.getDays();
        int month = period.getMonths();
        int year = period.getYears();
        
        if(year <= 18)
        {
            if(!(bYear == (today.getYear()-18) && ((month == 0 && days == 0) || bMonth > today.getMonthValue() || (bMonth == today.getMonthValue() && bDay >= today.getDayOfMonth()))))
                check = false;
        }
        return check;
    }
    
    public static int ageReturner(int bYear, int bMonth, int bDay)
    {
        LocalDate today = LocalDate.now();               //Today's date
        LocalDate birthday = LocalDate.of(bYear, bMonth, bDay);  //Birth date

        Period period = Period.between(birthday, today);

        //Now access the values as below
        int days = period.getDays();
        int month = period.getMonths();
        int year = period.getYears();
        
        if(year <= 18)
        {
            if((bYear == (today.getYear()-18) && ((month == 0 && days == 0) || bMonth > today.getMonthValue() || (bMonth == today.getMonthValue() && bDay >= today.getDayOfMonth()))))
                year = 18;
        }
        return year;
    }
    
    //verify either the entered value is digit or not
    public static boolean digitCheck(String num)
    {
        boolean check = true;
        for(int i = 0; i < num.length(); i++)
        {
            if(Character.isDigit(num.charAt(i)) != true)
            {
                check = false;
                break;
            }
        }
        return check;
    }
    
    public static LocalDate stringToLocalDateConverter(String date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
	//convert String to LocalDate
	LocalDate localDate = LocalDate.parse(date, formatter);
        
        return localDate;
    }
    
    public static String getCurrentTime()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a");  
        Date date = new Date();  
        //System.out.println(formatter.format(date).toString()); 
        return formatter.format(date).toString();
    }
    
    public static boolean mobCheckData(String mob) //this method verify the unity of mob number entered by user
    {
        boolean check = true;
        
        try
        {
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
            Statement stmt = conn.createStatement();
            
            ResultSet rs = stmt.executeQuery("select phone_number from customer_phone where phone_number = '" + mob + "';");
            
            if(rs.next())
                check = false;
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
        }
        
        return check;
    }
    
    public static boolean emailCheck(String email)
    {
        boolean check = false;
        
        
        if(email.substring(email.length()-4, email.length()).equals(".com"))
        {
            for(int i = 0; i < email.length(); i++)
            {
                if(Character.isDigit(email.charAt(i)) || Character.isLetter(email.charAt(i)) ||email.charAt(i) == '.' || email.charAt(i) == '@' || email.charAt(i) == '_')
                    check = true;
                else
                {
                    check = false;
                    break;
                }
            }
        }
        
        return check;
    }
    
    public static boolean emailCheckData(String email) //this method verify the unity of Email Entered By User 
    {
        boolean check = true;
        
        try
        {
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
            Statement stmt = conn.createStatement();
            
            ResultSet rs = stmt.executeQuery("select email from customer where email = '" + email + "';");
            
            if(rs.next())
                check = false;
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
        }
        
        return check;
    }
    
    
    //this function can throw an IOException 
    public static String getEmpID(String type, String branch) throws IOException//type can only be housekeeping, sweeper, laundryman, cook, repairer
    {
        String emp = "";
        try
        {
            boolean bigCheck = true;
            
            System.out.println("Branch in CHECKS : " + branch);
            
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
            Statement stmt = conn.createStatement();
            
            String tempEmp = "";
            
            ResultSet rs = stmt.executeQuery("SELECT employee.empID from employee, employee_type, type, branch where type.typeID = employee_type.typeID and employee.empID = employee_type.empID and branch.branchID = employee.branchID and type.type = '"+type+"' and branch.branch_name = '"+branch+"';");
            //rs = stmt.executeQuery("");
            
            while(rs.next())
            {
                tempEmp = tempEmp + rs.getString(1) + "#";
            }
            
            String[] employeesDB = tempEmp.split("#");
            
            int counted = 0;
            
            Scanner reader = new Scanner(new FileInputStream(type + ".txt"));

            
            if(reader.hasNextLine())
            {
                String employeesFile[] = reader.nextLine().split("#");
                
                for(int i = 0; i < employeesDB.length; i++)
                {
                    boolean check = true;
                    
                    for(int j = 0; j < employeesFile.length; j++)
                    {
                        if(employeesDB[i].equals(employeesFile[j]))
                        {
                            check = false; // it get match
                        }
                    }
                    
                    if(check)
                    {
                        emp = employeesDB[i];
                        counted = i;
                        bigCheck = false;
                    }
                }
            }

            reader.close();


            if(bigCheck)
            {
                emp = employeesDB[0];
                PrintWriter pw = new PrintWriter(new FileOutputStream(type + ".txt"));
                pw.print(employeesDB[0] + "#");
                pw.close();
            }
            else
            {
                PrintWriter pw = new PrintWriter(new FileOutputStream(type + ".txt", true));
                pw.print(employeesDB[counted] + "#");
                pw.close();
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.toString());
        }
        return emp;
    }
    
    public static boolean cnicCheckData(String cnic) //this method verify the unity of cnic Entered By User
    {
        boolean check = true; //return false when cnic
        
        try
        {
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=hamza");
            Statement stmt = conn.createStatement();
            
            ResultSet rs = stmt.executeQuery("select CNIC from customer where CNIC = '" + cnic + "';");
            
            if(rs.next())
                check = false;
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
        }
        
        return check;
    }
    
}
