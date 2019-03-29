package com.example.admin.mysecazadshushil;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class HolidaysNews extends AppCompatActivity {
    private ImageButton addimage,searchbt, closebt;
    private EditText htitile, hdate, hmonth, hdurtext, hdurday, insearcht,uphtitile, uphdate, uphmonth, uphdurtext, uphdurday;
    private Button sub, update;
    private CardView addingvisibility, deletevisibility, updatevisibility;
    private Uri imageUri;
    private StorageReference mstorage;
    private DatabaseReference mdatabase, upmdatabase;
    private static final int GALLERY_REQUEST=1;
    private ProgressDialog mprogreesdialog;
    private RecyclerView holi_recycle;
    private EditText getkey;
    private Button delete_holi;
    private CardView cardViewvisible;
    private FirebaseAuth mauth;
    private FirebaseAuth.AuthStateListener mauthlisten;
    private LinearLayout holigone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holidays_news);
        getSupportActionBar().setTitle("Holidays");
        mstorage= FirebaseStorage.getInstance().getReference();
        mdatabase= FirebaseDatabase.getInstance().getReference().child("Holidays");
        mdatabase.keepSynced(true);

        mauth=FirebaseAuth.getInstance();
        mauthlisten=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null)
                {
                    addingvisibility.setVisibility(View.VISIBLE);
                }
            }
        };

        htitile=(EditText)findViewById(R.id.holi_tittle);
        hdate=(EditText)findViewById(R.id.holi_date);
        hmonth=(EditText)findViewById(R.id.holi_month);
        hdurtext=(EditText)findViewById(R.id.holi_dur_text);
        hdurday=(EditText)findViewById(R.id.holi_dur_day);

        uphtitile=(EditText)findViewById(R.id.holi_tittle_up);
        uphdate=(EditText)findViewById(R.id.holi_date_up);
        uphmonth=(EditText)findViewById(R.id.holi_month_up);
        uphdurtext=(EditText)findViewById(R.id.holi_dur_text_up);
        uphdurday=(EditText)findViewById(R.id.holi_dur_day_up);

        update=(Button)findViewById(R.id.holi_update);
        sub=(Button)findViewById(R.id.holi_submit);
        getkey=(EditText)findViewById(R.id.holi_key);
        delete_holi=(Button)findViewById(R.id.holi_delete);
        searchbt=(ImageButton)findViewById(R.id.search_holi);
        closebt=(ImageButton)findViewById(R.id.close_holi);
        insearcht=(EditText)findViewById(R.id.inputsearchholi);
        cardViewvisible=(CardView)findViewById(R.id.cardvisiblesearch);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        addingvisibility=(CardView) findViewById(R.id.cardvisibleadd);
        deletevisibility=(CardView) findViewById(R.id.cardvisibledelete);
        updatevisibility=(CardView) findViewById(R.id.cardvisibleupdate);
        try {
            Bundle bun = getIntent().getExtras();
            String val = bun.getString("login");
            String key = "1";
            if (val.equals(key)) {
                addingvisibility.setVisibility(View.VISIBLE);
            }
        }catch (Exception ex){}

        mprogreesdialog=new ProgressDialog(this);

        holi_recycle=(RecyclerView)findViewById(R.id.holi_recycler);
        holi_recycle.setHasFixedSize(true);
        holi_recycle.setLayoutManager(new LinearLayoutManager(this));

        closebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardViewvisible.setVisibility(View.GONE);
                Animation anim= AnimationUtils.loadAnimation(HolidaysNews.this, R.anim.searchclose);
                cardViewvisible.startAnimation(anim);
            }
        });
        /*addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent=new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);
            }
        });*/

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mprogreesdialog.setMessage("Posting to Path...");
                mprogreesdialog.show();
                final String hhtitle=htitile.getText().toString().trim().toLowerCase();
                final String hhdate=hdate.getText().toString().trim();
                final String hhmonth=hmonth.getText().toString().trim();
                final String hhdurtext=hdurtext.getText().toString().trim();
                final String hhdurday=hdurday.getText().toString().trim();
                if(!TextUtils.isEmpty(hhtitle)&&!TextUtils.isEmpty(hhdate)&&!TextUtils.isEmpty(hhmonth)&&!TextUtils.isEmpty(hhdurtext)&&!TextUtils.isEmpty(hhdurday))
                {
                    final DatabaseReference newPost=mdatabase.push();
                    newPost.child("h_tittle").setValue(hhtitle);
                    newPost.child("h_date").setValue(hhdate);
                    newPost.child("h_month").setValue(hhmonth);
                    newPost.child("h_dur_text").setValue(hhdurtext);
                    newPost.child("h_dur_day").setValue(hhdurday);
                    mprogreesdialog.dismiss();
                    /*final StorageReference filepath=mstorage.child("TechlistCSE").child(imageUri.getLastPathSegment());
                    filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //Uri downloaduri=taskSnapshot.getDownloadUrl();
                            //Task<Uri> u = taskSnapshot.getStorage().getDownloadUrl();
                            final DatabaseReference newPost=mdatabase.push();
                            newPost.child("tech_name").setValue(ttname);
                            newPost.child("tech_position").setValue(tposition);
                            newPost.child("tech_contact").setValue(tcontact);
                            //newPost.child("tech_img").setValue(u.toString());
                            filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Map newImage = new HashMap();
                                    newImage.put("tech_image", uri.toString());
                                    newPost.updateChildren(newImage);

                                    //finish();
                                    return;
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    //finish();
                                    return;
                                }
                            });

                            mprogreesdialog.dismiss();
                        }
                    });*/
                }
            }
        });
        //updating section is here
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mprogreesdialog.setMessage("Posting to Path...");
                mprogreesdialog.show();
                final String hhtitle=uphtitile.getText().toString();
                final String hhdate=uphdate.getText().toString();
                final String hhmonth=uphmonth.getText().toString();
                final String hhdurtext=uphdurtext.getText().toString();
                final String hhdurday=uphdurday.getText().toString();
                if(!TextUtils.isEmpty(hhtitle)&&!TextUtils.isEmpty(hhdate)&&!TextUtils.isEmpty(hhmonth)&&!TextUtils.isEmpty(hhdurtext)&&!TextUtils.isEmpty(hhdurday))
                {
                    mdatabase = FirebaseDatabase.getInstance().getReference()
                            .child("Holidays").child(getkey.getText().toString());
                    mdatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            dataSnapshot.getRef().child("h_tittle").setValue(hhtitle);
                            dataSnapshot.getRef().child("h_date").setValue(hhdate);
                            dataSnapshot.getRef().child("h_month").setValue(hhmonth);
                            dataSnapshot.getRef().child("h_dur_text").setValue(hhdurtext);
                            dataSnapshot.getRef().child("h_dur_day").setValue(hhdurday);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    //final DatabaseReference newPost=mdatabase.push();
                    //mdatabase.child("h_tittle").setValue(hhtitle);
                    //mdatabase.child("h_date").setValue(hhdate);
                    //mdatabase.child("h_month").setValue(hhmonth);
                    //mdatabase.child("h_dur_text").setValue(hhdurtext);
                    //mdatabase.child("h_dur_day").setValue(hhdurday);
                    mprogreesdialog.dismiss();
                    /*final StorageReference filepath=mstorage.child("TechlistCSE").child(imageUri.getLastPathSegment());
                    filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //Uri downloaduri=taskSnapshot.getDownloadUrl();
                            //Task<Uri> u = taskSnapshot.getStorage().getDownloadUrl();
                            final DatabaseReference newPost=mdatabase.push();
                            newPost.child("tech_name").setValue(ttname);
                            newPost.child("tech_position").setValue(tposition);
                            newPost.child("tech_contact").setValue(tcontact);
                            //newPost.child("tech_img").setValue(u.toString());
                            filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Map newImage = new HashMap();
                                    newImage.put("tech_image", uri.toString());
                                    newPost.updateChildren(newImage);

                                    //finish();
                                    return;
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    //finish();
                                    return;
                                }
                            });

                            mprogreesdialog.dismiss();
                        }
                    });*/
                }
            }
        });

        //Deleting a list
        delete_holi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HolidaysNews.this, "key is: "+getkey.getText().toString(), Toast.LENGTH_SHORT).show();
                mdatabase = FirebaseDatabase.getInstance().getReference()
                        .child("Holidays").child(getkey.getText().toString());
                mdatabase.removeValue();
            }
        });

        //searching from list

        searchbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchtext=insearcht.getText().toString().trim().toLowerCase();
                searchteacherlist(searchtext);
            }
        });

    }
    private  void searchteacherlist(String searchtxt)
    {
        Toast.makeText(this, "Searching: "+searchtxt, Toast.LENGTH_SHORT).show();
        Query query=mdatabase.orderByChild("h_date").startAt(searchtxt).endAt(searchtxt+"\uf8ff");
        // Query query=mdatabase.orderByChild("tech_name").equalTo(searchtxt);
        FirebaseRecyclerAdapter<AddHolidays, HolidaysNews.HoliViewholder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<AddHolidays, HolidaysNews.HoliViewholder>( AddHolidays.class,
                R.layout.list_layout_holiday,
                HolidaysNews.HoliViewholder.class,
                query)
        {
            @Override
            protected void populateViewHolder(HolidaysNews.HoliViewholder viewHolder, AddHolidays model, int position) {
                final String holi_key=getRef(position).getKey();
                //final String tech_key=getRef(position).toString(); //for showing the database reading
                viewHolder.settittle(model.getH_tittle());
                viewHolder.sethdate(model.getH_date());
                viewHolder.setmonth(model.getH_month());
                viewHolder.setdurtext(model.getH_dur_text());
                viewHolder.setdurday(model.getH_dur_day());
                AnimatorSet animatorSet=new AnimatorSet();
                ObjectAnimator animatortranslate=ObjectAnimator.ofFloat(viewHolder.itemView, "translationY", true?400: -400,0);
                animatortranslate.setDuration(900);
                animatorSet.play(animatortranslate);
                animatorSet.start();

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       // Toast.makeText(HolidaysNews.this,holi_key,Toast.LENGTH_LONG).show();
                        getkey.setText(holi_key.toString().trim());
                    }
                });

            }
        };
        holi_recycle.setAdapter(firebaseRecyclerAdapter);
    }
    @Override
    protected void onStart() {
        super.onStart();

       mauth.addAuthStateListener(mauthlisten);

        //implementation 'com.firebaseui:firebase-ui-database:0.4.0'
        FirebaseRecyclerAdapter<AddHolidays, HolidaysNews.HoliViewholder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<AddHolidays, HolidaysNews.HoliViewholder>( AddHolidays.class,
                R.layout.list_layout_holiday,
                HolidaysNews.HoliViewholder.class,
                mdatabase)
        {
            @Override
            protected void populateViewHolder(HolidaysNews.HoliViewholder viewHolder, AddHolidays model, int position) {
                final String holi_key=getRef(position).getKey();

                //updating part
                final String up_tittle=model.getH_tittle();
                final String up_date=model.getH_date();
                final String up_month=model.getH_month();
                final String up_dur_text=model.getH_dur_text();
                final String up_dur_day=model.getH_dur_day();
                //final String tech_key=getRef(position).toString(); //for showing the database reading
                    viewHolder.settittle(model.getH_tittle());
                    viewHolder.sethdate(model.getH_date());
                    viewHolder.setmonth(model.getH_month());
                    viewHolder.setdurtext(model.getH_dur_text());
                    viewHolder.setdurday(model.getH_dur_day());
                AnimatorSet animatorSet=new AnimatorSet();
                ObjectAnimator animatortranslate=ObjectAnimator.ofFloat(viewHolder.itemView, "translationY", true?400: -400,0);
                animatortranslate.setDuration(900);
                animatorSet.play(animatortranslate);
                animatorSet.start();
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(HolidaysNews.this,holi_key,Toast.LENGTH_LONG).show();
                        getkey.setText(holi_key.toString().trim());

                        //updating part
                        uphtitile.setText(up_tittle.toString().trim());
                        uphmonth.setText(up_month.toString().trim());
                        uphdate.setText(up_date.toString().trim());
                        uphdurtext.setText(up_dur_text.toString().trim());
                        uphdurday.setText(up_dur_day.toString().trim());
                    }
                });

            }
        };
        holi_recycle.setAdapter(firebaseRecyclerAdapter);

    }

    public void deleteactive(View view) {
        deletevisibility.setVisibility(View.VISIBLE);
        addingvisibility.setVisibility(View.GONE);
        updatevisibility.setVisibility(View.GONE);
    }

    public void Addactive(View view) {
        deletevisibility.setVisibility(View.GONE);
        updatevisibility.setVisibility(View.GONE);
        addingvisibility.setVisibility(View.VISIBLE);
    }

    public void updateactive(View view) {
        deletevisibility.setVisibility(View.GONE);
        addingvisibility.setVisibility(View.GONE);
        updatevisibility.setVisibility(View.VISIBLE);
    }

    public static class HoliViewholder extends RecyclerView.ViewHolder{
        View mView;
        public HoliViewholder(View itemView)
        {
            super(itemView);
            mView=itemView;
        }
        public  void settittle(String tittle)
        {
            TextView htittle=(TextView) mView.findViewById(R.id.holidays_tittle);
            htittle.setText(tittle);
        }
        public  void sethdate(String date)
        {
            TextView hdate=(TextView) mView.findViewById(R.id.holidays_date);
            hdate.setText(date);
        }
        public  void setmonth(String month)
        {
            TextView hmonth=(TextView) mView.findViewById(R.id.holidays_month);
            hmonth.setText(month);
        }
        public  void setdurtext(String text)
        {
            TextView hdurtext=(TextView) mView.findViewById(R.id.holidays_dur_text);
            hdurtext.setText(text);
        }
        public  void setdurday(String day)
        {
            TextView hdurday=(TextView) mView.findViewById(R.id.holidays_dur_day);
            hdurday.setText(day);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.search_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.tech_search:
                cardViewvisible.setVisibility(View.VISIBLE);
                Animation anim= AnimationUtils.loadAnimation(this, R.anim.searchopen);
                cardViewvisible.startAnimation(anim);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
