package com.example.dikid.unsa_m;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.example.dikid.unsa_m.MainActivity.MyID;

public class CommentRecyclerAdapter extends RecyclerView.Adapter<CommentViewHolder> {
    @NonNull

    private ArrayList<CommentData> CommentList,saveList;

    public CommentRecyclerAdapter(ArrayList<CommentData> commentlist,ArrayList<CommentData> savelist)
    {
        CommentList =commentlist;
        saveList=savelist;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        Context context= viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View CommentView = layoutInflater.inflate(R.layout.commentdata,viewGroup,false);//
        CommentViewHolder viewHolder = new CommentViewHolder(CommentView,context){ };
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder viewHolder, int i) {
        CommentData commentData= CommentList.get(i);
        viewHolder.CommentUserName=CommentList.get(i).CommentUserName;
        viewHolder.CommentUserNum=CommentList.get(i).CommentUserNum;
        viewHolder.CommetntUserID=CommentList.get(i).CommetntUserID;
        viewHolder.CommentContents=CommentList.get(i).CommentContents;
        viewHolder.CommentTime=CommentList.get(i).CommentTime;
        viewHolder.PostID=CommentList.get(i).PostID+"";
        viewHolder.CommentID=CommentList.get(i).CommentID+"";
        viewHolder.CommentIsunknown=CommentList.get(i).CommentIsunknown+"";
        viewHolder.CommentIsvisible=CommentList.get(i).CommentIsvisible+"";

        viewHolder.Time.setText(viewHolder.CommentTime);
        viewHolder.Contents.setText(viewHolder.CommentContents);
        if(Integer.parseInt(viewHolder.CommentIsunknown)==1)
        {
            viewHolder.Name.setText("익명");
        }
        else
            viewHolder.Name.setText(viewHolder.CommentUserNum+"기"+" "+viewHolder.CommentUserName);

    }

    @Override
    public int getItemCount() {
        return CommentList.size();
    }

}

class CommentViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{

    String CommentUserName,CommentUserNum,CommetntUserID,CommentContents,CommentTime,PostID,CommentID,CommentIsunknown,CommentIsvisible;

    TextView Name,Contents,Time;


    public Context cont;

    public CommentViewHolder(View itemView,Context c){
        super(itemView);
        Name= (TextView) itemView.findViewById(R.id.CommentName);
        Contents = (TextView)itemView.findViewById(R.id.CommentContests);
        Time = (TextView)itemView.findViewById(R.id.CommentTime);
        cont = c;
        itemView.setOnLongClickListener(this);

    }

    @Override
    public boolean onLongClick(View v) {
        if(CommetntUserID.equals(MyID)||MyID.equals("admin")) {
            Intent it = new Intent(cont, SettingComment.class);
            it.putExtra("CommentID", CommentID);
            cont.startActivity(it);
            return  true;
        }else
            return false;
    }

}