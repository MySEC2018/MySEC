package com.example.admin.mysecazadshushil;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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
import android.view.animation.LayoutAnimationController;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.joooonho.SelectableRoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeachersListCSE extends AppCompatActivity {
private ImageButton addimage,searchbt, closebt;
private EditText tname, contact, insearcht;
private Button sub;
private CardView addingvisibility, deletevisibility;
private Uri imageUri;
private StorageReference mstorage;
private DatabaseReference mdatabase;
private static final int GALLERY_REQUEST=1;
private ProgressDialog mprogreesdialog;
private RecyclerView tech_cse_recycle;
private EditText getkey;
private Button delete_tech;
private CardView cardViewvisible;
private FirebaseAuth mauth;
private FirebaseAuth.AuthStateListener mauthlisten;
private RadioButton search, cse, eee, ce;
private Spinner tdept,position;
private ImageView call;
private int requst_call=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers_list_cse);
        getSupportActionBar().setTitle("Teacher List");
        mstorage= FirebaseStorage.getInstance().getReference();
        mdatabase= FirebaseDatabase.getInstance().getReference().child("TechListCSE");
        mdatabase.keepSynced(true);
        addimage=(ImageButton)findViewById(R.id.tech_addimage);
        tname=(EditText)findViewById(R.id.tech_name);
        position=(Spinner) findViewById(R.id.tech_position);
        contact=(EditText)findViewById(R.id.tech_contact);

        tdept=(Spinner)findViewById(R.id.tech_dept);
        setupSpinners();

        sub=(Button)findViewById(R.id.tech_submit);
        getkey=(EditText)findViewById(R.id.tech_key);
        delete_tech=(Button)findViewById(R.id.tech_delete);
        searchbt=(ImageButton)findViewById(R.id.search_tech);
        closebt=(ImageButton)findViewById(R.id.close_tech);
        insearcht=(EditText)findViewById(R.id.inputsearchtech);
        cardViewvisible=(CardView)findViewById(R.id.cardvisiblesearch);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        addingvisibility=(CardView) findViewById(R.id.cardvisibleadd);
        deletevisibility=(CardView) findViewById(R.id.cardvisibledelete);
        call=findViewById(R.id.staff_call);
        try {
            Bundle bun = getIntent().getExtras();
            String val = bun.getString("login");
            String key = "1";
            if (val.equals(key)) {
                addingvisibility.setVisibility(View.VISIBLE);
            }
        }catch (Exception ex){}

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


        mprogreesdialog=new ProgressDialog(this);

        tech_cse_recycle=(RecyclerView)findViewById(R.id.tech_cse_recycler);
        tech_cse_recycle.setHasFixedSize(true);
        tech_cse_recycle.setLayoutManager(new LinearLayoutManager(this));
        //runanimationrecyle(tech_cse_recycle, 0);

        closebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardViewvisible.setVisibility(View.GONE);
                Animation anim= AnimationUtils.loadAnimation(TeachersListCSE.this, R.anim.searchclose);
                cardViewvisible.startAnimation(anim);
            }
        });
        addimage.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent galleryIntent=new Intent(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent, GALLERY_REQUEST);
        }
    });

    sub.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mprogreesdialog.setMessage("Posting to Path...");
            mprogreesdialog.show();
            final String ttname=tname.getText().toString().trim().toLowerCase();
            final String tposition=position.getSelectedItem().toString().trim();
            final String tcontact=contact.getText().toString().trim();
            final String dept=tdept.getSelectedItem().toString().trim();
            if(!TextUtils.isEmpty(ttname)&&!TextUtils.isEmpty(tposition)&&!TextUtils.isEmpty(tcontact))
            {
                final StorageReference filepath=mstorage.child("TechlistCSE").child(imageUri.getLastPathSegment());
                filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //Uri downloaduri=taskSnapshot.getDownloadUrl();
                        //Task<Uri> u = taskSnapshot.getStorage().getDownloadUrl();
                        final DatabaseReference newPost=mdatabase.push();
                        newPost.child("tech_name").setValue(ttname);
                        newPost.child("tech_position").setValue(tposition);
                        newPost.child("tech_contact").setValue(tcontact);
                        newPost.child("tech_dept").setValue("Dept. of "+dept);
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
                });
            }
        }
    });

    delete_tech.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(TeachersListCSE.this, "key is: "+getkey.getText().toString(), Toast.LENGTH_SHORT).show();
            mdatabase = FirebaseDatabase.getInstance().getReference()
                    .child("TechListCSE").child(getkey.getText().toString());
            mdatabase.removeValue();
        }
    });

    searchbt.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String searchtext=insearcht.getText().toString().trim().toLowerCase();
            String orderchild="tech_name";
            searchteacherlist(searchtext, orderchild);
        }
    });

    }

    private void runanimationrecyle(RecyclerView recyclerView, int type) {
        Context context=recyclerView.getContext();
        LayoutAnimationController controller=null;
        if(type==0)
            controller=AnimationUtils.loadLayoutAnimation(context, R.anim.layoutrecycleviewanimation);
        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    private  void searchteacherlist(String searchtxt, String orderchild)
    {
        Toast.makeText(this, "Searching: "+searchtxt, Toast.LENGTH_SHORT).show();
        Query query=mdatabase.orderByChild(orderchild).startAt(searchtxt).endAt(searchtxt+"\uf8ff");
       // Query query=mdatabase.orderByChild("tech_name").equalTo(searchtxt);
        FirebaseRecyclerAdapter<AddteacherListCSE, TeachercseViewholder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<AddteacherListCSE, TeachercseViewholder>(
                AddteacherListCSE.class,
                R.layout.list_layout_teacher_cse,
                TeachercseViewholder.class,
                query)
        {
            @Override
            protected void populateViewHolder(TeachercseViewholder viewHolder, AddteacherListCSE model, int position) {
                final String tech_key=getRef(position).getKey();
                //final String tech_key=getRef(position).toString(); //for showing the database reading

                final String numberfirebase=model.getTech_contact();
                viewHolder.settechname(model.getTech_name());
                viewHolder.settechposition(model.getTech_position());
                viewHolder.settechcontact(model.getTech_contact());
                viewHolder.settechdept(model.getTech_dept());
                viewHolder.settechimage(model.getTech_image());

                AnimatorSet animatorSet=new AnimatorSet();
                ObjectAnimator animatortranslate=ObjectAnimator.ofFloat(viewHolder.itemView, "translationY", true?400: -400,0);
                animatortranslate.setDuration(900);
                animatorSet.playSequentially(animatortranslate);
                animatorSet.start();

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(TeachersListCSE.this,tech_key,Toast.LENGTH_LONG).show();
                        getkey.setText(tech_key.toString().trim());
                    }
                });
                viewHolder.mView.findViewById(R.id.staff_call).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final AlertDialog.Builder dialog = new AlertDialog.Builder(TeachersListCSE.this);
                        dialog.setMessage("Are you sure?");
                        dialog.setTitle("Make A Call");
                        dialog.setIcon(android.R.drawable.ic_dialog_alert);
                        dialog.setCancelable(false);
                        dialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                calling(numberfirebase);
                            }
                        });
                        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                        dialog.show();
                    }
                });

            }
        };

        tech_cse_recycle.setAdapter(firebaseRecyclerAdapter);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_REQUEST && resultCode==RESULT_OK){
            imageUri=data.getData();
            addimage.setImageURI(imageUri);
        }

    }
    @Override
    protected void onStart() {
        super.onStart();
        mauth.addAuthStateListener(mauthlisten);
        //implementation 'com.firebaseui:firebase-ui-database:0.4.0'
        FirebaseRecyclerAdapter<AddteacherListCSE, TeachercseViewholder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<AddteacherListCSE, TeachercseViewholder>( AddteacherListCSE.class,
                R.layout.list_layout_teacher_cse,
                TeachercseViewholder.class,
                mdatabase)
        {
            @Override
            protected void populateViewHolder(TeachercseViewholder viewHolder, AddteacherListCSE model, int position) {
                final String tech_key=getRef(position).getKey();
                //final String tech_key=getRef(position).toString(); //for showing the database reading

                final String numberfirebase=model.getTech_contact();
                viewHolder.settechname(model.getTech_name());
                viewHolder.settechposition(model.getTech_position());
                viewHolder.settechcontact(model.getTech_contact());
                viewHolder.settechdept(model.getTech_dept());
                viewHolder.settechimage(model.getTech_image());

                AnimatorSet animatorSet=new AnimatorSet();
                ObjectAnimator animatortranslate=ObjectAnimator.ofFloat(viewHolder.itemView, "translationY", true?400: -400,0);
                animatortranslate.setDuration(900);
                animatorSet.play(animatortranslate);
                animatorSet.start();

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       // Toast.makeText(TeachersListCSE.this,tech_key,Toast.LENGTH_LONG).show();
                        getkey.setText(tech_key.toString().trim());
                    }
                });
                viewHolder.mView.findViewById(R.id.staff_call).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final AlertDialog.Builder dialog = new AlertDialog.Builder(TeachersListCSE.this);
                        dialog.setMessage("Are you sure?");
                        dialog.setTitle("Make A Call");
                        dialog.setIcon(android.R.drawable.ic_dialog_alert);
                        dialog.setCancelable(false);
                        dialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                calling(numberfirebase);
                            }
                        });
                        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                        dialog.show();
                    }
                });

            }
        };
        tech_cse_recycle.setAdapter(firebaseRecyclerAdapter);

    }

    public void deleteactive(View view) {
        deletevisibility.setVisibility(View.VISIBLE);
        addingvisibility.setVisibility(View.GONE);
    }

    public void Addactive(View view) {
        deletevisibility.setVisibility(View.GONE);
        addingvisibility.setVisibility(View.VISIBLE);
    }

    public static class TeachercseViewholder extends RecyclerView.ViewHolder{
        View mView;
        public TeachercseViewholder(View itemView)
        {
            super(itemView);
           mView=itemView;
        }
        public  void settechname(String name)
        {
            TextView techcsename=(TextView) mView.findViewById(R.id.tech_list_name);
            techcsename.setText(name);
        }
        public  void settechposition(String position)
        {
            TextView techcsenpositon=(TextView) mView.findViewById(R.id.tech_list_positon);
            techcsenpositon.setText(position);
        }
        public  void settechcontact(String contact)
        {
            TextView techcsecontact=(TextView) mView.findViewById(R.id.tech_list_contact);
            techcsecontact.setText(contact);
        }
        public void settechdept(String dept)
        {
            TextView techcsedept=(TextView) mView.findViewById(R.id.tech_list_dept);
            techcsedept.setText(dept);
        }
        public void settechimage(String image)
        {
            SelectableRoundedImageView techimage=(SelectableRoundedImageView)mView.findViewById(R.id.tech_list_image);
            //ImageView techcseimage=(ImageView) mView.findViewById(R.id.tech_list_image);
            //Picasso.with(ctx).load(image).into(techcseimage);
            /*Picasso.with(ctx)
                    .load(image)
                    .placeholder(R.drawable.email)
                    .resize(50,50)
                    .into(techcseimage);
            Picasso.with(ctx).setLoggingEnabled(true);*/

            Picasso.get().load(image).centerCrop().placeholder(R.drawable.loading).fit().into(techimage);


        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.tech_menu, menu);
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
                item.setChecked(true);
                return true;
            case R.id.tech_searchcse:
                item.setChecked(true);
                String searchtext1="Dept. of CSE";
                String orderchild1="tech_dept";
                searchteacherlist(searchtext1, orderchild1);
                return true;
            case R.id.tech_searchee:
                item.setChecked(true);
                String searchtext2="Dept. of EEE";
                String orderchild2="tech_dept";
                searchteacherlist(searchtext2, orderchild2);
                return true;
            case R.id.tech_searchce:
                item.setChecked(true);
                String searchtext3="Dept. of CE";
                String orderchild3="tech_dept";
                searchteacherlist(searchtext3, orderchild3);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void setupSpinners() {
        List<String> dept = new ArrayList<>();
        dept.add("CSE");
        dept.add("EEE");
        dept.add("CE");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dept);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tdept.setAdapter(dataAdapter);


        List<String> posi = new ArrayList<>();
        posi.add("Professor");
        posi.add("Professor & Head");
        posi.add("Head");
        posi.add("Associate Professor");
        posi.add("Assistant Professor");
        posi.add("Lecturer");

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, posi);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        position.setAdapter(dataAdapter2);
    }
    //call making
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==requst_call)
        {
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
            {
                FirebaseRecyclerAdapter<AddteacherListCSE, TeachercseViewholder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<AddteacherListCSE, TeachercseViewholder>( AddteacherListCSE.class,
                        R.layout.list_layout_teacher_cse,
                        TeachercseViewholder.class,
                        mdatabase)
                {
                    @Override
                    protected void populateViewHolder(TeachercseViewholder viewHolder, AddteacherListCSE model, int position) {
                        final String numberfirebase=model.getTech_contact();
                        calling(numberfirebase);
                    }
                };
            }
            else{
                Toast.makeText(this, "Permision Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void calling(String numberfire)
    {
        String number=numberfire;
        if(number.trim().length()>0)
        {
            if(ContextCompat.checkSelfPermission(TeachersListCSE.this,
                    Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(TeachersListCSE.this,
                        new String[]{Manifest.permission.CALL_PHONE},requst_call);
            }else{
                String dial="tel:"+number;
                startActivity(new Intent(Intent.ACTION_CALL,Uri.parse(dial)));
            }
        }
    }
}
