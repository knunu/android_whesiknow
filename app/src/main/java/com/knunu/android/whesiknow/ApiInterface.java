package com.knunu.android.whesiknow;

import org.json.JSONObject;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Knunu on 2016. 7. 23..
 */
public interface ApiInterface {
    @GET("user")
    Call<List<User>> getUser(@Query("email") String email, @Query("login_group") String login_group, @Query("password") String password);

    @FormUrlEncoded
    @Multipart
    @PUT("user")
    Call<User> putUser(@Field("id") int id, @Field("password") String password, @Field("name") String name, @Part("profile_image") RequestBody profile_image, @Part("thumbnail_image") RequestBody thumbnail_image);


    @FormUrlEncoded
    @POST("user")
    Call<User> postUser(@Field("email") String email, @Field("login_group") String login_group, @Field("password") String password, @Field("name") String name);

    @DELETE("user/{id}")
    Call<Void> deleteUser(@Path("id") int id);
}
