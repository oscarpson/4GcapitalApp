package joslabs.a4gcapitalapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

import joslabs.a4gcapitalapp.dashboard.DashboardActivity;
import joslabs.a4gcapitalapp.reglogin.Register;
import joslabs.a4gcapitalapp.reglogin.UserMethod;
import joslabs.a4gcapitalapp.reglogin.UsersViewHolder;

public class MainActivity extends AppCompatActivity {
List<UserMethod> users;
FirebaseRecyclerAdapter<UserMethod,UsersViewHolder>firebaseRecyclerAdapter;
DatabaseReference dbref;
RecyclerView rcv_users;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                 //   mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    Intent intss=new Intent(getApplicationContext(), DashboardActivity.class);
                    startActivity(intss);
                    return true;
                case R.id.navigation_notifications:
                   intss=new Intent(getApplicationContext(), Register.class);
                   startActivity(intss);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
dbref= FirebaseDatabase.getInstance().getReference();
rcv_users=findViewById(R.id.rcv_customers);
rcv_users.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
       // mTextMessage = (TextView) findViewById(R.id.message);
        firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<UserMethod, UsersViewHolder>(UserMethod.class,R.layout.customer_layer,UsersViewHolder.class,dbref.child("users")) {
            @Override
            protected void populateViewHolder(UsersViewHolder viewHolder, UserMethod model, int position) {
            viewHolder.username.setText("UserName:\t "+model.getUsername());
            viewHolder.idno.setText("Id_No: \t"+model.getIdno());
            viewHolder.station.setText("Ststation: \t"+model.getUstation());
            viewHolder.locaton.setText("Location: \t");
            viewHolder.dob.setText("D.O.B:\t "+model.getUdob());
//setting customer profile image
                if (model.getImgprofile()==null||model.getImgprofile()==""){
                    viewHolder.imgprofile.setImageResource(R.drawable.babycare);
                }else {
                    Picasso.with(getApplicationContext())

                            .load(model.getImgprofile())

                            .into(viewHolder.imgprofile);
                }

            }
        };
        rcv_users.setAdapter(firebaseRecyclerAdapter);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
