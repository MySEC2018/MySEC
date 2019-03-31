package com.example.admin.mysecazadshushil;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import zoftino.com.firestore.R;


public class Syllabus_upload_frag extends Fragment {

    private static final String TAG = "Syllabus_upload_frag";

    private FirebaseStorage firebaseStorage;
    private FirebaseFirestore firestoreDB;
    private EditText filePath, filetitle;
    private Spinner filetype;
    private String userId;
    private String userPath;
    private ProgressDialog mprogreess;
    private DatabaseReference mdatabase;
    private Uri imageUri;
    private StorageReference mstorage;
    private static final String[] PUBLIC_DIR = {Environment.getExternalStoragePublicDirectory
            (Environment.DIRECTORY_DOWNLOADS).getPath(),
            Environment.getExternalStoragePublicDirectory
                    (Environment.DIRECTORY_PICTURES).getPath(),
            Environment.getExternalStoragePublicDirectory
                    (Environment.DIRECTORY_MUSIC).getPath(),
            Environment.getExternalStoragePublicDirectory
                    (Environment.DIRECTORY_MOVIES).getPath()};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //userId = getArguments().getString("userId");
        userPath = "syllabus" + "/";

        View view = inflater.inflate(R.layout.fragment_syllabus_upload_fragment,
                container, false);
        mprogreess=new ProgressDialog(getActivity());
        Button button = (Button) view.findViewById(R.id.upload_b);
        filePath = view.findViewById(R.id.file_name);
        filetitle=view.findViewById(R.id.file_title);
        filetype=view.findViewById(R.id.file_type);
        setupSpinners();

        firebaseStorage = FirebaseStorage.getInstance();
        firestoreDB = FirebaseFirestore.getInstance();
        mdatabase= FirebaseDatabase.getInstance().getReference().child("Syllabus");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile(filePath.getText().toString());
            }
        });

        return view;
    }


    public void uploadFile(final String fileNme) {
        String filePath = getFilePath(fileNme);
        if(filePath == null){
            Log.e(TAG, "File doesn't exist");
            Toast.makeText(getActivity(),
                    "File not found, please enter correct file name",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        final File file = new File(filePath);
        Uri fileUri = Uri.fromFile(file);

        final String toFilePath = userPath + fileUri.getLastPathSegment();

        mstorage= FirebaseStorage.getInstance().getReference();
        final StorageReference storageRef = firebaseStorage.getReference();
        final StorageReference uploadeRef = storageRef.child(toFilePath);
        mprogreess.setMessage("Posting to Path");
        mprogreess.show();
        uploadeRef.putFile(fileUri).addOnFailureListener(new OnFailureListener() {
            public void onFailure(@NonNull Exception exception) {
                Log.e(TAG, "Failed to upload file to cloud storage: " + exception.toString());
                Toast.makeText(getActivity(),
                        "Upload failed",
                        Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //add file name to firestore database
                addFileNameToDB(fileNme);
                /*Task<Uri> u = taskSnapshot.getStorage().getDownloadUrl();
                final DatabaseReference newPost=mdatabase.push();
                Map newImage = new HashMap();
                newImage.put("tech_image", u.toString());
                newPost.updateChildren(newImage);*/
                final String ttname=filetitle.getText().toString().trim().toLowerCase();
                final String tposition=filetype.getSelectedItem().toString().trim();
                final DatabaseReference newPost=mdatabase.push();
                newPost.child("file_title").setValue(ttname);
                newPost.child("file_type").setValue(tposition);
                uploadeRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Map newImage = new HashMap();
                        newImage.put("file_link", uri.toString());
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



                Toast.makeText(getActivity(),
                        "File has been uploaded to cloud storage",
                        Toast.LENGTH_SHORT).show();
                mprogreess.dismiss();
            }
        });
    }


    private String getFilePath(String fName) {
        File file;
        String tFile;
        for (String dir : PUBLIC_DIR) {
            tFile = dir +"/"+fName;
            Log.e(TAG, tFile);
            file = new File(tFile);
            if (file.exists()) {
                return tFile;
            }
        }
        return null;
    }
    private void addFileNameToDB(final String fileNAME) {
        String docKey =fileNAME;    //userid+filename chilo
        Map<String, String> mp = new HashMap<String, String>();
        mp.put("storagePath", fileNAME);
        //mp.put("date", getTodaysDate());
        //mp.put("userId", userId);

        firestoreDB.collection("files").document(docKey)
                .set(mp)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "file name has been added to firestore db ");
                        } else {
                            Log.e(TAG, "failed to add file name to db " + fileNAME);
                        }
                        restUi();
                    }
                });
    }

    private String getTodaysDate() {
        Date currentDate = Calendar.getInstance().getTime();
        java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("MM/dd/YYYY");
        return simpleDateFormat.format(currentDate);
    }

    private void restUi() {
        filePath.setText("");
    }
    private void setupSpinners() {
        List<String> type = new ArrayList<>();
        type.add("DOC");
        type.add("PDF");
        type.add("PPTX");
        type.add("Others");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, type);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filetype.setAdapter(dataAdapter);
    }
}
