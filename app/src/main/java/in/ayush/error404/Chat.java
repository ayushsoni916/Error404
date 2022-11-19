package in.ayush.error404;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class Chat extends AppCompatActivity {
    String name,image;
    ImageView pic;
    TextView nameTv;
    ImageView msgSend;
    EditText msg;
    String nameStr,ImgStr,receiver,Sender,text="";
    ImageView lock,send2;

    boolean ency = true;
    private static final String alphabetString = "abcdefghijklmnopqrstuvwxyz";

    FirebaseDatabase data;
    DatabaseReference ref;
    RecyclerView rcv;
    LinearLayoutManager manager;
    messageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        name = getIntent().getStringExtra("name");
        image = getIntent().getStringExtra("image");
        msgSend = findViewById(R.id.send);
        msg = findViewById(R.id.msg);
        lock = findViewById(R.id.lock);

        pic = findViewById(R.id.imageView27);
        nameTv  = findViewById(R.id.textView6);

        data =FirebaseDatabase.getInstance();
        send2=findViewById(R.id.imageView5);

        rcv = findViewById(R.id.rcvText);
        manager =new LinearLayoutManager(getApplicationContext());
//        songManager = new LinearLayoutManager(getApplicationContext());
        manager.setStackFromEnd(true);
        rcv.setLayoutManager(manager);


        nameTv.setText(name);
        Glide.with(pic.getContext()).load(image).thumbnail(0.01f)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(pic);

        sessionManager sessionManager = new sessionManager(getApplicationContext());
        HashMap<String ,String> userDetails = sessionManager.getUserDataFromSession();

        Sender = userDetails.get(sessionManager.KEY_FIRSTNAME);
        receiver = name;

        send2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSolana popUpClass = new sendSolana();
                popUpClass.showPopupWindow(view,getApplicationContext());

            }
        });

        onDialougeFinish listener = new onDialougeFinish() {
            @Override
            public void onFinish(String value) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(Chat.this, value, Toast.LENGTH_SHORT).show();
                        fillData(value);


                    }
                },100);
            }
        };
        lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                unlockPopUp popUpClass = new unlockPopUp();
                popUpClass.showPopupWindow(view,"Enter your key to decrypt your messages.",getApplicationContext(),listener);
            }
        });

        msgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text = msg.getText().toString();
                if(!text.equals("")){
                    SimpleDateFormat formater = new SimpleDateFormat("yyyy_MM_dd_HH_mm__ss", Locale.CANADA);
                    Date now =new Date();
                    String fileName = formater.format(now);
                    text = encrypt12(text, Integer.parseInt("12"));

                    ref =data.getReference().child("users/"+Sender).child("chats").child(receiver).child(fileName);
                    ref.child("message").setValue(text);
                    ref.child("yourStatus").setValue("sender");

                    ref =data.getReference().child("users/"+receiver).child("chats").child(Sender).child(fileName);
                    ref.child("message").setValue(text);
                    ref.child("yourStatus").setValue("receiver");
                    msg.setText(null);
                    ref=data.getReference().child("users/"+receiver).child("chats").child(Sender);
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            long size = snapshot.getChildrenCount();
//                            Toast.makeText(message.this, size, Toast.LENGTH_SHORT).show();
                            rcv.smoothScrollToPosition((int) (size-1));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
//



                }
            }
        });
        fillData("-1");






    }

    private void fillData(String s) {
        FirebaseRecyclerOptions<messageModel> options =
                new FirebaseRecyclerOptions.Builder<messageModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("users/"+Sender).child("chats").child(receiver), messageModel.class)
                        .build();

        adapter = new messageAdapter(options , getApplicationContext(),s);
        rcv.setAdapter(adapter);
        adapter.startListening();
    }

    public String encrypt12(String message, int shiftkey) {
        message = message.toLowerCase();
        String cipherText = "";
        for (int i = 0; i < message.length(); i++) {
            int charPosition = alphabetString.indexOf(message.charAt(i));
            int keyval = (shiftkey + charPosition) % 26;
            char replaceVAL = alphabetString.charAt(keyval);
            cipherText += replaceVAL;

        }

        // returning the final ciphertext
        return cipherText;
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}