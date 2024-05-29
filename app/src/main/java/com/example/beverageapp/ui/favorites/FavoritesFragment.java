package com.example.beverageapp.ui.favorites;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beverageapp.database.DatabaseConnection;
import com.example.beverageapp.databinding.FragmentFavoritesBinding;
import com.example.beverageapp.feature.favorites.MyFavAdapter;
import com.example.beverageapp.feature.home.Drink;
import com.example.beverageapp.feature.home.MyContentAdapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FavoritesFragment extends Fragment {

private FragmentFavoritesBinding binding;
    DatabaseConnection databaseConnection;
    Connection con;
    private RecyclerView recyclerView;
    ArrayList<Drink> fav_drinks = new ArrayList<Drink>();
    private int cus_id;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        FavoritesViewModel favoritesViewModel =
                new ViewModelProvider(this).get(FavoritesViewModel.class);

    binding = FragmentFavoritesBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        final TextView textView = binding.textFavorites;
        favoritesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
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
            recyclerView = binding.favRec;
            MyFavAdapter myFavAdapter = new MyFavAdapter(getContext(), fav_drinks, con, cus_id);
            recyclerView.setAdapter(myFavAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }catch (Exception e)
        {
            Log.d("ben", e.getCause()+": "+e.getMessage());
        }
    }

    private void GetFavDrinksFromDB()
    {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(()-> {
            try {
                String query = "select d.drink_id, drink_image, drink_price, drink_name, drink_desc, rate_counter, type_id " +
                        "from Favorite_Drinks f_d inner join Drinks d " +
                        "on f_d.drink_id=d.drink_id and f_d.customer_id='"+cus_id+"';";
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
                    fav_drinks.add(drink);
                    Log.d("benim", id +": "+name);
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
                    fav_drinks.clear();
                    GetFavDrinksFromDB();
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