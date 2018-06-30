package com.cloudvision.utp.quieroentradas.presentation.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cloudvision.utp.quieroentradas.R;
import com.cloudvision.utp.quieroentradas.data.model.CommentPlace;
import com.cloudvision.utp.quieroentradas.presentation.ui.helpers.SelectableAdapter;

import java.util.List;

/**
 * Created by Walberth Gutierrez Telles on 06,June,2018
 */
public class CommentAdapter extends SelectableAdapter<CommentAdapter.ViewHolder> {

    private Context context;
    private List<CommentPlace> commentList;

    public CommentAdapter(RecyclerView recyclerView, List<CommentPlace> commentList, Context context) {
        super(recyclerView);
        this.context = context;
        this.commentList = commentList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(commentList.get(getItemCount() - 1 - position));
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public class ViewHolder extends SelectableAdapter.ViewHolder{
        TextView  txtUserEmail, txtUserComment;
        ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);

            txtUserEmail = itemView.findViewById(R.id.txtUserEmail);
            txtUserComment = itemView.findViewById(R.id.txtUserComment);
            progressBar = itemView.findViewById(R.id.progressBarComment);
        }

        public void bind(CommentPlace comment){
            progressBar.setVisibility(View.VISIBLE);



            //TODO
            txtUserEmail.setText(comment.getIdUser());
        }
    }
}
