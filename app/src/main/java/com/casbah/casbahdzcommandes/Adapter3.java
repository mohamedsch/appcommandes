package com.casbah.casbahdzcommandes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
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


public class Adapter3 extends RecyclerView.Adapter<Adapter3.ExampleViewHolder> {
    private ArrayList<Item3> mExampleList;
    private int selected_item=-1,ver=0;
    private View v;


    public static class ExampleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mTextView1;
        public TextView mTextView2;
        public TextView mTextView3;


        public ImageView i;
        public CheckBox c;

public NumberPicker nb;

        public NumberPicker nb1;

        public ExampleViewHolder(View itemView) {
            super(itemView);


           i = itemView.findViewById(R.id.image);
           mTextView1=itemView.findViewById(R.id.textC3);
nb =itemView.findViewById(R.id.number_picker);
            nb1 =itemView.findViewById(R.id.number_picker1);

            // i.setOnClickListener(this);

        }
        public void onClick(View v) {

    }}

    public Adapter3(ArrayList<Item3> exampleList) {
        mExampleList = exampleList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card3, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        Item3 currentItem = mExampleList.get(position);

/*
if(!currentItem.getText3().equals("")){
    View someView = v.findViewById(R.id.ll);
    someView.setBackgroundColor(Color.parseColor("#7ed6df"));
    holder.c.setChecked(true);
}*/

       holder.mTextView1.setText(currentItem.getText2());

        holder.nb.setValue(Integer.parseInt(currentItem.getText3()));

        holder.nb1.setValue(Integer.parseInt(currentItem.getText4()));



      // holder.i.loadUrl("http://www.casbahdz.com/casbahtemplate/libs/image/produit.php?id="+currentItem.getText1()+"");

        View someView = v.findViewById(R.id.ll);// get Any child View

        if(!currentItem.getText1().equals("")){
            String s = currentItem.getText1().replace("data:image/png;base64,","");


            byte[] decodedString = Base64.decode(s, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.i.setImageBitmap(decodedByte);}
        else{
            holder.i.setImageResource(R.drawable.non);
        }

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