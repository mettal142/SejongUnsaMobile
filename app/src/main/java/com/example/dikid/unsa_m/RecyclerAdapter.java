package com.example.dikid.unsa_m;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import static com.example.dikid.unsa_m.MainActivity.MyID;

public class RecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {
    @NonNull

    private ArrayList<UserData> userList,saveList;

    public RecyclerAdapter(ArrayList<UserData> userlist,ArrayList<UserData> savelist)
    {
        userList=userlist;
        saveList=savelist;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        Context context= viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View userView = layoutInflater.inflate(R.layout.userdata,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(userView,context){ };
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        UserData userData= userList.get(i);
        viewHolder.ID_p = userList.get(i).ID;
        viewHolder.Name_p=userList.get(i).Name;
        viewHolder.Dep_p=userList.get(i).Dep;
        viewHolder.Email_p=userList.get(i).Email;
        viewHolder.Job_p=userList.get(i).Job;
        viewHolder.Num_p=userList.get(i).Num;
        viewHolder.Point_p=userList.get(i).Point;
        viewHolder.Info_p=userList.get(i).Info;
        viewHolder.Name.setText(viewHolder.Num_p+"기 "+viewHolder.Name_p);
        viewHolder.Dep.setText(viewHolder.Dep_p);

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}

class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {

    public TextView Name;
    public TextView Dep;
    public String ID_p, Name_p,Dep_p,Email_p,Job_p,Num_p,Point_p,Info_p;
    public Context cont;

    public ViewHolder(View itemView,Context c){
    super(itemView);
    Name = (TextView)itemView.findViewById(R.id.username);
    Dep = (TextView)itemView.findViewById(R.id.userdep);
    cont = c;
    itemView.setOnClickListener(this);
    itemView.setOnLongClickListener(this);
    }
//버튼액션
    @Override
    public void onClick(View v) {
        Intent DetailIntent = new Intent(cont,UserDetail.class);
        DetailIntent.putExtra("Name_p",Name_p);
        DetailIntent.putExtra("Dep_p",Dep_p);
        DetailIntent.putExtra("Email_p",Email_p);
        DetailIntent.putExtra("Job_p",Job_p);
        DetailIntent.putExtra("Num_p",Num_p);
        DetailIntent.putExtra("Point_p",Point_p);
        DetailIntent.putExtra("Info_p",Info_p);
        cont.startActivity(DetailIntent);
    }

    @Override
    public boolean onLongClick(View v) {
        if(MyID.equals("admin")){
            Intent it = new Intent(cont,SettingUser.class);
            it.putExtra("userID",ID_p);
            cont.startActivity(it);
        }
    return true;
    }
}
