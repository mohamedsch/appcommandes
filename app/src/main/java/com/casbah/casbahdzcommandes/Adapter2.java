package com.casbah.casbahdzcommandes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.travijuu.numberpicker.library.NumberPicker;

import java.util.ArrayList;


public class Adapter2 extends RecyclerView.Adapter<Adapter2.ExampleViewHolder> {
    private ArrayList<Item1> mExampleList;
    private int selected_item=-1,ver=0;
    private View v;


    public static class ExampleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mTextView1;
        public TextView mTextView2;
        public TextView mTextView3;

        public WebView i;
        public ImageView i2;
        public CheckBox c;

public NumberPicker nb;


        public ExampleViewHolder(View itemView) {
            super(itemView);


           i = itemView.findViewById(R.id.image);
           mTextView1=itemView.findViewById(R.id.textC1);
            mTextView2=itemView.findViewById(R.id.textC2);
nb =itemView.findViewById(R.id.number_picker);

            // i.setOnClickListener(this);

        }
        public void onClick(View v) {

            if (v.getId() == i.getId()) {

            } else if (v.getId() == i2.getId()) {
            }
    }}

    public Adapter2(ArrayList<Item1> exampleList) {
        mExampleList = exampleList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card2, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        Item1 currentItem = mExampleList.get(position);

/*
if(!currentItem.getText3().equals("")){
    View someView = v.findViewById(R.id.ll);
    someView.setBackgroundColor(Color.parseColor("#7ed6df"));
    holder.c.setChecked(true);
}*/

       holder.mTextView1.setText(currentItem.getText2());
        holder.mTextView2.setText(currentItem.getText5());
        holder.nb.setValue(Integer.parseInt(currentItem.getText4()));




       holder.i.loadUrl("http://www.casbahdz.com/casbahtemplate/libs/image/produit.php?id="+currentItem.getText1()+"");

      /*  View someView = v.findViewById(R.id.ll);// get Any child View

        if(position == selected_item) {
            someView.setBackgroundColor(Color.parseColor("#7ed6df"));

        } else {
            someView.setBackgroundColor(Color.WHITE);
        }

        if(ver==1){
            ImageView imageView = someView.findViewById(R.id.image);
            imageView.setImageResource(0);
        }*/

    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
    public void setSelectedItem(int i){
        selected_item=i;

    }
    public void setV(){
        ver=1;

    }

    public String getText1(){
        return mExampleList.get(1).getText1();


    }


}