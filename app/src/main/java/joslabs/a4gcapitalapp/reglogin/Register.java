package joslabs.a4gcapitalapp.reglogin;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import joslabs.a4gcapitalapp.R;

public class Register extends AppCompatActivity implements View.OnClickListener {
Button location,btnregister,btndatepick,btnselect,btncancel;
EditText edtidno,edtusername;
DatabaseReference dbref;
RelativeLayout relativeLayout;
String latsLongs, dates, times, datestime,username,idno,txtlocation,station;
LinearLayout linearLayout;
ImageView imguser;
Spinner spn;
MapView mapView;
TextView txtdob;
    Bitmap bitmap;
    private static final int SELECT_PICTURE = 100;

    private int mYear, mMonth, mDay, mHour, mMinute,x,maxPoints,p;
String fkey,photourl;
    StorageReference storageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        //my firebase storage url
        storageRef = storage.getReferenceFromUrl("gs://brucecompany-dca0f.appspot.com/");
        btnselect=findViewById(R.id.btnselect);
        btncancel=findViewById(R.id.btncancel);
        linearLayout=findViewById(R.id.layerbottom);
        relativeLayout=findViewById(R.id.relativeLayer);
        mapView=findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
txtdob=findViewById(R.id.txtDob);
        mapView.onResume();
        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

       location=findViewById(R.id.btnlocation);
       imguser=findViewById(R.id.imgprofile);
       btnregister=findViewById(R.id.btnreg);

       location.setOnClickListener(this);
        btnregister.setOnClickListener(this);
        imguser.setOnClickListener(this);
      //  btnid.setOnClickListener(this);

       btnregister=findViewById(R.id.btnreg);
       edtusername=findViewById(R.id.edtname);
       edtidno=findViewById(R.id.edtidno);
       btndatepick=findViewById(R.id.btndatepick);
        btndatepick.setOnClickListener(this);

        location.setOnClickListener(this);
        btnregister.setOnClickListener(this);
        btncancel.setOnClickListener(this);
        btnselect.setOnClickListener(this);


        dbref= FirebaseDatabase.getInstance().getReference();
        fkey=dbref.push().getKey();
       spn=findViewById(R.id.spn);
        List<String> spinnerArray = new ArrayList<String>();

        spinnerArray.add("Kisumu");
        spinnerArray.add("Nirobi");
        spinnerArray.add("Mombasa");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);
        spn.setAdapter(adapter);
        //here



        //tohere
        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                station=spn.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

               googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                   @Override
                   public void onMapClick(LatLng latLng) {
                       latsLongs=latLng.latitude+","+latLng.longitude;
                       Toast.makeText(Register.this, "map"+latsLongs, Toast.LENGTH_SHORT).show();
                   }
               });
            }
        });







    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btndatepick:
                getDate();
                break;
            case R.id.btnreg:
                Registeruser();
                break;
            case R.id.btnlocation:
                SelectLocation();
                break;
            case R.id.btncancel:
                CancelMap();
                break;
            case R.id.btnselect:
                CancelMap();
                break;
            case R.id.imgprofile:
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i,"Select Picture"),SELECT_PICTURE );

        }
    }

    private void CancelMap() {
        mapView.setVisibility(View.GONE);
        linearLayout.setVisibility(View.GONE);
        Toast.makeText(this,"clicked",Toast.LENGTH_SHORT);
        relativeLayout.setVisibility(View.VISIBLE);
    }

    private void SelectLocation() {
        mapView.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.VISIBLE);
        relativeLayout.setVisibility(View.GONE);

    }

    private void Registeruser() {
        username = edtusername.getText().toString();
        idno = edtidno.getText().toString();
        //from her to get the code
       if (imguser.getDrawable() != null) {
        Random rnd = new Random();
        final int randoms = 100000 + rnd.nextInt(900000);
        StorageReference myfileRef = storageRef.child(randoms + "quizimg.jpg");
        imguser.setDrawingCacheEnabled(true);
        imguser.buildDrawingCache();
        bitmap = imguser.getDrawingCache();
        imguser.setDrawingCacheEnabled(true);
        imguser.buildDrawingCache();
        bitmap = imguser.getDrawingCache();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = myfileRef.putBytes(data);

        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Register.this, taskSnapshot.getBytesTransferred() + "", Toast.LENGTH_SHORT).show();

            }
        });
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(Register.this, "TASK FAILED", Toast.LENGTH_SHORT).show();
                Log.i("errorx", exception.getMessage());
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Register.this, "TASK SUCCEEDED", Toast.LENGTH_SHORT).show();
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                String DOWNLOAD_URL = downloadUrl.getPath();
                Log.v("DOWNLOAD URL", DOWNLOAD_URL);
                Log.d("Downx", DOWNLOAD_URL + "\n" + downloadUrl);
                photourl = downloadUrl.toString();
                Toast.makeText(Register.this, DOWNLOAD_URL, Toast.LENGTH_SHORT).show();

                Date today = Calendar.getInstance().getTime();
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.UK);
                final String reportDate = df.format(today);
                UserMethod users = new UserMethod(username, idno, latsLongs, station, datestime,photourl);
                dbref.child("users").child(fkey).setValue(users);
            }
        });
    }else {
        //uptohr the code
        Date today = Calendar.getInstance().getTime();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.UK);
        final String reportDate = df.format(today);
            UserMethod users = new UserMethod("name", idno, latsLongs, station, datestime,photourl);
            dbref.child("users").child(fkey).setValue(users).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(Register.this, "Chats added succesfully", Toast.LENGTH_SHORT).show();
            }
        });
    }
    }


    private void getDate() {

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);




        Calendar calendar = Calendar.getInstance();
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpDialog = new DatePickerDialog(this, myDateListener, mYear, mMonth, mDay);


       // dpDialog.getDatePicker().setMinDate(System.currentTimeMillis()+(1000*60*60*24));
        Log.e("mindate",calendar.getTimeInMillis()+"");
        dpDialog.show();
    }
    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int year, int monthOfYear, int dayOfMonth) {
            dates=dayOfMonth + "." + (monthOfYear + 1) + "." + year+"";
            datestime = dates;
            txtdob.setText("D.O.B : "+datestime);
            //timedate.setText(datestime);
           // getTime();
            Log.e("onDateSet()", "arg0 = [" + arg0 + "], year = [" + year + "], monthOfYear = [" + monthOfYear + "], dayOfMonth = [" + dayOfMonth + "]");
        }
    };
    protected  void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode==RESULT_OK){
            if(requestCode==SELECT_PICTURE){
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // Get the path from the Uri
                    String path = getPathFromURI(selectedImageUri);
                    Log.i("IMAGE PATH TAG", "Image Path : " + path);
                    // Set the image in ImageView
                    imguser.setImageURI(selectedImageUri);

                }
            }
        }
    }
    private String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }
}
