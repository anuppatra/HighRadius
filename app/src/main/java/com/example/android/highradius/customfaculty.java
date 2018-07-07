package com.example.android.highradius;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


/**
 * Created by keshav on 14-03-2018.
 */

public class customfaculty extends ArrayAdapter<String>
{
    private Activity context;

    private String[] d;
    private String[] n;
    private String[] d1;
    private String[] r1;
    private String[] r2;
    private String[] r3;
    private String[] r4;
    private String[] r5;
    private String[] c;


    int way=3;

    public customfaculty(Activity contex, String[] d, String[] n, String[] d1, String[] r1, String[] r2, String[] r3, String[] r4, String[] r5, String[] c) {
        super(contex, R.layout.rowfiles,d);
        this.context=contex;
        this.d=d;
        this.n=n;
        this.d1=d1;
        this.r1=r1;
        this.r2=r2;
        this.r3=r3;
        this.r4=r4;
        this.r5=r5;
        this.c=c;
    }
    
    @Override
    public View getView(final int position, @Nullable  View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View v=inflater.inflate(R.layout.rowfiles,null,true);


        TextView d=(TextView)v.findViewById(R.id.deprev);

            d.setText(this.d[position]);
            TextView n = (TextView) v.findViewById(R.id.name);
            n.setText(this.n[position]);
            TextView d1 = (TextView) v.findViewById(R.id.dept);
            d1.setText(this.d1[position]);
            TextView r1 = (TextView) v.findViewById(R.id.r11);
            r1.setText(this.r1[position]);
        TextView r2 = (TextView) v.findViewById(R.id.r22);
        r1.setText(this.r2[position]);
        TextView r3 = (TextView) v.findViewById(R.id.r33);
        r1.setText(this.r3[position]);
        TextView r4 = (TextView) v.findViewById(R.id.r44);
        r1.setText(this.r4[position]);
        TextView r5 = (TextView) v.findViewById(R.id.r55);
        r1.setText(this.r5[position]);
            TextView c = (TextView) v.findViewById(R.id.comment);
            c.setText(this.c[position]);
        return v;
    }

}
