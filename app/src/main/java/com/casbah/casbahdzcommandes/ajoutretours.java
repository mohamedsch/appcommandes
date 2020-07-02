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
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;
import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class ajoutretours extends AppCompatActivity {
    private EditText mail;
    private Button send_p;
    private TextInputEditText D,H;
    private String e;
    private static final String DATA_INSERT_URL="http://www.casbahdz.com/CRUD.php";
    public ProgressDialog progress ;
    private int id_commande=0;
    private FloatingTextButton fab;
    private static final int PERMISSIONS_REQUEST = 100;
    private static final int CAMERA_REQUEST_CODE = 1450;

    private String mCurrentPhotoPath,b;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int CAMERA_PERMISSION_CODE = 1460;
    private FusedLocationProviderClient fusedLocationClient;
private         int cppt=0;

    public BDD bd;
    private String[] photo,q,produit,nom,QUANTITE,prix,QUANTITEX;
    private String[] QUANTITEU,description;
    private AutoCompleteTextView produits;
    private TextInputEditText QF,QU;
    private EditText desc;
    private ImageButton camera;
    private ImageView ivcamera;
    private static final int  ACTION_REQUEST_GALLERY=1465;



    private Bitmap[] images;
    private Integer[] ID,IDD,ind;
    private int cpt=0,bool=0;
    private RecyclerView mRecyclerView;
    private int position;
    private EditText theFilter;
    private boolean  shouldRefreshOnResume=false;
    private ImageButton add;
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



        }
    }
    public void onStop() {
        super.onStop();
        shouldRefreshOnResume = true;
    }


    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajoutretour);

        bd=new BDD(ajoutretours.this);
        progress = new ProgressDialog(this);
        progress.setTitle("Chargement");
        progress.setMessage("Attendez SVP...");
        progress.setCancelable(false);
        //fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

   desc= findViewById(R.id.description);
   produits=findViewById(R.id.produit);
   QF=findViewById(R.id.quantitef);
   QU=findViewById(R.id.quantiteu);


add=findViewById(R.id.add);

        get_liv();
        Onclick();
        ivcamera=findViewById(R.id.tv);

        ImageButton btnTakePhoto = findViewById(R.id.photo);
        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if app has permission to access the camera.
                if (EasyPermissions.hasPermissions(ajoutretours.this, Manifest.permission.CAMERA)) {
                    launchCamera();

                } else {
                    //If permission is not present request for the same.
                    EasyPermissions.requestPermissions(ajoutretours.this, getString(R.string.permission_text), CAMERA_PERMISSION_CODE, Manifest.permission.CAMERA);
                }
            }
        });

