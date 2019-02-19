package javaapplication13;


import java.sql.*;
import java.io.*;
import java.util.*;

public class MetaDataExample
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

            DatabaseMetaData dmd = con.getMetaData();

            ResultSet rs = dmd.getTables(null, null, null, null);

            System.out.println("Table Name\tTable Type");
            while (rs.next())
            {
                System.out.println(rs.getString(3) + "\t"+rs.getString(4));
            }

            con.close();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}


