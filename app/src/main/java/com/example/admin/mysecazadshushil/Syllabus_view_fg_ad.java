package com.example.admin.mysecazadshushil;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

//import zoftino.com.firestore.R;

public class Syllabus_view_fg_ad extends Fragment {

    private static final String TAG = "Syllabus_view_fg_ad";

    private FirebaseFirestore firestoreDB;
    private String userId;

    private RecyclerView filesRecyclerView;
    private EditText createDate;
    private Button deletelsyl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //userId = getArguments().getString("userId");

        View view = inflater.inflate(R.layout.fragment_syllabus_view_files_fragment_admin,
                container, false);
        deletelsyl=view.findViewById(R.id.delete_file_b);
        firestoreDB = FirebaseFirestore.getInstance();

        filesRecyclerView = (RecyclerView) view.findViewById(R.id.files_lst);
        //createDate =  view.findViewById(R.id.create_date);

        //Button searchButton = (Button) view.findViewById(R.id.search_files_b);
        getFielNames();
        /*searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFielNames();
            }
        });*/


        LinearLayoutManager recyclerLayoutManager =
                new LinearLayoutManager(getActivity().getApplicationContext());
        filesRecyclerView.setLayoutManager(recyclerLayoutManager);
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(filesRecyclerView.getContext(),
                        recyclerLayoutManager.getOrientation());
        filesRecyclerView.addItemDecoration(dividerItemDecoration);

        return view;
    }
    private void getFielNames(){
        getFileNamesFromFirestoreDb();
    }
    private void getFileNamesFromFirestoreDb() {
        firestoreDB.collection("files")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<String> fileList = new ArrayList<String>();
                            Log.d(TAG, "DOCS SIZE "+task.getResult().size());
                            for(DocumentSnapshot doc : task.getResult()){
                                fileList.add(doc.getString("storagePath"));
                            }
                            Syllabus_recycler_adapter_admin recyclerViewAdapter = new
                                    Syllabus_recycler_adapter_admin(fileList,
                                    getActivity(),null);  //userid
                            filesRecyclerView.setAdapter(recyclerViewAdapter);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}