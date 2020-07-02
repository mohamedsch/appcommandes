package com.casbah.casbahdzcommandes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.casbah.casbahdzcommandes.R;

import java.util.ArrayList;


public class Adapter extends RecyclerView.Adapter<Adapter.ExampleViewHolder> {
    private ArrayList<Item> mExampleList;
    private int selected_item=-1,ver=0;
    private View hold,v;


    public static class ExampleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mTextView1;
        public TextView mTextView2;
        public TextView mTextView3;

        public ImageView i;
        public ImageView i2;





        public ExampleViewHolder(View itemView) {
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.textC1);

            mTextView2 = itemView.findViewById(R.id.textC2);
            mTextView3 = itemView.findViewById(R.id.textC3);

            i = itemView.findViewById(R.id.image);


        }




        public void onClick(View v) {

            if (v.getId() == i.getId()) {

            } else if (v.getId() == i2.getId()) {

            }
    }}

    public Adapter(ArrayList<Item> exampleList) {
        mExampleList = exampleList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        hold=v;
        return evh;
    }


    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        Item currentItem = mExampleList.get(position);

        holder.mTextView1.setText(currentItem.getText1());
        holder.mTextView2.setText(currentItem.getText2());

        holder.mTextView3.setText(currentItem.getText3());

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