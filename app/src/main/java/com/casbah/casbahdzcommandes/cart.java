package com.casbah.casbahdzcommandes;


import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.casbah.casbahdzcommandes.ui.main.BDD;
import com.casbah.casbahdzcommandes.ui.main.PlaceholderFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.NumberPicker;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class cart extends AppCompatActivity {
    private EditText mail;
    private Button send_p;
    private TextInputEditText D,H;
    private String e;
    private static final String DATA_INSERT_URL="http://www.casbahdz.com/CRUD.php";
    public ProgressDialog progress ;
    private int id_commande=0;
    private FloatingActionButton fab;
    private static final int PERMISSIONS_REQUEST = 100;
    private static final int CAMERA_REQUEST_CODE = 1450;
    private String mCurrentPhotoPath,b;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int CAMERA_PERMISSION_CODE = 1460;
    private FusedLocationProviderClient fusedLocationClient;
private         int cppt=0;

    public BDD bd;
    private String[] photo,quantite,nom,QUANTITE,prix,QUANTITEX;
    private Integer[] ID,IDD,ind;
    private int cpt=0,bool=0;
    private RecyclerView mRecyclerView;
    private int position;
    private EditText theFilter;
    private boolean  shouldRefreshOnResume=false;
private View viewtotal;
private int verif=0;
    private static String commande="",id="",num="",nomm="",date="",heure="",wilaya="",adresse="",commune="";
    public static void set(String nm,String nom,String dte,String eure,String wilay,String ad,String com){
        num=nm;
       nomm=nom;
        date=dte;
        heure=eure;
        wilaya=wilay;
        adresse=ad;
       commune=com;
    }
    public void onResume(){


        super.onResume();
        //update your fragment
        if(shouldRefreshOnResume) {

            get_liv();


        }
    }
    public void onStop() {
        super.onStop();
        shouldRefreshOnResume = true;
    }


    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);

        bd=new BDD(cart.this);
        progress = new ProgressDialog(this);
        progress.setTitle("Chargement");
        progress.setMessage("Attendez SVP...");
        progress.setCancelable(false);
        //fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        Intent i = getIntent();
        Bundle extras = i.getExtras();
        if (extras.containsKey("commande")) {

           commande= i.getStringExtra("pvente");


        }
        if (extras.containsKey("id")) {
           id= i.getStringExtra("id");
            //TextInputEditText idd= findViewById(R.id.ID);
            //idd.setText(id);

        }
        if (extras.containsKey("num")) {
            num= i.getStringExtra("num");
           // TextInputEditText idd= findViewById(R.id.num);
          //  idd.setText(id);

        }
        if (extras.containsKey("nom")) {
           nomm= i.getStringExtra("nom");
           // TextInputEditText idd= findViewById(R.id.nom);
           // idd.setText(id);

        }
        if (extras.containsKey("date")) {
            date= i.getStringExtra("date");
           // TextInputEditText idd= findViewById(R.id.date);
            //idd.setText(id);

        }
        if (extras.containsKey("heure")) {
            heure= i.getStringExtra("heure");
           // TextInputEditText idd= findViewById(R.id.heure);
            //idd.setText(id);

        }
        if (extras.containsKey("adresse")) {
            adresse= i.getStringExtra("adresse");
            //TextInputEditText idd= findViewById(R.id.adresse);
            //idd.setText(id);

        }
        if (extras.containsKey("wilaya")) {
            wilaya= i.getStringExtra("wilaya");
            //AutoCompleteTextView idd= findViewById(R.id.wilaya);
            //idd.setText(id);

        }
        if (extras.containsKey("commune")) {
            commune= i.getStringExtra("commune");
          //  AutoCompleteTextView idd= findViewById(R.id.commune);
           // idd.setText(id);

        }

        if (extras.containsKey("id_c")) {
            id_commande= Integer.parseInt(i.getStringExtra("id_c"));


        }
        /*Locale locale = getResources().getConfiguration().locale;
        Locale.setDefault(locale);
        D = findViewById(R.id.date);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        D.setInputType(InputType.TYPE_NULL);
        D.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(cart.this,R.style.DialogTheme,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                    month++;
                                    String d=year+"-"+convertDate(month)+"-"+convertDate(day);
                                    D.setText(d+"");


                                }
                            }, year, month, dayOfMonth);

                    datePickerDialog.show();

                }
            }
        });
        D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(cart.this,R.style.DialogTheme,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                month++;
                                String d=year+"-"+convertDate(month)+"-"+convertDate(day);
                                D.setText(d+"");


                            }
                        }, year, month, dayOfMonth);

                datePickerDialog.show();}
        });


        ImageButton b = findViewById(R.id.add);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             /*   Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


                Intent myIntent = new Intent(cart.this, shop.class);
                startActivity(myIntent);
            }
        });


        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             /*   Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                new AlertDialog.Builder(cart.this)
                        .setMessage("Etes vous sur de modifier cette commande ?")
                        .setCancelable(false)
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                envoyer();


                            }
                        })
                        .setNegativeButton("Non", null)
                        .show();

            }
        });

        ImageButton p= findViewById(R.id.photo);
        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             /*   Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                if (EasyPermissions.hasPermissions(cart.this, Manifest.permission.CAMERA)) {
                    launchCamera();

                } else {
                    //If permission is not present request for the same.
                    EasyPermissions.requestPermissions(this, getString(R.string.permission_text), CAMERA_PERMISSION_CODE, Manifest.permission.CAMERA);
                }

            }
        });
*/




        get_liv();
        Onclick();

        ImageButton bb = findViewById(R.id.datex);
        bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(verif==1) {
                    new AlertDialog.Builder(cart.this)
                            .setTitle("Verification")
                            .setMessage("Etes-vous sur d'abandonner les modifications ?")
                            .setNegativeButton("Non", null)
                            .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    Integer[] pid = new Integer[0];
                                    String[] q = new String[0];
                                    MainActivity.copyID(pid);
                                    MainActivity.total(0);
                                    MainActivity.copyQ(q);
                                    finish();


                                }
                            })
                            .setNegativeButton("Non", null)
                            .show();

                }



                else{
                    Integer[] pid = new Integer[0];
                    String[] q= new String[0];
                    MainActivity.copyID(pid);
                    MainActivity.total(0);
                    MainActivity.copyQ(q);
                    finish();
                }


            }
        });
        ImageButton b = findViewById(R.id.add);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             /*   Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                double total=0;
                int c=0;
                if(cpt!=0){
                    for (int i=0;i<prix.length;i++){
                        for(int j=0;j<IDD.length;j++) {

                            if(IDD[j]==ID[i]) {
                                c++;
                                total = total + (Double.parseDouble(prix[i].replace(" DA", "")) * Double.parseDouble(QUANTITE[j]));
                            }
                        }


                    }}
                MainActivity.total(total);

                Intent myIntent = new Intent(cart.this, shop.class);
                myIntent.putExtra("motif", "CC");
                startActivity(myIntent);
            }
        });


        ImageButton bbb = findViewById(R.id.modif);

        bbb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             /*   Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/


                Intent myIntent = new Intent(cart.this, mod.class);



                myIntent.putExtra("id", id + "");


                myIntent.putExtra("commande", commande + "");
                myIntent.putExtra("wilaya", wilaya + "");

                myIntent.putExtra("commune", commune + "");
                myIntent.putExtra("adresse", adresse + "");
                myIntent.putExtra("id_c", id_commande + "");
                myIntent.putExtra("num", num + "");
                myIntent.putExtra("nom", nomm+ "");
                myIntent.putExtra("date", date+ "");
                myIntent.putExtra("heure", heure + "");

                startActivity(myIntent);
            }
        });




    }

    public void sup(View v){
        new AlertDialog.Builder(v.getContext())
                .setMessage("Etes-vous sur de supprimer cet article ?")
                .setCancelable(false)
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.supp(IDD[position]);
                        verif=1;

                        get();

                        List<String> q = new ArrayList<>();
                        List<Integer> idd= new ArrayList<>();

                        q=MainActivity.getQ();
                        idd=MainActivity.getIDD();
                        QUANTITEX=new String[idd.size()];



                        for(int j=0;j<idd.size();j++){
                            QUANTITEX[j]=q.get(j);
                        }


                    }
                })
                .setNegativeButton("Non", null)
                .show();

    }


    private void launchCamera() {

        Intent cameraIntent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);

        // Create the File where the photo should go
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            // Error occurred while creating the File
        }
        // Continue only if the File was successfully created
        if (photoFile != null) {
            Uri photoURI = FileProvider.getUriForFile(cart.this,
                    "com.example.casbahdzcommandes",
                    photoFile);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            //Start the camera application
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
        }
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Preview the image captured by the camera
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE) {
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 4;
            Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
            // ivCameraPreview.setImageBitmap(bitmap);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            if(bitmap!=null){
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

                byte[] byte_arr = stream.toByteArray();
                String temp = Base64.encodeToString(byte_arr, Base64.DEFAULT);}
        }
    }





    public void GPStask(){
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // finish();

        }
        else {
            //Check whether this app has access to the location permission//


            int permission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION);

//If the location permission has been granted, then start the TrackerService//

            if (permission == PackageManager.PERMISSION_GRANTED) {

            } else {

//If the app doesn’t currently have access to the user’s location, then request access//

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST);

            }

        }



    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[]
            grantResults) {


//If the permission has been granted...//
        if (EasyPermissions.hasPermissions(cart.this, Manifest.permission.CAMERA)) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, cart.this);
            launchCamera();

        }

        else {if (requestCode == PERMISSIONS_REQUEST && grantResults.length == 1
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            //...then start the GPS tracking service//

        } else  {

//If the user denies the permission request, then display a toast with some more information//

            Toast.makeText(cart.this, "Please enable location services to allow GPS tracking", Toast.LENGTH_SHORT).show();
        }}
    }



    public void onPermissionsGranted(int requestCode, List<String> perms) {
        launchCamera();
    }


    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "Permission has been denied");
    }




    private void send(){
        send_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                send_p.setClickable(false);
                progress.show();
                //GET VALUES
                String email=mail.getText().toString();


                //BASIC CLIENT SIDE VALIDATION
                if((email.length()<1  ))
                {
                    Toast.makeText(getApplicationContext(), "entrer l'email SVP", Toast.LENGTH_SHORT).show();
                    send_p.setClickable(true);
                    progress.dismiss();
                }
                else{
                    mail(email);

                }
            }
        });

    }
    public void mail(String email) {
        e=email;
        if(email==null )
        {
            Toast.makeText(getApplicationContext(), "Remplissez tous les champs SVP", Toast.LENGTH_SHORT).show();
        }
        else
        {
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            AndroidNetworking.post(DATA_INSERT_URL)
                    .addBodyParameter("action","send")
                    .addBodyParameter("mail",email)
                    .setTag("test")
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONArray(new JSONArrayRequestListener() {
                        @Override
                        public void onResponse(JSONArray response) {

                            if(response != null)
                                try{
                                    send_p.setClickable(true);
                                    progress.dismiss();

                                    //SHOW RESPONSE FROM SERVER
                                    String  password=response.getJSONObject(0).getString("password");
                                    String rep=response.get(0).toString();





                                } catch (JSONException e) {
                                    progress.dismiss();
                                    send_p.setClickable(true);
                                    e.printStackTrace();
                                    Toast.makeText(getApplicationContext(), "Mail n'existe pas", Toast.LENGTH_SHORT).show();
                                }

                        }

                        //ERROR
                        @Override
                        public void onError(ANError anError) {
                            progress.dismiss();
                            send_p.setClickable(true);
                            Toast.makeText(getApplicationContext(), "Erreur de connexion", Toast.LENGTH_SHORT).show();
                        }


                    });

        }

    }


    public String convertDate(int input) {
        if (input >= 10) {
            return String.valueOf(input);
        } else {
            return "0" + String.valueOf(input);
        }
    }
    public void onBackPressed() {
        if(verif==1){
        new AlertDialog.Builder(this)
                .setTitle("Verification")
                .setMessage("Etes-vous sur d'abandonner les modifications ?")
                .setNegativeButton("Non", null)
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        Integer[] pid = new Integer[0];
                        String[] q = new String[0];
                        MainActivity.copyID(pid);
                        MainActivity.copyQ(q);
                        cart.super.onBackPressed();
                    }
                }).create().show();}
        else{
            Integer[] pid = new Integer[0];
            String[] q = new String[0];
            MainActivity.copyID(pid);
            MainActivity.copyQ(q);
            cart.super.onBackPressed();
        }
    }

    public void onDestroy() {

        super.onDestroy();



        Integer[] pid = new Integer[0];
        String[] q= new String[0];
        MainActivity.copyID(pid);
        MainActivity.copyQ(q);
        MainActivity.total(0);


    }

    public void get_liv(){
        progress.show();
        final int[] x = {0};
        bd.open();
        int id=bd.getIDD();
        bd.close();

        AndroidNetworking.post(DATA_INSERT_URL)
                .addBodyParameter("action","get produittt")
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
                                ID=new Integer[response.length()];
                                nom=new String[response.length()];
                                IDD=new Integer[response.length()];
                                QUANTITE=new String[response.length()];
                                prix=new String[response.length()];




                                List<String> q = new ArrayList<>();
                                List<Integer> id= new ArrayList<>();

                                q=MainActivity.getQ();
                                id=MainActivity.getIDD();
                                QUANTITEX=new String[id.size()];



                                for(int j=0;j<id.size();j++){
                                    QUANTITEX[j]=q.get(j);
                                }



                                //SHOW RESPONSE FROM SERVER
                                for(int i=0;i<response.length();i++) {

                                    photo[i] = "";
                                    ID[i] = response.getJSONObject(i).getInt("id");
                                    nom[i] = response.getJSONObject(i).getString("nom");
                                    prix[i] = response.getJSONObject(i).getString("prix_f")+" DA";



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
                                Toast.makeText(cart.this, "Nom d'utilisateur où le mot de passe sont incorrectes ", Toast.LENGTH_SHORT).show();
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
                        IDD=new Integer[0];
                        nom=new String[0];
                        QUANTITE=new String[0];

                        QUANTITEX=new String[0];


                        ajout1();
                    }


                });


    }

    public void get(){
        cpt=0;
        List<String> q = new ArrayList<>();
        List<Integer> id= new ArrayList<>();

        q=MainActivity.getQ();
        id=MainActivity.getIDD();
        IDD= new Integer[id.size()];
        QUANTITE= new String[id.size()];
        for(int i=0;i<q.size();i++){
            IDD[i]=id.get(i);
            cpt++;
            Log.e("cccIDD",IDD[i]+"");

        }
        for(int i=0;i<q.size();i++){
            QUANTITE[i]=q.get(i);
        }



        ajout1();
        total();


    }

    public void gett(){
        cpt=0;
        List<String> q = new ArrayList<>();
        List<Integer> id= new ArrayList<>();

        q=MainActivity.getQ();
        id=MainActivity.getIDD();
        IDD= new Integer[id.size()];
        QUANTITE= new String[id.size()];
        for(int i=0;i<q.size();i++){
            IDD[i]=id.get(i);
            cpt++;
        }
        for(int i=0;i<q.size();i++){
            QUANTITE[i]=q.get(i);
        }



        total();


    }


    public void ajout1(){

        progress.dismiss();

        ArrayList<Item1> exampleList = new ArrayList<>();
        for(int j=0;j<IDD.length;j++) {

        for(int i=0;i<photo.length;i++) {

            int e = 0;


            if (IDD[j] == ID[i]) {
                Log.e("PRODUITTTTT",i+""+photo.length+" "+IDD.length);
                exampleList.add(new Item1(ID[i]+"", nom[i], "oui", QUANTITE[j] + "", (Double.parseDouble(prix[i].replace(" DA", ""))) * (Integer.parseInt(QUANTITE[j])) + " DA"));
                e = 1;


            }
        }


        }




        mRecyclerView = findViewById(R.id.recycler);

        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        Adapter2 mAdapter = new Adapter2(exampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);





    }
    public void Onclick(){
        mRecyclerView = findViewById(R.id.recycler);

        mRecyclerView.addOnItemTouchListener(            new PlaceholderFragment.RecyclerItemClickListener(this, new PlaceholderFragment.RecyclerItemClickListener.OnItemClickListener() {
            public void onItemClick(final View v, int pos) {
                position=pos;
                viewtotal=v;
              /*  NumberPicker b= v.findViewById(R.id.number_picker);
                Log.e("NUMMMBBB: ",b.getValue()+"");*/

                //Set TextView text color
                if(ind!=null)
                    position=ind[position];


