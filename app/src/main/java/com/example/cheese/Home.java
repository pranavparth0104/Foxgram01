package com.example.cheese;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

public class Home extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private NavController navController;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        navController = Navigation.findNavController(this,R.id.fragments);

        NavigationUI.setupWithNavController(bottomNavigationView,navController);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.home_menu,menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.direct:
//
//                break;
////            case R.id.signout:
////
////                if(user != null && firebaseAuth != null){
////
////                    firebaseAuth.signOut();
////
////                    Intent intent = new Intent(getApplicationContext(),Login.class);
////                    startActivity(intent);
////                    finish();
////                    SharedPreferences preferences = getSharedPreferences("checkuser",MODE_PRIVATE);
////                    SharedPreferences.Editor editor = preferences.edit();
////                    editor.remove("remember");
////                    editor.remove("uid");
////                    editor.remove("uname");
////                    editor.commit();
////                    editor.clear();
////
////                }
////
////                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }


}