package com.develop.anshulsingh.go_jekandroidassignment;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

public class SummaryView implements Parcelable {
    String summaryText,lang,intro,imageurl;
    Integer stars,folks;



    public SummaryView(String name,String lang,String intro,String imageurl,Integer stars,Integer folks) {
        this.summaryText = name;
        this.lang = lang;
        this.stars = stars;
        this.folks = folks;
        this.intro = intro;
        this.imageurl = imageurl;
    }

    protected SummaryView(Parcel in) {
        summaryText = in.readString();
    }

    public static final Creator<SummaryView> CREATOR = new Creator<SummaryView>() {
        @Override
        public SummaryView createFromParcel(Parcel in) {
            return new SummaryView(in);
        }

        @Override
        public SummaryView[] newArray(int size) {
            return new SummaryView[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(summaryText);
    }
}
