package com.maric.vlajko.mirror;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by Vlajko on 06-Feb-16.
 */
public class PicturesAdapter extends ArrayAdapter<MyPictures>{

    Context mContext;
    int mResourceId;
    MyPictures mObjects[] = null;
    public PicturesAdapter(Context context, int resource, MyPictures[] objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResourceId = resource;
        this.mObjects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        row = inflater.inflate(mResourceId, parent,false);

        TextView imageName = (TextView) row.findViewById(R.id.myLargeTextView);
        TextView authorName = (TextView) row.findViewById(R.id.myMediumTextView);
        ImageView imageView = (ImageView) row.findViewById(R.id.myImageView);

        MyPictures myPicture = mObjects[position];
        imageName.setText(myPicture.getNameOfImage());
        authorName.setText(myPicture.getNameOfAuthor());

        int resId = mContext.getResources().getIdentifier(myPicture.getImagePath(),"drawable",mContext.getPackageName());
        imageView.setImageResource(resId);

        return row;

    }
}
