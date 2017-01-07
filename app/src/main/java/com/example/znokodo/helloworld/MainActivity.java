package com.example.znokodo.helloworld;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText)findViewById(R.id.editText);
        final ImageView imageView = (ImageView)findViewById(R.id.image);

        editText.getText();
        Button submitButton = (Button) findViewById(R.id.button);
        SharedPreferences sharedPreferences2 = PreferenceManager.getDefaultSharedPreferences(this);
        editText.setText(sharedPreferences2.getString("money", null));
        submitButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(view.getContext());
                        sharedPreferences.edit().putString("money", editText.getText().toString()).apply();
                        Toast.makeText(view.getContext(), editText.getText(), Toast.LENGTH_LONG).show();
                        findViewById(R.id.image);
                        imageView.setImageResource(R.drawable.tennichi);
                        sendRequest();
                    }
                }

        );
    }
    public void sendRequest() {
        Request kmcRequest = new Request.Builder()
                .url("https://www.kmc.gr.jp")
                .build();
        Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String body = response.body().string();
                new Handler(Looper.getMainLooper()).post(
                        new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, body, Toast.LENGTH_LONG).show();
                            }
                        }
                );
            }

        };
        RequestBody formBody = new FormBody.Builder()
                .add("token", getString(R.string.token))
                .add("channel", "#powerword")
                .add("text", editText.getText().toString())
                .add("icon_emoji", ":powerword:")
                .add("username", "力 is パワー")
                .build();
        Request slackRequest = new Request.Builder()
                .url("https://slack.com/api/chat.postMessage")
                .post(formBody)
                .build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(slackRequest).enqueue(callback);
    }
}
