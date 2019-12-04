package com.example.to426final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class LandingActiviy extends AppCompatActivity implements View.OnClickListener {
    EditText editTextBirdName, editTextUserName, editTextZipCode, editTextImportance;
    Button buttonSubmit, buttonGoToSearch, buttonLogout;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_activiy);
        buttonGoToSearch = findViewById(R.id.buttonGoToSearch);
        buttonLogout = findViewById(R.id.buttonLogout);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        editTextBirdName = findViewById(R.id.editTextBirdName);
        editTextUserName = findViewById(R.id.editTextUsername);
        editTextZipCode = findViewById(R.id.editTextZipCode);



        buttonSubmit.setOnClickListener(this);
        buttonGoToSearch.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View v) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Bird");

        int createZipCode = Integer.parseInt(editTextZipCode.getText().toString());
        String createBirdName = editTextBirdName.getText().toString();
        String createPersonName = editTextUserName.getText().toString();

        if(v==buttonSubmit){
            mAuth = FirebaseAuth.getInstance();
            if (mAuth.getCurrentUser() != null){
                String EMAIL= mAuth.getCurrentUser().getEmail();
                if (EMAIL.equals(createPersonName)){

                    Bird myBird = new Bird(createBirdName, createZipCode, createPersonName);
                    myRef.push().setValue(myBird);

                    Toast.makeText(this, "Submission Successful!", Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(this, "Username does not match. GET WRECKED!", Toast.LENGTH_SHORT).show();

                }
            }

            }
        else if(v==buttonGoToSearch){
            Intent goToSearch = new Intent(this, SearchActivity.class);
            startActivity(goToSearch);

        }
        else if(v==buttonLogout){
            mAuth.signOut();
            Intent logOut = new Intent(this, MainActivity.class);
            startActivity(logOut);
        }






        }


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
            Toast.makeText(this, "You're already here!", Toast.LENGTH_SHORT).show();

        } else if (item.getItemId() == R.id.itemSearch){
            Intent goToSearch = new Intent(this, SearchActivity.class);
            startActivity(goToSearch);


        } else if (item.getItemId() == R.id.itemLogOut) {
            mAuth.signOut();
            Intent logOut = new Intent(this, MainActivity.class);
            startActivity(logOut);
        }
        return super.onOptionsItemSelected(item);
    }
}
