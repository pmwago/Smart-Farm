package com.example.smartfarm;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class inputActivity extends AppCompatActivity {
   TextView animalText; EditText inputPrice,inputItems; Button submitBTN;

    SharedPreferences sharedPreferences;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        animalText = findViewById(R.id.animalText);
        inputPrice = findViewById(R.id.inputPrice);
        inputItems = findViewById(R.id.inputItems);
        submitBTN = findViewById(R.id.SubmitBTN);

        sharedPreferences = getSharedPreferences("myPreferences", MODE_PRIVATE);
        String email= sharedPreferences.getString("email", null);
        String role= sharedPreferences.getString("role", null);
        String name= sharedPreferences.getString("name", null);



        if (role.equals("Cattle employee")){

            animalText.setText("CATTLE INPUT PRICE:");


        }
        else  if (role.equals("Chicken employee")){
            animalText.setText("CHICKEN INPUT PRICE:");

        }
        else if (role.equals("Pig employee")){
            animalText.setText("PIGS INPUT PRICE:");

        }
        else  if (role.equals("Goat employee")){

            animalText.setText("GOAT INPUT PRICE:");

        }

        ref=database.getReference().child("animalCostInputs");

        submitBTN.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if (inputPrice.getText().toString().equals("")) {
                    inputPrice.setError("Field required");
                } else if (inputItems.getText().toString().equals("")) {
                    inputItems.setError("Field required");
                } else {
                    String inputCost = inputPrice.getText().toString();
                    String inputDetails = inputItems.getText().toString();

                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();
                    String TheDate = dtf.format(now);

                    if (role.equals("Cattle employee")) {

                        String theRole = "Cattle";

                        String id = ref.push().getKey();
                        AnimalInputs nimalInputs = new AnimalInputs(theRole, inputCost, TheDate, inputDetails);
                        ref.child(id).setValue(nimalInputs);
                        Toast.makeText(getApplicationContext(), "Monthly Record inserted", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), Home.class);
                        startActivity(intent);
                    } else if (role.equals("Chicken employee")) {
                        String theRole = "Chicken";
                        String id = ref.push().getKey();
                        AnimalInputs nimalInputs = new AnimalInputs(theRole, inputCost, TheDate, inputDetails);
                        ref.child(id).setValue(nimalInputs);
                        Toast.makeText(getApplicationContext(), "Monthly Record inserted", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), Home.class);
                        startActivity(intent);
                    } else if (role.equals("Pig employee")) {
                        String theRole = "Pigs";
                        String id = ref.push().getKey();
                        AnimalInputs nimalInputs = new AnimalInputs(theRole, inputCost, TheDate, inputDetails);
                        ref.child(id).setValue(nimalInputs);
                        Toast.makeText(getApplicationContext(), "Monthly Record inserted", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), Home.class);
                        startActivity(intent);

                    } else if (role.equals("Goat employee")) {

                        String theRole = "Goat";
                        String id = ref.push().getKey();
                        AnimalInputs nimalInputs = new AnimalInputs(theRole, inputCost, TheDate, inputDetails);
                        ref.child(id).setValue(nimalInputs);
                        Toast.makeText(getApplicationContext(), "Monthly Record inserted", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), Home.class);
                        startActivity(intent);
                    }

                }
            }
            });



        }

}