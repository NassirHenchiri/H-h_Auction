package com.Exodia.H_and_N;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.Exodia.H_and_N.Retrofit.INodeJS;
import com.Exodia.H_and_N.Retrofit.RetrofitClient;
import com.Exodia.H_and_N.Room.MyDatabase;
import com.Exodia.H_and_N.entity.Produit;
import com.Exodia.H_and_N.entity.User;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

public class utilisateur extends AppCompatActivity {

Button back , confimer;
    String img_vraite;
    INodeJS myAPI;
    private Bitmap bitmap  ;
    EditText nom;
    EditText email;
    EditText telephone;
    EditText addresse;
    private ImageButton imageBn;
    int id;
    private  static  final  int IMG_REQUEST = 777;
    private MyDatabase database ;
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
        setContentView(R.layout.activity_utilisateur);
        Intent i = getIntent();
        //
        confimer = findViewById(R.id.confimer);
        back =findViewById(R.id.retour);
        database = MyDatabase.getMyDatabase(this);
        //
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);
        //
        nom = findViewById(R.id.nameprofile);
        email = findViewById(R.id.emailprofile);
        telephone = findViewById(R.id.telephoneprofile);
        addresse = findViewById(R.id.addresseprofile);
        //imageBn = findViewById(R.id.image_bnprofile);
        User u = database.userDao().getAll();
        id = u.getId();
        fetchdata(u.getEmail());
        confimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(publishPost())
                {
                   /* if(bitmap == null)
                    {
                        updateuser(nom.getText().toString(), email.getText().toString(),img_vraite, telephone.getText().toString(), addresse.getText().toString(), id);
                    }
                    else{
                        postimage(bitmap);

                    }*/
                    //  postimage(bitmap);
                    updateuser(nom.getText().toString(), email.getText().toString(),"noimage", telephone.getText().toString(), addresse.getText().toString(), id);
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retour();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data!=null ) {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                imageBn.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();


/*        imageBn.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                                           galleryIntent.setType("image/*");
                                           startActivityForResult(galleryIntent, IMG_REQUEST);
                                       }
                                   }
        );*/


    }
//

//
/*
private void postimage(Bitmap b) {
        try {
            Toast.makeText(utilisateur.this, "in post", Toast.LENGTH_SHORT).show();

            File filesDir = getApplicationContext().getFilesDir();
            File file = new File(filesDir, "image" + ".png");

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            //  b.compress(Bitmap.CompressFormat.PNG, 0, bos);
            byte[] bitmapdata = bos.toByteArray();


            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();


            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), reqFile);
            final RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "image");
            compositeDisposable.add(myAPI.uploadImage(body, name)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Produit>() {
                        @Override
                        public void accept(Produit s) throws Exception {
                            if (s != null) {
                                updateuser(nom.getText().toString(), email.getText().toString(), s.getImage(), telephone.getText().toString(), addresse.getText().toString(), id);
                                Toast.makeText(utilisateur.this, "" + s.getImage(), Toast.LENGTH_SHORT).show();//show error
                            }

                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            System.out.println(throwable);
                        }
                    })

            );


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/
//

    private  void updateuser(String name,String email,String image,String telephone,String addresse,int id) {
        compositeDisposable.add(myAPI.updateUser(name,email, image,telephone,addresse,id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {

                            Toast.makeText(utilisateur.this, ""+s, Toast.LENGTH_SHORT).show();//show error

                    }
                })
        );
    }


    private void fetchdata(String email) {
        compositeDisposable.add((myAPI.getoneUser(email))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<User>() {
                    @Override
                    public void accept(User user) throws Exception {
                        displayData(user);
                        System.out.println("////////////////////////");
                        System.out.println(user.toString());
                        System.out.println("////////////////////////");

                    }
                }));
    }
    private void displayData(User user){

        nom.setText(user.getName());
        email.setText(user.getEmail());
        img_vraite = user.getImage();

     //   Picasso.get().load("http://10.0.2.2:3000/uploads/" +user.getImage()).into(imageBn);

        if (user.getTelephone()== null)
        {
            telephone.setHint("Ajouter Votre Numero telephone");
        }
        else
        {
            telephone.setText(user.getTelephone());
        }

        if (user.getAddresse()== null)
        {
            addresse.setHint("Ajouter  Addresse");
        }
        else
        {
            addresse.setText(user.getAddresse());
        }

    }


    private void retour() {
        Intent intent = new Intent(this , MainActivity.class);
        startActivity(intent);
    }


    private boolean publishPost() {
        final String itemName = nom.getText().toString().trim();
        final String itemEmail= email.getText().toString().trim();
        final String itempTelph= telephone.getText().toString().trim();
        final String itemAdress = addresse.getText().toString().trim();
        if(!itemName.isEmpty() && !itempTelph.isEmpty()&& !itemEmail.isEmpty()&& !itemAdress.isEmpty() ) {
            if(isEmailValid(itemEmail))
                {
                    if(itempTelph.length() == 8)
                    {
                        return  true;

                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Invalid Numero TELEPHONE... Please Try Again.", Toast.LENGTH_SHORT).show();
                        return  false;
                    }

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Invalid Email... Please Try Again.", Toast.LENGTH_SHORT).show();
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