add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if app has permission to access the camera.
                int x=0;
                String descc=desc.getText().toString();
                String qunf=QF.getText().toString();
                String qunu=QU.getText().toString();
                String pro=produits.getText().toString();
                for (int i=0;i<cpt;i++){
                    if(produit[i]!=null){
                        if(produit[i].equals(pro)){
                            x=1;
                            Toast.makeText(ajoutretours.this, "Produit déjà ajouté", Toast.LENGTH_SHORT).show();

                        }
                    }
                }
                int j=1;

                for (int i=0;i<nom.length;i++){
                    if(nom[i]!=null){
                        if(nom[i].equals(pro)){
                            j=0;


                        }
                    }
                }
                if(j==1){
                    Toast.makeText(ajoutretours.this, "Selectionner un produit", Toast.LENGTH_SHORT).show();

                }

               else if(descc.isEmpty() || (qunf.isEmpty() && qunu.isEmpty()) ||pro.isEmpty()){
                    Toast.makeText(ajoutretours.this, "Remplissez tous les champs SVP", Toast.LENGTH_SHORT).show();

                }
                else if(Integer.parseInt(qunf)==0 && Integer.parseInt(qunu)==0){
                    Toast.makeText(ajoutretours.this, "Veuillez entrer la quantité endomagée", Toast.LENGTH_SHORT).show();

                }
                else if(x==0){
                    if(qunf.isEmpty())
                        qunf="0";
                    if(qunu.isEmpty())
                        qunu="0";
                    QUANTITE[cpt]=qunf;
                    QUANTITEU[cpt]=qunu;
                    produit[cpt]=pro;
                    description[cpt]=descc;
                    ivcamera.setImageBitmap(null);
                    desc.setText("");
                    QF.setText("");
                  QU.setText("");
                    produits.setText("");
                    cpt++;
                    ajout1();


                }










            }
        });


        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             /*   Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                envoyer();

            }
        });



        ImageButton buttonLoadImage = findViewById(R.id.gallery);


        buttonLoadImage.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, ACTION_REQUEST_GALLERY);
            }});


        ImageButton buttonLoadImage1 = findViewById(R.id.rotation);


        buttonLoadImage1.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                BitmapDrawable drawable = (BitmapDrawable)  ivcamera.getDrawable();
                if(drawable!=null){
                    Bitmap bitmap=drawable.getBitmap();

                    float degrees = 90; //rotation degree
                    Matrix matrix = new Matrix();
                    matrix.setRotate(degrees);
                    Bitmap bOutput = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                    bitmap=bOutput;
                    ivcamera.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 100, 100, false));

// Convert bitmap to Base64 encoded image for web

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    if(bitmap!=null){
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

                        images[cpt]=bitmap;

                        byte[] byte_arr = stream.toByteArray();
                        photo[cpt] = Base64.encodeToString(byte_arr, Base64.DEFAULT);
                        photo[cpt]=resizeBase64Image(photo[cpt]);}


                }

            }});


    }

    public void sup(View v){
        new AlertDialog.Builder(v.getContext())
                .setMessage("Etes-vous sur de supprimer cet article ?")
                .setCancelable(false)
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        verif=1;



                        for(int i=0;i<cpt;i++){
if(produit[i]!=null){
                            if (produit[i].equals(produit[position])) {
                                for (int j = i; j < cpt-1; j++) {
                                    if (produit[j + 1] != null)
                                        produit[j] = produit[j + 1];
                                    QUANTITE[j]=QUANTITE[j+1];
                                    QUANTITEU[j]=QUANTITEU[j+1];
                                    description[j]=description[j+1];
                                    if(photo[j+1]!=null)
                                    photo[j]=photo[j+1];
                                    else
                                        photo[j]="";
                                    if(images[j+1]!=null)

                                        images[j]=images[j+1];
                                    else
                                        images[j]=null;




                                }
                                cpt--;


                            }
                            ajout1();



                        }}




                    }
                })
                .setNegativeButton("Non", null)
                .show();

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
            Uri photoURI = FileProvider.getUriForFile(ajoutretours.this,
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
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                if(bitmap!=null){
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    images[cpt]=bitmap;

                    byte[] byte_arr = stream.toByteArray();
                    photo[cpt] = Base64.encodeToString(byte_arr, Base64.DEFAULT);
                    photo[cpt]=resizeBase64Image(photo[cpt]);}
            }}
        if (requestCode  == ACTION_REQUEST_GALLERY){
            Uri targetUri=null;
            Bitmap bitmap=null;
            if(data!=null){
                targetUri = data.getData();

                try {
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }}

            if(bitmap!=null){
                float degrees = 0; //rotation degree
                Matrix matrix = new Matrix();
                matrix.setRotate(degrees);
                Bitmap bOutput = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);



                bitmap=bOutput;
                ivcamera.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 100, 100, false));
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                if(bitmap!=null){
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    images[cpt]=bitmap;

                    byte[] byte_arr = stream.toByteArray();
                    photo[cpt] = Base64.encodeToString(byte_arr, Base64.DEFAULT);
                    photo[cpt]=resizeBase64Image(photo[cpt]);
                }
            }
        }

    }
    public String resizeBase64Image(String base64image){
        byte [] encodeByte=Base64.decode(base64image.getBytes(),Base64.DEFAULT);
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inPurgeable = true;
        Bitmap image = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length,options);


        if(image.getHeight() <= 400 && image.getWidth() <= 400){
            return base64image;
        }
        image = Bitmap.createScaledBitmap(image, 400, 400, false);

        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG,100, baos);

        byte [] b=baos.toByteArray();
        System.gc();
        return Base64.encodeToString(b, Base64.NO_WRAP);

    }




    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[]
            grantResults) {


//If the permission has been granted...//
        if (EasyPermissions.hasPermissions(ajoutretours.this, Manifest.permission.CAMERA)) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, ajoutretours.this);
            launchCamera();

        }

        else {if (requestCode == PERMISSIONS_REQUEST && grantResults.length == 1
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            //...then start the GPS tracking service//

        } else  {

//If the user denies the permission request, then display a toast with some more information//

            Toast.makeText(ajoutretours.this, "Please enable location services to allow GPS tracking", Toast.LENGTH_SHORT).show();
        }}
    }



    public void onPermissionsGranted(int requestCode, List<String> perms) {
        launchCamera();
    }


    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "Permission has been denied");
    }




    public String convertDate(int input) {
        if (input >= 10) {
            return String.valueOf(input);
        } else {
            return "0" + String.valueOf(input);
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
                                produit=new String[response.length()];

                                images=new Bitmap[response.length()];

                                ID=new Integer[response.length()];
                                nom=new String[response.length()];
                                description=new String[response.length()];
                                QUANTITE=new String[response.length()];
                                QUANTITEU=new String[response.length()];






                                //SHOW RESPONSE FROM SERVER
                                for(int i=0;i<response.length();i++) {

                                    photo[i] = "";
                                    ID[i] = response.getJSONObject(i).getInt("id");
                                    nom[i] = response.getJSONObject(i).getString("nom");




                                  /*  n[i] = response.getJSONObject(i).getString("nom");
                                    nm[i] = response.getJSONObject(i).getString("num");
                                    ad[i] = response.getJSONObject(i).getString("adresse");
                                    com[i] = response.getJSONObject(i).getString("commune");
                                    w[i] = response.getJSONObject(i).getString("wilaya");
                                    d[i] = response.getJSONObject(i).getString("date");
                                    h[i] = response.getJSONObject(i).getString("heure");
                                    cd[i] = response.getJSONObject(i).getString("commande");*/



                                    if(i==response.length()-1){
                                        ArrayAdapter<String> adapter =
                                                new ArrayAdapter<>(
                                                        ajoutretours.this,
                                                        R.layout.dropdown_menu_popup_item,
                                                        nom);

                                        produits.setAdapter(adapter);
                                    }

