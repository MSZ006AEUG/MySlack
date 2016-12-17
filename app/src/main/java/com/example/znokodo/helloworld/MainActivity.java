package com.example.znokodo.helloworld;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText editText = (EditText)findViewById(R.id.editText);
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
                    }
                }
        );
    }
}
