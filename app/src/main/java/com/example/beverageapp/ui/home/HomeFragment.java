package com.example.beverageapp.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beverageapp.MainActivity;
import com.example.beverageapp.R;
import com.example.beverageapp.database.DatabaseConnection;
import com.example.beverageapp.databinding.FragmentHomeBinding;
import com.example.beverageapp.feature.home.Drink;
import com.example.beverageapp.feature.home.MyContentAdapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeFragment extends Fragment {

    DatabaseConnection databaseConnection;
    Connection con=null;
    private RecyclerView recyclerView;
    ArrayList<Drink> drinks = new ArrayList<Drink>();
    int cus_id;

private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

    binding = FragmentHomeBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cus_id = getActivity().getIntent().getIntExtra("id", -1);

        ConnectDB();

    }

    private void ListContents()
    {
        try {
            recyclerView = binding.contentList;
            MyContentAdapter myContentAdapter = new MyContentAdapter(getContext(), drinks, con, cus_id);
            recyclerView.setAdapter(myContentAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }catch (Exception e)
        {
            Log.d("ben", e.getCause()+": "+e.getMessage());
        }

    }

    private void GetDrinksFromDB()
    {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(()-> {
            try {
                String query = "select * from Drinks;";
                PreparedStatement preparedStatement = con.prepareStatement(query);
                ResultSet resultset = preparedStatement.executeQuery();
                while(resultset.next())
                {
                    int id = resultset.getInt(1);
                    String image = resultset.getString(2);
                    int price = resultset.getInt(3);
                    String name = resultset.getString(4);
                    String desc = resultset.getString(5);
                    int r_c = resultset.getInt(6);
                    int t_id = resultset.getInt(7);
                    Drink drink = new Drink(id, image, price, name, desc, r_c, t_id);
                    drinks.add(drink);
                    Log.d("ben", id +": "+name);
                }

            }catch (SQLException sqlException)
            {
                Log.d("ben", sqlException.getCause()+": "+sqlException.getMessage());
            }
            getActivity().runOnUiThread(()->{
                ListContents();
            });
        });
    }

    public void ConnectDB()
    {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(()->{
            try {
                databaseConnection = new DatabaseConnection();
                con = databaseConnection.Conn();

                if(con==null)
                {
                    Log.d("ben", "Connection Failed!");
                }
                else{
                    Log.d("ben", "Connection Successful!");
                    drinks.clear();
                    GetDrinksFromDB();
                }
            }catch (Exception e)
            {
                Log.d("ben", e.getCause()+": "+e.getMessage());
            }
            getActivity().runOnUiThread(()->{
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException interruptedException) {

                }
            });
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}