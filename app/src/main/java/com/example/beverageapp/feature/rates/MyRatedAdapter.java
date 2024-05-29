package com.example.beverageapp.feature.rates;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beverageapp.R;
import com.example.beverageapp.feature.home.Drink;
import com.squareup.picasso.Picasso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class MyRatedAdapter extends RecyclerView.Adapter<MyRatedAdapter.MyViewHolder> {

    Context context;
    ArrayList<Drink> rated_drinks;
    Connection con;
    int cus_id;

    public MyRatedAdapter(Context context, ArrayList<Drink> rated_drinks, Connection con, int cus_id)
    {
        this.context = context;
        this.rated_drinks = rated_drinks;
        this.con = con;
        this.cus_id = cus_id;
    }

    @NonNull
    @Override
    public MyRatedAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.rates_raw_item, parent, false);
        return new MyRatedAdapter.MyViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull MyRatedAdapter.MyViewHolder holder, int position) {
        Picasso.get().load(rated_drinks.get(position).getImage()).
                resize(80,80).into(holder.image);
        holder.name.setText(rated_drinks.get(position).getName());
        SetRateValue(rated_drinks.get(position).getId(), holder, position);


        holder.rate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateRateValue(rated_drinks.get(position).getId(), 1);
            }
        });
        holder.rate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateRateValue(rated_drinks.get(position).getId(), 2);
            }
        });
        holder.rate3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateRateValue(rated_drinks.get(position).getId(), 3);
            }
        });
        holder.rate4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateRateValue(rated_drinks.get(position).getId(), 4);
            }
        });
        holder.rate5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateRateValue(rated_drinks.get(position).getId(), 5);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteRateValue(rated_drinks.get(position).getId());
            }
        });

    }

    private void DeleteRateValue(int drink_id)
    {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(()-> {
            try {
                String query = "delete from Drink_Rates where drink_id='" +
                        drink_id+"' and customer_id='"+cus_id+"';";
                PreparedStatement preparedStatement = con.prepareStatement(query);
                preparedStatement.executeUpdate();
            }catch (SQLException sqlException)
            {
                Log.d("ben", sqlException.getCause()+": "+sqlException.getMessage());
            }

        });
    }

    private void UpdateRateValue(int drink_id, int new_rate_value)
    {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(()-> {
            try {
                String query = "update Drink_Rates set rate_value='"+new_rate_value+"' where drink_id='" +
                        drink_id+"' and customer_id='"+cus_id+"';";
                PreparedStatement preparedStatement = con.prepareStatement(query);
                preparedStatement.executeUpdate();
            }catch (SQLException sqlException)
            {
                Log.d("ben", sqlException.getCause()+": "+sqlException.getMessage());
            }

        });
    }

    private void SetRateUI(MyRatedAdapter.MyViewHolder holder, int position, int rate_value)
    {
        Bitmap full_icon = BitmapFactory.decodeResource(context.getResources(), R.drawable._828884_2_2);
        switch (rate_value)
        {
            case 1:
                holder.rate1.setImageBitmap(full_icon);
                break;
            case 2:
                holder.rate1.setImageBitmap(full_icon);
                holder.rate2.setImageBitmap(full_icon);
                break;
            case 3:
                holder.rate1.setImageBitmap(full_icon);
                holder.rate2.setImageBitmap(full_icon);
                holder.rate3.setImageBitmap(full_icon);
                break;
            case 4:
                holder.rate1.setImageBitmap(full_icon);
                holder.rate2.setImageBitmap(full_icon);
                holder.rate3.setImageBitmap(full_icon);
                holder.rate4.setImageBitmap(full_icon);
                break;
            case 5:
                holder.rate1.setImageBitmap(full_icon);
                holder.rate2.setImageBitmap(full_icon);
                holder.rate3.setImageBitmap(full_icon);
                holder.rate4.setImageBitmap(full_icon);
                holder.rate5.setImageBitmap(full_icon);
                break;
        }
    }

    private void SetRateValue(int drink_id, MyRatedAdapter.MyViewHolder holder, int position)
    {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(()-> {
            try {
                String query = "select rate_value from Drink_Rates where drink_id='" +
                        drink_id+"' and customer_id='"+cus_id+"';";
                PreparedStatement preparedStatement = con.prepareStatement(query);
                ResultSet resultset = preparedStatement.executeQuery();

                while(resultset.next())
                {
                    SetRateUI(holder, position, resultset.getInt(1));
                }
            }catch (SQLException sqlException)
            {
                Log.d("ben", sqlException.getCause()+": "+sqlException.getMessage());
            }

        });
    }

    @Override
    public int getItemCount() {
        return rated_drinks.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView image;
        TextView name;
        ImageButton rate1;
        ImageButton rate2;
        ImageButton rate3;
        ImageButton rate4;
        ImageButton rate5;
        ImageButton delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.imageView2);
            name = (TextView) itemView.findViewById(R.id.textView);
            rate1 = (ImageButton) itemView.findViewById(R.id.rate1);
            rate2 = (ImageButton) itemView.findViewById(R.id.rate2);
            rate3 = (ImageButton) itemView.findViewById(R.id.rate3);
            rate4 = (ImageButton) itemView.findViewById(R.id.rate4);
            rate5 = (ImageButton) itemView.findViewById(R.id.rate5);
            delete = (ImageButton) itemView.findViewById(R.id.delete_rate);
        }
    }

}
