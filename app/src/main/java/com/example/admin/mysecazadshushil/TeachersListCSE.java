package com.example.admin.mysecazadshushil;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import maes.tech.intentanim.CustomIntent;

public class TeachersListCSE extends AppCompatActivity {
private ImageButton addimage,searchbt, closebt;
private EditText tname, position, contact, insearcht;
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
        position=(EditText)findViewById(R.id.tech_position);
        contact=(EditText)findViewById(R.id.tech_contact);
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
        try {
            Bundle bun = getIntent().getExtras();
            String val = bun.getString("login");
            String key = "1";
            if (val.equals(key)) {
                addingvisibility.setVisibility(View.VISIBLE);
            }
        }catch (Exception ex){}

        mprogreesdialog=new ProgressDialog(this);

        tech_cse_recycle=(RecyclerView)findViewById(R.id.tech_cse_recycler);
        tech_cse_recycle.setHasFixedSize(true);
        tech_cse_recycle.setLayoutManager(new LinearLayoutManager(this));

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
            final String tposition=position.getText().toString().trim();
            final String tcontact=contact.getText().toString().trim();
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
            searchteacherlist(searchtext);
        }
    });

    }
    private  void searchteacherlist(String searchtxt)
    {
        Toast.makeText(this, "Searching: "+searchtxt, Toast.LENGTH_SHORT).show();
        Query query=mdatabase.orderByChild("tech_name").startAt(searchtxt).endAt(searchtxt+"\uf8ff");
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


                viewHolder.settechname(model.getTech_name());
                viewHolder.settechposition(model.getTech_position());
                viewHolder.settechcontact(model.getTech_contact());
                viewHolder.settechimage(model.getTech_image());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(TeachersListCSE.this,tech_key,Toast.LENGTH_LONG).show();
                        getkey.setText(tech_key.toString().trim());
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


                viewHolder.settechname(model.getTech_name());
                viewHolder.settechposition(model.getTech_position());
                viewHolder.settechcontact(model.getTech_contact());
                viewHolder.settechimage(model.getTech_image());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(TeachersListCSE.this,tech_key,Toast.LENGTH_LONG).show();
                        getkey.setText(tech_key.toString().trim());
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
        public void settechimage(String image)
        {
            ImageView techcseimage=(ImageView) mView.findViewById(R.id.tech_list_image);
            //Picasso.with(ctx).load(image).into(techcseimage);
            /*Picasso.with(ctx)
                    .load(image)
                    .placeholder(R.drawable.email)
                    .resize(50,50)
                    .into(techcseimage);
            Picasso.with(ctx).setLoggingEnabled(true);*/

            Picasso.get().load(image).centerCrop().placeholder(R.mipmap.ic_launcher).fit().into(techcseimage);


        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.teacher_search_toolbar, menu);
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
