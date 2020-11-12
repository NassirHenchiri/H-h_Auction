package com.Exodia.H_and_N;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.Exodia.H_and_N.Adapter.Adapterr;
import com.Exodia.H_and_N.Retrofit.INodeJS;
import com.Exodia.H_and_N.Retrofit.RetrofitClient;
import com.Exodia.H_and_N.Room.MyDatabase;
import com.Exodia.H_and_N.entity.Model;
import com.Exodia.H_and_N.entity.User;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity  {
    INodeJS myAPI;
    ViewPager viewPager;
    Adapterr adapter;
    List<Model> models;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private MyDatabase database ;
    private ImageView imageBn;
    private TextView nameuser;
    private ViewFlipper viewFlipper;
    MediaPlayer mediaPlayer;
    private  static  final  int IMG_REQUEST = 777;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

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
        setContentView(R.layout.activity_main);

        //
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);
        //
        models = new ArrayList<>();
        models.add(new Model(R.drawable.kkkk, "Jeux vidéos", "Meilleur tendences aux H-h auctions"));
        models.add(new Model(R.drawable.kkkkk, "Vetements", "Meilleur tendences aux H-h auctions"));
        models.add(new Model(R.drawable.hhhh, "Furnitures", "Meilleur tendences aux H-h auctions"));
        models.add(new Model(R.drawable.kkkkkk, "Véhicules", "Meilleur tendences aux H-h auctions"));

        adapter = new Adapterr(models, this);

        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(130, 0, 130, 0);

        Integer[] colors_temp = {
                getResources().getColor(R.color.darkTextColor),
                getResources().getColor(R.color.darkTextColor),
                getResources().getColor(R.color.darkTextColor),
                getResources().getColor(R.color.darkTextColor)
        };


        colors = colors_temp;

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (position < (adapter.getCount() -1) && position < (colors.length - 1)) {
                    viewPager.setBackgroundColor(

                            (Integer) argbEvaluator.evaluate(
                                    positionOffset,
                                    colors[position],
                                    colors[position + 1]
                            )
                    );
                }

                else {
                    viewPager.setBackgroundColor(colors[colors.length - 1]);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.closer);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);
        viewFlipper = findViewById(R.id.view_flipper);

        viewFlipper.setFlipInterval(2000);
        viewFlipper.startFlipping();

        dl = (DrawerLayout)findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);
        database = MyDatabase.getMyDatabase(this);
        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imageBn = findViewById(R.id.profile_image);
        nameuser = findViewById(R.id.nameusseracceuil);
        nv = (NavigationView)findViewById(R.id.nv);
        User i =database.userDao().getAll();
        fetchdata(i.getEmail());
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.profil:


                        openUtilisateur();
                        Toast.makeText(MainActivity.this, "profil",Toast.LENGTH_SHORT).show();break;
                    case R.id.produits:

                        openProduits();
                        Toast.makeText(MainActivity.this, "produits",Toast.LENGTH_SHORT).show();break;
                    case R.id.payer:

                        openPaa();
                        Toast.makeText(MainActivity.this, "paiements",Toast.LENGTH_SHORT).show();break;

                    case R.id.enchere:
                        openBid();
                        Toast.makeText(MainActivity.this, "ventes aux enchéres",Toast.LENGTH_SHORT).show();break;

                    case R.id.lougout:
                        openLougout();
                        break;
                    default :
                        return true ;
                }


                return true;

            }
        });


    }

    private void openPaa() {
        Intent iiiiii = new Intent(this , bid.class);
        startActivity(iiiiii);
    }

    private void openLougout() {
        database.userDao().nukeTable();
        Intent intent = new Intent(getApplicationContext(),
                Login.class);
        startActivity(intent);
    }



    private void openBid() {
        Intent iiii = new Intent(this , Encheree.class);
        startActivity(iiii);
    }

    private void openAchats() {
        Intent iii =new Intent(this ,achat.class);
        startActivity(iii);
    }

    private void openProduits() {
        Intent ii = new Intent (this ,produits.class);
        startActivity(ii);
    }

    private void openUtilisateur() {
        Intent i = new Intent (this ,utilisateur.class);
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    private void fetchdata(String email) {
        compositeDisposable.add((myAPI.getoneUser(email))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<User>() {
                    @Override
                    public void accept(User user) throws Exception {
                        displayData(user);
                    }
                }));
    }
    private void displayData(User user){
        nameuser.setText(user.getName());
        Picasso.get().load("http://10.0.2.2:3000/uploads/"+user.getImage()).into(imageBn);



    }
}
