/* connecttodatabase application
 * CSCI 112 
 * @author David Seng
 * 
 */
package connectodatabase;

import java.io.*;
import java.sql.*;

/**
 *
 * @author David.Seng3
 */
public class ConnectoDataBase {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        
// establish a connection to the database as an instance of the Connection class
        Connection conn = DriverManager.getConnection
                ("jdbc:mysql://68.178.216.151/CWHDemo", "student", "Student%123");

        // create an instance of the Statement class to use with the connection
        Statement state = conn.createStatement();

        // call method to query database and write result set to CSV file
        queryToCSV(state);

        // call method to query database and write result set to console
        queryToConsole(state);

        // close connection to database
        conn.close();

    } // end main
 //*******************************************************************************//
     public static void queryToCSV(Statement s) throws Exception {

        String queryString; // String variable for query
        ResultSet rset;       // create instance of Result set object

        // create File class object
        File SQLResult = new File("SQLResult.csv");

        // create a PrintWriter object, link to File object
        PrintWriter outfile = new PrintWriter(SQLResult);

        // create query as a String
        queryString = "SELECT crn, subject, course, section, days, time "
                    + "FROM fall2014 "
                    + "WHERE subject = 'CSCI' "
                    + "ORDER BY crn;";
        
        // send statement executing query, save result set
        rset = s.executeQuery(queryString);

        // print headings for output
        outfile.print("CRN" + "," + "Subject" + "," + "Course" + ","
                    + "Section" + "," + "Days" + "," + "Time" + "," + "\n");

        // iterate through result set and write fields
        while (rset.next() ) {
            outfile.print(rset.getString(1) + "," + rset.getString(2) + "," + 
                    rset.getString(3) + "," + rset.getString(4) + "," +
                    rset.getString(5) + "," + rset.getString(6) + "\n");

        } // end while

        // close file
        outfile.close();

    } // end queryToCSV()
   //*****************************************************************************//
     public static void queryToConsole(Statement s) throws Exception {

        String queryString; // String variable for query
        ResultSet rset;       // create instance of Result set object

        // create query as a String
        queryString = "SELECT subject, course, enrollment "
                    + "FROM fall2014 "
                    + "WHERE enrollment < 10 "
                    + "ORDER BY enrollment;";
        
        // send statement executing query, save result set
        rset = s.executeQuery(queryString);

        // print headings for output
        System.out.println("\n" + queryString + "\n");
        
        System.out.printf("%-15s%-15s%-15s%n", "Subject", "Course #", "Enrollment");
        System.out.println("*****************************************");

        // iterate through result set and write fields
        while (rset.next() )
                System.out.printf("%-15s%-15s%-15s%n", rset.getString(1), rset.getString(2), rset.getString(3) );
     } // end querytoconsole
} // end class