if(position>=0 ){
    bool=1;

                NumberPicker b= v.findViewById(R.id.number_picker);
                b.setValueChangedListener (new DefaultValueChangedListener() {
                    public void valueChanged(int value, ActionEnum action) {
                        if(action.equals(ActionEnum.DECREMENT))
                        test(value, value+1,v);
                        else
                            test(value, value-1,v);


                        String actionText = action == ActionEnum.MANUAL ? "manually set" : (action == ActionEnum.INCREMENT ? "incremented" : "decremented");
                        String message = String.format("NumberPicker is %s to %d et la position : %d", actionText, value,position);
/*
                        Log.e("QUANTITe",IDD[position]+" "+value);
                       TextView cc= viewtotal.findViewById(R.id.textC2);
                       for(int i=0;i<prix.length;i++) {
                           if(ID[i]==IDD[position])
                           cc.setText((Double.parseDouble(prix[i].replace(" DA", ""))) * (Integer.parseInt(String.valueOf(value))) + " DA");
                       }
                        Log.v(this.getClass().getSimpleName(), message);
                       verif=1;
                       gett();*/
                    }
                });}



            }

            public boolean onTouch(View myView, MotionEvent event) {

                // TODO Auto-generated method stub
                return true;

            }




        }));












    }


    public void test(final int value, final int ancienv,final View v){
        bd.open();
        int id=bd.getIDD();
        bd.close();
        progress.show();
        AndroidNetworking.post(DATA_INSERT_URL)
                .addBodyParameter("action","get quantitec")
                .addBodyParameter("id",id+"")
                .addBodyParameter("id_produit",IDD[position]+"")

                .setTag("test")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        if(response != null)
                            try {






                                progress.dismiss();


            int quantite=0;                    //SHOW RESPONSE FROM SERVER
for(int j=0;j<ID.length;j++){
    if(IDD[position]==ID[j]){

        quantite= Integer.parseInt(QUANTITEX[position]);


    }
}





                                    if(value>(Integer.parseInt(response.get(0).toString())+quantite)){
                                        Toast.makeText(cart.this, "Quantite non disponible", Toast.LENGTH_SHORT).show();
                                        NumberPicker b= v.findViewById(R.id.number_picker);
                                        b.setValue(ancienv);


                                    }
                                    else{

                                        MainActivity.changeQ(IDD[position],value);
                                        TextView cc= viewtotal.findViewById(R.id.textC2);
                                        for(int i=0;i<prix.length;i++) {
                                            if(ID[i]==IDD[position])
                                                cc.setText((Double.parseDouble(prix[i].replace(" DA", ""))) * (Integer.parseInt(String.valueOf(value))) + " DA");
                                        }

                                        verif=1;
                                        gett();

                                    }














                                //bd();
                                // String responseString= response.get(0).toString();


                            } catch (JSONException e) {
                                NumberPicker b= v.findViewById(R.id.number_picker);
                                b.setValue(ancienv);

                                Toast.makeText(cart.this, "Quantite non disponible", Toast.LENGTH_SHORT).show();

                                MainActivity.changeQ(IDD[position],ancienv);
                                TextView cc= viewtotal.findViewById(R.id.textC2);
                                for(int i=0;i<prix.length;i++) {
                                    if(ID[i]==IDD[position])
                                        cc.setText((Double.parseDouble(prix[i].replace(" DA", ""))) * (Integer.parseInt(String.valueOf(value))) + " DA");
                                }

                                verif=1;
                                gett();

                                progress.dismiss();
                                e.printStackTrace();
                            }

                    }

                    //ERROR
                    @Override
                    public void onError(ANError anError) {
                        NumberPicker b= v.findViewById(R.id.number_picker);
                        b.setValue(ancienv);

                        progress.dismiss();
                        anError.printStackTrace();
                        Toast.makeText(cart.this, "Quantite non disponible", Toast.LENGTH_SHORT).show();

                        MainActivity.changeQ(IDD[position],ancienv);
                        TextView cc= viewtotal.findViewById(R.id.textC2);
                        for(int i=0;i<prix.length;i++) {
                            if(ID[i]==IDD[position])
                                cc.setText((Double.parseDouble(prix[i].replace(" DA", ""))) * (Integer.parseInt(String.valueOf(value))) + " DA");
                        }

                        verif=1;
                        gett();


                    }


                });

    }




    public void faire(int v){
        for(int i=0;i<cpt;i++){

            if (IDD[i] == v) {
                for (int j = i; j < cpt; j++) {
                    if(j+1<=photo.length-1){
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
     /*   if(theFilter.getText().toString()!="")
            ccc(theFilter.getText().toString());
        else
            cc();*/



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
                    exampleList.add(new Item1(ID[i]+"", nom[i] , "oui",QUANTITE[j]+"",prix[i]));
                    e=1;
                }

            }
            if(e==0){
                exampleList.add(new Item1(ID[i]+"", nom[i] , "","",prix[i]));
            }

        }




        mRecyclerView = findViewById(R.id.recycler);

        mRecyclerView.setHasFixedSize(true);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this,2);
        Adapter1 mAdapter = new Adapter1(exampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }




    private void sendd(){
        send_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                send_p.setClickable(false);
                progress.show();
                //GET VALUES
                String email=mail.getText().toString();


                //BASIC CLIENT SIDE VALIDATION
                if((email.length()<1  ))
                {
                    Toast.makeText(getApplicationContext(), "entrer l'email SVP", Toast.LENGTH_SHORT).show();
                    send_p.setClickable(true);
                    progress.dismiss();
                }
                else{


                }
            }
        });

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
                    if(nom[i].contains(charSequence)) {
                        ind[c]=i;
                        c++;
                        int e = 0;

                        for (int j = 0; j < cpt; j++) {


                            if (IDD[j] == ID[i]) {
                                exampleList.add(new Item1(ID[i]+"", nom[i] , "oui",QUANTITE[j]+"",prix[i]));
                                e = 1;
                            }

                        }
                        if (e == 0) {
                            exampleList.add(new Item1(ID[i]+"", nom[i] , "","",prix[i]));
                        }
                    }


                }




                mRecyclerView = findViewById(R.id.recycler);

                mRecyclerView.setHasFixedSize(true);
                GridLayoutManager mLayoutManager = new GridLayoutManager(cart.this,2);
                Adapter2 mAdapter = new Adapter2(exampleList);

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
            if(nom[i].contains(d)) {
                ind[c]=i;
                c++;
                int e = 0;

                for (int j = 0; j < cpt; j++) {


                    if (IDD[j] == ID[i]) {
                        exampleList.add(new Item1(ID[i]+"", nom[i] , "oui",QUANTITE[j]+"",prix[i]));
                        e = 1;
                    }

                }
                if (e == 0) {
                    exampleList.add(new Item1(ID[i]+"", nom[i] , "","",prix[i]));
                }
            }


        }




        mRecyclerView = findViewById(R.id.recycler);

        mRecyclerView.setHasFixedSize(true);
        GridLayoutManager mLayoutManager = new GridLayoutManager(cart.this,2);
        Adapter2 mAdapter = new Adapter2(exampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }


    public void total(){

        double total=0;
        int c=0;
        if(cpt!=0){
            for (int i=0;i<prix.length;i++){
                for(int j=0;j<IDD.length;j++) {

                    if(IDD[j]==ID[i]) {
c++;
                        total = total + (Double.parseDouble(prix[i].replace(" DA", "")) * Double.parseDouble(QUANTITE[j]));
                    }
                }


            }}
        TextView t=findViewById(R.id.totalp);
        t.setText("Total : "+(int)total+" DA");
        TextView tt=findViewById(R.id.totalQQ);
        tt.setText(""+c);

    }
