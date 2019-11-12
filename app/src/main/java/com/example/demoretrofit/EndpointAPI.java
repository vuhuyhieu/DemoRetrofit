package com.example.demoretrofit;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface EndpointAPI {

    @GET("posts/{id}/comments")
    Call<ArrayList<Comment>> getCommentList(@Path("id") String id);

    @POST("posts/1/comments")
    @FormUrlEncoded
    Call<Comment> addComment(@Field("postID") String postID,
                             @Field("id") String id,
                             @Field("name") String name,
                             @Field("email") String email,
                             @Field("body") String body
                             );
}