progress.dismiss();



                                }








                                //bd();
                                // String responseString= response.get(0).toString();


                            } catch (JSONException e) {

                                progress.dismiss();
                                e.printStackTrace();
                                Toast.makeText(ajoutretours.this, "Nom d'utilisateur où le mot de passe sont incorrectes ", Toast.LENGTH_SHORT).show();
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

        ArrayList<Item3> exampleList = new ArrayList<>();
        for(int i=0;i<cpt;i++) {

        if(produit[i]!=null)
                exampleList.add(new Item3(photo[i]+"", produit[i],  QUANTITEU[i] + "",QUANTITE[i]+""));




        }







        mRecyclerView = findViewById(R.id.recycler);

        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        Adapter3 mAdapter = new Adapter3(exampleList);

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



if(position>=0 ){
    bool=1;

                NumberPicker b= v.findViewById(R.id.number_picker);
                b.setValueChangedListener (new DefaultValueChangedListener() {
                    public void valueChanged(int value, ActionEnum action) {
                        if(action.equals(ActionEnum.DECREMENT)){
                       // test(value, value+1,v);

                        QUANTITEU[position]= String.valueOf(value);}
                        else{
                            //test(value, value-1,v);

                            QUANTITEU[position]= String.valueOf(value);}
                        verif=1;


                    }
                });}


                NumberPicker bb= v.findViewById(R.id.number_picker1);
                bb.setValueChangedListener (new DefaultValueChangedListener() {
                    public void valueChanged(int value, ActionEnum action) {
                        if(action.equals(ActionEnum.DECREMENT)){
                            // test(value, value+1,v);

                            QUANTITE[position]= String.valueOf(value);}
                        else{
                            //test(value, value-1,v);

                            QUANTITE[position]= String.valueOf(value);}
                        verif=1;


                    }
                });



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
                                        Toast.makeText(ajoutretours.this, "Quantite non disponible", Toast.LENGTH_SHORT).show();
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

                                Toast.makeText(ajoutretours.this, "Quantite non disponible", Toast.LENGTH_SHORT).show();

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
                        Toast.makeText(ajoutretours.this, "Quantite non disponible", Toast.LENGTH_SHORT).show();

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
                GridLayoutManager mLayoutManager = new GridLayoutManager(ajoutretours.this,2);
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
        GridLayoutManager mLayoutManager = new GridLayoutManager(ajoutretours.this,2);
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
                                                            Toast.makeText(ajoutretours.this, "Mail n'existe pas", Toast.LENGTH_SHORT).show();
                                                        }

                                                }

                                                //ERROR
                                                @Override
                                                public void onError(ANError anError) {
                                                    progress.dismiss();
                                                    // fab.setClickable(true);
                                                    Toast.makeText(ajoutretours.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
                                                }


                                            });


                                }


                            } catch (JSONException e) {
                                progress.dismiss();
                                //fab.setClickable(true);
                                e.printStackTrace();
                                Toast.makeText(ajoutretours.this, "Mail n'existe pas", Toast.LENGTH_SHORT).show();
                            }

                    }

                    //ERROR
                    @Override
                    public void onError(ANError anError) {
                        progress.dismiss();
                        // fab.setClickable(true);
                        Toast.makeText(ajoutretours.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
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

                                Toast.makeText(ajoutretours.this, "Votre commande est modifiée ! Merci de nous avoir fait confiance", Toast.LENGTH_SHORT).show();
                                verif=0;

                                finish();






                            } catch (JSONException e) {
                                progress.dismiss();
                                e.printStackTrace();
                                Toast.makeText(ajoutretours.this, "Mail n'existe pas", Toast.LENGTH_SHORT).show();
                            }

                    }

                    //ERROR
                    @Override
                    public void onError(ANError anError) {
                        progress.dismiss();
                        Toast.makeText(ajoutretours.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
                    }


                });

    }



    public void envoyer(){
        progress.show();
        fab.setClickable(false);








        if(cpt!=0)
        {
            bd.open();
            String id=bd.getID()+"";
            bd.close();


            /*View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }*/

            AndroidNetworking.post(DATA_INSERT_URL)
                    .addBodyParameter("action","add retour")
                    .addBodyParameter("id_pvente",id+"")

                    .setTag("test")
                    .setPriority(Priority.HIGH)
                    .build()
                    .getAsJSONArray(new JSONArrayRequestListener() {
                        @Override
                        public void onResponse(JSONArray response) {

                            if(response != null)
                                try{


                                    //SHOW RESPONSE FROM SERVER
                                    if(response.get(0).toString().equals("Success")){
                                        String id=response.get(1).toString();



                                        X(Integer.parseInt(id));}
                                    else{
                                        progress.dismiss();
                                        Toast.makeText(ajoutretours.this, "Vous avez déjà un retour dans cette date", Toast.LENGTH_SHORT).show();

                                    }





                                } catch (JSONException e) {
                                    progress.dismiss();
                                    fab.setClickable(true);
                                    e.printStackTrace();

                                    Toast.makeText(ajoutretours.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
                                }

                        }

                        //ERROR
                        @Override
                        public void onError(ANError anError) {
                            progress.dismiss();
                            fab.setClickable(true);
                            Toast.makeText(ajoutretours.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
                        }


                    });

        }

        else{
            Toast.makeText(ajoutretours.this, "Ajoutez des produits SVP", Toast.LENGTH_SHORT).show();
            progress.dismiss();
            fab.setClickable(true);

        }






    }

    public void X(int id){


        for(int i=0;i<cpt;i++){
            final int x=i;
            AndroidNetworking.post(DATA_INSERT_URL)
                    .addBodyParameter("action","add retoursp")
                    .addBodyParameter("id_retours",id+"")
                    .addBodyParameter("produit",produit[i]+"")
                    .addBodyParameter("quantite",QUANTITE[i]+"")
                    .addBodyParameter("quantite_u",QUANTITEU[i]+"")
                    .addBodyParameter("description",description[i]+"")
                    .addBodyParameter("photo",photo[i]+"")

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
                                    //id= Integer.parseInt(response.get(0).toString());
                                    ///Log.e("BBB",response.get(0).toString());
                               /* bd= new BDD(getActivity());
                                bd.open();
                                bd.Insert(id,"","","");
                                bd.close();*/

                                    if(x==cpt-1){
                                        progress.dismiss();
                                        fab.setClickable(true);
                                        AlertDialog.Builder alert = new AlertDialog.Builder(ajoutretours.this);


                                        alert.setTitle("Nous allons traiter vos retours !");
                                        alert.setMessage("Merci de nous avoir fait confiance");
                                        alert.setPositiveButton("OK",null);
                                        alert.show();


                                    }






                                } catch (JSONException e) {
                                    progress.dismiss();
                                    fab.setClickable(true);
                                    e.printStackTrace();
                                    Toast.makeText(ajoutretours.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
                                }

                        }

                        //ERROR
                        @Override
                        public void onError(ANError anError) {
                            progress.dismiss();
                            fab.setClickable(true);
                            Toast.makeText(ajoutretours.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
                        }


                    });



        }

    }


}
