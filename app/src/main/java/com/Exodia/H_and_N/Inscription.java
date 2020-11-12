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

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class Inscription extends AppCompatActivity {
    INodeJS myAPI;
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

    Button confirmer;
    private EditText username;
    private EditText Email;
    private EditText password;
    EditText pverif ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        setContentView(R.layout.activity_inscription);
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);
        username = findViewById(R.id.username);
        Email = findViewById(R.id.email);
        password = findViewById(R.id.passwordC);
        pverif =findViewById(R.id.passwordI);
        confirmer = findViewById(R.id.enregister);
        confirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(publishPost())
                {
                    RegisterUser(Email.getText().toString(), username.getText().toString(), password.getText().toString());

                }

            }
        });
    }

    private void RegisterUser(String email, String name, String password) {
        compositeDisposable.add(myAPI.registerUser(email, name, password).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if(s.contains("Register Successful"))
                        {
                            Toast.makeText(Inscription.this, ""+s , Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),
                                    Login.class);
                            startActivity(intent);


                        }
                        else{
                            Toast.makeText(Inscription.this, "" + s, Toast.LENGTH_SHORT).show();//show error
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.out.println(throwable);
                    }
                }
                )
        );
    }
    private boolean publishPost() {
        final String itemName = username.getText().toString().trim();
        final String itemEmail= Email.getText().toString().trim();
        final String itempverif= pverif.getText().toString().trim();
        final String itemPass = password.getText().toString().trim();
        if(!itemName.isEmpty() && !itemPass.isEmpty()&& !itemEmail.isEmpty()&& !itempverif.isEmpty() ) {
                                if(itemPass.equals(itempverif))
                                {
                                    if(isEmailValid(itemEmail))
                                    {
                                        return  true;

                                    }
                                            else
                                    {
                                        Toast.makeText(getApplicationContext(), "Invalid Email... Please Try Again.", Toast.LENGTH_SHORT).show();
                                        return  false;
                                    }
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(), "Password not matched... Please Try Again.", Toast.LENGTH_SHORT).show();
                                    return  false;
                                }
        } else {
            Toast.makeText(getApplicationContext(), "EMPTY Fields... Please Try Again.", Toast.LENGTH_SHORT).show();
            return false;
        }

    }
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


}
