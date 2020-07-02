package com.casbah.casbahdzcommandes.ui.main;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.casbah.casbahdzcommandes.Adapter;
import com.casbah.casbahdzcommandes.Item;
import com.casbah.casbahdzcommandes.MainActivity;
import com.casbah.casbahdzcommandes.R;
import com.casbah.casbahdzcommandes.cart;
import com.casbah.casbahdzcommandes.profilemod;
import com.casbah.casbahdzcommandes.shop;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import pub.devrel.easypermissions.EasyPermissions;
import ru.dimorinny.floatingtextbutton.FloatingTextButton;

import static android.content.Context.LOCATION_SERVICE;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private static final int CAMERA_PERMISSION_CODE = 1460;
private int verif=0;
    private static final int CAMERA_REQUEST_CODE = 1450;
    private String mCurrentPhotoPath;
    private ImageView ivCameraPreview;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String DATA_INSERT_URL = "http://www.casbahdz.com/CRUD.php";
    public ProgressDialog progress ;

    private FusedLocationProviderClient fusedLocationClient;
    private  String longg="",lat="",nom="",num="",wilaya="",commune="",adresse="",heure="",date="",temp="";
    private int id=0;
    private static  String[] n,w,com,ad,nm,d,h,cd,id_livraison,ID;
    private FloatingTextButton fab;
    private View c;
    private BDD bd;
    private EditText theFilter;

    private RecyclerView mRecyclerView;
    private Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Context con;
    private View v;
    private int position=0;
    private static Integer[][] pid;
    private static String [][] q;
    private View vv;
    private Date tomorrow;

 private Integer ind=0;
    private static final int PERMISSIONS_REQUEST = 100;


    private Integer[] IDD;
    private String[] QUANTITE;
    private Button D;
    public static int year,month,dayOfMonth,yearr,monthh,dayy;
    private TextInputEditText DD,H;
    private TextInputLayout CC;
    private boolean  shouldRefreshOnResume=false;
    public void onResume(){


        super.onResume();

        //update your fragment
        List<Integer> iddd= new ArrayList<>();

        iddd=MainActivity.getIDD();
        TextView nb= getActivity().findViewById(R.id.nbshop);
        nb.setText("("+iddd.size()+")");

        if(shouldRefreshOnResume) {
            bd.open();

            WebView ww= getActivity().findViewById(R.id.web);



            ww.loadUrl("http://www.casbahdz.com/casbahtemplate/libs/image/pvente1.php?id="+bd.getIDD()+"");


            bd.close();
            bd.open();
            getinfos(bd.getID());
            bd.close();

            D = getActivity().findViewById(R.id.datex);
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");

            try {
                get_liv(format1.format(format2.parse(D.getText().toString())));
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }
    public void onStop() {
        super.onStop();
        shouldRefreshOnResume = true;
    }

    public void getinfos(int id){
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








                                //SHOW RESPONSE FROM SERVER

                                TextView nom = getActivity().findViewById(R.id.nome);
                                nom.setText(response.getJSONObject(0).getString("nom"));


/*
String s=response.getJSONObject(0).getString("photo");
                                Bitmap decodedByte=null;
                                String c="";
                                if(!response.getJSONObject(0).getString("photo").equals("") && !response.getJSONObject(0).getString("photo").toLowerCase().equals("null")){



                                       // ivCameraPreview.setImageBitmap(getBitmapFromURL("http://www.casbahdz.com/casbahtemplate/libs/image/pvente.php?id="+response.getJSONObject(0).getString("id")));



                                }*/



                                progress.dismiss();



                                //bd();
                                // String responseString= response.get(0).toString();


                            } catch (JSONException e) {

                                progress.dismiss();
                                e.printStackTrace();
                                Toast.makeText(getContext(), "Nom d'utilisateur où le mot de passe sont incorrectes ", Toast.LENGTH_SHORT).show();
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


    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }

        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root;
        Log.e("YAWW",pageViewModel.getIndex()+"");
        if(pageViewModel.getIndex()==1){
        root = inflater.inflate(R.layout.fragment_main, container, false);


            c=root;

            fusedLocationClient = LocationServices.getFusedLocationProviderClient(c.getContext());


            progress = new ProgressDialog(c.getContext());
            progress.setTitle("Chargement");
            progress.setMessage("Attendez SVP...");
            progress.setCancelable(false);
            bd=new BDD(c.getContext());
            bd.open();
            id=bd.getID();
            bd.close();




            Locale locale = getResources().getConfiguration().locale;
            Locale.setDefault(locale);
             DD = c.findViewById(R.id.date);
            DD.setFocusable(false);
            final Calendar calendar = Calendar.getInstance();
            Date today = calendar.getTime();

            calendar.add(Calendar.DAY_OF_YEAR, 1);
             tomorrow = calendar.getTime();
            final DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

             yearr = calendar.get(Calendar.YEAR);
             monthh = calendar.get(Calendar.MONTH);
             dayy = calendar.get(Calendar.DAY_OF_MONTH);
            String tomorrowAsString = dateFormat.format(tomorrow);
            DD.setText(tomorrowAsString);
            DD.setInputType(InputType.TYPE_NULL);
            DD.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus) {
                        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),R.style.DialogTheme,
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                        month++;
                                        monthh=month-1;
                                        yearr=year;
                                        dayy=day;
                                        String d=convertDate(day)+"-"+convertDate(month)+"-"+year;
                                        DD.setText(d+"");


                                    }
                                }, yearr, monthh, dayy);
                        Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.DAY_OF_YEAR, 1);
