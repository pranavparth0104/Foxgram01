package com.example.cheese;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cheese.model.Dp;
import com.example.cheese.model.Posts;
import com.example.cheese.ui.ProfileAdapter;
import com.example.cheese.util.UserApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import agency.tango.android.avatarview.views.AvatarView;

public class ProfilePage extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageReference;
    private List<Posts> postsLists;
    private List<Dp> dpList;
    private RecyclerView recyclerView;
    private ProfileAdapter recyclerAdapter;
    private NestedScrollView nestedScrollView;
    private String imageUrl;
    private Toolbar toolbars;


    private CollectionReference collectionReference = (CollectionReference) db.collection("Posts");
    private CollectionReference dps =  db.collection("Dp");

    private TextView username;
    private TextView nop;
    private TextView uid;
    private String userid;
    private String usernames;
    private AvatarView avatarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
             usernames = bundle.getString("name");
             userid = bundle.getString("uid");
        }
        username = findViewById(R.id.usernameprofile2);
        uid = findViewById(R.id.idprofile2);
        avatarView = findViewById(R.id.avatarView2);
        nop = findViewById(R.id.noprof);
        toolbars = findViewById(R.id.toolbar2);
        nestedScrollView = findViewById(R.id.nested);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        recyclerView = findViewById(R.id.profileview2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        postsLists = new ArrayList<>();
        dpList = new ArrayList<>();

        setSupportActionBar(toolbars);









        registerForContextMenu(avatarView);
        //Toast.makeText(getContext().getApplicationContext(), imageUrl, Toast.LENGTH_SHORT).show();






            username.setText("Name : " +usernames);
            uid.setText("Id : "+userid);



    }

    private void popupMenu(AvatarView avatarView) {

    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.popup, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.newDp:

                Intent intent = new Intent(getApplicationContext(),Profilephoto.class);
                startActivityForResult(intent,2);

                break;
            case R.id.deleteDp:

                dps

                        .whereEqualTo("userId",UserApi.getInstance().getUserid())
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                if(!queryDocumentSnapshots.isEmpty()) {
                                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                        dps.document(document.getId()).delete();
                                        Toast.makeText(getApplicationContext(), "Delete Successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = getIntent();

                                        startActivity(intent);


                                    }
                                }else{
                                    Toast.makeText(getApplicationContext(), "Upload First", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

                break;
        }
        return true;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu,menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    //    @Override
////    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
////        ((AppCompatActivity)getActivity()).getMenuInflater().inflate(R.menu.menu,menu);
////        super.onCreateOptionsMenu(menu, inflater);
////    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()){
//
//            case R.id.signout:
//
//                if(user != null && firebaseAuth != null){
//
//                    firebaseAuth.signOut();
//
//                    Intent intent = new Intent(getApplicationContext(),Login.class);
//                    startActivity(intent);
//                    finish();
//
//                    SharedPreferences preferences = getSharedPreferences("checkuser", Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = preferences.edit();
//                    editor.remove("remember");
//                    editor.remove("uid");
//                    editor.remove("uname");
//                    editor.commit();
//                    editor.clear();
//
//                }
//
//                break;
//            case R.id.settings:
//                Intent intent = new Intent(getApplicationContext(),Settings.class);
//                startActivity(intent);
//
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onStart() {
        super.onStart();
        collectionReference
                .whereEqualTo("userId",userid)
//                .orderBy("time", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()){
                            for(QueryDocumentSnapshot posts : queryDocumentSnapshots){
                                Posts post = posts.toObject(Posts.class);
                                postsLists.add(post);

                            }
                            recyclerAdapter = new ProfileAdapter(getApplicationContext(),postsLists);
                            recyclerView.setAdapter(recyclerAdapter);
                            recyclerView.setNestedScrollingEnabled(true);
                            recyclerAdapter.notifyDataSetChanged();


                        }else{
                            nop.setVisibility(View.VISIBLE);
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Adapter Error", Toast.LENGTH_SHORT).show();

                    }
                });

        dps
                .whereEqualTo("userId",userid)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()){
                            for(QueryDocumentSnapshot dpss : queryDocumentSnapshots){
                                Dp dp = dpss.toObject(Dp.class);
                                dpList.add(dp);
                                imageUrl = dp.getImageUrl();
                                Picasso.get()
                                        .load(imageUrl)
                                        .placeholder(R.drawable.ic_loading)
                                        .into(avatarView);
                                //Toast.makeText(getContext().getApplicationContext(), imageUrl, Toast.LENGTH_SHORT).show();


                            }





                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Display Error", Toast.LENGTH_SHORT).show();

                    }
                });


    }


}