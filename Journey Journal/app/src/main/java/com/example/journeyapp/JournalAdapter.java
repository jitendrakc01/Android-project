package com.example.journeyapp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class JournalAdapter extends FirebaseRecyclerAdapter<JournalModel,JournalAdapter.myViewHolder> {
    public JournalAdapter(@NonNull FirebaseRecyclerOptions<JournalModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull final JournalModel model) {
        holder.title.setText(model.getTitle());
        holder.description.setText(model.getDescription());
        holder.date.setText(model.getDate());
        Glide.with(holder.img.getContext())
                .load(model.getPurl())
                .placeholder(R.drawable.common_google_signin_btn_icon_dark)
                .error(R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);
        //image onclick to go to details fragement
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity=(AppCompatActivity)view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,new JournalDetailsFragment(model.getTitle(),model.getDescription(),model.getDate(),model.getPurl())).addToBackStack(null).commit();
            }
        });

        //on click to edit
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus=DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.edit_dialog))
                        .setExpanded(true,1200)
                        .create();
                //get the runtime reference via view
                View myview=dialogPlus.getHolderView();
                final EditText purl=myview.findViewById(R.id.uimgurl);
                final EditText title=myview.findViewById(R.id.utitle);
                final EditText description=myview.findViewById(R.id.udescription);
                final EditText date=myview.findViewById(R.id.udate);
                Button submit=myview.findViewById(R.id.usubmit);

                purl.setText(model.getPurl());
                title.setText(model.getTitle());
                description.setText(model.getDescription());
                date.setText(model.getDate());

                dialogPlus.show();

                //on click to save/update data to firebase datebase
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map=new HashMap<>();
                        map.put("purl",purl.getText().toString());
                        map.put("title",title.getText().toString());
                        map.put("description",description.getText().toString());
                        map.put("date",date.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("journals")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });
                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder=new AlertDialog.Builder(holder.img.getContext());
                        builder.setTitle("Delete Journal");
                        builder.setMessage("Are you sure you want to delete the selected journal?");

                        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseDatabase.getInstance().getReference().child("journals")
                                        .child(getRef(position).getKey()).removeValue();
                            }
                        });

                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        builder.show();
                    }
                });

            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.journal_item,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView title, description, date;
        Button edit, delete;
        //setting viewholder ofr singlerow item class --journal-item.xml
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            img=(ImageView) itemView.findViewById(R.id.img1);
            title=(TextView)itemView.findViewById(R.id.titletxt);
            description=(TextView)itemView.findViewById(R.id.desccriptiontext);
            date=(TextView)itemView.findViewById(R.id.Datetxt);
            edit=(Button) itemView.findViewById(R.id.editBtn);
            delete=(Button) itemView.findViewById(R.id.delBtn);


        }
    }
}
