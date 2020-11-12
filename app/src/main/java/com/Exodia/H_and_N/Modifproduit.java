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
import android.widget.TextView;
import android.widget.Toast;

import com.Exodia.H_and_N.Retrofit.INodeJS;
import com.Exodia.H_and_N.Retrofit.RetrofitClient;
import com.Exodia.H_and_N.entity.Produit;
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

public class Modifproduit extends AppCompatActivity {

    private ImageButton imageBn;
    private EditText Name;
    private EditText Desc;
    private EditText editBaseBid;
    private Button submitBn;
    private Bitmap bitmap ;
    int id_produit;
    private TextView encour;
    int id_produit_vraite;
    String imag_vraite;
    double number = 0;
    private  static  final  int IMG_REQUEST = 777;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifproduit);
        Intent i = getIntent();
         id_produit = i.getIntExtra("id_produit",0);
        Toast.makeText(Modifproduit.this, ""+id_produit , Toast.LENGTH_SHORT).show();//show error
      //
    //
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(INodeJS.class);
        //


        imageBn = findViewById(R.id.image_bn_modifier);
        Name = findViewById(R.id.Namepmodifier);
        Desc = findViewById(R.id.descriptionmodiier);
        editBaseBid = findViewById(R.id.prixdebutmodifier);
        submitBn = findViewById(R.id.submit_modifier);
        encour = findViewById(R.id.encourmodifer);

        //
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchdata(id_produit);//start your method from here
        imageBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, IMG_REQUEST);
            }
        });
        submitBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = Double.valueOf(editBaseBid.getText().toString());
                if(publishPost())
                {
                    if(bitmap == null)
                    {
                        updateProduit(imag_vraite,Name.getText().toString(), Desc.getText().toString() ,number, id_produit_vraite);

                    }
                    else{
                        postimage(bitmap);

                    }


                }
            }
        });

    }


    //
    private boolean publishPost() {
        final String itemName = Name.getText().toString().trim();
        final String itemDesc = Desc.getText().toString().trim();
        final String baseBid = editBaseBid.getText().toString().trim();

        if (!itemName.isEmpty() && !itemDesc.isEmpty() && !baseBid.isEmpty() ) {
            return true;
        } else {
            Toast.makeText(getApplicationContext(), "Empty Field... Please Try Again.", Toast.LENGTH_SHORT).show();
            return false;
        }

    }//

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data!=null) {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                imageBn.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }

    private void postimage(Bitmap b)
    {
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
            final RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "image");
            compositeDisposable.add(myAPI.uploadImage(body,name)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Produit>() {
                        @Override
                        public void accept(Produit s) throws Exception {
                            if(s !=null)
                            {
                                updateProduit(s.getImage(),Name.getText().toString(), Desc.getText().toString() ,number,id_produit_vraite);
                                Toast.makeText(Modifproduit.this, ""+s.getImage() , Toast.LENGTH_SHORT).show();//show error
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

    private void fetchdata(int id_produit) {
        compositeDisposable.add((myAPI.getonepoduit(id_produit))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Produit>() {
                    @Override
                    public void accept(Produit user) throws Exception {
                        displayData(user);
                     //   Toast.makeText(Modifproduit.this, "" + user.toString(), Toast.LENGTH_SHORT).show();//show error

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.out.println(throwable);
                    }
                }));
    }
    private void displayData(Produit user) {
          id_produit_vraite = user.getId();
        Name.setText(user.getName());
        Desc.setText(user.getDescrption());
        String b = String.valueOf(user.getPrixdebut());
        editBaseBid.setText(b);
        imag_vraite = user.getImage();
        if(bitmap == null)
        {
            Picasso.get().load("http://10.0.2.2:3000/uploads/" +imag_vraite).into(imageBn);
        }
        else
        {
            imageBn.setImageBitmap(bitmap);

        }
/////
        if(user.getEncour() == 0)
        {
            encour.setText(" n'est au enchere");
        }
        else if(user.getEncour() == 1)
        {
                 encour.setText("Encour de Bid");
        }
        else if(user.getEncour() == 3)
        {
            encour.setText("a ete achete");
        }


    }
    private void updateProduit(String image, String name, String descrption, double prixdebut, int user_id) {
        compositeDisposable.add(myAPI.updateProduit(image, name, descrption, prixdebut, user_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Toast.makeText(Modifproduit.this, "" + s, Toast.LENGTH_SHORT).show();//show error
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
}
