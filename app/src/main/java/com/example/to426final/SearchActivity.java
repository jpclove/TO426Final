package com.example.to426final;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editTextZipCodeEnter;
    Button buttonSearch, buttonGoToMain, buttonAddImportance;
    TextView textViewBird, textViewLastSeenBy;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        editTextZipCodeEnter = findViewById(R.id.editTextZipCodeEnter);
        buttonGoToMain = findViewById(R.id.buttonGoToMain);
        buttonSearch = findViewById(R.id.buttonSearch);
        buttonAddImportance = findViewById(R.id.buttonAddImportance);
        textViewBird = findViewById(R.id.textViewBird);
        textViewLastSeenBy = findViewById(R.id.textViewLastSeenBy);


        buttonSearch.setOnClickListener(this);
        buttonGoToMain.setOnClickListener(this);
        buttonAddImportance.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();





    }
    @Override
    public void onClick(View v) {

        if(v==buttonAddImportance) {

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference myRef = database.getReference("Bird");


            int findZipCode = Integer.parseInt(editTextZipCodeEnter.getText().toString());

            myRef.orderByChild("zipcode").equalTo(findZipCode).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    String findKey = dataSnapshot.getKey();
                    Bird foundZipCode = dataSnapshot.getValue(Bird.class);
                    String findPersonName = foundZipCode.username;
                    String findBirdName = foundZipCode.birdname;
                    textViewBird.setText(findBirdName);
                    textViewLastSeenBy.setText(findPersonName);
                    foundZipCode.importance ++;

                    String myKey = dataSnapshot.getKey();
                    myRef.child(myKey).child("importance").setValue(foundZipCode.importance);




                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }


        else if(v==buttonGoToMain) {
            mAuth.signOut();
            Intent goToMain = new Intent(this, MainActivity.class);
            startActivity(goToMain);
        }
        else if(v== buttonSearch){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference myRef = database.getReference("Bird");

            int searchByZip = Integer.parseInt(editTextZipCodeEnter.getText().toString());

            myRef.orderByChild("zipcode").equalTo(searchByZip).addChildEventListener(new ChildEventListener() {

                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    String findKey = dataSnapshot.getKey();
                    Bird foundZipCode = dataSnapshot.getValue(Bird.class);
                    String findPersonName = foundZipCode.username;
                    String findBirdName = foundZipCode.birdname;
                    int findImportance = foundZipCode.importance;
                    textViewBird.setText(findBirdName);
                    textViewLastSeenBy.setText(findPersonName);


                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    };



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Using intents to direct users from the menu
        if(item .getItemId() == R.id.itemLanding){
            Intent goToLanding = new Intent(this, LandingActiviy.class);
            startActivity(goToLanding);



        } else if (item.getItemId() == R.id.itemSearch){
            Toast.makeText(this, "You're already here!", Toast.LENGTH_SHORT).show();


        } else if (item.getItemId() == R.id.itemLogOut) {
            mAuth.signOut();
            Intent logOut = new Intent(this, MainActivity.class);
            startActivity(logOut);
        }
        return super.onOptionsItemSelected(item);
    }}
