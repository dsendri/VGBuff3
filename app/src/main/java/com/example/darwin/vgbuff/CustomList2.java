package com.example.darwin.vgbuff;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomList2 extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] web;
    private final Integer[] imageId;
    private final String[] web2;

    public CustomList2(Activity context,
                       String[] web, Integer[] imageId,String[] web2) {
        super(context, R.layout.list_view_image2, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;
        this.web2 = web2;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View rowView= inflater.inflate(R.layout.list_view_image2, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.gameMode);
        TextView txtTitle2 = (TextView) rowView.findViewById(R.id.txt2);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.hero);
        txtTitle.setText(web[position]);
        txtTitle2.setText(web2[position]);
        imageView.setImageResource(imageId[position]);
        return rowView;
    }
}