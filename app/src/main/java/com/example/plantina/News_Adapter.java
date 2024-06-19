package com.example.plantina;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class News_Adapter extends RecyclerView.ViewHolder {
    View mView;
    public News_Adapter(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
    }

//    public void setDetails(Context ctx, String heading, String desription)
//    {
//        TextView mHeading = mView.findViewById(R.id.datedataval);
//        TextView mDescription = mView.findViewById(R.id.headingdataval);
//
//        mHeading.setText(heading);
//        mDescription.setText(desription);
//    }

    public void setDate(String date)
        {
            TextView Date = itemView.findViewById(R.id.datedataval);
            Date.setText(date);
        }
        public void setHeading(String heading)
        {
            TextView Head = itemView.findViewById(R.id.headingdataval);
            Head.setText(heading);
        }

}
