package com.Exodia.H_and_N.Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Exodia.H_and_N.R;
import com.Exodia.H_and_N.Retrofit.INodeJS;
import com.Exodia.H_and_N.Retrofit.RetrofitClient;
import com.Exodia.H_and_N.dialg;
import com.Exodia.H_and_N.entity.Produit;
import com.squareup.picasso.Picasso;
import com.Exodia.H_and_N.entity.enchere;


import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class enchereAdapter extends RecyclerView.Adapter<enchereViewHolder> {

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    Retrofit retrofit = RetrofitClient.getInstance();
    INodeJS myAPI = retrofit.create(INodeJS.class);
//
    Context context;
    List<Produit> userList;
    double currentcost;

    public enchereAdapter(Context context, List<Produit> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public enchereViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_enchere,parent,false);

        return new enchereViewHolder(view);
    }
    ///
    private void getcurrentcost(int id_produit) {
        compositeDisposable.add(myAPI.getcurrentbid(id_produit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<enchere>() {
                    @Override
                    public void accept(enchere s) throws Exception {
                        System.out.println("/////////////////////////");
                        System.out.println(""+s.toString());
                        System.out.println("/////////////////////////");
                        displayData(s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        // System.out.println(throwable);
                    }
                }));
    }

    private  void displayData(enchere users){
        currentcost = users.getCost();
        System.out.println("ba3ed display :"+currentcost);
    }

    @Override
    public void onBindViewHolder(@NonNull final enchereViewHolder holder, final int position) {
        getcurrentcost(userList.get(position).getId());


        Picasso.get().load("http://10.0.2.2:3000/uploads/"+userList.get(position).getImage()).fit().centerInside().into(holder.image);
        holder.name.setText(String.valueOf(userList.get(position).getName()));
        holder.descrption.setText(String.valueOf(userList.get(position).getDescrption()));
        holder.dateajout.setText(String.valueOf(userList.get(position).getTobid_at()));//
        holder.cost.setText(String.valueOf(userList.get(position).getPrixdebut()));
     holder.bid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context ,dialg.class);
                int a = userList.get(position).getId();
                double b = currentcost;
               i.putExtra("id_produit",a);
               i.putExtra("costbid",b);
                System.out.println("/////////////////////////");
                System.out.println(currentcost);
                System.out.println("/////////////////////////");
                context.startActivity(i);

            }
        });
     holder.aff.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             String s = String.valueOf(currentcost);
             holder.costb.setText(s);
         }
     });
    }


    @Override
    public int getItemCount() {
        return userList.size();
    }

}
