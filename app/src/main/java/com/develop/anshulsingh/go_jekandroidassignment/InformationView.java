package com.develop.anshulsingh.go_jekandroidassignment;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class InformationView extends ExpandableGroup<SummaryView> {
    public InformationView(String title, List<SummaryView> items) {
        super(title, items);
    }
}
