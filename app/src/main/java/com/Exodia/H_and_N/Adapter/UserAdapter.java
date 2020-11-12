package com.Exodia.H_and_N.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Exodia.H_and_N.Encheree;
import com.Exodia.H_and_N.Modifproduit;
import com.Exodia.H_and_N.R;
import com.Exodia.H_and_N.Retrofit.INodeJS;
import com.Exodia.H_and_N.Retrofit.RetrofitClient;
import com.Exodia.H_and_N.Room.MyDatabase;
import com.Exodia.H_and_N.entity.Produit;
import com.Exodia.H_and_N.entity.User;
import com.Exodia.H_and_N.produits;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    Retrofit retrofit = RetrofitClient.getInstance();
    INodeJS myAPI = retrofit.create(INodeJS.class);

    ///
    Context context;
    List<Produit> userList;

    public UserAdapter(Context context, List<Produit> userList) {
        this.context = context;
        this.userList = userList;
    }
    MyDatabase database = MyDatabase.getMyDatabase(context);
    User i = database.userDao().getAll();
    int id_user = i.getId();
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listuser,parent,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, final int position) {


      Picasso.get().load("http://10.0.2.2:3000/uploads/"+userList.get(position).getImage()).into(holder.image);
        final int id = userList.get(position).getId();
        holder.name.setText(String.valueOf(userList.get(position).getName()));
        holder.descrption.setText(String.valueOf(userList.get(position).getDescrption()));
        holder.cost.setText(String.valueOf(userList.get(position).getPrixdebut()));
        holder.modifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context , Modifproduit.class);
                i.putExtra("id_produit",id);
                context.startActivity(i);
            }
        });
        holder.supprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Delete(id);
            }
        });
        holder.aubid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AjouteraEnchere(id_user,id,userList.get(position).getPrixdebut());
                encour(1,id);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    private void Delete(int idproduit) {
        compositeDisposable.add(myAPI.deleteProduit(idproduit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Intent i = new Intent(context , produits.class);
                        context.startActivity(i);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.out.println(throwable);
                    }
                }));
    }
    private void AjouteraEnchere(int user_id , int id_produit , double cost) {
        compositeDisposable.add(myAPI.ajouterauenchere(user_id , id_produit,cost)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.out.println(throwable);
                    }
                }));
    }
    private void encour(int encour , int id_produit) {
        compositeDisposable.add(myAPI.encour(encour,id_produit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Intent i = new Intent(context , Encheree.class);
                        context.startActivity(i);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.out.println(throwable);
                    }
                }));
    }
}
