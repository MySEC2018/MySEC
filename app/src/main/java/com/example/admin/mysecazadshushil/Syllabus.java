package com.example.admin.mysecazadshushil;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//import zoftino.com.firestore.EmailPasswordAuthActivity;
//import zoftino.com.firestore.MainActivity;
//import zoftino.com.firestore.R;

public class Syllabus extends AppCompatActivity {

    //private FirebaseUser user;
    private static final String TAG = "Syllabus";
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus);

        /*user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            //if user is not authenticated show authentication screen
            Intent i = new Intent();
            //i.setClass(this, EmailPasswordAuthActivity.class);
            startActivity(i);
        }*/

        //Toolbar tb = findViewById(R.id.toolbar);
        //setSupportActionBar(tb);
        // tb.setSubtitle("Upload Files");

        fm = getSupportFragmentManager();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.file_menu, menu);
        return true;
    }
    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.upload_file_m:
                addFileFrgmt(user.getUid());
                return true;
            case R.id.view_file_m:
                viewFilesFrgmt(user.getUid());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/
    public void addFileFrgmt(View view){
        Bundle bundle = new Bundle();
        //bundle.putString("userId", userId);
        Syllabus_upload_frag uploadFragment = new Syllabus_upload_frag();
        uploadFragment.setArguments(bundle);
        FragmentTransaction ft = fm.beginTransaction();
        //ft.replace(R.id.files_frame, uploadFragment);
        ft.commit();
    }
    public void viewFilesFrgmt(View view){
        Bundle bundle = new Bundle();
        //bundle.putString("userId", userId);

        Syllabus_viewfil_frag viewFilesFragment = new Syllabus_viewfil_frag();
        viewFilesFragment.setArguments(bundle);

        FragmentTransaction ft = fm.beginTransaction();
        //ft.replace(R.id.files_frame, viewFilesFragment);
        ft.commit();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "external public folder access granted");
                } else {
                    Log.e(TAG, "external public folder access denied sending user to main screen");
                    Toast.makeText(this,
                            "Please grant permission to access public folders to use the feature",
                            Toast.LENGTH_SHORT).show();
                    toMainScreen();
                }
                return;
            }
        }
    }
    private void toMainScreen(){
        Intent i = new Intent();
        i.setClass(this, Syllabus.class);
        startActivity(i);
    }
}