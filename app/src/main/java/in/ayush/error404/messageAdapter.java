package in.ayush.error404;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class messageAdapter extends FirebaseRecyclerAdapter<messageModel, messageAdapter.myViewHolder>  {
    Context context;
    Boolean eny=true;
    String key ;
    public messageAdapter(@NonNull FirebaseRecyclerOptions<messageModel> options, Context context,String key) {
        super(options);
        this.context = context;
        this.key = key;
        if(key!="-1"){
            eny=false;
        }
        else {
            eny = true;
        }
    }


    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull messageModel model) {
        String status = model.getYourStatus();
        try{
            if (status.equals("sender")) {
                holder.lay.setGravity(Gravity.END);

            }
            else {
                holder.lay.setGravity(Gravity.START);

            }
        }
        catch (Exception e){
            holder.lay.setGravity(Gravity.START);
        }

        if(eny ==false){
//            decrypt12(model.getMessage(), Integer.parseInt(key));
            try{
                holder.txt.setText(utility.decrypt1(model.getMessage(), Integer.parseInt(key)).toLowerCase());
            }
            catch (Exception e){
                Toast.makeText(context, "Wrong Key.", Toast.LENGTH_SHORT).show();
                holder.txt.setText(model.getMessage());
            }

        }
        else {
            holder.txt.setText(model.getMessage());
        }


    }



    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.chat_rcv_design, parent, false);
        return new messageAdapter.myViewHolder(v);
    }


    public class myViewHolder extends RecyclerView.ViewHolder {
        LinearLayout lay;
        TextView txt;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            txt = itemView.findViewById(R.id.textView10);
            lay = itemView.findViewById(R.id.lay);
        }
    }
}