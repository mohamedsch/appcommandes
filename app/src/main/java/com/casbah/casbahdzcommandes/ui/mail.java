package com.casbah.casbahdzcommandes.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.casbah.casbahdzcommandes.R;
import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;

import org.json.JSONArray;
import org.json.JSONException;


public class mail extends AppCompatActivity {
    private EditText mail;
    private Button send_p;
    private String e;
    private static final String DATA_INSERT_URL="http://www.casbahdz.com/CRUD.php";
    public ProgressDialog progress ;

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mdp_oublie);
        send_p= (Button) findViewById(R.id.veri);
        mail= (EditText) findViewById(R.id.mail);
        progress = new ProgressDialog(this);
        progress.setTitle("Chargement");
        progress.setMessage("Attendez SVP...");
        progress.setCancelable(false);
        send();
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
                    Toast.makeText(getApplicationContext(), "Entrer l'email SVP", Toast.LENGTH_SHORT).show();
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
        String s=String.valueOf(email.toLowerCase());
        email= s.replaceAll("\\s+","");
        e=email;
        if(email==null || email.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "Entrer un Email SVP", Toast.LENGTH_SHORT).show();
        }
        else
        {
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            AndroidNetworking.post(DATA_INSERT_URL)
                    .addBodyParameter("action","sendP")
                    .addBodyParameter("mail",e)
                    .setTag("test")
                    .setPriority(Priority.MEDIUM)
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

                                    BackgroundMail.newBuilder(mail.this)
                                            .withUsername("moscherchar@gmail.com")
                                            .withPassword("091994Cherchar07")
                                            .withMailto(e)
                                            .withType(BackgroundMail.TYPE_PLAIN)
                                            .withSubject("Mot de passe récupération Casbah Commandes")
                                            .withBody("Votre mot de passe est : "+password)
                                            .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                                                @Override
                                                public void onSuccess() {
                                                    Toast.makeText(mail.this, "Succes", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                                                @Override
                                                public void onFail() {
                                                    Toast.makeText(mail.this, "UnSucces", Toast.LENGTH_SHORT).show();

                                                    //do some magic
                                                }
                                            })
                                            .send();



                                } catch (JSONException e) {
                                    progress.dismiss();
                                    send_p.setClickable(true);
                                    e.printStackTrace();
                                    Toast.makeText(mail.this, "Mail n'existe pas", Toast.LENGTH_SHORT).show();
                                }

                        }

                        //ERROR
                        @Override
                        public void onError(ANError anError) {
                            progress.dismiss();
                            send_p.setClickable(true);
                            Toast.makeText(mail.this, "Erreur de connexion", Toast.LENGTH_SHORT).show();
                        }


                    });

        }

    }



}