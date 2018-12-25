package com.example.admin.mysecazadshushil;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CampusNews extends AppCompatActivity {
    private ImageButton addimage;
    private EditText ttitle, position, contact;
    private Button sub;
    private Uri imageUri;
    private StorageReference mstorage;
    private DatabaseReference mdatabase;
    private static final int GALLERY_REQUEST=1;
    private ProgressDialog mprogreesdialog;
    private RecyclerView camp_recycle;
    private EditText getkey;
    private Button delete_tech;
    private LinearLayout addingvisibility;
    ArrayList<AddCampusNews> campfire;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campus_news);
        getSupportActionBar().setTitle("Campus News");
        mstorage= FirebaseStorage.getInstance().getReference();
        mdatabase= FirebaseDatabase.getInstance().getReference().child("campus_news");
        mdatabase.keepSynced(true);
        addimage=(ImageButton)findViewById(R.id.camp_addimage);
        ttitle=(EditText)findViewById(R.id.camp_tittle);
        position=(EditText)findViewById(R.id.camp_descrip);
        contact=(EditText)findViewById(R.id.camp_date);
        sub=(Button)findViewById(R.id.camp_submit);
        getkey=(EditText)findViewById(R.id.camp_key);
        delete_tech=(Button)findViewById(R.id.camp_delete);

        addingvisibility=(LinearLayout)findViewById(R.id.addinglayout);
        try {
            Bundle bun = getIntent().getExtras();
            String val = bun.getString("login");
            String key = "1";
            if (val.equals(key)) {
                addingvisibility.setVisibility(View.VISIBLE);
            }
        }catch (Exception ex){}

        mprogreesdialog=new ProgressDialog(this);

        camp_recycle=(RecyclerView)findViewById(R.id.camp_recycler);
        camp_recycle.setHasFixedSize(true);
        camp_recycle.setLayoutManager(new LinearLayoutManager(this));

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
                final String tttitle=ttitle.getText().toString().trim();
                final String tposition=position.getText().toString().trim();
                final String tcontact=contact.getText().toString().trim();
                if(!TextUtils.isEmpty(tttitle)&&!TextUtils.isEmpty(tposition)&&!TextUtils.isEmpty(tcontact))
                {
                    final StorageReference filepath=mstorage.child("campus_news").child(imageUri.getLastPathSegment());
                    filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //Uri downloaduri=taskSnapshot.getDownloadUrl();
                            //Task<Uri> u = taskSnapshot.getStorage().getDownloadUrl();
                            final DatabaseReference newPost=mdatabase.push();

                            newPost.child("camp_tittle").setValue(tttitle);
                            newPost.child("camp_descrip").setValue(tposition);
                            newPost.child("camp_date").setValue(tcontact);
                            //newPost.child("camp_img").setValue(u.toString());
                            filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Map newImage = new HashMap();
                                    newImage.put("camp_image", uri.toString());
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
                Toast.makeText(CampusNews.this, "key is: "+getkey.getText().toString(), Toast.LENGTH_SHORT).show();
                mdatabase = FirebaseDatabase.getInstance().getReference()
                        .child("campus_news").child(getkey.getText().toString());
                mdatabase.removeValue();
            }
        });

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
        final FirebaseRecyclerAdapter<AddCampusNews, CampNewsViewholder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<AddCampusNews, CampNewsViewholder>( AddCampusNews.class,
                R.layout.list_layout,
                CampNewsViewholder.class,
                mdatabase)
        {
            @Override
            protected void populateViewHolder(CampNewsViewholder viewHolder, AddCampusNews model, int position) {
                final String camp_key=getRef(position).getKey();
                //final String camp_key=getRef(position).toString(); //for showing the database reading
                viewHolder.setTitle(model.getCamp_tittle());
                viewHolder.setDescription(model.getCamp_descrip());
                viewHolder.setDate(model.getCamp_date());
                viewHolder.setImage(model.getCamp_image());
                /*String t=model.getCampus_date();
                String d=model.getCampus_descrip();
                String dd=model.getCampus_date();
                String i=model.getCampus_img();
                AddCampusNews camp=new AddCampusNews(d, dd, i, t);
                campfire.add(camp);*/
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CampusNews.this,camp_key,Toast.LENGTH_LONG).show();
                        getkey.setText(camp_key.toString().trim());
                    }
                });

            }
        };
        camp_recycle.setAdapter(firebaseRecyclerAdapter);
    }
    public static class CampNewsViewholder extends RecyclerView.ViewHolder{
        View mView;
        public CampNewsViewholder(View itemView)
        {
            super(itemView);
            mView=itemView;
        }
        public  void setTitle(String name)
        {
            TextView camp_ttitle=(TextView) mView.findViewById(R.id.tech_list_name);
            camp_ttitle.setText(name);
        }
        public  void setDescription(String position)
        {
            TextView camp_des=(TextView) mView.findViewById(R.id.tech_list_positon);
            camp_des.setText(position);
        }
        public  void setDate(String contact)
        {
            TextView camp_date=(TextView) mView.findViewById(R.id.tech_list_contact);
            camp_date.setText(contact);
        }
        public void setImage(String image)
        {
            ImageView campimage=(ImageView) mView.findViewById(R.id.tech_list_image);
            //Picasso.with(ctx).load(image).into(techcseimage);
            /*Picasso.with(ctx)
                    .load(image)
                    .placeholder(R.drawable.email)
                    .resize(50,50)
                    .into(techcseimage);
            Picasso.with(ctx).setLoggingEnabled(true);*/

            Picasso.get().load(image).centerCrop().placeholder(R.mipmap.ic_launcher).fit().into(campimage);


        }
    }
}
