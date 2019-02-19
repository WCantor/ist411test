package javaapplication13;


import java.sql.*;
import java.io.*;
import java.util.*;

public class ResultSetExample
{
    public static void main (String args[])
    {

        String className = null;
        String url = null;
        String user = null;
        String password = null;

        try
        {
            ResourceBundle resources;
            InputStream in = null;
            ResourceBundle newResources;

            in = ClassLoader.getSystemResourceAsStream("db.properties");

            resources = new PropertyResourceBundle(in);

            in.close();

            className = resources.getString("jdbc.driver");
            url = resources.getString("jdbc.url");
            user = resources.getString("jdbc.user");
            password = resources.getString("jdbc.password");
        }
        catch (Exception exp)
        {
            System.out.println("Couldn't load resources.");
            System.exit(-1);
        }

        try
        {
            Class.forName(className);
        }
        catch (Exception e)
        {
            System.out.println(className + " driver failed to load.");
            System.exit(-1);
        }

        try
        {
            Connection con =
                    DriverManager.getConnection(url, user, password);

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * from Inventory ORDER BY ProductName");

            while (rs.next())
            {
                String product = rs.getString("ProductName");
                String description = rs.getString("ProductDescription");

                float price = rs.getFloat("Price");

                System.out.println(product + " described by many as \"" + description +
                        "\" is sold for $" + price);
            }

            stmt.close();

            con.close();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}


