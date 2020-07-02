package com.casbah.casbahdzcommandes;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.casbah.casbahdzcommandes.ui.main.BDD;
import com.casbah.casbahdzcommandes.ui.main.PlaceholderFragment;
import com.casbah.casbahdzcommandes.R;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class shop extends AppCompatActivity {
    private EditText mail;
    private Button send_p;
    private String e;
    private static final String DATA_INSERT_URL="http://www.casbahdz.com/CRUD.php";
    public ProgressDialog progress ;
    private int id=0,cpt=0;
    public BDD bd;
    private String[] photo,quantite,nom,QUANTITE,prix,fardeau,famille;
    private Integer[] ID,IDD,ind;
private int click=0;
    private  RecyclerView mRecyclerView;
    private int position;
    private EditText theFilter;
    private String motif="";
    private  AutoCompleteTextView editTextFilledExposedDropdown;
    private double total=0;

    /** Called when the activity is first created. */
    public void onDestroy() {

        super.onDestroy();



        MainActivity.total(total);


    }
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop);
        IDD=new Integer[150];
        QUANTITE=new String[150];
        bd=new BDD(shop.this);
        total=MainActivity.gettotal();
        progress = new ProgressDialog(this);
        progress.setTitle("Chargement");
        progress.setMessage("Attendez SVP...");
        progress.setCancelable(false);
        Intent i = getIntent();
        Bundle extras = i.getExtras();
        if (extras.containsKey("motif")) {
           motif= i.getStringExtra("motif");



        }

       /* Intent i = getIntent();
        Bundle extras = i.getExtras();
        if (extras.containsKey("commande")) {
            String commande= i.getStringExtra("pvente");


        }
        if (extras.containsKey("id")) {
            String id= i.getStringExtra("id");
            TextInputEditText idd= findViewById(R.id.ID);
            idd.setText(id);

        }
        if (extras.containsKey("num")) {
            String id= i.getStringExtra("num");
            TextInputEditText idd= findViewById(R.id.num);
            idd.setText(id);

        }
        if (extras.containsKey("nom")) {
            String id= i.getStringExtra("nom");
            TextInputEditText idd= findViewById(R.id.nom);
            idd.setText(id);

        }
        if (extras.containsKey("date")) {
            String id= i.getStringExtra("date");
            TextInputEditText idd= findViewById(R.id.date);
            idd.setText(id);

        }
        if (extras.containsKey("heure")) {
            String id= i.getStringExtra("heure");
            TextInputEditText idd= findViewById(R.id.heure);
            idd.setText(id);

        }
        if (extras.containsKey("adresse")) {
            String id= i.getStringExtra("adresse");
            TextInputEditText idd= findViewById(R.id.adresse);
            idd.setText(id);

        }
        if (extras.containsKey("wilaya")) {
            String id= i.getStringExtra("wilaya");
            AutoCompleteTextView idd= findViewById(R.id.wilaya);
            idd.setText(id);

        }
        if (extras.containsKey("commune")) {
            String id= i.getStringExtra("commune");
            AutoCompleteTextView idd= findViewById(R.id.commune);
            idd.setText(id);

        }

        if (extras.containsKey("id_c")) {
            id= Integer.parseInt(i.getStringExtra("id_c"));


        }*/
       get_liv();

        ImageButton bb = findViewById(R.id.datexx);
        bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();

            }
        });

        Onclick();

        theFilter = (EditText) findViewById(R.id.searchFilter);
        famille();
        filt();


    }

    public void famille(){
        AndroidNetworking.post(DATA_INSERT_URL)
                .addBodyParameter("action","get familles")


                .setTag("test")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void onResponse(JSONArray response) {

                        if(response != null)
                            try {



                                String[] COUNTRIES= new String[response.length()];
                                //SHOW RESPONSE FROM SERVER
                                for(int i=0;i<response.length();i++) {

                                   COUNTRIES[i] = response.getJSONObject(i).getString("famille");







                                }

                                ArrayAdapter<String> adapter =
                                        new ArrayAdapter<>(
                                                shop.this,
                                                R.layout.dropdown_menu_popup_item,
                                                COUNTRIES);

                               editTextFilledExposedDropdown = findViewById(R.id.famille);
                                editTextFilledExposedDropdown.setAdapter(adapter);
                                if(COUNTRIES.length>0)
editTextFilledExposedDropdown.setText(COUNTRIES[0],false);
                                editTextFilledExposedDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View arg1, int pos,
                                                            long id) {
                                        theFilter.setText("");
                                        get_liv(editTextFilledExposedDropdown.getText().toString());

                                    }
                                });


                                //bd();
                                // String responseString= response.get(0).toString();


                            } catch (JSONException e) {

                                progress.dismiss();
                                e.printStackTrace();
                                Toast.makeText(shop.this, "Nom d'utilisateur où le mot de passe sont incorrectes ", Toast.LENGTH_SHORT).show();
                            }

                    }

                    //ERROR
                    @Override
                    public void onError(ANError anError) {
                        anError.printStackTrace();


                    }


                });



    }

    public void infos(View v){

        final Dialog dialog = new Dialog(shop.this);
        dialog.setContentView(R.layout.window);
        dialog.setTitle(nom[position]);

        // set the custom dialog components - text, image and button
        TextView text = (TextView) dialog.findViewById(R.id.text);
        text.setText(nom[position]+"\nFamille : "+famille[position]+"\nBouteilles dans un fardeau : "+fardeau[position]+"\nPrix : "+prix[position]);
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        String s = photo[position].replace("data:image/png;base64,","");
        Log.e("BASEE64",s);

        byte[] decodedString = Base64.decode(s, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        image.setImageBitmap(decodedByte);

        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();




    }


    public void get_liv(){
        progress.show();
        final int[] x = {0};
        bd.open();
        id=bd.getIDD();
        bd.close();

        AndroidNetworking.post(DATA_INSERT_URL)
                .addBodyParameter("action","get produit")
                .addBodyParameter("id",id+"")


                .setTag("test")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        if(response != null)
                            try {

                              /*  n=new String[response.length()];
                                w=new String[response.length()];
                                com=new String[response.length()];
                                ad=new String[response.length()];
                                nm=new String[response.length()];
                                d=new String[response.length()];
                                h=new String[response.length()];

                               */
                                photo=new String[response.length()];
                                fardeau=new String[response.length()];
                               famille=new String[response.length()];

                                ID=new Integer[response.length()];
                                nom=new String[response.length()];

                                prix=new String[response.length()];








                                //SHOW RESPONSE FROM SERVER
                                for(int i=0;i<response.length();i++) {

                                    photo[i] = response.getJSONObject(i).getString("photo");
                                   fardeau[i] = response.getJSONObject(i).getString("fardeau");
                                    famille[i] = response.getJSONObject(i).getString("famille");

                                    ID[i] = response.getJSONObject(i).getInt("id");
                                    nom[i] = response.getJSONObject(i).getString("nom");
                                    prix[i] = response.getJSONObject(i).getString("prix_f")+" DA/Fardeau";



                                  /*  n[i] = response.getJSONObject(i).getString("nom");
                                    nm[i] = response.getJSONObject(i).getString("num");
                                    ad[i] = response.getJSONObject(i).getString("adresse");
                                    com[i] = response.getJSONObject(i).getString("commune");
                                    w[i] = response.getJSONObject(i).getString("wilaya");
                                    d[i] = response.getJSONObject(i).getString("date");
                                    h[i] = response.getJSONObject(i).getString("heure");
                                    cd[i] = response.getJSONObject(i).getString("commande");*/



if(i==response.length()-1)
    get();




                                }








                                //bd();
                                // String responseString= response.get(0).toString();


                            } catch (JSONException e) {

                                progress.dismiss();
                                e.printStackTrace();
                                Toast.makeText(shop.this, "Nom d'utilisateur où le mot de passe sont incorrectes ", Toast.LENGTH_SHORT).show();
                            }

                    }

                    //ERROR
                    @Override
                    public void onError(ANError anError) {
                        anError.printStackTrace();
                       /*
                        n=new String[0];


                        w=new String[0];
                        com=new String[0];
                        ad=new String[0];
                        nm=new String[0];
                        d=new String[0];
                        h=new String[0];
                        cd=new String[0];
*/
                        ID=null;
                        photo=new String[0];
                        famille=new String[0];
                        fardeau=new String[0];

                        nom=new String[0];



                        ajout1();
                    }


                });


    }

    public void get_liv(String f){
        progress.show();
        final int[] x = {0};
        ID=null;
        ind=null;
        photo=new String[0];
        famille=new String[0];
        fardeau=new String[0];

        nom=new String[0];
        bd.open();
        id=bd.getIDD();
        bd.close();
        AndroidNetworking.post(DATA_INSERT_URL)
                .addBodyParameter("action","get produitF")

                .addBodyParameter("famille",f)
                .addBodyParameter("id",id+"")

                .setTag("test")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        if(response != null)
                            try {

                              /*  n=new String[response.length()];
                                w=new String[response.length()];
                                com=new String[response.length()];
                                ad=new String[response.length()];
                                nm=new String[response.length()];
                                d=new String[response.length()];
                                h=new String[response.length()];

                               */
                                photo=new String[response.length()];
                                fardeau=new String[response.length()];
                                famille=new String[response.length()];

                                ID=new Integer[response.length()];
                                nom=new String[response.length()];
                                prix=new String[response.length()];








                                //SHOW RESPONSE FROM SERVER
                                for(int i=0;i<response.length();i++) {

                                    photo[i] = response.getJSONObject(i).getString("photo");
                                    fardeau[i] = response.getJSONObject(i).getString("fardeau");
                                    famille[i] = response.getJSONObject(i).getString("famille");

                                    ID[i] = response.getJSONObject(i).getInt("id");
                                    nom[i] = response.getJSONObject(i).getString("nom");
                                    prix[i] = response.getJSONObject(i).getString("prix_f")+" DA/Fardeau";



                                  /*  n[i] = response.getJSONObject(i).getString("nom");
                                    nm[i] = response.getJSONObject(i).getString("num");
                                    ad[i] = response.getJSONObject(i).getString("adresse");
                                    com[i] = response.getJSONObject(i).getString("commune");
                                    w[i] = response.getJSONObject(i).getString("wilaya");
                                    d[i] = response.getJSONObject(i).getString("date");
                                    h[i] = response.getJSONObject(i).getString("heure");
                                    cd[i] = response.getJSONObject(i).getString("commande");*/



                                    if(i==response.length()-1)
                                        ajout1();




                                }








                                //bd();
                                // String responseString= response.get(0).toString();


                            } catch (JSONException e) {

                                progress.dismiss();
                                e.printStackTrace();
                                Toast.makeText(shop.this, "Nom d'utilisateur où le mot de passe sont incorrectes ", Toast.LENGTH_SHORT).show();
                            }

                    }

                    //ERROR
                    @Override
                    public void onError(ANError anError) {
                        anError.printStackTrace();
                       /*
                        n=new String[0];


                        w=new String[0];
                        com=new String[0];
                        ad=new String[0];
                        nm=new String[0];
                        d=new String[0];
                        h=new String[0];
                        cd=new String[0];
*/
                        ID=null;
                        photo=new String[0];
                        famille=new String[0];
                        fardeau=new String[0];

                        nom=new String[0];




                        ajout1();
                    }


                });


    }

    public void get(){
        List<String> q = new ArrayList<>();
        List<Integer> id= new ArrayList<>();

        q=MainActivity.getQ();
        id=MainActivity.getIDD();

        for(int i=0;i<q.size();i++){
            IDD[i]=id.get(i);
            cpt++;
        }
        for(int i=0;i<q.size();i++){
            QUANTITE[i]=q.get(i);
        }



        ajout1();
        total1();


    }


    public void ajout1(){

progress.dismiss();

        ArrayList<Item1> exampleList = new ArrayList<>();

        for(int i=0;i<photo.length;i++) {

            int e=0;

            for(int j=0;j<cpt;j++) {


                if(IDD[j]==ID[i] ) {
                    exampleList.add(new Item1(photo[i], nom[i] , "oui",QUANTITE[j]+"",prix[i]));
                    e=1;
                }

            }
            if(e==0){
                exampleList.add(new Item1(photo[i], nom[i] , "","",prix[i]));
            }


        }




        mRecyclerView = findViewById(R.id.recycler);

        mRecyclerView.setHasFixedSize(true);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this,2);
        Adapter1 mAdapter = new Adapter1(exampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);





    }
    public void Onclick(){
        mRecyclerView = findViewById(R.id.recycler);

        mRecyclerView.addOnItemTouchListener(            new PlaceholderFragment.RecyclerItemClickListener(this, new PlaceholderFragment.RecyclerItemClickListener.OnItemClickListener() {

            public void onItemClick(View v, int pos) {


                    position = pos;



                    //Set TextView text color
                    if (ind != null)
                        position = ind[position];



            }

            public boolean onTouch(View v, MotionEvent event){
return true;
            }



        }));




    }

