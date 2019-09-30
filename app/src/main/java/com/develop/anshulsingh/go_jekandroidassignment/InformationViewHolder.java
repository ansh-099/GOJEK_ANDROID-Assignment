package com.develop.anshulsingh.go_jekandroidassignment;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

public class InformationViewHolder extends GroupViewHolder {
    TextView tvUserName,tvIntro;
    ImageView ivPerson;
    View lineView;
    public InformationViewHolder(View itemView) {
        super(itemView);
        tvUserName = itemView.findViewById(R.id.tvUserName);
        tvIntro = itemView.findViewById(R.id.tvIntro);
        ivPerson = itemView.findViewById(R.id.ivPerson);
        lineView = itemView.findViewById(R.id.lineView);

    }
    public void bind(InformationView info){

        tvUserName.setText(info.getTitle());
        tvIntro.setText(info.getItems().get(0).intro);

    }

    @Override
    public void expand() {
        lineView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void collapse() {
        lineView.setVisibility(View.VISIBLE);
    }
}
