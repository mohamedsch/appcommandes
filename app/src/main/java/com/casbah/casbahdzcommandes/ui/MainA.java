package com.casbah.casbahdzcommandes.ui;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.casbah.casbahdzcommandes.MainActivity;
import com.casbah.casbahdzcommandes.R;
import com.casbah.casbahdzcommandes.intro;
import com.casbah.casbahdzcommandes.profile;
import com.casbah.casbahdzcommandes.profilemod;
import com.casbah.casbahdzcommandes.ui.main.BDD;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;


public class MainA extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private static final String DATA_INSERT_URL="http://www.casbahdz.com/CRUD.php";
    private static final int PERMISSIONS_REQUEST = 100;
    private static final int CAMERA_REQUEST_CODE = 1450;
    private static final int CAMERA_PERMISSION_CODE = 1460;

    private String mCurrentPhotoPath,b;
    private EditText u,mail;
    private TextView mdp,qr;
    private EditText p;
    private Button login,send_p,profile;
    private int id=-1,v=1;
    private String pass="",user="";
    public ProgressDialog progress ;
    private BDD bd;
    private Intent intent;
    private static int c=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);


        setContentView(R.layout.login);
        //REFERENCE VIEWS
        this.initializeViews();
        progress = new ProgressDialog(this);
        progress.setTitle("Chargement");
        progress.setMessage("Attendez SVP...");
        progress.setCancelable(false);
        bd= new BDD(this);

        // disable dismiss by tapping outside of the dialog

        //HANDLE EVENTS
        this.handleClickEvents();
        verif();
        p.setText(pass);
        u.setText(user);
        if(!pass.equals("")){
        p.requestFocus();
        p.setSelection(pass.length());}

    }




    private void initializeViews() {

        u= (EditText) findViewById(R.id.user);
        mdp=  (TextView) findViewById(R.id.textmdp);
       profile=   findViewById(R.id.profile);
qr=(TextView) findViewById(R.id.qr);

        p= (EditText) findViewById(R.id.password);



        login= (Button) findViewById(R.id.login);



    }

    /*
    HANDLE CLICK EVENTS
     */
    private void handleClickEvents() {
        //EVENTS : ADD
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progress.show();
                login.setClickable(false);
                //GET VALUES

                String user=u.getText().toString();
                String password =p.getText().toString();

                //BASIC CLIENT SIDE VALIDATION
                if((user.length()<1 || password.length()<1  ))
                {
                    login.setClickable(true);
                    progress.dismiss();
                    Toast.makeText(MainA.this, "Remplissez tous les champs SVP", Toast.LENGTH_SHORT).show();

                }
                else{
                    login();



                }

            }
        });
        mdp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(MainA.this, mail.class);
                startActivity(myIntent);

            }
        });
     profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(MainA.this, profile.class);
                startActivity(myIntent);

            }
        });

        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainA.this);
                final EditText edittext = new EditText(MainA.this);
                alert.setMessage("S'identifier avec le Numero de client");
                alert.setTitle("");
                edittext.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                final ImageButton i = new ImageButton(MainA.this);
                  i.setImageResource(R.drawable.qr);
                LinearLayout layout = new LinearLayout(MainA.this);
                layout.setOrientation(LinearLayout.VERTICAL);
               i.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {



                                try {

                                    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                                    intent.putExtra("SCAN_MODE", "QR_CODE_MODE"); // "PRODUCT_MODE for bar codes

                                    startActivityForResult(intent, 0);

                                } catch (Exception e) {

                                    Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
                                    Intent marketIntent = new Intent(Intent.ACTION_VIEW,marketUri);
                                    startActivity(marketIntent);

                                }




                            }
                        });
