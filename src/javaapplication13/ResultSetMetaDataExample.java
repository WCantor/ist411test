package javaapplication13;


import java.sql.*;
import java.io.*;
import java.util.*;

public class ResultSetMetaDataExample
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

            boolean notDone = true;
            String sqlStr = null;
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(System.in));
            while (notDone)
            {
                System.out.println("Enter SELECT Statement:");
                sqlStr = br.readLine();

                if (sqlStr.startsWith("SELECT") || sqlStr.startsWith("select"))
                {
                    ResultSet rs = stmt.executeQuery(sqlStr);
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int columnCount = rsmd.getColumnCount();

                    for (int x = 1; x <= columnCount; x++)
                    {
                        String columnName = rsmd.getColumnName(x);
                        System.out.print(columnName + "\t");
                    }
                    System.out.println("");

                    while (rs.next())
                    {
                        for (int x = 1; x <= columnCount; x++)
                        {
                            String resultStr = rs.getString(x);
                            System.out.print(resultStr + "\t");
                        }
                        System.out.println("");
                    }
                }
                else if (sqlStr.startsWith("exit")||sqlStr.startsWith("quit"))
                    notDone = false;
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


