package com.example.admin.mysecazadshushil;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.joooonho.SelectableRoundedImageView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import maes.tech.intentanim.CustomIntent;
import me.relex.circleindicator.CircleIndicator;

public class Home extends AppCompatActivity implements  DrawerLayout.DrawerListener {
     Dialog mydialog, logindia;
     ImageView mImage;
     SelectableRoundedImageView roundImage;
     Uri mImageUri;
     EditText name;
     TextView setname;
     static final int PICK_IMAGE_REQUEST=1;
     Button namesetter, imagesetter;
     Menu menuanimate;
     public static final int requst_call=1;

    EditText loginemail, loginpass;
    static Button loginbutton;
    String email, pass;
    TextView success, fail;
    LinearLayout loginp, adminp,techcsesection,campsection,campsetionad, sylopen, Holiopen;
    private DrawerLayout myDrawer;
    private ActionBarDrawerToggle myToggle;
    private LinearLayout l1;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static final Integer[] XMEN= {R.drawable.two,R.drawable.three, R.drawable.one, R.drawable.four, R.drawable.five};
    private ArrayList<Integer> XMENArray = new ArrayList<Integer>();
    private static Context context;
    private FirebaseAuth mauth;
    private FirebaseAuth.AuthStateListener mauthlisten;
    private DatabaseReference mdatabase;
    private ProgressDialog mprogreesdialog;
    private CardView logincard, logoutcard, adminpanelcard;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setTitle("Welcome");
        Home.context = getApplicationContext();
        init();

        //firebase auth
        mauth=FirebaseAuth.getInstance();
        mdatabase= FirebaseDatabase.getInstance().getReference().child("admin");
        mprogreesdialog=new ProgressDialog(this);
        logincard=(CardView)findViewById(R.id.logincard);
        logoutcard=(CardView)findViewById(R.id.logoutcard);
        adminpanelcard=(CardView) findViewById(R.id.adminpanelcard);

