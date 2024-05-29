package com.example.beverageapp.database;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection
{
    protected static String dbName = "BeverageAppDB";
    protected static String ip = "10.0.2.2";
    protected static String port = "8889";
    protected static String username = "root";
    protected static String password = "root";

    public Connection Conn()
    {
        Log.d("ben","3");
        Connection con = null;
        try {
            Log.d("ben","4");
            Class.forName("com.mysql.jdbc.Driver");
            String conStr = "jdbc:mysql://"+ip+":"+port+"/"+dbName;
            Log.d("ben","5");
            con = DriverManager.getConnection(conStr, username, password);
            Log.d("ben","6");
        }catch (Exception e)
        {
            Log.d("ben", e.getMessage()+" "+e.getCause());
        }
        return con;
    }
}
