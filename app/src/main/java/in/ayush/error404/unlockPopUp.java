package in.ayush.error404;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import java.util.concurrent.Executor;

public class unlockPopUp {
    public void showPopupWindow(final View view, String data, Context context, onDialougeFinish listener) {

        onDialougeFinish myListener= listener ;
        //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.unloack_screen, null);

        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;

        //Create a window with our parameters
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        //Initialize the elements of our window, install the handler

        TextView test2 = popupView.findViewById(R.id.textView95);
        test2.setText(data);
        EditText key = popupView.findViewById(R.id.key);


        ImageView img = popupView.findViewById(R.id.imageView3);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myListener.onFinish(key.getText().toString());
                popupWindow.dismiss();
            }
        });


//
//

//        buttonEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                //As an example, display the message
//                String amount = test2.getText().toString();
//
//                sessionManager sessionManager = new sessionManager(view.getContext());
//                HashMap<String, String> userDetails = sessionManager.getUserDataFromSession();
//
//                String data = userDetails.get(sessionManager.KEY_ID);
//                ApiInterface apiInterface = RetrofitClient.getRetrofit().create(ApiInterface.class);
//
//                final withdrawModel rModel = new withdrawModel(data,amount);
//
//                Call<withdrawModel> call = apiInterface.getMoney(rModel);
//                call.enqueue(new Callback<withdrawModel>() {
//                    @Override
//                    public void onResponse(Call<withdrawModel> call, Response<withdrawModel> response) {
//                        if(response.code()==200){
//                            Toast.makeText(view.getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                            popupWindow.dismiss();
//                        }
//                        else {
//                            Toast.makeText(view.getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<withdrawModel> call, Throwable t) {
//                        Toast.makeText(view.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//
//
//                    }
//                });
//
//
//            }
//        });



        //Handler for clicking on the inactive zone of the window

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //Close the window when clicked



                return true;
            }
        });

    }
}
