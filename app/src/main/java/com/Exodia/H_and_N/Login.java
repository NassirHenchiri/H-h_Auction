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

public class Login extends AppCompatActivity {
    Button connexion, inscription;
    private EditText email;
    private EditText password;
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
        database = MyDatabase.getMyDatabase(this);
        database.userDao().nukeTable();
        setContentView(R.layout.activity_login);
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);
        connexion = (Button) findViewById(R.id.connexion);
        inscription = (Button) findViewById(R.id.inscription);
        email = findViewById(R.id.emaillogin);
        password = findViewById(R.id.passwordlogin);
        connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (publishPost())
                {
                    loginUser(email.getText().toString(), password.getText().toString());

                }


            }
        });
        inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openInscription();
            }
        });
    }

    private void openInscription() {
        Intent ii = new Intent(this, Inscription.class);
        startActivity(ii);
    }


    private void loginUser(final String email, String password) {
        compositeDisposable.add(myAPI.LoginUser(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<User>() {
                    @Override
                    public void accept(User s) throws Exception {
                        if (s.getError() == null ) {

                            Toast.makeText(Login.this, "LOGIN SUCCESS", Toast.LENGTH_SHORT).show();
                           createsession(s);
                            System.out.println("////////////////////////////");
                            System.out.println(s.getError());
                            System.out.println("////////////////////////////");
                            Intent intent = new Intent(getApplicationContext(),
                                    MainActivity.class);
                            startActivity(intent);
                        } else
                        {

                            Toast.makeText(Login.this, ""+s.getError() , Toast.LENGTH_SHORT).show();//show error

                        }



                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.out.println(throwable);
                    }
                })

        );

    }
    private boolean publishPost() {
        final String itemName = email.getText().toString().trim();
        final String itemDesc = password.getText().toString().trim();
        if(!itemName.isEmpty() && !itemDesc.isEmpty()) {
           /* if(isEmailValid(itemName))
            {
                return  true;

            }
            else{
                Toast.makeText(getApplicationContext(), "Invalid Email... Please Try Again.", Toast.LENGTH_SHORT).show();
                return false;
            }*/
           return  true;

        } else {
            Toast.makeText(getApplicationContext(), "EMPTY Field... Please Try Again.", Toast.LENGTH_SHORT).show();
            return false;
        }

    }
    private void createsession(User user){

        User i = new User(user.getId(),user.getEmail());
    database.userDao().insertOne(i);


    }
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
