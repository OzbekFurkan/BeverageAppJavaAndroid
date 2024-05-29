package com.example.beverageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.beverageapp.database.DatabaseConnection;
import com.example.beverageapp.databinding.ActivityLogRegBinding;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LogRegActivity extends AppCompatActivity {
    ActivityLogRegBinding activityLogRegBinding;
    Connection con;
    DatabaseConnection databaseConnection;

    EditText usrn;
    EditText mail;
    EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLogRegBinding = ActivityLogRegBinding.inflate(getLayoutInflater());
        setContentView(activityLogRegBinding.getRoot());
        InitializeVariables();
        ConnectDB();

    }
    private void InitializeVariables()
    {
        databaseConnection = new DatabaseConnection();
        usrn = (EditText) findViewById(R.id.editTextText);
        mail = (EditText) findViewById(R.id.editTextText2);
        pass = (EditText) findViewById(R.id.editTextText3);
    }
    public void SignInOrReg(View view)
    {
        Log.d("ben", "tıklandı");
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(()-> {
            try {
                String query = "select * from Customers where customer_name='"+usrn.getText()+
                        "' and customer_mail='"+mail.getText()+"' and customer_password='"+pass.getText()+"';";
                PreparedStatement preparedStatement = con.prepareStatement(query);
                ResultSet resultset = preparedStatement.executeQuery();
                if(resultset.next())
                {
                    Log.d("ben", "customer found");
                }
                else {
                    String newCus = "insert into Customers(customer_name, customer_mail, customer_password, is_admin) " +
                            "values('"+usrn.getText()+"','"+mail.getText()+"','"+pass.getText()+"',0);";
                    PreparedStatement newCusStatement = con.prepareStatement(newCus);
                    newCusStatement.executeUpdate();
                    Log.d("ben", "new customer added");
                }


                String getId = "select customer_id from Customers where customer_name='"+usrn.getText()+
                        "' and customer_mail='"+mail.getText()+"' and customer_password='"+pass.getText()+"';";
                PreparedStatement st = con.prepareStatement(getId);
                ResultSet rs = st.executeQuery();
                rs.next();
                GoToMain(rs.getInt(1));
            }catch (SQLException sqlException)
            {
                    Log.d("ben", sqlException.getCause()+": "+sqlException.getMessage());
            }
        });
    }

    private void GoToMain(int id)
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
    public void ConnectDB()
    {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Log.d("ben","1");
        executorService.execute(()->{
            try {
                databaseConnection = new DatabaseConnection();
                Log.d("ben","2");
                con = databaseConnection.Conn();

                if(con==null)
                {
                    Log.d("ben", "Connection Failed!");
                }
                else{
                    Log.d("ben", "Connection Successful!");
                }
            }catch (Exception e)
            {
                Log.d("ben", e.getCause()+": "+e.getMessage());
            }

            runOnUiThread(()->{
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException interruptedException)
                {

                }
            });
        });


    }

}