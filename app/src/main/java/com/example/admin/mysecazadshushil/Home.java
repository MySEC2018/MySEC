package com.example.admin.mysecazadshushil;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.joooonho.SelectableRoundedImageView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import maes.tech.intentanim.CustomIntent;
import me.relex.circleindicator.CircleIndicator;

public class Home extends AppCompatActivity {
    Dialog mydialog, logindia;
     ImageView mImage;
     SelectableRoundedImageView roundImage;
     Uri mImageUri;
     EditText name;
     TextView setname;
     static final int PICK_IMAGE_REQUEST=1;
     Button namesetter, imagesetter;


    EditText loginemail, loginpass;
    static Button loginbutton;
    String email, pass;
    TextView success, fail;
    LinearLayout loginp, adminp,techcsesection,campsection, sylopen;


    private DrawerLayout myDrawer;
    private ActionBarDrawerToggle myToggle;
    private LinearLayout l1;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static final Integer[] XMEN= {R.drawable.two,R.drawable.three, R.drawable.one, R.drawable.four, R.drawable.five};
    private ArrayList<Integer> XMENArray = new ArrayList<Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setTitle("Welcome");
        init();

        ///header layout part
        mydialog=new Dialog(this);
        logindia=new Dialog(this);
        roundImage=(SelectableRoundedImageView) findViewById(R.id.userphoto);
        name=(EditText) findViewById(R.id.editname);
        setname=(TextView)findViewById (R.id.myname);
        //forsharepreferencefore retrieving ingormation
        try{
            retrieve();
        }catch (Exception ex)
        {

        }





        l1=(LinearLayout)findViewById(R.id.l1);
        Animation animation= AnimationUtils.loadAnimation(this, R.anim.splashscreen);
        l1.startAnimation(animation);
        myDrawer=(DrawerLayout) findViewById(R.id.drawer);
        myToggle=new ActionBarDrawerToggle(this, myDrawer, R.string.open, R.string.close);
        myDrawer.addDrawerListener(myToggle);
        myToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        if(menuItem.getItemId()==R.id.about)
                        {
                            Intent homeintent=new Intent(Home.this, About.class);
                            startActivity(homeintent); //start your activity
                            AzadIntentAnimation();
                        }
                        if(menuItem.getItemId()==R.id.web)
                        {
                            Intent homeintent=new Intent(Home.this, Web.class);
                            startActivity(homeintent);
                            AzadIntentAnimation();
                        }
                        if(menuItem.getItemId()==R.id.share)
                        {
                            Intent shareapp=new Intent(Intent.ACTION_SEND);
                            shareapp.setType("text/plain");
                            shareapp.putExtra(Intent.EXTRA_SUBJECT, "My SEC");
                            String applink="http://play.google.com";
                            shareapp.putExtra(Intent.EXTRA_SUBJECT, "Try MySEC app");
                            startActivity(Intent.createChooser(shareapp, "Share the app Via"));
                        }
                        /*if(menuItem.getItemId()==R.id.map)
                        {
                            Intent homeintent=new Intent(Home.this, MapsActivity.class);
                            startActivity(homeintent);
                            AzadIntentAnimation();
                        }
                        if(menuItem.getItemId()==R.id.admission)
                        {
                            Intent homeintent=new Intent(Home.this, Admission.class);
                            startActivity(homeintent);
                            AzadIntentAnimation();
                        }
                        if(menuItem.getItemId()==R.id.admissiontest)
                        {
                            Intent homeintent=new Intent(Home.this, AdmissionTest.class);
                            startActivity(homeintent);
                            AzadIntentAnimation();
                        }*/
                        if(menuItem.getItemId()==R.id.syllabus)
                        {
                            Intent homeintent=new Intent(Home.this, Syllabus.class);
                            startActivity(homeintent);
                            AzadIntentAnimation();
                        }
                        if(menuItem.getItemId()==R.id.adnews)
                        {
                            Intent homeintent=new Intent(Home.this, CampusNews.class);
                            startActivity(homeintent);
                            AzadIntentAnimation();
                        }
                        if(menuItem.getItemId()==R.id.teacherslist)
                        {
                            Intent homeintent=new Intent(Home.this, TeachersListCSE.class);
                            startActivity(homeintent);
                            AzadIntentAnimation();
                        }
                        if(menuItem.getItemId()==R.id.exit)
                        {
                            Intent in = new Intent(Intent.ACTION_MAIN);
                            in.addCategory(Intent.CATEGORY_HOME);
                            in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(in);
                            finish();
                            System.exit(0);
                        }
                        menuItem.setChecked(true);
                        myDrawer.closeDrawers();

                        return true;
                    }
                });
    }
