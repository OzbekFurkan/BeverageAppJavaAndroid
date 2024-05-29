package com.example.beverageapp.feature.home;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beverageapp.R;
import com.example.beverageapp.database.DatabaseConnection;
import com.squareup.picasso.Picasso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyContentAdapter extends RecyclerView.Adapter<MyContentAdapter.MyViewHolder> {

    Context context;
    ArrayList<Drink> drinks;
    Connection con;
    int cus_id;
    Dialog rate_dialog;

    public MyContentAdapter(Context context, ArrayList<Drink> drinks, Connection con, int cus_id)
    {
        this.context = context;
        this.drinks = drinks;
        this.con = con;
        this.cus_id = cus_id;
    }

    @NonNull
    @Override
    public MyContentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.home_content_item, parent, false);
        return new MyContentAdapter.MyViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull MyContentAdapter.MyViewHolder holder, int position) {
        Picasso.get().load(drinks.get(position).getImage()).resize(280,200).into(holder.image);
        holder.name.setText(drinks.get(position).getName());
        holder.desc.setText(drinks.get(position).getDesc());
        GetAvgRate(holder, position);
        holder.b_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDrinkToFav(position);
            }
        });
        holder.b_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rate_dialog = new Dialog(context);
                rate_dialog.setContentView(R.layout.rate_content_popup);
                rate_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                ImageButton rb1 = (ImageButton) rate_dialog.findViewById(R.id.rb1);
                ImageButton rb2 = (ImageButton) rate_dialog.findViewById(R.id.rb2);
                ImageButton rb3 = (ImageButton) rate_dialog.findViewById(R.id.rb3);
                ImageButton rb4 = (ImageButton) rate_dialog.findViewById(R.id.rb4);
                ImageButton rb5 = (ImageButton) rate_dialog.findViewById(R.id.rb5);
                ImageButton close = (ImageButton) rate_dialog.findViewById(R.id.close_popup);
                rb1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RateDrink(position, 1);
                        rate_dialog.dismiss();
                    }
                });
                rb2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RateDrink(position, 2);
                        rate_dialog.dismiss();
                    }
                });
                rb3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RateDrink(position, 3);
                        rate_dialog.dismiss();
                    }
                });
                rb4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RateDrink(position, 4);
                        rate_dialog.dismiss();
                    }
                });
                rb5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RateDrink(position, 5);
                        rate_dialog.dismiss();
                    }
                });
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rate_dialog.dismiss();
                    }
                });
                rate_dialog.show();
            }
        });

    }

    private void SetRateTextUI(MyContentAdapter.MyViewHolder holder, int position, float avg_rate)
    {
        holder.r_c.setText(String.valueOf(avg_rate)+" ("+
                String.valueOf(drinks.get(position).getRateCounter())+")");
    }

    private void GetAvgRate(MyContentAdapter.MyViewHolder holder, int position)
    {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(()-> {
            int sum=0;
            int rate_counter=0;
            try {
                String query = "select d.drink_id, sum(d_r.rate_value) " +
                        "from Drink_Rates d_r inner join Drinks d " +
                        "on d_r.drink_id=d.drink_id and d_r.drink_id='"+drinks.get(position).getId()+"' " +
                        "group by d.drink_id;";
                PreparedStatement preparedStatement = con.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next())
                {
                    sum=resultSet.getInt(2);
                }

                String r_c_query = "select rate_counter " +
                        "from Drinks " +
                        "where drink_id='"+drinks.get(position).getId()+"';";
                PreparedStatement ps = con.prepareStatement(r_c_query);
                ResultSet rs = ps.executeQuery();
                if(rs.next())
                {
                    rate_counter=rs.getInt(1);
                }
            }catch (SQLException sqlException)
            {
                Log.d("ben", sqlException.getCause()+": "+sqlException.getMessage());
            }
            float finalSum = sum;
            float finalRate_counter = rate_counter;
            ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.d("ben", "sum: "+finalSum+" counter: "+finalRate_counter);
                        SetRateTextUI(holder, position, finalSum / finalRate_counter);
                    }catch (ArithmeticException arithmeticException)
                    {
                        SetRateTextUI(holder, position, 0);
                    }
                }
            });
        });
    }

    private void RateDrink(int pos, int rate_value)
    {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(()-> {
            try {
                String query = "insert into Drink_Rates (rate_value, customer_id, drink_id) values('" +
                        rate_value+"','"+cus_id+"','"+drinks.get(pos).getId()+"');";
                PreparedStatement preparedStatement = con.prepareStatement(query);
                preparedStatement.executeUpdate();
                String inc_rate_counter = "update Drinks set rate_counter=rate_counter+1 " +
                        "where drink_id='"+drinks.get(pos).getId()+"';";
                PreparedStatement ps = con.prepareStatement(inc_rate_counter);
                ps.executeUpdate();
            }catch (SQLException sqlException)
            {
                Log.d("ben", sqlException.getCause()+": "+sqlException.getMessage());
            }
        });
    }

    private void AddDrinkToFav(int pos)
    {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(()-> {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String strDate = sdf.format(new Date());
                Log.d("ben", strDate);
                Log.d("ben", String.valueOf(cus_id));
                String query = "insert into Favorite_Drinks (record_date, customer_id, drink_id) values('" +
                         strDate+"','"+cus_id+"','"+drinks.get(pos).getId()+"');";
                PreparedStatement preparedStatement = con.prepareStatement(query);
                preparedStatement.executeUpdate();
            }catch (SQLException sqlException)
            {
                Log.d("ben", sqlException.getCause()+": "+sqlException.getMessage());
            }
        });
    }

    @Override
    public int getItemCount() {
        return drinks.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView name;
        TextView desc;
        TextView r_c;
        ImageButton b_fav;
        ImageButton b_rate;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.imageView);
            name = (TextView) itemView.findViewById(R.id.textDrinkName);
            desc = (TextView) itemView.findViewById(R.id.textDrinkDesc);
            r_c = (TextView) itemView.findViewById(R.id.textView5);
            b_fav = (ImageButton) itemView.findViewById(R.id.imageButtonFav);
            b_rate = (ImageButton) itemView.findViewById(R.id.imageButtonRate);
        }
    }

}