        mauth=FirebaseAuth.getInstance();
        mauthlisten=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null)
                {
                    logoutcard.setVisibility(View.VISIBLE);
                    logincard.setVisibility(View.GONE);
                    adminpanelcard.setVisibility(View.VISIBLE);
                }
            }
        };

        ///header layout part
        mydialog=new Dialog(Home.this);
        //logindia=new Dialog(Home.this);
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
        /*AnimatorSet animatorSet=new AnimatorSet();
        ObjectAnimator animatortranslate=ObjectAnimator.ofFloat(navigationView.getMenu().size(), "translationY", true?400: -400,0);
        animatortranslate.setDuration(900);
        animatorSet.play(animatortranslate);
        animatorSet.start();*/

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
                        if(menuItem.getItemId()==R.id.holidays)
                        {
                            Intent homeintent=new Intent(Home.this, HolidaysNews.class);
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

    
    public static Context getAppContext() {
        return Home.context;
    }

    public static int getPx(Context context, int dimensionDp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dimensionDp * density + 0.5f);
    }
    public void AzadIntentAnimation()
    {
        CustomIntent.customType(this, "right-to-left");
    }

    @Override
    protected void onStart() {
        super.onStart();
        mauth.addAuthStateListener(mauthlisten);
    }
    public void calling()
    {
        String number="01732914039";
        if(number.trim().length()>0)
        {
            if(ContextCompat.checkSelfPermission(Home.this,
                    Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(Home.this,
                        new String[]{Manifest.permission.CALL_PHONE},requst_call);
            }else{
                String dial="tel:"+number;
                startActivity(new Intent(Intent.ACTION_CALL,Uri.parse(dial)));
            }
        }
    }
    public void emailing()
    {
        String recipient="info@sec.ac.bd";
        String subject="";
        String meassage="";
        Intent eintent=new Intent(Intent.ACTION_SEND);
        eintent.setData(Uri.parse("Mail to SEC:"));
        eintent.setType("text/plain");
        eintent.putExtra(Intent.EXTRA_EMAIL, new String[]{recipient});
        try{
            startActivity(Intent.createChooser(eintent, "Choose an Email Client"));
        }catch (Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==requst_call)
        {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                calling();
            }
            else{
                Toast.makeText(this, "Permision Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(myToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId())
        {
            case R.id.call:
                final AlertDialog.Builder dialog = new AlertDialog.Builder(Home.this);
                dialog.setMessage("Are you sure?");
                dialog.setTitle("Call to office");
                dialog.setIcon(android.R.drawable.ic_dialog_alert);
                dialog.setCancelable(false);
                dialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        calling();
                    }
                });
                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                dialog.show();
                break;
            case R.id.Email:
                emailing();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
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
        logindia=new Dialog(Home.this);
        logindia.requestWindowFeature(Window.FEATURE_NO_TITLE);
        logindia.setContentView(R.layout.activity_login);
        Window window = logindia.getWindow();
        window.setLayout(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
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
        Holiopen=(LinearLayout)logindia.findViewById(R.id.holidaysopen);
        final String email="ali", pass="123";
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mprogreesdialog.setMessage("Checking...");
                mprogreesdialog.show();
                logingtest();
               /* if(loginemail.getText().toString().trim().equals(email) && loginpass.getText().toString().trim().equals(pass))
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
                    Holiopen.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle=new Bundle();
                            bundle.putString("login", "1");
                            //try{campusNews.visible();}catch (Exception ex){}
                            Intent nextintent=new Intent(Home.this, HolidaysNews.class);
                            nextintent.putExtras(bundle);
                            startActivity(nextintent);
                        }
                    });


                }
                else {
                    fail.setVisibility((View.VISIBLE));
                    success.setVisibility(View.GONE);
                }*/
            }

        });
    }

    //login with firebase authentication
    public  void logingtest()
    {
        String email=loginemail.getText().toString();
        String pass=loginpass.getText().toString();
        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass))
        {
            mauth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        mprogreesdialog.dismiss();
                        //checkuserExitst();
                        adminvisible();
                        logincard.setVisibility(View.GONE);
                        logoutcard.setVisibility(View.VISIBLE);
                        adminpanelcard.setVisibility(View.VISIBLE);

                    }else{
                        mprogreesdialog.dismiss();
                        fail.setVisibility((View.VISIBLE));
                        success.setVisibility(View.GONE);
                    }
                }
            });
        }
    }
    public void checkuserExitst()
    {
        final String user_id=mauth.getCurrentUser().getUid();
        mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(user_id))
                {
                    Intent loginIntent=new Intent(Home.this, CampusNews.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);
                } else {
                    //Toast.makeText(Home.this, "Invalid", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void adminvisible()
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
        Holiopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("login", "1");
                //try{campusNews.visible();}catch (Exception ex){}
                Intent nextintent=new Intent(Home.this, HolidaysNews.class);
                nextintent.putExtras(bundle);
                startActivity(nextintent);
            }
        });
    }
    public void setAdminpanelcard()
    {
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
        Holiopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("login", "1");
                //try{campusNews.visible();}catch (Exception ex){}
                Intent nextintent=new Intent(Home.this, HolidaysNews.class);
                nextintent.putExtras(bundle);
                startActivity(nextintent);
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

    public void opencgpacal(View view) {
        Intent intent=new Intent(this,CGPA_calculator.class);
        startActivity(intent);
        AzadIntentAnimation();
    }

    public void openschedule(View view) {
        Intent intent=new Intent(this,SchedulerActivity.class);
        startActivity(intent);
        AzadIntentAnimation();
    }

    public void logout(View view) {
        mauth.signOut();
        logincard.setVisibility(View.VISIBLE);
        adminpanelcard.setVisibility(View.GONE);
    }
    public void openadmnipanel(View view) {
        logindia=new Dialog(Home.this);
        logindia.requestWindowFeature(Window.FEATURE_NO_TITLE);
        logindia.setContentView(R.layout.activity_login);
        Window window = logindia.getWindow();
        window.setLayout(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        logindia.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        logindia.show();
        techcsesection=(LinearLayout) logindia.findViewById(R.id.techcseopen);
        campsection=(LinearLayout) logindia.findViewById(R.id.campopen);
        sylopen=(LinearLayout) logindia.findViewById(R.id.syllabusopen);
        Holiopen=(LinearLayout)logindia.findViewById(R.id.holidaysopen);
        setAdminpanelcard();
    }

    @Override
    public void onDrawerSlide(@NonNull View view, float v) {
        
    }

    @Override
    public void onDrawerOpened(@NonNull View view) {

    }

    @Override
    public void onDrawerClosed(@NonNull View view) {

    }

    @Override
    public void onDrawerStateChanged(int i) {

    }
}