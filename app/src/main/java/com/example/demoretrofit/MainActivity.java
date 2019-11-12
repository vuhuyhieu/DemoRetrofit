package com.example.demoretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView mTextViewResult;
    private Button mButtonAdd;

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private String TAG = "hieuvh";
    private ArrayList<Comment> listComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextViewResult = findViewById(R.id.txtResult);
        mButtonAdd = findViewById(R.id.btnAdd);
        fetchData();
        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addComment();
            }
        });
    }

    private void addComment() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).
                build();
        EndpointAPI endpointAPI = retrofit.create(EndpointAPI.class);
        Call<Comment> result = endpointAPI.addComment("1", "6", "hieu", "hieu@gmail.com", "example");
        result.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                if (response.isSuccessful()) {
                    fetchData();
                    Toast.makeText(MainActivity.this, "number of comment = " + listComment.size(), Toast.LENGTH_LONG).show();
                    Log.d(TAG, "onResponse POST success: "+response.body());
                } else {
                    Log.d(TAG, "onResponse POST error: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                t.printStackTrace();
                Log.d(TAG, "onFailure POST: " + t.toString());
            }
        });
    }

    private void fetchData() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).
                build();
        EndpointAPI endpointAPI = retrofit.create(EndpointAPI.class);
        Call<ArrayList<Comment>> result = endpointAPI.getCommentList("1");
        result.enqueue(new Callback<ArrayList<Comment>>() {
            @Override
            public void onResponse(Call<ArrayList<Comment>> call, Response<ArrayList<Comment>> response) {
                if (response.isSuccessful()) {
                    listComment = response.body();
                    mTextViewResult.setText(listComment.size() + "");
                } else {
                    ResponseBody responseBody = response.errorBody();
                    Log.d(TAG, "onResponse GET error: " + responseBody);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Comment>> call, Throwable t) {
                t.printStackTrace();
                Log.d(TAG, "onFailure GET: " + t.toString());
            }
        });
    }
}
