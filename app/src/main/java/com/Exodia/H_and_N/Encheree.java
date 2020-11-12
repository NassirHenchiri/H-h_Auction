package com.Exodia.H_and_N;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.Exodia.H_and_N.Adapter.enchereAdapter;
import com.Exodia.H_and_N.Retrofit.INodeJS;
import com.Exodia.H_and_N.Retrofit.RetrofitClient;
import com.Exodia.H_and_N.Room.MyDatabase;
import com.Exodia.H_and_N.entity.Produit;
import com.Exodia.H_and_N.entity.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class Encheree extends AppCompatActivity {

    INodeJS myAPI;
    RecyclerView Rproduit;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MyDatabase database ;
    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enchere);
        BottomNavigationView bottomNav = findViewById(R.id.navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);
        //
        database = MyDatabase.getMyDatabase(this);
        //
        Rproduit =findViewById(R.id.recycleproduit);
        Rproduit.setLayoutManager(new LinearLayoutManager(this));
        User i = database.userDao().getAll();

        fetchdata();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {

                        case R.id.exit:
                            openRetour();
                            break;

                    }

                    return true;
                }
            };

    private void openRetour() {
        Intent i1 = new Intent(this ,MainActivity.class);
        startActivity(i1);
    }


    private void fetchdata() {
        compositeDisposable.add((myAPI.getallproduit())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Produit>>() {
                               @Override
                               public void accept(List<Produit> users) throws Exception {

                                   displayData(users);


                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   System.out.println(throwable);
                               }
                           }
                ));
    }
    private  void displayData(List<Produit> users){
       enchereAdapter adapter =new enchereAdapter(this,users);
        Rproduit.setAdapter(adapter);


    }

}
