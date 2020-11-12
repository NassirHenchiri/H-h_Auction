package com.Exodia.H_and_N.Retrofit;

import com.Exodia.H_and_N.entity.Produit;
import com.Exodia.H_and_N.entity.User;
import com.Exodia.H_and_N.entity.enchere;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface INodeJS {
    @POST("register")
    @FormUrlEncoded
    Observable<String> registerUser(@Field("email") String email,
                                    @Field("name") String name,
                                    @Field("password") String password);

    @POST("login")
    @FormUrlEncoded
    Observable<User> LoginUser(@Field("email") String email,
                               @Field("password") String password);
    @GET("afficher")
    Observable<List<User>> getUser();
    @GET("afficheralllProduit")
    Observable<List<Produit>> getallproduit();

    @POST("afficherallProduit")
    @FormUrlEncoded
    Observable<List<Produit>> getProduit(@Field("user_id")int user_id);

    @POST("afficherparemail")
    @FormUrlEncoded
    Observable<User> getoneUser(@Field("email") String email);

    @POST("getoneproduit")
    @FormUrlEncoded
    Observable<Produit> getonepoduit(@Field("id")int id);

    @POST("update")
    @FormUrlEncoded
    Observable<String> updateUser(@Field("name") String name,
                                  @Field("email") String email,
                                  @Field("image") String image,
                                  @Field("telephone") String telephone,
                                  @Field("addresse") String addresse,
                                  @Field("id") int id);
     @POST("RegisterProduit")
     @FormUrlEncoded
     Observable<String> registreproduit(@Field("image") String image,
                                         @Field("name") String name,
                                         @Field("description")String descrption,
                                         @Field("prixdebut")double prixdebut,
                                         @Field("user_id")int id_user);
    @POST("updateProduit")
    @FormUrlEncoded
    Observable<String> updateProduit(@Field("image") String image,
                                       @Field("name") String name,
                                       @Field("description")String descrption,
                                       @Field("prixdebut")double prixdebut,
                                       @Field("id")int id);
    @POST("bid")
    @FormUrlEncoded
    Observable<String> bid(@Field("id_user_achete")int id_user,@Field("cost")double cost_final,@Field("id_produit")int id_produit);

    @POST("ajouteraubid")
    @FormUrlEncoded
    Observable<String> ajouterauenchere(@Field("id_user_achete")int id_user,
                                        @Field("id_produit")int id_produit,
                                        @Field("cost")double cost);

    @POST("deleteProduit")
    @FormUrlEncoded
    Observable<String> deleteProduit(@Field("id")int id_produit);

    @POST("updatencour")
    @FormUrlEncoded
    Observable<String> encour(@Field("encour")int encour,@Field("id")int id_produit);
    //
    @Multipart
    @POST("/upload")
    Observable<Produit> uploadImage(@Part MultipartBody.Part image, @Part("image") RequestBody name);
    //
    @POST("Selectlastbid")
    @FormUrlEncoded
    Observable<enchere> getcurrentbid(@Field("id_produit")int id_produit);


}
