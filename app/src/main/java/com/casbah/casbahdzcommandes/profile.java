package com.casbah.casbahdzcommandes;


import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.casbah.casbahdzcommandes.ui.MainA;
import com.casbah.casbahdzcommandes.ui.main.BDD;
import com.casbah.casbahdzcommandes.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import pub.devrel.easypermissions.EasyPermissions;

public class profile extends AppCompatActivity {
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
    private static final int  ACTION_REQUEST_GALLERY=1465;
    private FusedLocationProviderClient fusedLocationClient;

    public BDD bd;
    private Spinner P;
    private boolean veri=false,verii=false;
    private ImageView ivCameraPriview;
    private List<String> arraySpinner;
    private String longg="",lat="",nom="",num="",wilaya="",commune="",adresse="",heure="",date="",temp="",email="",mdp="",mdpc="";


    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        bd=new BDD(profile.this);
        progress = new ProgressDialog(this);
        progress.setTitle("Chargement");
        progress.setMessage("Attendez SVP...");
        progress.setCancelable(false);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        ivCameraPriview= findViewById(R.id.tv);

   /*     ImageButton b = findViewById(R.id.add);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


                Intent myIntent = new Intent(mod.this, shop.class);
                startActivity(myIntent);
            }
        });

        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                new AlertDialog.Builder(mod.this)
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
        });*/



      /*  String[] COUNTRIES = new String[] {"Adrar","Chlef","Laghouat","Oum El Bouaghi","Batna","Béjaia","Biskra","Béchar","Blida","Bouira","Tamanrasset","Tébéessa","Tlemcen","Tiaret","Tizi Ouzou","Alger","Djelfa","Jijel","Sétif","Saida","Skikda","Sidi Bel Abbes","Annaba","Guelma","Constantine","Médéa","Mostaganem","M'Sila","Mascara","Ouargla","Oran","El Bayadh","Illizi","Bordj Bou Arreridj","Boumerdes","El Tarf","Tindouf","Tissemsilt","El Oued","Khenchela","Souk Ahras","Tipaza","Mila","Ain Defla","Naama","Ain Témouchent","Ghardaia","Relizane"};

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        mod.this,
                        R.layout.dropdown_menu_popup_item,
                        COUNTRIES);

        AutoCompleteTextView editTextFilledExposedDropdown =
                findViewById(R.id.wilaya);
        editTextFilledExposedDropdown.setAdapter(adapter);*/