//Set min time to now
                        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());

                        datePickerDialog.show();

                    }
                }
            });


            DD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
             /*   Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),R.style.DialogTheme,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                    month++;
                                    String d=convertDate(day)+"-"+convertDate(month)+"-"+year;
                                    monthh=month-1;
                                    yearr=year;
                                    dayy=day;
                                    DD.setText(d+"");


                                }
                            }, yearr, monthh, dayy);
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DAY_OF_YEAR, 1);
//Set min time to now
                    datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());

                    datePickerDialog.show();
                    datePickerDialog.show();

                }
            });
h();
            fab = ((MainActivity)getActivity()).findViewById(R.id.fab);

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
             /*   Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                    envoyer();

                }
            });
             ImageButton b = c.findViewById(R.id.add);

            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
             /*   Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/


                    Intent myIntent = new Intent(getActivity(), shop.class);
                    myIntent.putExtra("motif", "");

                    startActivity(myIntent);
                }
            });

/*
            ImageButton p= c.findViewById(R.id.photo);
            p.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                    if (EasyPermissions.hasPermissions(c.getContext(), Manifest.permission.CAMERA)) {
                        launchCamera();

                    } else {
                        //If permission is not present request for the same.
                        EasyPermissions.requestPermissions(getActivity(), getString(R.string.permission_text), CAMERA_PERMISSION_CODE, Manifest.permission.CAMERA);
                    }

                }
            });

*/


        }
        else {
            root = inflater.inflate(R.layout.fragment_main1, container, false);


            c=root;
            progress = new ProgressDialog(getContext());
            progress.setTitle("Chargement");
            progress.setMessage("Attendez SVP...");
            progress.setCancelable(false);
            mRecyclerView = root.findViewById(R.id.recycler);


            //filt();
            Onclick();
            Locale locale = getResources().getConfiguration().locale;
            Locale.setDefault(locale);

            D = root.findViewById(R.id.datex);
            Calendar calendar = Calendar.getInstance();
             year = calendar.get(Calendar.YEAR);
     month = calendar.get(Calendar.MONTH);
            dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                D.setText(convertDate(dayOfMonth)+"-"+convertDate(month+1)+"-"+year);

            get_liv(year+"-"+convertDate(month+1)+"-"+convertDate(dayOfMonth));


            D.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker datePicker, int yearr, int monthh, int day) {
                                    month++;
                                    String d=convertDate(day)+"-"+convertDate(monthh+1)+"-"+yearr;
                                    D.setText(d+"");

                                    ind=null;
                                    year= yearr;
                                    month=monthh;
                                    dayOfMonth=day;
                                 String dd=yearr+"-"+convertDate(monthh+1)+"-"+convertDate(day);
                                    get_liv(dd);
                                }
                            }, year, month, dayOfMonth);

                    datePickerDialog.show();}
            });

        }

       /* final TextView textView = root.findViewById(R.id.section_label);
        pageViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        bd.open();
        getinfos(bd.getID());
        bd.close();

        return root;
    }

public void hide(){
    TextInputEditText n = c.findViewById(R.id.nom);
      n.setVisibility(View.INVISIBLE);

    TextInputEditText nmm = c.findViewById(R.id.num);
    nmm.setVisibility(View.INVISIBLE);



    AutoCompleteTextView w= c.findViewById(R.id.wilaya);
    w.setVisibility(View.INVISIBLE);


    AutoCompleteTextView cc= c.findViewById(R.id.commune);
    cc.setVisibility(View.INVISIBLE);


    TextInputEditText a= c.findViewById(R.id.adresse);
    a.setVisibility(View.INVISIBLE);

    ImageButton bb= c.findViewById(R.id.photo);
    bb.setVisibility(View.INVISIBLE);


    TextInputLayout x= c.findViewById(R.id.magasin);
    x.setVisibility(View.INVISIBLE);


    TextInputLayout xxx= c.findViewById(R.id.NUM);
    xxx.setVisibility(View.INVISIBLE);
    TextInputLayout vxx= c.findViewById(R.id.l);
    vxx.setVisibility(View.INVISIBLE);
    TextInputLayout uxx= c.findViewById(R.id.ll);
    uxx.setVisibility(View.INVISIBLE);
    TextInputLayout bxx= c.findViewById(R.id.IDDl);
    bxx.setVisibility(View.INVISIBLE);


    TextInputLayout dd= c.findViewById(R.id.datee);
    final RelativeLayout.LayoutParams lppp = (RelativeLayout.LayoutParams) dd.getLayoutParams();
    lppp.addRule(RelativeLayout.BELOW, 0);
    dd.setLayoutParams(lppp);
    TextInputLayout hh= c.findViewById(R.id.heuree);
    final RelativeLayout.LayoutParams ll = (RelativeLayout.LayoutParams) hh.getLayoutParams();
    ll.addRule(RelativeLayout.BELOW, 0);
    hh.setLayoutParams(ll);







}

    public void show(){
        TextInputEditText n = c.findViewById(R.id.nom);
        n.setVisibility(View.VISIBLE);

        TextInputEditText nmm = c.findViewById(R.id.num);
        nmm.setVisibility(View.VISIBLE);




        AutoCompleteTextView w= c.findViewById(R.id.wilaya);
        w.setVisibility(View.VISIBLE);


        AutoCompleteTextView cc= c.findViewById(R.id.commune);
        cc.setVisibility(View.VISIBLE);


        TextInputEditText a= c.findViewById(R.id.adresse);
        a.setVisibility(View.VISIBLE);

        ImageButton bb= c.findViewById(R.id.photo);
        bb.setVisibility(View.VISIBLE);


        TextInputLayout xxx= c.findViewById(R.id.NUM);
        xxx.setVisibility(View.VISIBLE);
        TextInputLayout vxx= c.findViewById(R.id.l);
        vxx.setVisibility(View.VISIBLE);
        TextInputLayout uxx= c.findViewById(R.id.ll);
        uxx.setVisibility(View.VISIBLE);
        TextInputLayout bxx= c.findViewById(R.id.IDDl);
        bxx.setVisibility(View.VISIBLE);
        TextInputLayout max= c.findViewById(R.id.magasin);
        max.setVisibility(View.VISIBLE);


        TextInputLayout dd= c.findViewById(R.id.datee);
        final RelativeLayout.LayoutParams lppp = (RelativeLayout.LayoutParams) dd.getLayoutParams();
        lppp.addRule(RelativeLayout.BELOW, R.id.IDDl);
        dd.setLayoutParams(lppp);
        TextInputLayout hh= c.findViewById(R.id.heuree);
        final RelativeLayout.LayoutParams ll = (RelativeLayout.LayoutParams) hh.getLayoutParams();
        ll.addRule(RelativeLayout.BELOW, R.id.IDDl);
        hh.setLayoutParams(ll);

    }
    public void h(){
        H = c.findViewById(R.id.heure);
        H.setFocusable(false);
        Calendar mcurrentTime = Calendar.getInstance();
        final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        final int minute = mcurrentTime.get(Calendar.MINUTE);
        H.setText(convertDate(hour)+":"+convertDate(minute));
        H.setInputType(InputType.TYPE_NULL);
        H.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                   TimePickerDialog mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
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
                TimePickerDialog mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
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
    /*public void onBackPressed(){
        if(){
        }else{
            super.onBackPressed();
        }
    }*/




    public String convertDate(int input) {
        if (input >= 10) {
            return String.valueOf(input);
        } else {
            return "0" + String.valueOf(input);
        }
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
            Uri photoURI = FileProvider.getUriForFile(getContext(),
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
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    /**
     * Previews the captured picture on the app
     * Called when the picture is taken
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
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
             temp = Base64.encodeToString(byte_arr, Base64.DEFAULT);}
        }
    }



    public void envoyer(){
        progress.show();
        fab.setClickable(false);




        TextInputEditText d = c.findViewById(R.id.date);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");

        try {
            date=format1.format(format2.parse(d.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        TextInputEditText h = c.findViewById(R.id.heure);
        heure=h.getText().toString();




            List<String> q = new ArrayList<>();
            List<Integer> iddd= new ArrayList<>();

            q=MainActivity.getQ();
            iddd=MainActivity.getIDD();

            IDD=new Integer[iddd.size()];
            QUANTITE=new String[iddd.size()];

            for(int i=0;i<q.size();i++){
                IDD[i]=iddd.get(i);

            }
            for(int i=0;i<q.size();i++){
                QUANTITE[i]=q.get(i);
            }




bd.open();
String idd=bd.getID()+"";
bd.close();


        if(!idd.toString().equals("") && iddd.size()!=0)
        {
            bd.open();
            id=bd.getID();
            bd.close();


            /*View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }*/
            GPStask();
            AndroidNetworking.post(DATA_INSERT_URL)
                    .addBodyParameter("action","add commande")
                    .addBodyParameter("nom","")
                    .addBodyParameter("num","")
                    .addBodyParameter("wilaya","")
                    .addBodyParameter("commune","")
                    .addBodyParameter("adresse","")
                    .addBodyParameter("longg",longg)
                    .addBodyParameter("lat",lat)
                    .addBodyParameter("heure",heure)
                    .addBodyParameter("date",date)
                    .addBodyParameter("id",id+"")
                    .addBodyParameter("photo",temp)
                    .setTag("test")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONArray(new JSONArrayRequestListener() {
                        @Override
                        public void onResponse(JSONArray response) {

                            if(response != null)
                                try{


                                    //SHOW RESPONSE FROM SERVER
                                    if(!response.get(0).toString().equals("Unsuccessfull")){
                                    String id=response.get(1).toString();

                                    Log.e("iddd",id);

                                    X(Integer.parseInt(id));}
                                    else{
                                        progress.dismiss();
                                        Toast.makeText(c.getContext(), "Vous avez déjà une commande dans cette date", Toast.LENGTH_SHORT).show();

                                    }





                                } catch (JSONException e) {
                                    progress.dismiss();
                                    fab.setClickable(true);
                                    e.printStackTrace();
                                    AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                                    Toast.makeText(c.getContext(), "Erreur de connexion", Toast.LENGTH_SHORT).show();
                                }

                        }

                        //ERROR
                        @Override
                        public void onError(ANError anError) {
                            progress.dismiss();
                            fab.setClickable(true);
                            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                            Toast.makeText(c.getContext(), "Erreur de connexion", Toast.LENGTH_SHORT).show();
                        }


                    });

        }

        else{
            Toast.makeText(c.getContext(), "Ajoutez des produits SVP", Toast.LENGTH_SHORT).show();
            progress.dismiss();
            fab.setClickable(true);

        }






    }


    public void GPS() {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
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



    public void ajout(){

Log.e("CDDDD ",cd.length+"");

        ArrayList<Item> exampleList = new ArrayList<>();

        for(int i=0;i<cd.length;i++) {






if(Integer.parseInt(id_livraison[i])!=0)
            exampleList.add(new Item(h[i],"Commande n° : "+cd[i],"Prise en charge : oui"));
else
    exampleList.add(new Item(h[i],"Commande n° : "+cd[i],"Prise en charge : pas encore"));


        }




        mRecyclerView = ((MainActivity)getActivity()).findViewById(R.id.recycler);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager((MainActivity)getActivity());
        mAdapter = new Adapter(exampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
if(cd.length!=0){
      // v();
    }



    }

    public void Onclick(){
        mRecyclerView=c.findViewById(R.id.recycler);

        mRecyclerView.addOnItemTouchListener(            new PlaceholderFragment.RecyclerItemClickListener(getContext(), new PlaceholderFragment.RecyclerItemClickListener.OnItemClickListener() {
            public void onItemClick(View view, int pos) {
                position = pos;




                /* Make selected if user clicks and maintain the state urself*/


                // Do your stuff here



            }

            public boolean onTouch(View v, MotionEvent event){
                return true;

            }


        }));




    }
    public void click(){



    }

    public void get_liv(){
        progress.show();
        final int[] x = {0};
        bd=new BDD(getActivity());
        bd.open();
        int idd=bd.getID();
        bd.close();

        AndroidNetworking.post(DATA_INSERT_URL)
                .addBodyParameter("action","get commande")
                .addBodyParameter("id",idd+"")

                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        if(response != null)
                            try {

                                n=new String[response.length()];
                                w=new String[response.length()];
                                com=new String[response.length()];
                                ad=new String[response.length()];
                                nm=new String[response.length()];
                                d=new String[response.length()];
                                h=new String[response.length()];
                                cd=new String[response.length()];
                                ID=new String[response.length()];
                                pid=new Integer[response.length()][50];
                                q=new String[response.length()][50];






                                //SHOW RESPONSE FROM SERVER
                                for(int i=0;i<response.length();i++) {

                                    ID[i] = response.getJSONObject(i).getString("id");

                                    n[i] = response.getJSONObject(i).getString("nom");
                                    nm[i] = response.getJSONObject(i).getString("num");
                                    ad[i] = response.getJSONObject(i).getString("adresse");
                                    com[i] = response.getJSONObject(i).getString("commune");
                                    w[i] = response.getJSONObject(i).getString("wilaya");
                                    d[i] = response.getJSONObject(i).getString("date");
                                    h[i] = response.getJSONObject(i).getString("heure");
                                    cd[i] = response.getJSONObject(i).getString("commande");


                                   getcommandep(Integer.parseInt(ID[i]),response.length(),i);





                                }



                                //bd();
                                // String responseString= response.get(0).toString();


                            } catch (JSONException e) {

                                progress.dismiss();
                                e.printStackTrace();
                                Toast.makeText(getContext(), "Nom d'utilisateur où le mot de passe sont incorrectes ", Toast.LENGTH_SHORT).show();
                            }

                    }

                    //ERROR
                    @Override
                    public void onError(ANError anError) {
                        progress.dismiss();
                        anError.printStackTrace();
                        n=new String[0];
                        ID=new String[0];

                        w=new String[0];
                        com=new String[0];
                        ad=new String[0];
                        nm=new String[0];
                        d=new String[0];
                        h=new String[0];
                        cd=new String[0];
                        pid=new Integer[0][0];
                        q=new String[0][0];



                        ajout();
                    }


                });


    }




    public void getcommandep(int id, final int size, final int pos){

        Log.e("IDP====",size+"");
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

                                    if(i==response.length()-1 && pos==size-1){
                                        ajout();
                                        progress.dismiss();
                                    }





                                }



                                //bd();
                                // String responseString= response.get(0).toString();


                            } catch (JSONException e) {

                                progress.dismiss();
                                Toast.makeText(getContext(), "Nom d'utilisateur où le mot de passe sont incorrectes ", Toast.LENGTH_SHORT).show();
                            }

                    }

                    //ERROR
                    @Override
                    public void onError(ANError anError) {
                        progress.dismiss();
                        n=new String[0];
                        w=new String[0];
                        com=new String[0];
                        ad=new String[0];
                        nm=new String[0];
                        d=new String[0];
                        h=new String[0];
                        cd=new String[0];
                        ID=new String[0];
                        pid=new Integer[0][0];
                        q=new String[0][0];





                        ajout();

                    }


                });
    }

    public void get_liv(String dd){
        progress.show();
        final int[] x = {0};
        bd=new BDD(getActivity());
        bd.open();
        int idd=bd.getID();
        bd.close();
        Log.e("IDD",idd+"");

        AndroidNetworking.post(DATA_INSERT_URL)
                .addBodyParameter("action","get commandee")
                .addBodyParameter("id",idd+"")
                .addBodyParameter("date",dd)

                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        if(response != null)
                            try {

                                n=new String[response.length()];
                                w=new String[response.length()];
                                com=new String[response.length()];
                                ad=new String[response.length()];
                                nm=new String[response.length()];
                                d=new String[response.length()];
                                h=new String[response.length()];
                                cd=new String[response.length()];
                                id_livraison=new String[response.length()];

                                ID=new String[response.length()];
                                pid=new Integer[response.length()][50];
                                q=new String[response.length()][50];



                                //SHOW RESPONSE FROM SERVER
                                for(int i=0;i<response.length();i++) {
                                    ID[i] = response.getJSONObject(i).getString("id");

                                  n[i] = response.getJSONObject(i).getString("nom");
                                    nm[i] = response.getJSONObject(i).getString("num");
                                    ad[i] = response.getJSONObject(i).getString("adresse");
                                    com[i] = response.getJSONObject(i).getString("commune");
                                    w[i] = response.getJSONObject(i).getString("wilaya");
                                    d[i] = response.getJSONObject(i).getString("date");
                                   h[i] = response.getJSONObject(i).getString("heure");
                                    cd[i] = response.getJSONObject(i).getString("commande");
                                    id_livraison[i] = response.getJSONObject(i).getString("id_livraison");



                                    getcommandep(Integer.parseInt(ID[i]),response.length(),i);





                                }





                                //bd();
                                // String responseString= response.get(0).toString();


                            } catch (JSONException e) {

                                progress.dismiss();
                                //e.printStackTrace();
                                Toast.makeText(getContext(), "Nom d'utilisateur où le mot de passe sont incorrectes ", Toast.LENGTH_SHORT).show();
                            }

                    }

                    //ERROR
                    @Override
                    public void onError(ANError anError) {
                        progress.dismiss();
                        //anError.printStackTrace();
                        n=new String[0];
                        w=new String[0];
                        com=new String[0];
                        ad=new String[0];
                        nm=new String[0];
                        d=new String[0];
                        h=new String[0];
                        cd=new String[0];
                        id_livraison=new String[0];

                        ID=new String[0];
                        pid=new Integer[0][0];
                        q=new String[0][0];





                        ajout();
                    }


                });


    }


    public static class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
        private GestureDetector mGestureDetector;
        private OnItemClickListener mListener;
        public interface OnItemClickListener {
            public void onItemClick(View view, int position);
            public boolean onTouch(View view, MotionEvent motionEvent);
        }

        public RecyclerItemClickListener(Context context, OnItemClickListener listener) {
            mListener = listener;
            mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
            View childView = view.findChildViewUnder(e.getX(), e.getY());
            if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
                mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {


        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }


    public void supprimer(){



        progress = new ProgressDialog(getContext());
        progress.setTitle("Chargement");
        progress.setMessage("Attendez SVP...");





                        progress.show();
                        AndroidNetworking.post(DATA_INSERT_URL)
                                .addBodyParameter("action", "supprimer commande")
                                .addBodyParameter("id", ID[position] + "")
                                .setTag("test")
                                .setPriority(Priority.MEDIUM)
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
                                                Button D = ((MainActivity)getActivity()).findViewById(R.id.datex);

                                                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                                                SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");
                                                String[] split= new String[3];
                                                try {
                                                    date=format1.format(format2.parse(D.getText().toString()));

                                                    D.setText(format2.format(format1.parse(date)));
                                                    split=(format2.format(format1.parse(date))).toString().split("-");


                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }
                                                Log.e("DATEEE",split[2]+" "+split[1]+" "+split[0]);
                                                year= Integer.parseInt(split[2]);
                                                month=Integer.parseInt(split[1])-1;
                                                dayOfMonth=Integer.parseInt(split[0]);
                                                get_liv(date);



                                         /*   motif.setText("");
                                            QV.setText(Q.getText());
                                            M.setText(mon.get(i));
                                                   */
                                            } catch (JSONException e) {
                                                //a.setClickable(true);

                                                progress.dismiss();
                                                e.printStackTrace();
                                                Toast.makeText(getContext(), "Nom d'utilisateur où le mot de passe sont incorrectes ", Toast.LENGTH_SHORT).show();
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
                                        Toast.makeText(getContext(), "Erreur de connexion", Toast.LENGTH_SHORT).show();


                                    }


                                });


                        //a.setClickable(true);
                        progress.dismiss();







    }



    public void X(int id){
        List<String> q = new ArrayList<>();
       final List<Integer> idd= MainActivity.getIDD();

        q=MainActivity.getQ();

        for(int i=0;i<idd.size();i++){
            final int x=i;
        AndroidNetworking.post(DATA_INSERT_URL)
                .addBodyParameter("action","add commandep")
                .addBodyParameter("id_commande",id+"")
                .addBodyParameter("id_produit",idd.get(i)+"")
                .addBodyParameter("quantite",q.get(i)+"")


                .setTag("test")
                .setPriority(Priority.MEDIUM)
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

                                if(x==idd.size()-1){
                                    progress.dismiss();
                                    fab.setClickable(true);
                                    AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                                    bd=new BDD(getActivity());
                                    bd.open();

                                    alert.setTitle("Votre commande a été prise !");
                                    alert.setMessage("Merci de nous avoir fait confiance");
                                    alert.setPositiveButton("OK",null);
                                    alert.show();
                                    bd.close();
                                    Integer[] pid = new Integer[0];
                                    String[] q= new String[0];
                                    MainActivity.copyID(pid);
                                    MainActivity.copyQ(q);
                                    MainActivity.total(0);
                                    List<Integer> iddd= new ArrayList<>();

                                    iddd=MainActivity.getIDD();
                                    TextView nb= getActivity().findViewById(R.id.nbshop);
                                    nb.setText("("+iddd.size()+")");
                                    Button D = ((MainActivity)getActivity()).findViewById(R.id.datex);
                                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                                    SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");
                                    String[] split= new String[3];
                                    try {

                                        D.setText(format2.format(format1.parse(date)));
                                       split=(format2.format(format1.parse(date))).toString().split("-");


                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    Log.e("DATEEE",split[2]+" "+split[1]+" "+split[0]);
                                    year= Integer.parseInt(split[2]);
                                    month=Integer.parseInt(split[1])-1;
                                    dayOfMonth=Integer.parseInt(split[0]);
                                    get_liv(date);


                                }









                            } catch (JSONException e) {
                                progress.dismiss();
                                fab.setClickable(true);
                                e.printStackTrace();
                                Toast.makeText(c.getContext(), "Erreur de connexion", Toast.LENGTH_SHORT).show();
                            }

                    }

                    //ERROR
                    @Override
                    public void onError(ANError anError) {
                        progress.dismiss();
                        fab.setClickable(true);
                        Toast.makeText(c.getContext(), "Erreur de connexion", Toast.LENGTH_SHORT).show();
                    }


                });



    }

    }










    public void GPStask(){
        LocationManager lm = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // finish();

        }
        else {
            //Check whether this app has access to the location permission//


            int permission = ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION);