public void AzadIntentAnimation()
{
    CustomIntent.customType(this, "right-to-left");

}
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(myToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    boolean twice=false;
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if(twice==true) {
            Intent in = new Intent(Intent.ACTION_MAIN);
            in.addCategory(Intent.CATEGORY_HOME);
            in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(in);
            finish();
            System.exit(0);
        }
        twice =true;
        Toast.makeText(Home.this, "Please Press again to Exit !", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                twice=false;
            }
        }, 3000);
    }




    //slide image
    private void init() {
        for(int i=0;i<XMEN.length;i++)
            XMENArray.add(XMEN[i]);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new MyAdapter(Home.this,XMENArray));
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mPager);

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == XMEN.length) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 2500, 2500);
    }

    public void loginDialog(View view)
    {
        logindia.setContentView(R.layout.activity_login);
        logindia.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        logindia.show();

        final CampusNews campusNews=new CampusNews();

        loginemail=(EditText)logindia.findViewById(R.id.login_email);
        loginpass=(EditText)logindia.findViewById(R.id.login_pass);
        loginbutton=(Button)logindia.findViewById(R.id.login_login);
        success=(TextView)logindia.findViewById(R.id.success);
        fail=(TextView)logindia.findViewById(R.id.fail);
        loginp=(LinearLayout)logindia.findViewById(R.id.loginpanel);
        adminp=(LinearLayout)logindia.findViewById(R.id.adminpanel);
        techcsesection=(LinearLayout) logindia.findViewById(R.id.techcseopen);
        campsection=(LinearLayout) logindia.findViewById(R.id.campopen);
        sylopen=(LinearLayout) logindia.findViewById(R.id.syllabusopen);
        final String email="ali", pass="123";
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(loginemail.getText().toString().trim().equals(email) && loginpass.getText().toString().trim().equals(pass))
                {
                    success.setVisibility(View.VISIBLE);
                    fail.setVisibility(View.GONE);
                    loginp.setVisibility(View.GONE);
                    adminp.setVisibility(View.VISIBLE);



                    techcsesection.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle=new Bundle();
                            bundle.putString("login", "1");
                            //try{campusNews.visible();}catch (Exception ex){}
                            Intent nextintent=new Intent(Home.this, TeachersListCSE.class);
                            nextintent.putExtras(bundle);
                            startActivity(nextintent);
                        }
                    });
                    campsection.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle=new Bundle();
                            bundle.putString("login", "1");
                            //try{campusNews.visible();}catch (Exception ex){}
                            Intent nextintent=new Intent(Home.this, CampusNews.class);
                            nextintent.putExtras(bundle);
                            startActivity(nextintent);
                        }
                    });
                    sylopen.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle=new Bundle();
                            bundle.putString("login", "1");
                            //try{campusNews.visible();}catch (Exception ex){}
                            Intent nextintent=new Intent(Home.this, Syllabus.class);
                            nextintent.putExtras(bundle);
                            startActivity(nextintent);
                        }
                    });


                }
                else {
                    fail.setVisibility((View.VISIBLE));
                    success.setVisibility(View.GONE);
                }
            }
        });
    }
    //header layout section
    public void profileDialog(View view) {

        Button pop, four, five, six,cl;
        TextView tclose;
        tclose=(TextView)mydialog.findViewById(R.id.closenow);
        roundImage=(SelectableRoundedImageView) mydialog.findViewById(R.id.userphoto);
        name=(EditText) mydialog.findViewById(R.id.editname);
        setname=(TextView) mydialog.findViewById (R.id.myname);


        mydialog.setContentView(R.layout.activity_my_profile);
        mydialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mydialog.show();
        //tclose.setOnClickListener(new View.OnClickListener() {  //error ashbe
        //@Override
        //public void onClick(View v) {
        //  mydialog.dismiss();
        // }
        //});
    }
    public void SetImage(View view) {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
        } else {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
        }
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE_REQUEST) {
            if (resultCode == RESULT_OK) {
                if (data != null) {

                    mImageUri = data.getData();

                    this.grantUriPermission(this.getPackageName(), mImageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    final int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        this.getContentResolver().takePersistableUriPermission(mImageUri, takeFlags);
                    }

                    SharedPreferences preferences =
                            PreferenceManager.getDefaultSharedPreferences(this);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("image", String.valueOf(mImageUri));
                    editor.commit();

                    roundImage.setImageURI(mImageUri);
                    roundImage.invalidate();
                }
            }
        }
    }

    public void Setname(View view) {
        String profilename=name.getText().toString().trim();
        setname.setText(profilename.toString().trim());

        SharedPreferences pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("key_name", name.getText().toString().trim());
        editor.apply();
    }
    public void retrieve()
    {
        //retreiving data for circle image
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String mImageUri = preferences.getString("image", null);
        roundImage.setImageURI(Uri.parse(mImageUri));

        SharedPreferences pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String name=pref.getString("key_name","");
        setname.setText(name);
    }

    public void myprofile(View view) {
        Intent intent=new Intent(Home.this, MyProfile.class);
        startActivity(intent);
    }
}