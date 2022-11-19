package in.ayush.error404;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Home extends AppCompatActivity {
    RecyclerView rcv ;
    LinearLayoutManager manager;
    userAdapter adapter;
    FirebaseDatabase mDatabase;
    ImageView wallet ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        wallet = findViewById(R.id.wallet);

        wallet.setOnClickListener(new View.OnClickListener() {
            @Override
//            public void onClick(View view){
//                walletPopUp popUpClass = new walletPopUp();
//                popUpClass.showPopupWindow(view,"Enter your key to decrypt your messages.",getApplicationContext());
//
//            }
            public void onClick(View view) {

                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                MediaType mediaType = MediaType.parse("text/plain");
                RequestBody body = RequestBody.create(mediaType, "\n  {\n    \"jsonrpc\": \"2.0\",\n    \"id\": 1,\n    \"method\": \"getAccountInfo\",\n    \"params\": [\n      \"9v625sK8MfDD7mmJAcbu8Vv4z1yXM3rHDnYiJf2jZHxA\",\n      {\n        \"encoding\": \"base58\"\n      }\n    ]\n  }\n");
                Request request = new Request.Builder()
                        .url("https://solana-mainnet.g.alchemy.com/v2/y3iA0gaju4rYoi340-EgSJ7Vmw6v_FMz")
                        .method("POST", body)
                        .addHeader("Content-Type", "text/plain")
                        .build();

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Response response = client.newCall(request).execute();
                            String jsonData = response.body().string();
                            JSONObject Jobject = new JSONObject(jsonData);
                            JSONObject jData = Jobject.getJSONObject("result");
                            JSONObject ans = jData.getJSONObject("value");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
//                                        Toast.makeText(Home.this, String.valueOf(ans.get("lamports")), Toast.LENGTH_SHORT).show();
                                        double l = Double.parseDouble(String.valueOf(ans.get("lamports")));
                                        double balance = (double) (l/(Math. pow(10,9)));
                                        walletPopUp popUpClass = new walletPopUp();
                                        popUpClass.showPopupWindow(view,String.valueOf(balance),getApplicationContext());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();

//                Retrofit retrofit = new Retrofit.Builder()
//                        .baseUrl("https://solana-mainnet.g.alchemy.com/v2/y3iA0gaju4rYoi340-EgSJ7Vmw6v_FMz/")
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .build();
//
//                SInterface solanaInterface = retrofit.create(SInterface.class);
//                ArrayList<Object> params = new ArrayList<>();
//                params.add("7VQYTJuLzKzakogmoUqppjD8LSKVSAWgKQ3ZQhUJhxhz");
////                {
////                    "encoding": "base58"
////                }
//                HashMap<String,String > str =new HashMap<>();
//                str.put("encoding","base58");
//                params.add(str);
//
//                String walletAddress = "7VQYTJuLzKzakogmoUqppjD8LSKVSAWgKQ3ZQhUJhxhz";
//                Call<SInterface.getAccoutnResponce> responseCall = solanaInterface.getAccountRes(
//                        new SInterface.getAccountInfo(
//                                "2.0",
//                                1,
//                                "getAccountInfo",
//                                params
//                        )
//                );
//
//                responseCall.enqueue(new Callback<SInterface.getAccoutnResponce>() {
//                    @Override
//                    public void onResponse(Call<SInterface.getAccoutnResponce> call, Response<SInterface.getAccoutnResponce> response) {
//                        if(response.isSuccessful()){
//                            Toast.makeText(Home.this, "Success", Toast.LENGTH_SHORT).show();
//                        }
//                        else{
//                            Toast.makeText(Home.this, String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<SInterface.getAccoutnResponce> call, Throwable t) {
//                        Toast.makeText(Home.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//                responseCall.enqueue(new Callback<SInterface.GetBalanceResponse>() {
//                    @Override
//                    public void onResponse(Call<SInterface.GetBalanceResponse> call, Response<SInterface.GetBalanceResponse> response) {
//                        if(response.isSuccessful()){
//                            Toast.makeText(Home.this, "Success: " + response.body().toString(), Toast.LENGTH_SHORT).show();
//
//                        } else {
//                            Toast.makeText(Home.this, String.valueOf(response.code()) , Toast.LENGTH_SHORT).show();
//
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<SInterface.GetBalanceResponse> call, Throwable t) {
//                        Toast.makeText(Home.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//                        Log.e("ayush",t.getMessage()+"fail" );
//                    }
//                });
            }
        });

        mDatabase =FirebaseDatabase.getInstance();

        rcv = findViewById(R.id.rcv);
        manager =new LinearLayoutManager(getApplicationContext());

        rcv.setLayoutManager(manager);
        FirebaseRecyclerOptions<userModel> options =
                new FirebaseRecyclerOptions.Builder<userModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("users"), userModel.class)
                        .build();
        adapter = new userAdapter(options , getApplicationContext());
        rcv.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}