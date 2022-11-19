package in.ayush.error404;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView login;
    EditText name,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.key);
        pass = findViewById(R.id.nameET);

        login = findViewById(R.id.imageView2);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().length()>0 && pass.getText().toString().length()>0){
                    sessionManager sessionManager = new sessionManager(getApplicationContext());
                    sessionManager.createLoginSession(name.getText().toString(),name.getText().toString());
                    Intent i = new Intent(getApplicationContext() , Home.class);
                    startActivity(i);
                }
            }
        });
    }
}