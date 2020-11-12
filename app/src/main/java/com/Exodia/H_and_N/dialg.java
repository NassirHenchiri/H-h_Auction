package com.Exodia.H_and_N;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.Exodia.H_and_N.Retrofit.INodeJS;
import com.Exodia.H_and_N.Retrofit.RetrofitClient;
import com.Exodia.H_and_N.Room.MyDatabase;
import com.Exodia.H_and_N.entity.User;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class dialg extends AppCompatActivity {
    Button rt;
    int id_produit;
    EditText bidvalues;
    double currentcost;
    INodeJS myAPI;
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
        setContentView(R.layout.activity_dialg);
        rt=findViewById(R.id.btnretourrr);


        //
        database = MyDatabase.getMyDatabase(this);
        //
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);
        //
       Intent i = getIntent();
        id_produit = i.getIntExtra("id_produit",0);
        currentcost = i.getDoubleExtra("costbid",1);
        Toast.makeText(dialg.this, ""+id_produit , Toast.LENGTH_SHORT).show();
        Toast.makeText(dialg.this, ""+currentcost , Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onResume() {
        super.onResume();
        bidvalues = findViewById(R.id.editEnterBid);
        String a = String.valueOf(currentcost);
        bidvalues.setText(a);
        rt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (publishPost())
                {

                    User i= database.userDao().getAll();
                    final int id_user = i.getId();
                    final   double number = Double.valueOf(bidvalues.getText().toString());
                    bidProduit(id_user,number,id_produit);

                }
            }
        });
    }

    private void bidProduit(int id_user_achete ,double cost,int id_produit) {
        compositeDisposable.add(myAPI.bid(id_user_achete,cost,id_produit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Intent intent = new Intent(getApplicationContext(),
                                Encheree.class);
                        startActivity(intent);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.out.println(throwable);
                    }
                }));
    }

    private void openRetour() {
        Intent i = new Intent(this , Encheree.class);
        startActivity(i);
    }
    private boolean publishPost() {
        final String itemName = bidvalues.getText().toString().trim();
        if(!itemName.isEmpty()) {
            return  true;
        } else {
            Toast.makeText(getApplicationContext(), "Error... Please Try Again.", Toast.LENGTH_SHORT).show();
            return false;
        }

    }
}
