package com.develop.anshulsingh.go_jekandroidassignment;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class SummaryViewHolder extends ChildViewHolder {
    TextView tvSummaryText,tvLang,tvStars,tvForks;




    public SummaryViewHolder(View itemView) {
        super(itemView);
        tvSummaryText = itemView.findViewById(R.id.tvSummaryText);
        tvLang = itemView.findViewById(R.id.tvLang);
        tvStars = itemView.findViewById(R.id.tvStars);
        tvForks = itemView.findViewById(R.id.tvForks);

    }

    public void bind(SummaryView summ){
        tvSummaryText.setText(summ.summaryText);
        tvLang.setText(summ.lang);
        tvStars.setText(summ.stars.toString());
        tvForks.setText(summ.folks.toString());
    }

}
