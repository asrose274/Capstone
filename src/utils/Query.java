package utils;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Provided class
 */

import static utils.JDBC.connection;

public class Query {
    private static String query;
    private static Statement stmt;
    private static ResultSet result;
    private static int resultInt;

    public static void makeQuery(String q){
        query =q;
        try{
            stmt=connection.createStatement();
            // determine query execution
            if(query.toLowerCase().startsWith("select"))
                result=stmt.executeQuery(q);
            if(query.toLowerCase().startsWith("delete")||query.toLowerCase().startsWith("insert")||query.toLowerCase().startsWith("update"))
                stmt.executeUpdate(q);

        }
        catch(Exception ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }

    public static ResultSet getResult(){
        return result;
    }



}