layout.addView(edittext);
layout.addView(i);
                alert.setView(layout);
                alert.setPositiveButton("S'identifier", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        String id = edittext.getText().toString();
                        if(!id.isEmpty())
                        getinfos(Integer.parseInt(id));

                    }
                });

                alert.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                    }
                });

                alert.show();

            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {

            if (resultCode == RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT");
if(isInteger(contents) && !contents.isEmpty())
               getinfos(Integer.parseInt(contents));



            }
            if(resultCode == RESULT_CANCELED){
                //handle cancel
            }
        }
    }
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }
    public void getinfos(final int id){
        progress.show();
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











                                bd.open();

                                bd.Insert(id,response.getJSONObject(0).getString("email"),response.getJSONObject(0).getString("password"),"");

                                bd.close();
                                progress.dismiss();
                                Intent myIntent = new Intent(MainA.this, MainActivity.class);
                                startActivity(myIntent);
                                finish();




                                //bd();
                                // String responseString= response.get(0).toString();


                            } catch (JSONException e) {

                                progress.dismiss();
                                e.printStackTrace();
                                Toast.makeText(MainA.this, "Nom d'utilisateur où le mot de passe sont incorrectes ", Toast.LENGTH_SHORT).show();
                            }

                    }

                    //ERROR
                    @Override
                    public void onError(ANError anError) {
                        progress.dismiss();
                        anError.printStackTrace();
                        Toast.makeText(MainA.this, "Client n'existe pas", Toast.LENGTH_SHORT).show();

                    }


                });



    }


    protected void onStart(){
        super.onStart();
        final Intent myIntent = new Intent(MainA.this, intro.class);

        if(c==0) {
            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            myIntent.putExtra("keep", true);

            startActivity(myIntent);
            c++;}


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(c==1){
                    myIntent.putExtra("keep", false);
                    startActivity(myIntent);
                    c++;}
            }
        }, 5000);



    }
    public void verif(){
        bd.open();

        id=bd.getID();
        user=bd.getuser();
        pass=bd.getpassword();
        b=bd.getb();



        if(!user.equals("") && !pass.equals("0")){
            Intent myIntent = new Intent(MainA.this, MainActivity.class);

            myIntent.putExtra("id", id+"");

            myIntent.putExtra("branche", b+"");


            startActivity(myIntent);
        finish();}
        else if(bd.getIDD()!=0){
            Intent myIntent = new Intent(MainA.this, MainActivity.class);

            myIntent.putExtra("id", id+"");

            myIntent.putExtra("branche", b+"");


            startActivity(myIntent);
            finish();

        }
        else{

            user=bd.getuserR();
            pass=bd.getpasswordD();
            b=bd.getb();

        }
        if(pass.equals("0")){
            pass="";
        }
        bd.close();
    }


    public void login() {
        final String pass = String.valueOf(p.getText());

         String s=String.valueOf(u.getText()).toLowerCase();
        final String user = s.replaceAll("\\s+","");
        if(user==null || pass==null)
        {
            Toast.makeText(MainA.this, "Remplissez tous les champs SVP", Toast.LENGTH_SHORT).show();
        }
        else
        {
            AndroidNetworking.post(DATA_INSERT_URL)
                    .addBodyParameter("action","loginP")
                    .addBodyParameter("user",user.toLowerCase())
                    .addBodyParameter("password",pass)
                    .setTag("test")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONArray(new JSONArrayRequestListener() {
                        @Override
                        public void onResponse(JSONArray response) {

                            if(response != null)
                                try {
                                    login.setClickable(true);
                                    progress.dismiss();
                                    //SHOW RESPONSE FROM SERVER
                                    id=response.getJSONObject(0).getInt("id");

                                    bd.open();

                                    bd.Insert(id,user,pass,"");

                                    bd.close();
                                    Intent myIntent = new Intent(MainA.this, MainActivity.class);
                                    startActivity(myIntent);
                                    finish();


                                    // String responseString= response.get(0).toString();


                                } catch (JSONException e) {
                                    login.setClickable(true);
                                    progress.dismiss();
                                    e.printStackTrace();
                                    Toast.makeText(MainA.this, "Email où le mot de passe sont incorrectes ", Toast.LENGTH_SHORT).show();
                                }

                        }

                        //ERROR
                        @Override
                        public void onError(ANError anError) {
                            login.setClickable(true);
                            progress.dismiss();
                            Toast.makeText(MainA.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();

                        }


                    });

        }

    }










}
