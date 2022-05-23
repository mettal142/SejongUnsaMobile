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

public class PostRecyclerAdapeter extends RecyclerView.Adapter<PostViewHolder> {
    @NonNull

    private ArrayList<PostData> PostList,saveList;

    public PostRecyclerAdapeter(ArrayList<PostData> postlist,ArrayList<PostData> savelist)
    {
        PostList =postlist;
        saveList=savelist;
    }

    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        Context context= viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View postView = layoutInflater.inflate(R.layout.postdata,viewGroup,false);//
        PostViewHolder viewHolder = new PostViewHolder(postView,context){ };
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder viewHolder, int i) {
        PostData postData= PostList.get(i);
        viewHolder.PostUserName=PostList.get(i).PostUserName;
        viewHolder.PostUserNum=PostList.get(i).PostUserNum;
        viewHolder.PostUserID=PostList.get(i).PostUserID;
        viewHolder.PostTitle=PostList.get(i).PostTitle;
        viewHolder.PostContents=PostList.get(i).PostContents;
        viewHolder.PostKey=PostList.get(i).PostKey+"";
        viewHolder.PostField=PostList.get(i).PostField+"";
        viewHolder.PostIsvisible=PostList.get(i).PostIsvisible+"";
        viewHolder.PostIsunknown=PostList.get(i).PostIsunknown+"";
        viewHolder.PostTime=PostList.get(i).PostTime;

        viewHolder.Title.setText(viewHolder.PostTitle);
        viewHolder.Contents.setText(viewHolder.PostContents);
        if(Integer.parseInt(viewHolder.PostIsunknown)==1)
            viewHolder.PostUserName="익명";
        viewHolder.TimeName.setText(viewHolder.PostTime+"   "+viewHolder.PostUserName);

//        viewHolder.Name.setText(viewHolder.Num_p+"기 "+viewHolder.Name_p);
//        viewHolder.Dep.setText(viewHolder.Dep_p);

    }

    @Override
    public int getItemCount() {
        return PostList.size();
    }

}

class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {

    String PostUserName,PostUserNum,PostUserID,PostTitle,PostContents, PostKey, PostField, PostIsvisible,PostIsunknown,PostTime;
    TextView Title,Contents,TimeName;

    public Context cont;

    public PostViewHolder(View itemView,Context c){
        super(itemView);
        Title= (TextView) itemView.findViewById(R.id.PostTitle);
        Contents = (TextView)itemView.findViewById(R.id.PostContents);
        TimeName = (TextView)itemView.findViewById(R.id.TimeName);
        cont = c;
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }
    //버튼액션
    @Override
    public void onClick(View v) {
        Intent DetailIntent = new Intent(cont,PostDetail.class);
        DetailIntent.putExtra("PostUserName",PostUserName);
        DetailIntent.putExtra("PostUserNum",PostUserNum);
        DetailIntent.putExtra("PostUserID",PostUserID);
        DetailIntent.putExtra("PostTitle",PostTitle);
        DetailIntent.putExtra("PostContents",PostContents);
        DetailIntent.putExtra("PostKey",PostKey);
        DetailIntent.putExtra("PostField",PostField);
        DetailIntent.putExtra("PostIsvisible",PostIsvisible);
        DetailIntent.putExtra("PostIsunknown",PostIsunknown);
        DetailIntent.putExtra("PostTime",PostTime);
        cont.startActivity(DetailIntent);
    }

    @Override
    public boolean onLongClick(View v) {
        if(PostUserID.equals(MyID)||MyID.equals("admin")) {
            Intent it = new Intent(cont, SettingPost.class);
            it.putExtra("PostKey", PostKey);
            it.putExtra("PostTitle",PostTitle);
            it.putExtra("PostContents",PostContents);
            cont.startActivity(it);
            return  true;
        }else
            return false;
    }
}
