package com.casbah.casbahdzcommandes.ui.main;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BDD {

    public static final String KEY_STUID = "stuid";
    public static final String KEY_SUB1 = "subject_one";
    public static final String KEY_SUB2 = "subject_two";
    public static final String KEY_SUB3 = "subject_three";
    public static final String KEY_MARKS1 = "marks_one";
    public static final String KEY_MARKS2 = "marks_two";
    public static final String KEY_MARKS3 = "marks_three";
    private static final String DATA_INSERT_URL="http://www.casbahdz.com/CRUD.php";

    private static final String DATABASE_NAME = "hand_to_hand";
    private static final String DATABASE_MARKSTABLE = "StudentMarks";
    private static final int DATABASE_VERSION = 1;
private int x=1,k=0, id=-1;;
    private DbHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;
    private int []id_lx,id_pv;
    private String[][] produitx,quantitex,quantite_ux,montant_bonx,endomagex,payementx,QVx,QVUx,IDPx,restex;


    private static class DbHelper extends SQLiteOpenHelper {

        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub
         /*   db.execSQL(" CREATE TABLE " + DATABASE_MARKSTABLE + " (" +
                    KEY_STUID + " TEXT PRIMARY KEY, " +
                    KEY_SUB1 + " TEXT NOT NULL, " +
                    KEY_SUB2 + " TEXT NOT NULL, " +
                    KEY_SUB3 + " TEXT NOT NULL, " +
                    KEY_MARKS1 + " INTEGER NOT NULL, " +
                    KEY_MARKS2 + " INTEGER NOT NULL, " +
                    KEY_MARKS3 + " INTEGER NOT NULL);"
            );*/


            db.execSQL(" CREATE TABLE " + "login" + " (" +
                    "id" + " INTEGER , " +
                    "user" + " TEXT not null , " +
                    "connecte" + " TEXT  default 'oui', " +
                    "branche" + " TEXT , " +
                    "password" + " TEXT NOT NULL );"
            );

            ContentValues cv = new ContentValues();
            cv.put("id", 0);
            cv.put("user", "");
            cv.put("password", "0");
            cv.put("branche", "c");


            db.insertWithOnConflict("login", null, cv, SQLiteDatabase.CONFLICT_IGNORE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub

            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_MARKSTABLE);
            onCreate(db);
        }

    }

    public BDD(Context c) {
        ourContext = c;
    }
    public void cc(){
        ourDatabase.execSQL("DROP TABLE livraison" );
        ourDatabase.execSQL("DROP TABLE produitv" );
        ourDatabase.execSQL("DROP TABLE pvente" );
        ourDatabase.delete("livraison","user=?",new String [] {String.valueOf("non")});
        ourDatabase.delete("produitv"," id_livraison not in(select id from livraison where user=?)",new String [] {String.valueOf("non")});




        ourDatabase.execSQL("CREATE TABLE livraison\n" +
                "( id Integer NOT NULL,\n" +
                "  id_livreur integer, \n" +
                "  bon integer, \n" +
                "  livreur TEXT ,\n" +
                "    pvente TEXT ,\n" +
                "    produit TEXT ,\n" +
                "    type_p TEXT,\n" +
                "    motif TEXT,\n" +
                "    photo BLOB,\n" +
                "    temps_p TEXT,\n" +
                "  date TEXT NOT NULL,\n" +
                "    heure TEXT NOT NULL,\n" +
                "    heure_a TEXT ,\n" +
                "    date_m TEXT,\n" +
                "    user TEXT NOT NULL default 'non',\n" +
                "    faite TEXT NOT NULL default 'non',\n" +
                "    valide TEXT  default 'non'\n" +

                ");");
        ourDatabase.execSQL("CREATE TABLE produitv\n" +
                "(id Integer NOT NULL, \n" +
                "id_livraison integer, \n" +
                "  id_livreur integer, \n" +
                "  produit TEXT ,\n" +
                "     quantite int not null,\n" +
                "     quantite_u int not null default 0,\n" +
                "    quantite_v int not null default 0,\n" +
                "   quantite_u_v int not null default 0,\n" +
                "    endomage int not null default 0,\n" +
                "    montant_bon double,\n" +
                "   payement double default 0,\n" +
                "   reste double default 0,\n" +
                "    date TEXT\n" +

                ");");


        ourDatabase.execSQL("CREATE TABLE pvente\n" +
                "(id Integer NOT NULL, \n" +
                "  nom TEXT ,\n" +
                "  num TEXT ,\n" +
                "  branche TEXT ,\n" +
                "  adresse TEXT ,\n" +
                "  credit TEXT ,\n" +
                "  zone TEXT \n" +

                ");");

        ourDatabase.execSQL("CREATE TABLE produit\n" +
                "(id Integer NOT NULL, \n" +
                "  nom TEXT ,\n" +
                "  prix_u TEXT ,\n" +
                "  prix_f double ,\n" +
                "  quantite_f TEXT \n"+
                ");");
      /*  ourDatabase.execSQL("CREATE TABLE stockm\n" +
                "(id Integer NOT NULL,\n" +
                "  produit text ,\n" +
                "     quantite_u int not null ,\n" +
                "     quantite_f int not null ,\n" +
                "    fardeau int not null ,\n" +

                "     valeur double not null \n" +


                ");");*/

    }

    public void drop(){
        ourDatabase.execSQL("DROP TABLE stockm" );
        ourDatabase.execSQL("DROP TABLE produit" );
        ourDatabase.execSQL("DROP TABLE pvente" );
        ourDatabase.execSQL("DROP TABLE p" );
        ourDatabase.delete("livraison","user=?",new String [] {String.valueOf("non")});
        ourDatabase.delete("produitv"," id_livraison not in(select id from livraison where user=?)",new String [] {String.valueOf("non")});



        /*ourDatabase.execSQL("DROP TABLE livraison" );
        ourDatabase.execSQL("DROP TABLE produitv" );


        ourDatabase.execSQL("CREATE TABLE livraison\n" +
                "( id Integer NOT NULL,\n" +
                "  id_livreur integer, \n" +
                "  id_pvente integer, \n" +
                "  bon integer, \n" +
                "  livreur TEXT ,\n" +
                "    pvente TEXT ,\n" +
                "    produit TEXT ,\n" +
                "    type_p TEXT,\n" +
                "    motif TEXT,\n" +
                "    photo BLOB,\n" +
                "    temps_p TEXT,\n" +
                "  date TEXT NOT NULL,\n" +
                "    heure TEXT NOT NULL,\n" +
                "    heure_a TEXT ,\n" +
                "    date_m TEXT,\n" +
                "    user TEXT NOT NULL default 'non',\n" +
                "    faite TEXT NOT NULL default 'non',\n" +
                "    valide TEXT  default 'non',\n" +
                "       UNIQUE (id_livreur,pvente,date,user)\n"+
                ");");
        ourDatabase.execSQL("CREATE TABLE produitv\n" +
                "( id Integer NOT NULL,\n" +
                "  id_livraison integer, \n" +
                "  id_livreur integer, \n" +
                "  id_pvente integer, \n" +
                "  produit TEXT ,\n" +
                "     quantite int not null,\n" +
                "     quantite_u int not null default 0,\n" +
                "    quantite_v int not null default 0,\n" +
                "   quantite_u_v int not null default 0,\n" +
                "    endomage int not null default 0,\n" +
                "    montant_bon double,\n" +
                "   payement double default 0,\n" +
                "   reste double default 0,\n" +
                "    date TEXT,\n" +
                "UNIQUE (produit,date,id_pvente)\n"+
                ");");*/

        ourDatabase.execSQL("CREATE TABLE stockm\n" +
                "(id Integer NOT NULL,\n" +
                "  produit text ,\n" +
                "     quantite_u int not null ,\n" +
                "     quantite_f int not null ,\n" +
                "    fardeau int not null ,\n" +

                "     valeur double not null \n" +


                ");");



        ourDatabase.execSQL("CREATE TABLE pvente\n" +
                "(id Integer NOT NULL, \n" +
                "  nom TEXT ,\n" +
                "  num TEXT ,\n" +
                "  branche TEXT ,\n" +
                "  adresse TEXT ,\n" +
                "  credit TEXT ,\n" +
                "  zone TEXT ,\n" +
                "     UNIQUE (nom,zone)\n"+
                ");");

        ourDatabase.execSQL("CREATE TABLE produit\n" +
                "(id Integer NOT NULL, \n" +
                "  nom TEXT ,\n" +
                "  prix_u TEXT ,\n" +
                "  prix_f double ,\n" +
                "  quantite_f TEXT \n"+
                ");");
        ourDatabase.execSQL("CREATE TABLE p\n" +
                "(id Integer NOT NULL, \n" +
                "  nom TEXT ,\n" +
                "  prix_u TEXT ,\n" +
                "  prix_f double \n" +

                ");");

    }
    public void c(){
        ourDatabase.execSQL("DROP TABLE login" );

        ourDatabase.execSQL("CREATE TABLE livraison\n" +
                "( id Integer NOT NULL,\n" +
                "  id_livreur integer, \n" +
                "  id_pvente integer, \n" +
                "  bon integer, \n" +
                "  livreur TEXT ,\n" +
                "    pvente TEXT ,\n" +
                "    produit TEXT ,\n" +
                "    type_p TEXT,\n" +
                "    motif TEXT,\n" +
                "    photo BLOB,\n" +
                "    temps_p TEXT,\n" +
                "  date TEXT NOT NULL,\n" +
                "    heure TEXT NOT NULL,\n" +
                "    heure_a TEXT ,\n" +
                "    date_m TEXT,\n" +
                "    user TEXT NOT NULL default 'non',\n" +
                "    faite TEXT NOT NULL default 'non',\n" +
                "    valide TEXT  default 'non'\n" +

                ");");
        ourDatabase.execSQL("CREATE TABLE produitv\n" +
                "( id Integer NOT NULL,\n" +
                "  id_livraison integer, \n" +
                "  id_livreur integer, \n" +
                "  id_pvente integer, \n" +
                "  produit TEXT ,\n" +
                "     quantite int not null,\n" +
                "     quantite_u int not null default 0,\n" +
                "    quantite_v int not null default 0,\n" +
                "   quantite_u_v int not null default 0,\n" +
                "    endomage int not null default 0,\n" +
                "    montant_bon double,\n" +
                "   payement double default 0,\n" +
                "   reste double default 0,\n" +
                "    date TEXT\n" +

                ");");

        ourDatabase.execSQL("CREATE TABLE stockm\n" +
                "(id Integer NOT NULL,\n" +
                "  produit text ,\n" +
                "     quantite_u int not null ,\n" +
                "     quantite_f int not null ,\n" +
                "     valeur double not null \n" +


                ");");

        ourDatabase.execSQL(" CREATE TABLE " + "login" + " (" +
                "id" + " INTEGER , " +
                "user" + " TEXT not null , " +
                "connecte" + " TEXT  default 'oui', " +
                "branche" + " TEXT  , " +
                "password" + " TEXT NOT NULL );"
        );
        ContentValues cv = new ContentValues();
        cv.put("id", 2);
        cv.put("user", "");
        cv.put("password", "0");
        cv.put("branche", "");


        ourDatabase.insertWithOnConflict("login", null, cv, SQLiteDatabase.CONFLICT_IGNORE);

    }