        fab = findViewById(R.id.add);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             /*   Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                envoyer();

            }
        });
        String[] COUNTRIES = new String[] {"Adrar","Chlef","Laghouat","Oum El Bouaghi","Batna","Béjaia","Biskra","Béchar","Blida","Bouira","Tamanrasset","Tébéessa","Tlemcen","Tiaret","Tizi Ouzou","Alger","Djelfa","Jijel","Sétif","Saida","Skikda","Sidi Bel Abbes","Annaba","Guelma","Constantine","Médéa","Mostaganem","M'Sila","Mascara","Ouargla","Oran","El Bayadh","Illizi","Bordj Bou Arreridj","Boumerdes","El Tarf","Tindouf","Tissemsilt","El Oued","Khenchela","Souk Ahras","Tipaza","Mila","Ain Defla","Naama","Ain Témouchent","Ghardaia","Relizane"};

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        R.layout.dropdown_menu_popup_item,
                        COUNTRIES);

        AutoCompleteTextView editTextFilledExposedDropdown = findViewById(R.id.wilaya);
        editTextFilledExposedDropdown.setAdapter(adapter);



        P = findViewById(R.id.Spinner);
        arraySpinner= new ArrayList<>();
        arraySpinner.add("Supérette");
        arraySpinner.add("Réstaurant");
        arraySpinner.add("Fast food");
        arraySpinner.add("Grossiste");
        arraySpinner.add("Epicerie");
        arraySpinner.add("Autre");





        ArrayAdapter<String> adapterr = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, arraySpinner);
        adapterr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        P.setAdapter(adapterr);

        ImageButton btnTakePhoto = findViewById(R.id.photo);
        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if app has permission to access the camera.
                if (EasyPermissions.hasPermissions(profile.this, Manifest.permission.CAMERA)) {
                    launchCamera();

                } else {
                    //If permission is not present request for the same.
                    EasyPermissions.requestPermissions(profile.this, getString(R.string.permission_text), CAMERA_PERMISSION_CODE, Manifest.permission.CAMERA);
                }
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

                BitmapDrawable drawable = (BitmapDrawable)  ivCameraPriview.getDrawable();
                if(drawable!=null){
                    Bitmap bitmap=drawable.getBitmap();

                    float degrees = 90; //rotation degree
                    Matrix matrix = new Matrix();
                    matrix.setRotate(degrees);
                    Bitmap bOutput = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                    bitmap=bOutput;
                    ivCameraPriview.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 100, 100, false));

// Convert bitmap to Base64 encoded image for web

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    if(bitmap!=null){
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

                        byte[] byte_arr = stream.toByteArray();
                        temp = Base64.encodeToString(byte_arr, Base64.DEFAULT);
                        temp=resizeBase64Image(temp);}


                }

            }});


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
            Uri photoURI = FileProvider.getUriForFile(profile.this,
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
            ivCameraPriview.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 100, 100, false));
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            if(bitmap!=null){
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

                byte[] byte_arr = stream.toByteArray();
                temp = Base64.encodeToString(byte_arr, Base64.DEFAULT);
                temp=resizeBase64Image(temp);}
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
                ivCameraPriview.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 100, 100, false));
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                if(bitmap!=null){
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

                    byte[] byte_arr = stream.toByteArray();
                    temp = Base64.encodeToString(byte_arr, Base64.DEFAULT);
                    temp=resizeBase64Image(temp);}
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
    public void GPS() {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(final Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {



                            longg=location.getLongitude() + "";
                            lat= location.getLatitude() + "";



                        }
                    }
                });

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
                GPS();
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
        if (EasyPermissions.hasPermissions(profile.this, Manifest.permission.CAMERA)) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, profile.this);
            launchCamera();

        }

        else {if (requestCode == PERMISSIONS_REQUEST && grantResults.length == 1
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            //...then start the GPS tracking service//

            GPS();
        } else  {

//If the user denies the permission request, then display a toast with some more information//

            Toast.makeText(profile.this, "Please enable location services to allow GPS tracking", Toast.LENGTH_SHORT).show();
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
    public void h(){
        H = findViewById(R.id.heure);
        H.setFocusable(false);

        Calendar mcurrentTime = Calendar.getInstance();
        final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        final int minute = mcurrentTime.get(Calendar.MINUTE);
        H.setText(convertDate(hour)+":"+convertDate(minute));
        H.setInputType(InputType.TYPE_NULL);
        H.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    TimePickerDialog mTimePicker = new TimePickerDialog(profile.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            H.setText( convertDate(selectedHour) + ":" + convertDate(selectedMinute));
                        }
                    }, hour, minute, true);//Yes 24 hour time
                    mTimePicker.setTitle("Selectionner le temps");
                    mTimePicker.show();

                }
            }
        });


        H.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             /*   Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                TimePickerDialog mTimePicker = new TimePickerDialog(profile.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        H.setText( convertDate(selectedHour) + ":" + convertDate(selectedMinute));
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Selectionner le temps");
                mTimePicker.show();

            }
        });


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



    }



    public void envoyer(){
        progress.show();
        fab.setClickable(false);

        TextInputEditText n = findViewById(R.id.nom);
        nom=n.getText().toString();


        TextInputEditText nmm = findViewById(R.id.num);
        num=nmm.getText().toString();




        AutoCompleteTextView w= findViewById(R.id.wilaya);
        wilaya=w.getText().toString();

        AutoCompleteTextView cc= findViewById(R.id.commune);
        commune=cc.getText().toString();

        TextInputEditText a= findViewById(R.id.adresse);
        adresse=a.getText().toString();
        TextInputEditText ae= findViewById(R.id.email);
      email=ae.getText().toString().toLowerCase();

        TextInputEditText a2= findViewById(R.id.mdp);
        mdp=a2.getText().toString();
        TextInputEditText a12= findViewById(R.id.mdp2);
        mdpc=a12.getText().toString();

        List<String> q = new ArrayList<>();
        List<Integer> iddd= new ArrayList<>();

        q=MainActivity.getQ();
        iddd=MainActivity.getIDD();



if(nom.isEmpty() || num.isEmpty() || mdpc.isEmpty() || mdp.isEmpty() || wilaya.isEmpty() || email.isEmpty() || adresse.isEmpty() || commune.isEmpty())
{
    Toast.makeText(profile.this, "Remplissez tous les champs SVP", Toast.LENGTH_SHORT).show();
    progress.dismiss();
    fab.setClickable(true);

}
else if(!mdpc.equals(mdp)){
    Toast.makeText(profile.this, "Le mot de passe confirmé n'est pas juste", Toast.LENGTH_SHORT).show();
    progress.dismiss();
    fab.setClickable(true);
}

     else
        {
            GPStask();



            /*View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }*/
            //GPS();
            AndroidNetworking.post(DATA_INSERT_URL)
                    .addBodyParameter("action","add pventeC")
                    .addBodyParameter("nom",nom)
                    .addBodyParameter("num",num)
                    .addBodyParameter("wilaya",wilaya)
                    .addBodyParameter("commune",commune)
                    .addBodyParameter("adresse",adresse)
                    .addBodyParameter("longg",longg)
                    .addBodyParameter("lat",lat)
                    .addBodyParameter("mdp",mdp+"")
                    .addBodyParameter("email",email+"")
                    .addBodyParameter("photo",temp)
                    .addBodyParameter("type",P.getSelectedItem().toString() + "")

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
                                   if(response.get(0).toString().equals("Success")){
                                       fab.setClickable(true);

                                       progress.dismiss();
                                       bd.open();
                                       String id= response.get(1).toString();
                                       email = email.replaceAll("\\s+","");

                                       bd.Insert(Integer.parseInt(id),email.toLowerCase(),mdp,"");

                                       bd.close();
                                       Intent myIntent = new Intent(profile.this, MainActivity.class);
                                       startActivity(myIntent);
                                       finish();

                                   }
                                   else{
                                       fab.setClickable(true);

                                       progress.dismiss();

                                       Toast.makeText(profile.this, "Changer l'email où le nom", Toast.LENGTH_SHORT).show();

                                   }







                                } catch (JSONException e) {
                                    progress.dismiss();
                                    fab.setClickable(true);
                                    e.printStackTrace();
                                    Toast.makeText(profile.this, "Mail n'existe pas", Toast.LENGTH_SHORT).show();
                                }

                        }

                        //ERROR
                        @Override
                        public void onError(ANError anError) {
                            progress.dismiss();
                            fab.setClickable(true);
                            Toast.makeText(profile.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
                        }


                    });

        }








    }



    public void set(){




        TextInputEditText d = findViewById(R.id.date);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");

        try {
            date=format1.format(format2.parse(d.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        TextInputEditText h = findViewById(R.id.heure);
        heure=h.getText().toString();




        cart.set(num,nom,date,heure,wilaya,adresse,commune);




    }

}