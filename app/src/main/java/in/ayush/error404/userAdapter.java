package in.ayush.error404;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class userAdapter extends FirebaseRecyclerAdapter<userModel, userAdapter.myViewHolder> {
    Context context;

    public userAdapter(@NonNull FirebaseRecyclerOptions<userModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull userModel model) {
        holder.name.setText(model.getName());
        Glide.with(holder.pic.getContext()).load(model.getPic()).thumbnail(0.01f)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.pic);

        holder.cns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context , Chat.class);
                i.putExtra("image",model.getPic());
                i.putExtra("name",model.getName());
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });


    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.user_rcv,parent,false);
        return new userAdapter.myViewHolder(v);
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        ImageView pic;
        ConstraintLayout cns;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            pic = itemView.findViewById(R.id.pic);
            cns = itemView.findViewById(R.id.cns);

        }
    }
}