public void dec(){
    ContentValues cv = new ContentValues();
    cv.put("connecte", "non");
   ourDatabase.update("login", cv,"id<>0",null );

}
    public void updateproduitv(int id_pvente,int id_livraison,String date,String produit){
        ContentValues cv = new ContentValues();
        cv.put("id_livraison", id_livraison);
        ourDatabase.update("produitv", cv,"id_pvente="+id_pvente+" and date="+date+" and produit="+produit+" and id_livraison in(select id from livraison where user='oui') ",null );

    }

    public BDD open() throws SQLException {
        ourHelper = new DbHelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        ourHelper.close();
    }

    public long createInsert(String stuid, String subject1, String subject2,
                             String subject3, String marks1, String marks2, String marks3) {
        // TODO Auto-generated method stub
        ContentValues cv = new ContentValues();
        cv.put(KEY_STUID, stuid);
        cv.put(KEY_SUB1, subject1);
        cv.put(KEY_SUB2, subject2);
        cv.put(KEY_SUB3, subject3);
        cv.put(KEY_MARKS1, marks1);
        cv.put(KEY_MARKS2, marks2);
        cv.put(KEY_MARKS3, marks3);
        return ourDatabase.insert(DATABASE_MARKSTABLE, null, cv);

    }

    public long Insert(int id, String user, String mdp,String b) {
        // TODO Auto-generated method stub
        ContentValues cv = new ContentValues();
        cv.put("id", id);
        cv.put("user", user);
        cv.put("password", mdp);
        cv.put("branche",b );
        cv.put("connecte", "oui");


        return ourDatabase.update("login", cv,null,null );

    }
    public long InsertLocation(int id, String longg, String lat,String date,String heure) {
        // TODO Auto-generated method stub
        ContentValues cv = new ContentValues();
        cv.put("id", id);
        cv.put("longg", longg);
        cv.put("lat", lat);
        cv.put("date",date);
        cv.put("heure",heure);

        return ourDatabase.insertWithOnConflict("location", null, cv, SQLiteDatabase.CONFLICT_IGNORE);

    }


    public long InsertStock(int id, String longg, String lat,String date,String heure,int f) {
        ourDatabase.delete("stockm","produit=?",new String [] {String.valueOf(longg)});
        // TODO Auto-generated method stub
        ContentValues cv = new ContentValues();
        cv.put("id", id);
        cv.put("produit", longg);
        cv.put("quantite_f", lat);
        cv.put("quantite_u",date);
        cv.put("valeur",heure);
        cv.put("fardeau",f);


        return ourDatabase.insertWithOnConflict("stockm", null, cv, SQLiteDatabase.CONFLICT_IGNORE);

    }

    /*
    public void InsertLiv(int id,int id_l,int bon, String pvente, String heure,String faite,String motif,String date,String valide,String date_m) {
        // TODO Auto-generated method stub

        Cursor c =ourDatabase.rawQuery("select * from livraison where id=? ",new String [] {String.valueOf(id_l)});
        if(c.getCount()==0){


        ContentValues cv = new ContentValues();
        cv.put("id", id_l);
        cv.put("id_livreur", id);
        cv.put("bon", bon);
        cv.put("pvente", pvente);
        cv.put("heure",heure);
        cv.put("faite",faite);
        cv.put("motif",motif);
        cv.put("date",date);
        ourDatabase.insert("livraison", null, cv);
        }
       else if(c.getString(c.getColumnIndex("date_m")).equals(date_m)){
            ContentValues cv = new ContentValues();
            cv.put("id_livreur", id);
            cv.put("pvente", pvente);
            cv.put("bon", bon);
            cv.put("heure",heure);
            cv.put("faite",faite);
            cv.put("motif",motif);
            cv.put("date",date);
            ourDatabase.update("livraison",  cv,"id="+id_l,null);

        }

    }*/
    public void InsertLiva(int id,int id_l,int idpv,int bon, String pvente, String heure,String faite,String motif,String date,String valide,String date_m,String user) {
        // TODO Auto-generated method stub

        ourDatabase.delete("livraison","id=?",new String [] {String.valueOf(id_l)});


        ContentValues cv = new ContentValues();
        cv.put("id", id_l);
        cv.put("id_livreur", id);
        cv.put("bon", bon);
        cv.put("pvente", pvente);
        cv.put("heure",heure);
        cv.put("faite",faite);
        cv.put("motif",motif);
        cv.put("date",date);
        cv.put("user",user);
        cv.put("id_pvente",idpv);


        ourDatabase.insert("livraison", null, cv);



    }


    public void InsertLiv(int id,int id_l,String idpv,int bon, String pvente, String heure,String faite,String motif,String date,String valide,String date_m,String user) {
        // TODO Auto-generated method stub

        ourDatabase.delete("livraison","id=?",new String [] {String.valueOf(id_l)});


            ContentValues cv = new ContentValues();
            cv.put("id", id_l);
            cv.put("id_livreur", id);
            cv.put("bon", bon);
        cv.put("id_pvente", idpv);

        cv.put("pvente", pvente);
            cv.put("heure",heure);
            cv.put("faite",faite);
            cv.put("motif",motif);
            cv.put("date",date);
        cv.put("user",user);

        ourDatabase.insert("livraison", null, cv);



    }
    public void insertpvente(String branche,int id,String nom, String num,String adresse,String zone,String credit) {
        // TODO Auto-generated method stub

        ourDatabase.delete("pvente","id=?",new String [] {String.valueOf(id)});


        ContentValues cv = new ContentValues();
        cv.put("id", id);
        cv.put("branche", branche);
        cv.put("num", num);
        cv.put("nom", nom);
        cv.put("adresse",adresse);
        cv.put("zone",zone);
        cv.put("credit",credit);


        ourDatabase.insert("pvente", null, cv);



    }
    public void supprimer(int id){
        ourDatabase.delete("produitv","id_livraison=?",new String [] {String.valueOf(id)});

        ourDatabase.delete("livraison","id=?",new String [] {String.valueOf(id)});


    }
    public void supprimerpvente(int id){
        ourDatabase.delete("pvente","id=?",new String [] {String.valueOf(id)});



    }
    public void Insertproduitv(int id,int id_l,int id_livreur, String produit,String quantite, String quantite_u,String montant,String payement,String endomage,String QV,String QVU,String reste,String date) {
        // TODO Auto-generated method stub

        ourDatabase.delete("produitv","id_livraison=?",new String [] {String.valueOf(id_livreur)});


        ContentValues cv = new ContentValues();
        cv.put("id", id);
        cv.put("id_livraison", id_livreur);

        cv.put("date", date);

        cv.put("id_livreur", id_l);
        cv.put("produit", produit);
        cv.put("quantite", quantite);
        cv.put("quantite_u", quantite_u);
        cv.put("montant_bon", montant);
        cv.put("payement", payement);
        cv.put("endomage", endomage);

        cv.put("quantite_v", QV);
        cv.put("quantite_u_v", QVU);
        cv.put("reste", reste);

        ourDatabase.insert("produitv", null, cv);



    }

    public void Insertproduitvv(int id,int id_l,int id_livreur,int id_pvente, String produit,String quantite, String quantite_u,String montant,String payement,String endomage,String QV,String QVU,String reste) {
        // TODO Auto-generated method stub

        ourDatabase.delete("produitv","id_livraison=?",new String [] {String.valueOf(id_livreur)});
        String  date=new SimpleDateFormat("yyyy-MM-dd").format(new Date());


        ContentValues cv = new ContentValues();
        cv.put("id", id);
        cv.put("id_livraison", id_livreur);
        cv.put("date", date);
        cv.put("id_pvente", id_pvente);

        cv.put("id_livreur", id_l);
        cv.put("produit", produit);
        cv.put("quantite", quantite);
        cv.put("quantite_u", quantite_u);
        cv.put("montant_bon", montant);
        cv.put("payement", payement);
        cv.put("endomage", endomage);

        cv.put("quantite_v", QV);
        cv.put("quantite_u_v", QVU);
        cv.put("reste", reste);

        ourDatabase.insert("produitv", null, cv);



    }


    public void Insertproduit(int id, String produit, String prix_u,String prix_f,String quantite_f) {
        // TODO Auto-generated method stub

        ourDatabase.delete("produit","id=?",new String [] {String.valueOf(id)});


        ContentValues cv = new ContentValues();
        cv.put("id", id);
        cv.put("nom", produit);
        cv.put("quantite_f", quantite_f);
        cv.put("prix_u", prix_u);
        cv.put("prix_f", prix_f);


        ourDatabase.insert("produit", null, cv);



    }
    public void Insertpro(int id, String nom, String prix_u,String prix_f) {
        // TODO Auto-generated method stub

        ourDatabase.delete("p","id=?",new String [] {String.valueOf(id)});


        ContentValues cv = new ContentValues();
        cv.put("id", id);
        cv.put("nom", nom);
        cv.put("prix_u", prix_u);
        cv.put("prix_f", prix_f);


        ourDatabase.insert("p", null, cv);



    }

    public void synchronisatioLocation(){
        open();

        Cursor c =ourDatabase.rawQuery("select * from location",null);
        while (c.moveToNext()) {
            int id = c.getInt(c.getColumnIndex("id"));
            String longg = c.getString(c.getColumnIndex("longg"));

            String lat = c.getString(c.getColumnIndex("lat"));
            String date = c.getString(c.getColumnIndex("date"));
            String heure = c.getString(c.getColumnIndex("heure"));

            AndroidNetworking.post(DATA_INSERT_URL)

                    .addBodyParameter("action","locationA")
                    .addBodyParameter("lat", String.valueOf(lat))
                    .addBodyParameter("long", String.valueOf(longg))
                    .addBodyParameter("id", id+"")
                    .addBodyParameter("heure",heure)
                    .addBodyParameter("date",date)
                    .setTag("test")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONArray(new JSONArrayRequestListener() {
                        @Override
                        public void onResponse(JSONArray response) {

                            if(response != null)
                                try {

                                    //SHOW RESPONSE FROM SERVER

                                    String responseString= response.get(0).toString();




                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                        }

                        //ERROR
                        @Override
                        public void onError(ANError anError) {
                            Log.e( "BDD","Erreur");

                        }


                    });



        }

        close();


    }

    public void synchronisatioLiv(){
open();
        Cursor c =ourDatabase.rawQuery("select * from livraison",null);
        final int count =c.getCount();
        final int i=0;
        Log.e("count",count+"");

        while (c.moveToNext()) {
            int id = c.getInt(c.getColumnIndex("id"));
            int id_livreur = c.getInt(c.getColumnIndex("id_livreur"));

            String date_m="",temps_p="",heure_a="",heure="",photo="",idpv="",date="";
            String faite= c.getString(c.getColumnIndex("faite"));
            if(c.getString(c.getColumnIndex("date_m"))!=null)
            date_m = c.getString(c.getColumnIndex("date_m"));
            if(c.getString(c.getColumnIndex("date"))!=null)
                date = c.getString(c.getColumnIndex("date"));
            String motif = c.getString(c.getColumnIndex("motif"));
            if(c.getString(c.getColumnIndex("photo"))!=null)
                photo = c.getString(c.getColumnIndex("photo"));
            String user = c.getString(c.getColumnIndex("user"));
            String bon = c.getString(c.getColumnIndex("bon"));
            if(c.getString(c.getColumnIndex("id_pvente"))!=null)
                 idpv = c.getString(c.getColumnIndex("id_pvente"));
            String pvente = c.getString(c.getColumnIndex("pvente"));
            if(c.getString(c.getColumnIndex("temps_p"))!=null)
                temps_p= c.getString(c.getColumnIndex("temps_p"));
            String valide = c.getString(c.getColumnIndex("valide"));
            if(c.getString(c.getColumnIndex("heure_a"))!=null)
                heure_a= c.getString(c.getColumnIndex("heure_a"));
            if(c.getString(c.getColumnIndex("heure"))!=null)
                heure= c.getString(c.getColumnIndex("heure"));
           // Log.e( "BDD","id:"+id+"idlivreur"+id_livreur+"id_pvente:"+idpv+"date_m:"+date_m+"pvente:"+pvente+"valide:"+valide+"user:"+user+"faite:"+faite+"photo"+photo+"temps_p:"+temps_p+"heure:"+heure+"heure_a"+heure_a+"bon"+bon+"motif"+motif);


            AndroidNetworking.post(DATA_INSERT_URL)
                    .addBodyParameter("action","update livraisonS")
                    .addBodyParameter("id", id+"")
                    .addBodyParameter("id_livreur", id_livreur+"")
                    .addBodyParameter("id_pvente", idpv+"")
                    .addBodyParameter("pvente", pvente+"")
                    .addBodyParameter("valide", valide+"")
                    .addBodyParameter("user", String.valueOf(user))
                    .addBodyParameter("faite",faite)
                    .addBodyParameter("photo",photo)
                    .addBodyParameter("temps_p",temps_p)
                    .addBodyParameter("heure",heure)
                    .addBodyParameter("heure_a",heure_a)
                    .addBodyParameter("bon",bon)
                    .addBodyParameter("motif",motif)
                    .addBodyParameter("date_m",date_m)
                    .addBodyParameter("date",date)
                    .setTag("test")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONArray(new JSONArrayRequestListener() {
                        @Override
                        public void onResponse(JSONArray response) {

                            if(response != null) {

                                //SHOW RESPONSE FROM SERVER

                                try {
                                    String responseString= response.get(0).toString();
                                    if(k==count-1){
                                       synchronisationproduitv();
                                       k=0;
                                    }
                                    k++;

                                    if(responseString.contains("Unsuccessfull")){
                                        x=0;
                                        Toast.makeText(ourContext, "Erreur ! Réessayez la synchronisation ", Toast.LENGTH_SHORT).show();


                                    }
                                    else{

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }

                        }

                        //ERROR
                        @Override
                        public void onError(ANError anError) {
                            Log.e( "BDD","1111111111"+anError);

                        }


                    });



        }
close();

    }

    public void synchronisationproduitv(){
open();
        Cursor c =ourDatabase.rawQuery("select * from produitv",null);
        while (c.moveToNext()) {
            int id = c.getInt(c.getColumnIndex("id"));
            int id_livreur = c.getInt(c.getColumnIndex("id_livreur"));
            int id_livraison = c.getInt(c.getColumnIndex("id_livraison"));

            String quantite="",quantite_u="",quantite_v="",quantite_u_v="",reste="",produit="",endomage="",payement="",date="",idpv="",montant="";
            if(c.getString(c.getColumnIndex("quantite"))!=null)
                quantite= c.getString(c.getColumnIndex("quantite"));
            if(c.getString(c.getColumnIndex("quantite_u"))!=null)
                quantite_u= c.getString(c.getColumnIndex("quantite_u"));
            if(c.getString(c.getColumnIndex("quantite_v"))!=null)
                quantite_v= c.getString(c.getColumnIndex("quantite_v"));
            if(c.getString(c.getColumnIndex("quantite_u_v"))!=null)
                quantite_u_v= c.getString(c.getColumnIndex("quantite_u_v"));
            if(c.getString(c.getColumnIndex("reste"))!=null)
                reste= c.getString(c.getColumnIndex("reste"));
            if(c.getString(c.getColumnIndex("payement"))!=null)
                payement= c.getString(c.getColumnIndex("payement"));
            if(c.getString(c.getColumnIndex("endomage"))!=null)
                endomage= c.getString(c.getColumnIndex("endomage"));
            if(c.getString(c.getColumnIndex("id_pvente"))!=null)
                idpv = c.getString(c.getColumnIndex("id_pvente"));
            if(c.getString(c.getColumnIndex("date"))!=null)
                date= c.getString(c.getColumnIndex("date"));
            if(c.getString(c.getColumnIndex("produit"))!=null)
                produit= c.getString(c.getColumnIndex("produit"));
            if(c.getString(c.getColumnIndex("montant_bon"))!=null)
                montant= c.getString(c.getColumnIndex("montant_bon"));

//Log.e("HYY","id"+id+"montant"+montant+"idpvente"+idpv+"reste:"+reste+"produit"+produit+"id_livreur"+id_livreur+"id_livraison"+id_livraison+"quantite"+quantite+"quantite_u"+quantite_u+"qv"+quantite_v+"quantite_u_v"+quantite_u_v+"endomage"+endomage+"payement"+payement+"date"+date);
            AndroidNetworking.post(DATA_INSERT_URL)
                    .addBodyParameter("action","update produitS")
                    .addBodyParameter("id", id+"")
                    .addBodyParameter("id_livreur", id_livreur+"")
                    .addBodyParameter("id_pvente", idpv+"")
                    .addBodyParameter("quantite", quantite+"")
                    .addBodyParameter("quantite_u", quantite_u+"")
                    .addBodyParameter("quantite_v", quantite_v)
                    .addBodyParameter("quantite_u_v",quantite_u_v)
                    .addBodyParameter("endomage",endomage)
                    .addBodyParameter("montant",montant)
                    .addBodyParameter("produit",produit)
                    .addBodyParameter("reste",reste)
                    .addBodyParameter("payement",payement)
                    .addBodyParameter("date",date)
                    .addBodyParameter("id_livraison",id_livraison+"")
                    .setTag("test")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONArray(new JSONArrayRequestListener() {
                        @Override
                        public void onResponse(JSONArray response) {

                            if(response != null) {

                                //SHOW RESPONSE FROM SERVER

                                try {
                                    String responseString= response.get(0).toString();


                                    if(responseString.contains("Unsuccessfull")){
                                        x=0;
                                        Toast.makeText(ourContext, "Erreur ! Réessayez la synchronisation ", Toast.LENGTH_SHORT).show();


                                    }
                                    else{

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }

                        }

                        //ERROR
                        @Override
                        public void onError(ANError anError) {
                            Log.e( "BDD","2222222"+anError);

                        }


                    });



        }

close();
    }
    public void synchronisationpvente(){
open();
        Cursor c =ourDatabase.rawQuery("select * from pvente",null);
        while (c.moveToNext()) {
            int id = c.getInt(c.getColumnIndex("id"));


            String branche="",zone="",num="",nom="",adresse="";
            if(c.getString(c.getColumnIndex("branche"))!=null)
                branche= c.getString(c.getColumnIndex("branche"));
            if(c.getString(c.getColumnIndex("zone"))!=null)
                zone= c.getString(c.getColumnIndex("zone"));
            if(c.getString(c.getColumnIndex("num"))!=null)
                num= c.getString(c.getColumnIndex("num"));
            if(c.getString(c.getColumnIndex("nom"))!=null)
                nom= c.getString(c.getColumnIndex("nom"));
            if(c.getString(c.getColumnIndex("adresse"))!=null)
                adresse= c.getString(c.getColumnIndex("adresse"));



            AndroidNetworking.post(DATA_INSERT_URL)
                    .addBodyParameter("action","mod pvente")
                    .addBodyParameter("id", id+"")
                    .addBodyParameter("branche", branche+"")
                    .addBodyParameter("zone", zone+"")
                    .addBodyParameter("nom", nom+"")
                    .addBodyParameter("lieu",adresse+"")
                    .addBodyParameter("num",num+"")
                    .setTag("test")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONArray(new JSONArrayRequestListener() {
                        @Override
                        public void onResponse(JSONArray response) {

                            if(response != null) {

                                //SHOW RESPONSE FROM SERVER

                                try {
                                    String responseString= response.get(0).toString();


                                    if(responseString.contains("Unsuccessfull")){
                                        x=0;
                                        Toast.makeText(ourContext, "Erreur ! Réessayez la synchronisation ", Toast.LENGTH_SHORT).show();


                                    }
                                    else{

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }

                        }

                        //ERROR
                        @Override
                        public void onError(ANError anError) {
                            Log.e( "BDD","33333333"+anError);

                        }


                    });



        }
        close();


    }

    public void synchronisationstockm(){


open();
        Cursor c =ourDatabase.rawQuery("select * from stockm",null);
        int qu=0,qf=0,f=1;
        while(c.moveToNext()){
            if(c.getString(c.getColumnIndex("quantite_u"))!=null)

            qu = Integer.parseInt(c.getString(c.getColumnIndex("quantite_u")));
            if(c.getString(c.getColumnIndex("quantite_f"))!=null)

                qf = Integer.parseInt(c.getString(c.getColumnIndex("quantite_f")));
            if(c.getString(c.getColumnIndex("fardeau"))!=null)

            f = Integer.parseInt(c.getString(c.getColumnIndex("fardeau")));

            qu=qf*f+qu;


            int id = c.getInt(c.getColumnIndex("id"));


            String produit="";
            if(c.getString(c.getColumnIndex("produit"))!=null)
                produit= c.getString(c.getColumnIndex("produit"));





            AndroidNetworking.post(DATA_INSERT_URL)
                    .addBodyParameter("action","update stockm")
                    .addBodyParameter("id", id+"")
                    .addBodyParameter("quantite", qu+"")
                    .addBodyParameter("produit", produit+"")
                    .setTag("test")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONArray(new JSONArrayRequestListener() {
                        @Override
                        public void onResponse(JSONArray response) {

                            if(response != null) {

                                //SHOW RESPONSE FROM SERVER

                                try {
                                    String responseString= response.get(0).toString();


                                    if(responseString.contains("Unsuccessfull")){
                                        x=0;
                                        Toast.makeText(ourContext, "Erreur ! Réessayez la synchronisation ", Toast.LENGTH_SHORT).show();


                                    }
                                    else if(x==1){
                                        Toast.makeText(ourContext, "Synch reussie", Toast.LENGTH_SHORT).show();

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }

                        }

                        //ERROR
                        @Override
                        public void onError(ANError anError) {
                            Log.e( "BDD","44444444"+anError);

                        }


                    });



        }
        close();


    }
    public int getID(){

        Cursor c =ourDatabase.rawQuery("select id from login",null);
while (c.moveToNext()){

    int data = c.getInt(c.getColumnIndex("id"));
    return data;
}

return 0;

    }


    public String getuser(){

        Cursor c =ourDatabase.rawQuery("select user from login where connecte='oui'",null);
        while (c.moveToNext()){

            String data = c.getString(c.getColumnIndex("user"));

            return data;
        }

        return "";

    }

    public int getIDD(){

        Cursor c =ourDatabase.rawQuery("select id from login where connecte='oui'",null);
        while (c.moveToNext()){

            int data = c.getInt(c.getColumnIndex("id"));

            return data;
        }

        return 0;

    }

    public String getpassword(){
        Cursor c =ourDatabase.rawQuery("select password from login  where connecte='oui'",null);
        while (c.moveToNext()){

            String data = String.valueOf(c.getString(c.getColumnIndex("password")));


            return data;
        }

        return "";

    }
    public String getb(){
        Cursor c =ourDatabase.rawQuery("select branche from login  where connecte='oui'",null);
        while (c.moveToNext()){

            String data = String.valueOf(c.getString(c.getColumnIndex("branche")));


            return data;
        }

        return "";

    }

    public String getuserR(){

        Cursor c =ourDatabase.rawQuery("select user from login where connecte='non'",null);
        while (c.moveToNext()){

            String data = c.getString(c.getColumnIndex("user"));

            return data;
        }

        return "";

    }

    public String getpasswordD(){
        Cursor c =ourDatabase.rawQuery("select password from login  where connecte='non'",null);
        while (c.moveToNext()){

            String data = String.valueOf(c.getString(c.getColumnIndex("password")));


            return data;
        }

        return "";

    }
    public Cursor getLiv(int id){
        String  date=new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        Cursor c =ourDatabase.rawQuery("select * from livraison where id_livreur=? and date=? and user='non' and valide='non'",new String [] {String.valueOf(id),String.valueOf(date)});


        return c;

    }
    public Cursor getLiva(int id){
        String  date=new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        Cursor c =ourDatabase.rawQuery("select * from livraison where id_livreur=? and date=? and user='oui' and valide='non'",new String [] {String.valueOf(id),String.valueOf(date)});


        return c;

    }
    public Cursor getLivv(int id,int idp){
        String  date=new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        Cursor c =ourDatabase.rawQuery("select * from livraison where user='non' and id_livreur=? and id_pvente in (select * from produitv where id_pvente=? and reste>0 ) ",new String [] {String.valueOf(id),String.valueOf(idp)});


        return c;

    }
    public Cursor getbonliv(int id){

        Cursor c =ourDatabase.rawQuery("select * from livraison where id_livreur=? and bon is not null and id is not null order by id desc limit 1",new String [] {String.valueOf(id)});


        return c;

    }
    public Cursor getp(int id){

        Cursor c =ourDatabase.rawQuery("select * from produitv where id_livreur=? and id is not null order by id desc limit 1",new String [] {String.valueOf(id)});


        return c;

    }
    public Cursor getLivv(int id){
        String  date=new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        Cursor c =ourDatabase.rawQuery("select * from livraison where id_livreur=? and date=? and user='oui' and valide='non'",new String [] {String.valueOf(id),String.valueOf(date)});


        return c;

    }
    public Cursor getproduitv(int id){
        String  date=new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        Cursor c =ourDatabase.rawQuery("select * from produitv where id_livraison=? ",new String [] {String.valueOf(id)});


        return c;

    }
    public Cursor getproduit(){

        Cursor c =ourDatabase.rawQuery("select * from produit ",null);


        return c;

    }
    public Cursor getpr(){

        Cursor c =ourDatabase.rawQuery("select * from p",null);


        return c;

    }
    public Cursor getpvente(){

        Cursor c =ourDatabase.rawQuery("select * from pvente ",null);


        return c;

    }
    public Cursor getpventee(){

        Cursor c =ourDatabase.rawQuery("select * from pvente order by id desc limit 1 ",null);


        return c;

    }

    public Cursor getmag(int id){
        String  date=new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        Cursor c =ourDatabase.rawQuery("select * from stockm where id="+id,null);


        return c;

    }
    public Cursor getmagg(int id){
        String  date=new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        Cursor c =ourDatabase.rawQuery("select produit,quantite_f*fardeau+quantite_u as quantite from stockm where id="+id,null);


        return c;

    }


    public void updateLivA(String idp,int id_l,String quantite_v,String quantite_u_v,String payement,String endomage,String reste,String produit,int id_livreur,int u){

        ContentValues cv = new ContentValues();
        String  date=new SimpleDateFormat("yyyy-MM-dd_HH:mm").format(new Date());
        cv.put("motif", "");
        cv.put("photo", "");
        cv.put("temps_p", "");
        cv.put("valide", "non");
        cv.put("faite", "oui");
        cv.put("date_m", date);
        ourDatabase.update("livraison",  cv,"id="+id_l,null);


        Cursor x =ourDatabase.rawQuery("select * from stockm where  produit=\""+produit+"\"",null);
        int qu=0,qf=0,f=1;
        while(x.moveToNext()){

            qu = Integer.parseInt(x.getString(x.getColumnIndex("quantite_u")));
            qf = Integer.parseInt(x.getString(x.getColumnIndex("quantite_f")));

            f = Integer.parseInt(x.getString(x.getColumnIndex("fardeau")));

            qu=qf*f+qu;

        }
        Cursor c =ourDatabase.rawQuery("select * from produitv where id="+idp,null);
        int uu=0,qv=0;
        double p=0,pu=0;
        while(c.moveToNext()){

            int quu = Integer.parseInt(c.getString(c.getColumnIndex("quantite_u_v")));
             qv = Integer.parseInt(c.getString(c.getColumnIndex("quantite_v")));

            uu=(qv*f)+quu;

        }

        ContentValues cc = new ContentValues();
        int R=qu+uu-u;
        int res=(int)((qu/f)+(uu/f)-(u/f));
            cc.put("quantite_u", R-(res*f));
        cc.put("quantite_f", res);



        ourDatabase.update("stockm", cc,"produit=\""+produit+"\"",null );

        ContentValues cvv = new ContentValues();
        cvv.put("payement", payement);
        cvv.put("quantite_v", quantite_v);
        cvv.put("quantite_u_v", quantite_u_v);
        cvv.put("endomage", endomage);
        cvv.put("reste", reste);

        ourDatabase.update("produitv", cvv,"id="+idp,null );


    }
    public void updateLivB(String idp,int id_l,String quantite_v,String quantite_u_v,String payement,String endomage,String reste,String produit,int id_livreur,int u,String motif){

        ContentValues cv = new ContentValues();
        String  date=new SimpleDateFormat("yyyy-MM-dd_HH:mm").format(new Date());
        cv.put("motif", motif);
        cv.put("valide", "non");
        cv.put("faite", "oui");
        cv.put("date_m", date);
        ourDatabase.update("livraison",  cv,"id="+id_l,null);


        Cursor x =ourDatabase.rawQuery("select * from stockm where  produit=\""+produit+"\"",null);
        int qu=0,qf=0,f=1;
        while(x.moveToNext()){

            qu = Integer.parseInt(x.getString(x.getColumnIndex("quantite_u")));
            qf = Integer.parseInt(x.getString(x.getColumnIndex("quantite_f")));

            f = Integer.parseInt(x.getString(x.getColumnIndex("fardeau")));

            qu=qf*f+qu;

        }
        Cursor c =ourDatabase.rawQuery("select * from produitv where id="+idp,null);
        int uu=0,qv=0;
        double p=0,pu=0;
        while(c.moveToNext()){

            int quu = Integer.parseInt(c.getString(c.getColumnIndex("quantite_u_v")));
            qv = Integer.parseInt(c.getString(c.getColumnIndex("quantite_v")));

            uu=(qv*f)+quu;

        }

        ContentValues cc = new ContentValues();
        int R=qu+uu-u;
        int res=(int)((qu/f)+(uu/f)-(u/f));
        cc.put("quantite_u", R-(res*f));
        cc.put("quantite_f", res);



        ourDatabase.update("stockm", cc,"produit=\""+produit+"\"",null );

        ContentValues cvv = new ContentValues();
        cvv.put("payement", payement);
        cvv.put("quantite_v", quantite_v);
        cvv.put("quantite_u_v", quantite_u_v);
        cvv.put("endomage", endomage);
        cvv.put("reste", reste);

        ourDatabase.update("produitv", cvv,"id="+idp,null );


    }


    public void updateLivC(String idp,int id_l,String quantite_v,String quantite_u_v,String payement,String endomage,String reste,String produit,int id_livreur,int u,String motif,String photo,String temps){

        ContentValues cv = new ContentValues();
        String  date=new SimpleDateFormat("yyyy-MM-dd_HH:mm").format(new Date());
        cv.put("motif", motif);
        cv.put("photo", photo);
        cv.put("temps_p", temps);
        cv.put("valide", "non");
        cv.put("faite", "oui");
        cv.put("date_m", date);
        ourDatabase.update("livraison",  cv,"id="+id_l,null);


        Cursor x =ourDatabase.rawQuery("select * from stockm where  produit=\""+produit+"\"",null);
        int qu=0,qf=0,f=1;
        while(x.moveToNext()){

            qu = Integer.parseInt(x.getString(x.getColumnIndex("quantite_u")));
            qf = Integer.parseInt(x.getString(x.getColumnIndex("quantite_f")));

            f = Integer.parseInt(x.getString(x.getColumnIndex("fardeau")));

            qu=qf*f+qu;

        }
        Cursor c =ourDatabase.rawQuery("select * from produitv where id="+idp,null);
        int uu=0,qv=0;
        double p=0,pu=0;
        while(c.moveToNext()){

            int quu = Integer.parseInt(c.getString(c.getColumnIndex("quantite_u_v")));
            qv = Integer.parseInt(c.getString(c.getColumnIndex("quantite_v")));

            uu=(qv*f)+quu;

        }

        ContentValues cc = new ContentValues();
        int R=qu+uu-u;
        int res=(int)((qu/f)+(uu/f)-(u/f));
        cc.put("quantite_u", R-(res*f));
        cc.put("quantite_f", res);



        ourDatabase.update("stockm", cc,"produit=\""+produit+"\"",null );

        ContentValues cvv = new ContentValues();
        cvv.put("payement", payement);
        cvv.put("quantite_v", quantite_v);
        cvv.put("quantite_u_v", quantite_u_v);
        cvv.put("endomage", endomage);
        cvv.put("reste", reste);

        ourDatabase.update("produitv", cvv,"id="+idp,null );


    }



    public void annuler(int id,String idp,int id_livreur,String produit){

        ContentValues cv = new ContentValues();
        String  date=new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        cv.put("motif", "");
        cv.put("photo", "");
        cv.put("temps_p", "");
        cv.put("valide", "non");
        cv.put("faite", "non");
        cv.put("date_m", "");


        ourDatabase.update("livraison",  cv,"id="+id,null);


        Cursor x =ourDatabase.rawQuery("select * from stockm where  produit=\""+produit+"\"",null);
        int qu=0,qf=0,f=1;
        while(x.moveToNext()){

            qu = Integer.parseInt(x.getString(x.getColumnIndex("quantite_u")));
            qf = Integer.parseInt(x.getString(x.getColumnIndex("quantite_f")));

            f = Integer.parseInt(x.getString(x.getColumnIndex("fardeau")));

            qu=qf*f+qu;

        }
        Cursor c =ourDatabase.rawQuery("select * from produitv where id="+idp,null);
        int uu=0,qv=0;
        double p=0,pu=0;
        while(c.moveToNext()){

            int quu = Integer.parseInt(c.getString(c.getColumnIndex("quantite_u_v")));
            qv = Integer.parseInt(c.getString(c.getColumnIndex("quantite_v")));

            uu=(qv*f)+quu;

        }

        ContentValues cc = new ContentValues();
        int R=qu+uu;
        int res=(int)((qu/f)+(uu/f));
        cc.put("quantite_u", R-(res*f));
        cc.put("quantite_f", res);

        ourDatabase.update("stockm", cc,"produit=\""+produit+"\"",null );

        ContentValues cvv = new ContentValues();
        cvv.put("payement", "0");
        cvv.put("quantite_v", "0");
        cvv.put("quantite_u_v", "0");
        cvv.put("endomage", "0");
        cvv.put("reste", "0");

        ourDatabase.update("produitv", cvv,"id="+idp,null );


    }
}