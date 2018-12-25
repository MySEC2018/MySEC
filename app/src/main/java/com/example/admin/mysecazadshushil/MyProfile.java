package com.example.admin.mysecazadshushil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.joooonho.SelectableRoundedImageView;

import java.io.ByteArrayOutputStream;

public class MyProfile extends AppCompatActivity {
    private ImageView mImage;
    private SelectableRoundedImageView roundImage;
    private Uri mImageUri;
    private EditText name;
    private TextView setname;
    private static final int PICK_IMAGE_REQUEST=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        roundImage=(SelectableRoundedImageView) findViewById(R.id.userphoto);
        name=(EditText) findViewById(R.id.editname);
        setname=(TextView)findViewById (R.id.myname);
        try {
        retrieve();
        }catch (Exception ex)
        {

        }
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


/**

 private void prepareForm() {
 SharedPreferences preferences = getSharedPreferences("myprefs",MODE_PRIVATE);
 EditText etUserName=(EditText)findViewById(R.id.username);
 EditText etUserPhone=(EditText)findViewById(R.id.userphone);
 EditText etUserEmail=(EditText)findViewById(R.id.useremail);
 ImageView ivUserPhoto=(ImageView) findViewById(R.id.userphoto);
 ImageView ivUserSavedPhoto=(ImageView) findViewById(R.id.usersavedphoto);
 // If value for key not exist then return second param value - In this case "..."
 etUserName.setText(preferences.getString("username", "..."));
 etUserPhone.setText(preferences.getString("userphone", "..."));
 etUserEmail.setText(preferences.getString("useremail", "..."));
 String img_str=preferences.getString("userphoto", "");
 if (!img_str.equals("")){
 //decode string to image
 String base=img_str;
 byte[] imageAsBytes = Base64.decode(base.getBytes(), Base64.DEFAULT);
 ivUserPhoto.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length) );
 ivUserSavedPhoto.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length) );
 }
 }

 private void checkFirstLogin() {
 SharedPreferences preferences = getSharedPreferences("myprefs",MODE_PRIVATE);
 // If value for key not exist then return second param value - In this case true
 if (preferences.getBoolean("firstLogin", true)) {
 initProfile();
 SharedPreferences.Editor editor = preferences.edit();
 editor.putBoolean("firstLogin", false);
 editor.commit();
 }
 }

 private void initProfile() {
 SharedPreferences preferences = getSharedPreferences("myprefs",MODE_PRIVATE);
 SharedPreferences.Editor editor = preferences.edit();
 editor.putString("username","Demo TryTest");
 editor.putString("userphone","01234567890");
 editor.putString("useremail","demotrytest@gmail.com");
 editor.commit();
 }

 public void setProfileImage(View view){
 ImageView ivphoto = (ImageView)findViewById(R.id.userphoto);

 //code image to string
 ivphoto.buildDrawingCache();
 Bitmap bitmap = ivphoto.getDrawingCache();
 ByteArrayOutputStream stream=new ByteArrayOutputStream();
 bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
 byte[] image=stream.toByteArray();
 //System.out.println("byte array:"+image);
 //final String img_str = "data:image/png;base64,"+ Base64.encodeToString(image, 0);
 //System.out.println("string:"+img_str);
 String img_str = Base64.encodeToString(image, 0);

 //decode string to image
 String base=img_str;
 byte[] imageAsBytes = Base64.decode(base.getBytes(), Base64.DEFAULT);
 ImageView ivsavedphoto = (ImageView)this.findViewById(R.id.usersavedphoto);
 ivsavedphoto.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes,0, imageAsBytes.length) );

 //save in sharedpreferences
 SharedPreferences preferences = getSharedPreferences("myprefs",MODE_PRIVATE);
 SharedPreferences.Editor editor = preferences.edit();
 editor.putString("userphoto",img_str);
 editor.commit();

 }**/
}