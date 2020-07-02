package com.casbah.casbahdzcommandes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.casbah.casbahdzcommandes.R;

import java.util.ArrayList;


public class Adapter1 extends RecyclerView.Adapter<Adapter1.ExampleViewHolder> {
    private ArrayList<Item1> mExampleList;
    private int selected_item=-1,ver=0;
    private View v;


    public static class ExampleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mTextView1;
        public TextView mTextView2;
        public TextView mTextView3;

        public ImageView i;
        public ImageView i2;
        public CheckBox c;




        public ExampleViewHolder(View itemView) {
            super(itemView);


           i = itemView.findViewById(R.id.image);
           mTextView1=itemView.findViewById(R.id.textC1);
            mTextView2=itemView.findViewById(R.id.textC2);
            mTextView3=itemView.findViewById(R.id.textC3);



            // i.setOnClickListener(this);

        }
        public void onClick(View v) {

            if (v.getId() == i.getId()) {

            } else if (v.getId() == i2.getId()) {
            }
    }}

    public Adapter1(ArrayList<Item1> exampleList) {
        mExampleList = exampleList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card1, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        Item1 currentItem = mExampleList.get(position);


if(!currentItem.getText3().equals("")){
    View someView = v.findViewById(R.id.image);
holder.itemView.setBackgroundColor(Color.parseColor("#7ed6df"));
holder.i.setBackgroundColor(Color.parseColor("#7ed6df"));

}
else{
    View someView = v.findViewById(R.id.image);
    holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
    holder.i.setBackgroundColor(Color.parseColor("#ffffff"));

}

       holder.mTextView1.setText(currentItem.getText2());
        holder.mTextView2.setText(currentItem.getText4());
        holder.mTextView3.setText(currentItem.getText5());

        if(!currentItem.getText1().equals("")){
        String s = currentItem.getText1().replace("data:image/png;base64,","");


        byte[] decodedString = Base64.decode(s, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
       holder.i.setImageBitmap(decodedByte);}
        else{
            holder.i.setImageResource(R.drawable.non);
        }
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