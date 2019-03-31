package com.example.admin.mysecazadshushil;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class Adminapply extends AppCompatActivity {

private EditText name, reg, email, phone, pass;
private ImageButton addimage;
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
    Dialog mydialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminapply);
        getSupportActionBar().setTitle("Admin Application");
        mstorage= FirebaseStorage.getInstance().getReference();
        mdatabase= FirebaseDatabase.getInstance().getReference().child("Admin_request");
        mdatabase.keepSynced(true);
        addimage=(ImageButton)findViewById(R.id.app_image);
        name=(EditText)findViewById(R.id.app_name);
        reg=(EditText) findViewById(R.id.app_reg);
        email=(EditText)findViewById(R.id.app_email);
        phone=(EditText)findViewById(R.id.app_phone);
        pass=(EditText)findViewById(R.id.app_pass);
        sub=(Button)findViewById(R.id.app_sub);
        mprogreesdialog=new ProgressDialog(this);
        mydialog=new Dialog(Adminapply.this);
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
                mprogreesdialog.setMessage("Completing Registration...");
                mprogreesdialog.show();
                final String aname=name.getText().toString().trim().toLowerCase();
                final String areg=reg.getText().toString().trim();
                final String aemail=email.getText().toString().trim();
                final String aphone=phone.getText().toString().trim();
                final String apass=pass.getText().toString().trim();
                if(!TextUtils.isEmpty(aname)&&!TextUtils.isEmpty(areg)&&!TextUtils.isEmpty(aemail)
                        &&!TextUtils.isEmpty(aphone)&&!TextUtils.isEmpty(apass))
                {
                    final StorageReference filepath=mstorage.child("Admin_request").child(imageUri.getLastPathSegment());
                    filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            final DatabaseReference newPost=mdatabase.push();
                            newPost.child("app_name").setValue(aname);
                            newPost.child("app_reg").setValue(areg);
                            newPost.child("app_email").setValue(aemail);
                            newPost.child("app_phone").setValue(aphone);
                            newPost.child("app_pass").setValue(apass);
                            filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Map newImage = new HashMap();
                                    newImage.put("app_image", uri.toString());
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
                            mydialog=new Dialog(Adminapply.this);
                            mydialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            mydialog.setContentView(R.layout.admin_form_complete_notified);
                            Window window = mydialog.getWindow();
                            window.setLayout(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
                            mydialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            mydialog.show();
                            Toast.makeText(Adminapply.this, "Successfully Submitted", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    mprogreesdialog.dismiss();
                    Toast.makeText(Adminapply.this, "Empty field", Toast.LENGTH_SHORT).show();
                }
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
}