//If the location permission has been granted, then start the TrackerService//

            if (permission == PackageManager.PERMISSION_GRANTED) {
                GPS();
            } else {

//If the app doesn’t currently have access to the user’s location, then request access//

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST);

            }

        }



    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[]
            grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, getActivity());
//If the permission has been granted...//
        if (EasyPermissions.hasPermissions(getActivity(), Manifest.permission.CAMERA)) {

            launchCamera();

        }

        else {if (requestCode == PERMISSIONS_REQUEST && grantResults.length == 1
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            //...then start the GPS tracking service//

           GPS();
        } else  {

//If the user denies the permission request, then display a toast with some more information//

            Toast.makeText(getContext(), "Please enable location services to allow GPS tracking", Toast.LENGTH_SHORT).show();
        }}
    }

    public void onPermissionsGranted(int requestCode, List<String> perms) {
        launchCamera();
    }


    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "Permission has been denied");
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
                            .setMessage("Etes-vous sur de supprimer cette commande ?")
                            .setCancelable(false)
                            .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                  supprimer();


                                }
                            })
                            .setNegativeButton("Non", null)
                            .show();

























        verif=1;



}


public void enter(View v){



    Intent myIntent = new Intent(getContext(), cart.class);
    List<String> idd = new ArrayList<>();
    List<String> nom = new ArrayList<>();
    List<String> num = new ArrayList<>();
    List<String> wilaya = new ArrayList<>();
    List<String> commune = new ArrayList<>();
    List<String> adresse = new ArrayList<>();
    List<String> date = new ArrayList<>();
    List<String> heure = new ArrayList<>();
    List<String> commande = new ArrayList<>();
    List<String> IDD = new ArrayList<>();
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
    bd = new BDD(getContext());
    bd.open();


    myIntent.putExtra("id", bd.getID() + "");
    bd.close();

    myIntent.putExtra("commande", cd[position] + "");
    myIntent.putExtra("wilaya", w[position] + "");

    myIntent.putExtra("commune", com[position] + "");
    myIntent.putExtra("adresse", ad[position] + "");
    myIntent.putExtra("id_c", ID[position] + "");
    myIntent.putExtra("num", nm[position] + "");
    myIntent.putExtra("nom", n[position] + "");
    myIntent.putExtra("date", d[position] + "");
    myIntent.putExtra("heure", h[position] + "");

    MainActivity.copyID(pid[position]);
    MainActivity.copyQ(q[position]);


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