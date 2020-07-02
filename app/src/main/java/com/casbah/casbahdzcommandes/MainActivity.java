package com.casbah.casbahdzcommandes;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.casbah.casbahdzcommandes.ui.MainA;
import com.casbah.casbahdzcommandes.ui.main.BDD;
import com.casbah.casbahdzcommandes.ui.main.PlaceholderFragment;
import com.casbah.casbahdzcommandes.ui.main.SectionsPagerAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {
    private static final int CAMERA_PERMISSION_CODE = 1460;

    private static final int CAMERA_REQUEST_CODE = 1450;
    private String mCurrentPhotoPath;
    private ImageView ivCameraPreview;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String DATA_INSERT_URL = "http://www.casbahdz.com/CRUD.php";
    public ProgressDialog progress ;
    private String[] n,w,com,ad,nm,d,h,cd,ID;


    private FusedLocationProviderClient fusedLocationClient;
    private static  String longg="",lat="",nom="",num="",wilaya="",commune="",adresse="",heure="",date="";
    private int id=0;
private  FloatingActionButton fab;
    private static  List<String> Nom,quantite ;
    private static List<Integer> IDD;

private BDD bd;
    private static Integer[][] pid;
    private String [][] q;
    private int position=0;
 private ViewPager viewPager;
private RecyclerView mRecyclerView;
    private PlaceholderFragment someFragment;
private static int c=0;
public static double total=0;


public static void total(double t){
    total=t;
}

    public static double gettotal(){
        return  total;
    }

    public void v(View v){
        viewPager = findViewById(R.id.view_pager);
        Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.view_pager + ":" + viewPager.getCurrentItem());
        // based on the current position you can then cast the page to the correct
        // class and call the method:
        Log.e("PAGGEE",viewPager.getCurrentItem()+"");
        if (viewPager.getCurrentItem() == 1 && page != null) {
            ((PlaceholderFragment)page).v(v);
        }

    }


    public void enter(View v){
        viewPager = findViewById(R.id.view_pager);
        Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.view_pager + ":" + viewPager.getCurrentItem());
        // based on the current position you can then cast the page to the correct
        // class and call the method:
        Log.e("PAGGEE",viewPager.getCurrentItem()+"");
        if (viewPager.getCurrentItem() == 1 && page != null) {
            ((PlaceholderFragment)page).enter(v);
        }

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        IDD = new ArrayList<>();
        Nom = new ArrayList<>();
        quantite = new ArrayList<>();


        setContentView(R.layout.activity_main);
        bd= new BDD(this);

        bd.open();

        WebView ww= findViewById(R.id.web);



        ww.loadUrl("http://www.casbahdz.com/casbahtemplate/libs/image/pvente1.php?id="+bd.getIDD()+"");


        bd.close();
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        final TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingTextButton fab = findViewById(R.id.fab);

        progress = new ProgressDialog(this);
        progress.setTitle("Chargement");
        progress.setMessage("Attendez SVP...");
        progress.setCancelable(false);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             /*   Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

            }
        });
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.e("RRR",tab.getPosition()+"");
                if(tab.getPosition()==1){
                    FloatingTextButton fab = findViewById(R.id.fab);

                    fab.setVisibility(View.INVISIBLE);



                }
                else{
                    FloatingTextButton fab = findViewById(R.id.fab);

                    fab.setVisibility(View.VISIBLE);




                }
                //do stuff here
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


       Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);



        ww.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_MOVE){
                    return false;
                }

                if (event.getAction()==MotionEvent.ACTION_UP){
                    Intent myIntent = new Intent(MainActivity.this, profilemod.class);
                    startActivity(myIntent);

                }

                return false;
            }
        });

        verif();



    }
    public void verif(){
        bd.open();
        final String user=bd.getuser();
        final String  pass=bd.getpassword();
        id=bd.getIDD();
        bd.close();
        if(id!=0){
            AndroidNetworking.post(DATA_INSERT_URL)
                    .addBodyParameter("action","get pventee")
                    .addBodyParameter("id",id+"")

                    .setTag("test")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONArray(new JSONArrayRequestListener() {
                        @Override
                        public void onResponse(JSONArray response) {

                            if(response != null)
                                try {

                                    if(!user.equals(response.getJSONObject(0).getString("email")) || !pass.equals(response.getJSONObject(0).getString("password"))){
                                        bd.open();
                                        bd.dec();
                                        bd.close();
                                        Intent intent1 = new Intent(MainActivity.this, MainA.class);
                                        startActivity(intent1);
                                        finish();
                                    }



                                    // String responseString= response.get(0).toString();


                                } catch (JSONException e) {
                                    bd.open();
                                    bd.dec();
                                    bd.close();
                                    Intent intent1 = new Intent(MainActivity.this, MainA.class);
                                    startActivity(intent1);
                                    finish();

                                }

                        }

                        //ERROR
                        @Override
                        public void onError(ANError anError) {
                            Toast.makeText(MainActivity.this, "Pas de connexion", Toast.LENGTH_SHORT).show();

                        }


                    });     }

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.dec) {
            bd.open();
            bd.dec();
            bd.close();

            Intent intent = this.getBaseContext().getPackageManager().getLaunchIntentForPackage(this.getBaseContext().getPackageName() );
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
            return true;
        }


        if (id == R.id.profile) {

            Intent myIntent = new Intent(MainActivity.this, profilemod.class);
            startActivity(myIntent);
            return true;
        }
        if (id == R.id.apropos) {

            Intent myIntent = new Intent(MainActivity.this, apropos.class);
            startActivity(myIntent);
            return true;
        }
        if (id == R.id.retours) {

            Intent myIntent = new Intent(MainActivity.this, retours.class);
            startActivity(myIntent);
            return true;
        }
        if (id == R.id.mdpp) {

            Intent myIntent = new Intent(MainActivity.this, mdp.class);
            startActivity(myIntent);
            return true;
        }



        return super.onOptionsItemSelected(item);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    public void getcommandep(int id, final int size, final int pos){
        AndroidNetworking.post(DATA_INSERT_URL)
                .addBodyParameter("action","get commandep")
                .addBodyParameter("id",id+"")

                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        if(response != null)
                            try {






                                //SHOW RESPONSE FROM SERVER
                                for(int i=0;i<response.length();i++) {

                                    pid[pos][i] = response.getJSONObject(i).getInt("id_produit");

                                    q[pos][i] = response.getJSONObject(i).getString("quantite");
                                    Log.e("XXXXXXXXXXx",q[pos][i]+"  "+pid[pos][i]);

                                    if(i==response.length()-1 && pos==size-1){
                                        ajout();
                                        progress.dismiss();
                                    }





                                }



                                //bd();
                                // String responseString= response.get(0).toString();


                            } catch (JSONException e) {

                                progress.dismiss();
                                e.printStackTrace();
                                Toast.makeText(MainActivity.this, "Nom d'utilisateur où le mot de passe sont incorrectes ", Toast.LENGTH_SHORT).show();
                            }

                    }

                    //ERROR
                    @Override
                    public void onError(ANError anError) {
                        progress.dismiss();
                        anError.printStackTrace();

                    }


                });
    }






    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public void ajout(){



        ArrayList<Item> exampleList = new ArrayList<>();

        for(int i=0;i<cd.length;i++) {







            exampleList.add(new Item("",cd[i],""));

        }




         mRecyclerView = findViewById(R.id.recycler);

        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
        Adapter mAdapter = new Adapter(exampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);





    }



    public static void copyID(Integer[] id){
        IDD = new ArrayList<>();
        for(int i=0;i<id.length;i++) {
            if (id[i] != null){
                IDD.add(id[i]);
            }

        }

    }

    public static void copyQ(String[] Q){
        quantite = new ArrayList<>();
        for(int i=0;i<Q.length;i++) {
            if (Q[i] != null)
                quantite.add(Q[i]);}

    }
    public static void supp(int pos){

        for(int i=0;i<quantite.size();i++) {
            if (IDD.get(i) == pos){
                quantite.remove(i);
            IDD.remove(i);
            }}
    }
    public static void changeQ(int pos,int q){

        for(int i=0;i<quantite.size();i++) {
            if (IDD.get(i) == pos){
                quantite.set(i,q+"");
            }}




    }

    public static List<Integer> getIDD(){
        return IDD;
    }

    public static List<String> getQ(){
        return quantite;
    }




}