public void quantite(View v){
        test();
   /* final NumberPicker task = new NumberPicker(shop.this);
    if (!vselect(ID[position])) {
        task.setMinValue(1);
        task.setMaxValue(150);
        task.setWrapSelectorWheel(true);
        AlertDialog dialog = new AlertDialog.Builder(shop.this)
                .setTitle("" + nom[position])
                .setMessage("Quantité souhaitée en fardeau")
                .setView(task)
                .setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String quan = String.valueOf(task.getValue());
                        if (Integer.parseInt(quan) != 0 && !quan.isEmpty()) {
                            QUANTITE[cpt] = quan;
                            IDD[cpt] = ID[position];
                            total = total + (Double.parseDouble(prix[position].replace(" DA/Fardeau", "")) * Double.parseDouble(QUANTITE[cpt]));

                            cpt++;
                            total();

                            MAJ();

                            if (theFilter.getText().toString() != "")
                                ccc(theFilter.getText().toString());
                            else
                                cc();


                        } else {

                        }


                    }
                })
                .setNegativeButton("Annuler", null)
                .create();
        dialog.show();


    }

    else{
        for(int i=0;i<IDD.length;i++) {
            if (IDD[i]==ID[position])
                total = total - (Double.parseDouble(prix[position].replace(" DA/Fardeau", "")) * Double.parseDouble(QUANTITE[i]));
        }
        faire(ID[position]);





    }*/
}



    public void faire(int v){
        for(int i=0;i<cpt;i++){

            if (IDD[i] == v) {
                for (int j = i; j < cpt; j++) {
                    if(j+1<=cpt-1){
                    if (IDD[j + 1] != null) {
                        IDD[j] = IDD[j + 1];
                        QUANTITE[j] = QUANTITE[j + 1];
                    }}
                    else if(j==0){
                        IDD[j]=null;
                        QUANTITE[j]=null;
                    }

                    if(j==cpt-1){
                        IDD[j]=null;
                        QUANTITE[j]=null;

                    }


                }
                cpt--;
                MAJ();


            }




        }
        total();
        if(theFilter.getText().toString()!="")
            ccc(theFilter.getText().toString());
        else
            cc();



    }
    public void MAJ(){

MainActivity.copyID(IDD);
MainActivity.copyQ(QUANTITE);


    }


    public void cc(){
        ArrayList<Item1> exampleList = new ArrayList<>();

        for(int i=0;i<photo.length;i++) {


int e=0;

            for(int j=0;j<cpt;j++) {


            if(IDD[j]==ID[i]) {
                exampleList.add(new Item1(photo[i], nom[i] , "oui",QUANTITE[j]+"",prix[i]));
                e=1;
            }

                }
            if(e==0){
                exampleList.add(new Item1(photo[i], nom[i] , "","",prix[i]));
            }

        }




        mRecyclerView = findViewById(R.id.recycler);

        mRecyclerView.setHasFixedSize(true);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this,2);
        Adapter1 mAdapter = new Adapter1(exampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }



    public boolean vselect(int v){
        if(IDD!=null){
        for(int i=0;i<cpt;i++){
            if(IDD[i]!=null) {
                if (IDD[i] == v)
                    return true;
            }

        }}
        return false;


    }
    public void filt(){

        theFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int k, int i1, int i2) {

                ArrayList<Item1> exampleList = new ArrayList<>();
                ind= new Integer[ID.length];
int c=0;
                for(int i=0;i<photo.length;i++) {
                 if(nom[i].toLowerCase().contains(charSequence.toString().toLowerCase())) {
                     ind[c]=i;
                     c++;
                     int e = 0;

                     for (int j = 0; j < cpt; j++) {


                         if (IDD[j] == ID[i]) {
                             exampleList.add(new Item1(photo[i], nom[i] , "oui",QUANTITE[j]+"",prix[i]));
                             e = 1;
                         }

                     }
                     if (e == 0) {
                         exampleList.add(new Item1(photo[i], nom[i] , "","",prix[i]));
                     }
                 }


                }




                mRecyclerView = findViewById(R.id.recycler);

                mRecyclerView.setHasFixedSize(true);
                GridLayoutManager mLayoutManager = new GridLayoutManager(shop.this,2);
                Adapter1 mAdapter = new Adapter1(exampleList);

                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);



            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }


    public void ccc(String d){

        ArrayList<Item1> exampleList = new ArrayList<>();
        int c=0;
        ind= new Integer[ID.length];

        for(int i=0;i<photo.length;i++) {
            if(nom[i].toLowerCase().contains(d.toLowerCase())) {
                ind[c]=i;
                c++;
                int e = 0;

                for (int j = 0; j < cpt; j++) {


                    if (IDD[j] == ID[i]) {
                        exampleList.add(new Item1(photo[i], nom[i] , "oui",QUANTITE[j]+"",prix[i]));
                        e = 1;
                    }

                }
                if (e == 0) {
                    exampleList.add(new Item1(photo[i], nom[i] , "","",prix[i]));
                }
            }


        }




        mRecyclerView = findViewById(R.id.recycler);

        mRecyclerView.setHasFixedSize(true);
        GridLayoutManager mLayoutManager = new GridLayoutManager(shop.this,2);
        Adapter1 mAdapter = new Adapter1(exampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }


    public void total(){



        TextView t=findViewById(R.id.totalp);
        t.setText("Total : "+(int)total+" DA");
        TextView tt=findViewById(R.id.totalQQ);
        tt.setText(""+cpt);

    }


    public void total1(){



        TextView t=findViewById(R.id.totalp);
        t.setText("Total : "+MainActivity.gettotal()+" DA");
        TextView tt=findViewById(R.id.totalQQ);
        tt.setText(""+cpt);

    }


    public void test(){
bd.open();
id=bd.getIDD();
bd.close();
        progress.show();
        AndroidNetworking.post(DATA_INSERT_URL)
                .addBodyParameter("action","get quantitec")
                .addBodyParameter("id",id+"")
                .addBodyParameter("id_produit",ID[position]+"")

                .setTag("test")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        if(response != null)
                            try {






                                progress.dismiss();


                                //SHOW RESPONSE FROM SERVER


                                      if(!response.get(0).toString().equals("0") &&(Integer.parseInt(response.get(0).toString())>0)){
                                          final NumberPicker task = new NumberPicker(shop.this);
                                          if (!vselect(ID[position])) {
                                              task.setMinValue(1);
                                              task.setMaxValue(Integer.parseInt(response.get(0).toString()));
                                              task.setWrapSelectorWheel(true);
                                              AlertDialog dialog = new AlertDialog.Builder(shop.this)
                                                      .setTitle("" + nom[position])
                                                      .setMessage("Quantité souhaitée en fardeau")
                                                      .setView(task)
                                                      .setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
                                                          @Override
                                                          public void onClick(DialogInterface dialog, int which) {
                                                              String quan = String.valueOf(task.getValue());
                                                              if (Integer.parseInt(quan) != 0 && !quan.isEmpty()) {
                                                                  QUANTITE[cpt] = quan;
                                                                  IDD[cpt] = ID[position];
                                                                  total = total + (Double.parseDouble(prix[position].replace(" DA/Fardeau", "")) * Double.parseDouble(QUANTITE[cpt]));

                                                                  cpt++;
                                                                  total();

                                                                  MAJ();

                                                                  if (theFilter.getText().toString() != "")
                                                                      ccc(theFilter.getText().toString());
                                                                  else
                                                                      cc();


                                                              } else {

                                                              }


                                                          }
                                                      })
                                                      .setNegativeButton("Annuler", null)
                                                      .create();
                                              dialog.show();


                                          }

                                          else{
                                              for(int i=0;i<IDD.length;i++) {
                                                  if (IDD[i]==ID[position])
                                                      total = total - (Double.parseDouble(prix[position].replace(" DA/Fardeau", "")) * Double.parseDouble(QUANTITE[i]));
                                              }
                                              faire(ID[position]);





                                          }

                                      }
                                      else{
                                          Toast.makeText(shop.this, "Produit non disponible", Toast.LENGTH_SHORT).show();


                                      }










                                //bd();
                                // String responseString= response.get(0).toString();


                            } catch (JSONException e) {

                                progress.dismiss();
                                e.printStackTrace();
                                Toast.makeText(shop.this, "Produit non disponible", Toast.LENGTH_SHORT).show();
                            }

                    }

                    //ERROR
                    @Override
                    public void onError(ANError anError) {
                        progress.dismiss();
                        anError.printStackTrace();
                        Toast.makeText(shop.this, "Produit non disponible", Toast.LENGTH_SHORT).show();


                    }


                });

    }


}


