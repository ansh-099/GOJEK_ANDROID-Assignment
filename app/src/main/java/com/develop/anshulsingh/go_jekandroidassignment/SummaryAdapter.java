package com.develop.anshulsingh.go_jekandroidassignment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class SummaryAdapter extends ExpandableRecyclerViewAdapter<InformationViewHolder,SummaryViewHolder> {
    public SummaryAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
    }

    @Override
    public InformationViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout
        .one_unit_info,parent,false);
        return new InformationViewHolder(v);
    }

    @Override
    public SummaryViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .one_unit_view,parent,false);
        return new SummaryViewHolder(v);
    }

    @Override
    public void onBindChildViewHolder(final SummaryViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final SummaryView summ = (SummaryView) group.getItems().get(childIndex);
        holder.bind(summ);


    }

    @Override
    public void onBindGroupViewHolder(final InformationViewHolder holder, int flatPosition, ExpandableGroup group) {
        final InformationView info = (InformationView) group;
        holder.bind(info);

    }
}
