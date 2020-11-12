package com.Exodia.H_and_N;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.net.Uri;

import com.Exodia.H_and_N.Retrofit.INodeJS;
import com.Exodia.H_and_N.Retrofit.RetrofitClient;
import com.Exodia.H_and_N.Room.MyDatabase;
import com.Exodia.H_and_N.entity.Produit;
import com.Exodia.H_and_N.entity.User;

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

public class Ajoutproduit extends AppCompatActivity {


    private ImageButton imageBn;
    private EditText Name;
    private EditText Desc;
    private EditText editBaseBid;
    private Button submitBn;
    private Bitmap bitmap;
    double number = 0;
    private static final int IMG_REQUEST = 777;
    INodeJS myAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MyDatabase database;
    int id;

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
        //
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);
        //
        setContentView(R.layout.activity_ajoutproduit);
        imageBn = findViewById(R.id.image_bn);
        Name = findViewById(R.id.Namep);
        Desc = findViewById(R.id.description);
        editBaseBid = findViewById(R.id.prixdebut);
        submitBn = findViewById(R.id.submit_bn);
        database = MyDatabase.getMyDatabase(this);
        //Picasso.get().load("http://10.0.2.2:3000/uploads/5b1b2108d38e8ced8c90dad2aee1ddeb.png").into(imageBn);


        imageBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, IMG_REQUEST);

            }
        });


        submitBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (publishPost()) {
                    String str = editBaseBid.getText().toString();
                    User i = database.userDao().getAll();
                    id = i.getId();
                    System.out.println("//////////////");
                    System.out.println(i.toString());
                    System.out.println("//////////////");
                    System.out.println(id);
                    System.out.println("//////////////");
                    //System.out.println("" + mImageUri.getPath());
                    System.out.println("//////////////");
                    System.out.println(Name.getText().toString());
                    System.out.println("//////////////");
                    System.out.println(Desc.getText().toString());
                    System.out.println("//////////////");
                    number = Double.valueOf(editBaseBid.getText().toString());
                    System.out.println(number);
                    System.out.println("//////////////");

                    if(bitmap == null)
                    {
                        Toast.makeText(Ajoutproduit.this, "ajouter l'image" , Toast.LENGTH_SHORT).show();//show error

                    }
                    else{
                            postimage(bitmap);

                    }


                }


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                imageBn.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    private void RegisterProduit(String image, String name, String descrption, double prixdebut, int user_id) {
        compositeDisposable.add(myAPI.registreproduit(image, name, descrption, prixdebut, user_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Toast.makeText(Ajoutproduit.this, "" + s, Toast.LENGTH_SHORT).show();//show error
                        Intent intent = new Intent(getApplicationContext(),
                                produits.class);
                        startActivity(intent);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.out.println(throwable);
                    }
                }));
    }

    //
    public void postimage(Bitmap b) {
        if (b == null) {
            RegisterProduit("ajouter.png", Name.getText().toString(), Desc.getText().toString(), number, id);

        } else {

            try {
                File filesDir = getApplicationContext().getFilesDir();
                File file = new File(filesDir, "image" + ".png");

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                b.compress(Bitmap.CompressFormat.PNG, 0, bos);
                byte[] bitmapdata = bos.toByteArray();


                FileOutputStream fos = new FileOutputStream(file);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();


                RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), reqFile);
                RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "image");
                compositeDisposable.add(myAPI.uploadImage(body, name)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Produit>() {
                            @Override
                            public void accept(Produit s) throws Exception {
                                if (s != null) {
                                    Toast.makeText(Ajoutproduit.this, "" + s.getImage(), Toast.LENGTH_SHORT).show();//show error
                                    RegisterProduit(s.getImage(), Name.getText().toString(), Desc.getText().toString(), number, id);
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
    }
        //
        private boolean publishPost () {
            final String itemName = Name.getText().toString().trim();
            final String itemDesc = Desc.getText().toString().trim();
            final String baseBid = editBaseBid.getText().toString().trim();

            if (!itemName.isEmpty() && !itemDesc.isEmpty() && !baseBid.isEmpty()) {
                return true;
            } else {
                Toast.makeText(getApplicationContext(), "Empty Field... Please Try Again.", Toast.LENGTH_SHORT).show();
                return false;
            }

        }//
    }

