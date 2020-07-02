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
import android.graphics.Matrix;
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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class retours extends AppCompatActivity {
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
    private String[] photo,q,nom,QUANTITE,DATE,date_l,QUANTITEX;
    private String[] QUANTITEU,description;
    private AutoCompleteTextView produits;
    private TextInputEditText QF,QU;
    private EditText desc;
    private ImageButton camera;
    private ImageView ivcamera;

private Integer[] pid;
    private Bitmap[] images;
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
        setContentView(R.layout.retours);

        bd=new BDD(retours.this);
        progress = new ProgressDialog(this);
        progress.setTitle("Chargement");
        progress.setMessage("Attendez SVP...");
        progress.setCancelable(false);
        //fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


       FloatingActionButton btnTakePhoto = findViewById(R.id.add);
        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if app has permission to access the camera.
                Intent myIntent = new Intent(retours.this, ajoutretours.class);
                startActivity(myIntent);
            }
        });

get_liv();
Onclick();





    }
    public void get_liv(){
        progress.show();
        final int[] x = {0};
        bd=new BDD(retours.this);
        bd.open();
        int idd=bd.getID();
        bd.close();
        Log.e("IDD",idd+"");

        AndroidNetworking.post(DATA_INSERT_URL)
                .addBodyParameter("action","get retours")
                .addBodyParameter("id",idd+"")

                .setTag("test")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        if(response != null)
                            try {
progress.dismiss();

                                pid=new Integer[response.length()];
                               DATE=new String[response.length()];
                                date_l=new String[response.length()];





                                //SHOW RESPONSE FROM SERVER
                                for(int i=0;i<response.length();i++) {
                                    pid[i] = response.getJSONObject(i).getInt("id");

                                    DATE[i] = response.getJSONObject(i).getString("date");
                                    date_l[i] = response.getJSONObject(i).getString("id_livraison");







                                }
                                ajout();





                                //bd();
                                // String responseString= response.get(0).toString();


                            } catch (JSONException e) {

                                progress.dismiss();
                                //e.printStackTrace();
                                Toast.makeText(retours.this, "Nom d'utilisateur où le mot de passe sont incorrectes ", Toast.LENGTH_SHORT).show();
                            }

                    }

                    //ERROR
                    @Override
                    public void onError(ANError anError) {
                        progress.dismiss();
                        //anError.printStackTrace();


                        DATE=new String[0];
                        pid=new Integer[0];
                        q=new String[0];





                        ajout();
                    }


                });


    }



    private void launchCamera() {

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Create the File where the photo should go
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            // Error occurred while creating the File
        }
        // Continue only if the File was successfully created
        if (photoFile != null) {
            Uri photoURI = FileProvider.getUriForFile(retours.this,
                    "com.casbah.casbahdzcommandes",
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
            if(bitmap!=null){
                float degrees = 90; //rotation degree
                Matrix matrix = new Matrix();
                matrix.setRotate(degrees);
                Bitmap bOutput = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);



                bitmap=bOutput;
                ivcamera.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 100, 100, false));
                images[cpt]=bitmap;
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                if(bitmap!=null){
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

                    byte[] byte_arr = stream.toByteArray();
                    photo[cpt] = Base64.encodeToString(byte_arr, Base64.DEFAULT);}
            }}
    }




    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[]
            grantResults) {


//If the permission has been granted...//
        if (EasyPermissions.hasPermissions(retours.this, Manifest.permission.CAMERA)) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, retours.this);
            launchCamera();

        }

        else {if (requestCode == PERMISSIONS_REQUEST && grantResults.length == 1
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            //...then start the GPS tracking service//

        } else  {

//If the user denies the permission request, then display a toast with some more information//

            Toast.makeText(retours.this, "Please enable location services to allow GPS tracking", Toast.LENGTH_SHORT).show();
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
                        retours.super.onBackPressed();
                    }
                }).create().show();}
        else{
            Integer[] pid = new Integer[0];
            String[] q = new String[0];
            MainActivity.copyID(pid);
            MainActivity.copyQ(q);
            retours.super.onBackPressed();
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





    }


    public void ajout(){

        progress.dismiss();

        ArrayList<Item> exampleList = new ArrayList<>();
        for(int i=0;i<pid.length;i++) {




            if(Integer.parseInt(date_l[i])!=0)
                exampleList.add(new Item("","Retour n° : "+(pid.length-i),"Prise en charge : oui"));
            else
                exampleList.add(new Item("","Retour n° : "+(pid.length-i),"Prise en charge : pas encore"));






        }




        mRecyclerView = findViewById(R.id.recycler);

        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        Adapter mAdapter = new Adapter(exampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);





    }
    public void Onclick(){
        mRecyclerView = findViewById(R.id.recycler);

        mRecyclerView.addOnItemTouchListener(            new PlaceholderFragment.RecyclerItemClickListener(this, new PlaceholderFragment.RecyclerItemClickListener.OnItemClickListener() {
            public void onItemClick(final View v, int pos) {
                position=pos;






            }

            public boolean onTouch(View myView, MotionEvent event) {

                // TODO Auto-generated method stub
                return true;

            }




        }));












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

     /*   if(theFilter.getText().toString()!="")
            ccc(theFilter.getText().toString());
        else
            cc();*/



    }
    public void MAJ(){

        MainActivity.copyID(IDD);
        MainActivity.copyQ(QUANTITE);


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



public void modif(View v){
        verif=0;
        progress.show();
        X();

}

    public void v(final View v){

       /* if(verif==0){
            ImageView i = getActivity().findViewById(R.id.image);


            i.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
              Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                    new AlertDialog.Builder(getContext())
                            .setMessage("Etes vous sur de supprimer cette commande ?")
                            .setCancelable(false)
                            .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    supprimer();


                                }
                            })
                            .setNegativeButton("Non", null)
                            .show();

                }
            });
mRecyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.image);
            ImageView ii = vv.findViewById(R.id.image2);

*/




             /*   Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
        new AlertDialog.Builder(v.getContext())
                .setMessage("Etes-vous sur de supprimer ce retour ?")
                .setCancelable(false)
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                       progress.show();
                        supprimer();


                    }
                })
                .setNegativeButton("Non", null)
                .show();



























    }

    public void supprimer(){



        progress = new ProgressDialog(retours.this);
        progress.setTitle("Chargement");
        progress.setMessage("Attendez SVP...");





        progress.show();
        AndroidNetworking.post(DATA_INSERT_URL)
                .addBodyParameter("action", "supprimer retour")
                .addBodyParameter("id",pid[position] + "")
                .setTag("test")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        if (response != null)
                            try {
                                                  /*     bd.open();
                                                       bd.annuler(id_l);
                                                       bd.close();
                                                       progress.dismiss();*/
                                progress.dismiss();

                                String responseString = response.get(0).toString();

get_liv();

                                         /*   motif.setText("");
                                            QV.setText(Q.getText());
                                            M.setText(mon.get(i));
                                                   */
                            } catch (JSONException e) {
                                //a.setClickable(true);

                                progress.dismiss();
                                e.printStackTrace();
                                Toast.makeText(retours.this, "Nom d'utilisateur où le mot de passe sont incorrectes ", Toast.LENGTH_SHORT).show();
                            }

                    }

                    //ERROR
                    @Override
                    public void onError(ANError anError) {
                                             /*  bd.open();
                                               bd.annuler(id_l);
                                               bd.close();*/

                        // a.setClickable(true);

                        progress.dismiss();
                        Toast.makeText(retours.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();


                    }


                });


        //a.setClickable(true);
        progress.dismiss();







    }


    public void X() {


        AndroidNetworking.post(DATA_INSERT_URL)
                .addBodyParameter("action", "sup retour")
                .addBodyParameter("id_commande", pid[position] + "")
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
                                                            Toast.makeText(retours.this, "Mail n'existe pas", Toast.LENGTH_SHORT).show();
                                                        }

                                                }

                                                //ERROR
                                                @Override
                                                public void onError(ANError anError) {
                                                    progress.dismiss();
                                                    // fab.setClickable(true);
                                                    Toast.makeText(retours.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
                                                }


                                            });


                                }


                            } catch (JSONException e) {
                                progress.dismiss();
                                //fab.setClickable(true);
                                e.printStackTrace();
                                Toast.makeText(retours.this, "Mail n'existe pas", Toast.LENGTH_SHORT).show();
                            }

                    }

                    //ERROR
                    @Override
                    public void onError(ANError anError) {
                        progress.dismiss();
                        // fab.setClickable(true);
                        Toast.makeText(retours.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
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

                                Toast.makeText(retours.this, "Votre commande est modifiée ! Merci de nous avoir fait confiance", Toast.LENGTH_SHORT).show();
                                Integer[] pid = new Integer[0];
                                String[] q= new String[0];
                                MainActivity.copyID(pid);
                                MainActivity.copyQ(q);
                                finish();






                            } catch (JSONException e) {
                                progress.dismiss();
                                e.printStackTrace();
                                Toast.makeText(retours.this, "Mail n'existe pas", Toast.LENGTH_SHORT).show();
                            }

                    }

                    //ERROR
                    @Override
                    public void onError(ANError anError) {
                        progress.dismiss();
                        Toast.makeText(retours.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
                    }


                });

    }

    public void enter(View v){



        Intent myIntent = new Intent(retours.this, retoursmod.class);
        List<String> idd = new ArrayList<>();

              /*  if(ind!=null)

                    position=ind[position];*/

             /*   for(int i=0;i<n.length;i++) {
                    if (n[position] != null)
                        nom.add(n[position]);
                    if (nm[position] != null)
                        num.add(nm[position]);
                    if (w[position] != null)
                        wilaya.add(w[position]);
                    if (com[position] != null)
                        commune.add(com[position]);
                    if (ad[position] != null)
                        adresse.add(ad[position]);
                    if (cd[position] != null)
                        commande.add(cd[position]);
                    if (d[position] != null)
                        date.add(d[position]);
                    if (h[position] != null)
                        heure.add(h[position]);
                    if (ID[position] != null)
                        IDD.add(ID[position]);



                }*/


        myIntent.putExtra("id_retours", pid[position] + "");



             /*   myIntent.putStringArrayListExtra("wilaya", (ArrayList<String>) wilaya);


                myIntent.putStringArrayListExtra("quantite", (ArrayList<String>)q);
                myIntent.putStringArrayListExtra("quantite_u", (ArrayList<String>)qu);

                myIntent.putStringArrayListExtra("montant", (ArrayList<String>) mon);
                myIntent.putStringArrayListExtra("quantite_v", (ArrayList<String>) qv);
                myIntent.putStringArrayListExtra("quantite_u_v", (ArrayList<String>) quv);

                myIntent.putExtra("motif", motif[position]+"");
                myIntent.putStringArrayListExtra("payement", (ArrayList<String>) pay);
                myIntent.putStringArrayListExtra("endomage", (ArrayList<String>) end);
                myIntent.putStringArrayListExtra("reste", (ArrayList<String>) r);
*/


        startActivity(myIntent);




    }

}