public void modif(View v){
        verif=0;
        progress.show();
        X();

}

    public void X() {


        AndroidNetworking.post(DATA_INSERT_URL)
                .addBodyParameter("action", "sup commandep")
                .addBodyParameter("id_commande", id_commande + "")
                .setTag("test")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        if (response != null)
                            try {


                                //SHOW RESPONSE FROM SERVER
                                Log.e("RESponse",response.get(0).toString());
                                //id= Integer.parseInt(response.get(0).toString());
                                ///Log.e("BBB",response.get(0).toString());
                               /* bd= new BDD(getActivity());
                                bd.open();
                                bd.Insert(id,"","","");
                                bd.close();*/

                                // Log.e("RESPONSE",response.get(0).toString());
                                List<String> q = new ArrayList<>();
                                final List<Integer> idd = MainActivity.getIDD();

                                q = MainActivity.getQ();

                                for (int i = 0; i < idd.size(); i++) {

                                    final int x = i;
                                    Log.e("INFOS","idc "+id_commande+" id_p "+idd.get(i));
                                    AndroidNetworking.post(DATA_INSERT_URL)
                                            .addBodyParameter("action", "add commandepp")
                                            .addBodyParameter("id_commande", id_commande + "")
                                            .addBodyParameter("id_produit", idd.get(i) + "")
                                            .addBodyParameter("quantite", q.get(i) + "")
                                            .addBodyParameter("i", i + "")


                                            .setTag("test")
                                            .setPriority(Priority.HIGH)
                                            .build()
                                            .getAsJSONArray(new JSONArrayRequestListener() {
                                                @Override
                                                public void onResponse(JSONArray response) {

                                                    if (response != null)
                                                        try {


                                                            //SHOW RESPONSE FROM SERVER
                                                            //response.get(0).toString();
                                                            //id= Integer.parseInt(response.get(0).toString());
                                                            ///Log.e("BBB",response.get(0).toString());
                               /* bd= new BDD(getActivity());
                                bd.open();
                                bd.Insert(id,"","","");
                                bd.close();*/
                                                            Log.e("response", response.get(0).toString() + " " + x);

                                                            if (cppt == idd.size() - 1) {
                                                                mod();
                                                                Integer[] ppid = new Integer[0];
                                                                String[] qq = new String[0];
                                                                MainActivity.copyID(ppid);
                                                                MainActivity.copyQ(qq);
                                                                cppt=0;

                                                            }
                                                            cppt++;


                                                        } catch (JSONException e) {
                                                            progress.dismiss();
                                                            //fab.setClickable(true);
                                                            e.printStackTrace();
                                                            Toast.makeText(cart.this, "Mail n'existe pas", Toast.LENGTH_SHORT).show();
                                                        }

                                                }

                                                //ERROR
                                                @Override
                                                public void onError(ANError anError) {
                                                    progress.dismiss();
                                                    // fab.setClickable(true);
                                                    Toast.makeText(cart.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
                                                }


                                            });


                                }


                            } catch (JSONException e) {
                                progress.dismiss();
                                //fab.setClickable(true);
                                e.printStackTrace();
                                Toast.makeText(cart.this, "Mail n'existe pas", Toast.LENGTH_SHORT).show();
                            }

                    }

                    //ERROR
                    @Override
                    public void onError(ANError anError) {
                        progress.dismiss();
                        // fab.setClickable(true);
                        Toast.makeText(cart.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
                    }


                });





    }

    public void mod(){


            /*View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }*/
        //GPS();
        AndroidNetworking.post(DATA_INSERT_URL)
                .addBodyParameter("action","modifier commande")
                .addBodyParameter("nom",nomm)
                .addBodyParameter("num",num)
                .addBodyParameter("wilaya",wilaya)
                .addBodyParameter("commune",commune)
                .addBodyParameter("adresse",adresse)
                .addBodyParameter("heure",heure)
                .addBodyParameter("date",date)
                .addBodyParameter("id",id+"")
                .addBodyParameter("id_commande",id_commande+"")
                .addBodyParameter("longg","")
                .addBodyParameter("lat","")
                .addBodyParameter("photo","")
                .setTag("test")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        if(response != null)
                            try{


                                //SHOW RESPONSE FROM SERVER
                                response.get(0).toString();
                                Log.e("BBB",response.get(0).toString());
                                progress.dismiss();

                                Toast.makeText(cart.this, "Votre commande est modifiée ! Merci de nous avoir fait confiance", Toast.LENGTH_SHORT).show();
                                Integer[] pid = new Integer[0];
                                String[] q= new String[0];
                                MainActivity.copyID(pid);
                                MainActivity.copyQ(q);
                                finish();






                            } catch (JSONException e) {
                                progress.dismiss();
                                e.printStackTrace();
                                Toast.makeText(cart.this, "Mail n'existe pas", Toast.LENGTH_SHORT).show();
                            }

                    }

                    //ERROR
                    @Override
                    public void onError(ANError anError) {
                        progress.dismiss();
                        Toast.makeText(cart.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
                    }


                });

    }

}
