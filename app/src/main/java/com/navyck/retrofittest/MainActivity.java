package com.navyck.retrofittest;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView textView = null;
    private Retrofit retrofit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView1);
        Button btnRequest = findViewById(R.id.btnRequest1);
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequest();
            }
        });

        String BASE_URL = "https://api.github.com";
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private void sendRequest() {
        GitHubAPI gitHubAPI = retrofit.create(GitHubAPI.class);
        Call<List<Contributor>> call = gitHubAPI.contributors("square", "retrofit");

        call.enqueue(new Callback<List<Contributor>>() {
            @Override
            public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {
                List<Contributor> contributors = response.body();

                for (Contributor contributor : contributors) {
                    textView.append("\n");
                    textView.append("\n");
                    textView.append("닉네임 : " + contributor.login);
                    textView.append("\n");
                    textView.append("기부금 : " + contributor.contributions);
                    textView.append("\n");
                    textView.append("아이디 : " + contributor.id);
                }
            }

            @Override
            public void onFailure(Call<List<Contributor>> call, Throwable t) {
                textView.append("\n에러 => " + t.getMessage());
            }
        });
    }
}