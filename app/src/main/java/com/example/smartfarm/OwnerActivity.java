package com.example.smartfarm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OwnerActivity extends AppCompatActivity {
    EditText textDate,totalCost,totalProfit,input,cost,output,revenue; Button activateStatus;

    SharedPreferences sharedPreferences;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference ref;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner);

        textDate = findViewById(R.id.textDate);
        totalCost = findViewById(R.id.totalCost);
        totalProfit = findViewById(R.id.totalProfit);
        input = findViewById(R.id.input);
        cost = findViewById(R.id.cost);
        output = findViewById(R.id.output);
        revenue = findViewById(R.id.revenue);
        activateStatus = findViewById(R.id.activateStatus);


        sharedPreferences = getSharedPreferences("myPreferences", MODE_PRIVATE);
        String email= sharedPreferences.getString("email", null);
        String role= sharedPreferences.getString("role", null);
        String name= sharedPreferences.getString("name", null);
        String animal= sharedPreferences.getString("animal", null);

        activateStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("ownerStatusOnBTNActivation");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            String BTNStatus = data.child("BTNStatus").getValue().toString();
                            if(BTNStatus.equals("INACTIVE")) {
                                String keyId = data.getKey();
                                ref.child(keyId).child("BTNStatus").setValue("ACTIVATE");
                                Toast.makeText(getApplicationContext(), "Status Activated successfully", Toast.LENGTH_SHORT).show();
                                activateStatus.setText("DEACTIVATE EMPLOYEE STATUS");
                                activateStatus.setBackgroundColor(Color.RED);

                            }else {
                                String keyId = data.getKey();
                                ref.child(keyId).child("BTNStatus").setValue("INACTIVE");
                                Toast.makeText(getApplicationContext(), "Status Inactivated", Toast.LENGTH_SHORT).show();
                                activateStatus.setText("ACTIVATE EMPLOYEE STATUS");
                                activateStatus.setBackgroundColor(Color.BLUE);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });
            }
        });

        if(animal.equals("goats")) {

            ref=database.getReference().child("GoatsRecords");
            ref.addListenerForSingleValueEvent(new ValueEventListener(){
                @Override
                public void onDataChange (DataSnapshot dataSnapshot) {
                    double totalReturn = 0;
                    for (DataSnapshot data : dataSnapshot.getChildren()) {

                        double dailyCash = Double.parseDouble(data.child("dailyCash").getValue().toString().trim());

                        totalReturn += dailyCash;


                        ref=database.getReference().child("animalCostInputs");

                        ref.addListenerForSingleValueEvent(new ValueEventListener(){
                            @Override
                            public void onDataChange (DataSnapshot dataSnapshot) {
                                double totalReturn = 0;
                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                    if(data.child("theRole").getValue().toString().trim().equals("Pigs")) {
                                        //Get Input Details
                                        double inputCost = Double.parseDouble(data.child("inputCost").getValue().toString().trim());
                                        String inputDetails = data.child("inputDetails").getValue().toString().trim();
                                        String animalCost = data.child("inputCost").getValue().toString().trim();

                                        output.setText(String.valueOf(totalReturn));
                                        input.setText(inputDetails);
                                        cost.setText(animalCost);

                                        //Calculate Revenue
                                        double Therevenue = totalReturn - Double.parseDouble(animalCost);

                                        revenue.setText(String.valueOf(Therevenue));
                                    }


                                }

                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }

                        });
                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });

        }
        else if(animal.equals("pigs")){
            ref=database.getReference().child("PigsRecords");
            ref.addListenerForSingleValueEvent(new ValueEventListener(){
                @Override
                public void onDataChange (DataSnapshot dataSnapshot) {
                    double totalReturn = 0;
                    for (DataSnapshot data : dataSnapshot.getChildren()) {

                        double dailyCash = Double.parseDouble(data.child("dailyCash").getValue().toString().trim());

                         totalReturn += dailyCash;


                        ref=database.getReference().child("animalCostInputs");

                        ref.addListenerForSingleValueEvent(new ValueEventListener(){
                            @Override
                            public void onDataChange (DataSnapshot dataSnapshot) {
                                double totalReturn = 0;
                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                    if(data.child("theRole").getValue().toString().trim().equals("Pigs")) {
                                        //Get Input Details
                                        double inputCost = Double.parseDouble(data.child("inputCost").getValue().toString().trim());
                                        String inputDetails = data.child("inputDetails").getValue().toString().trim();
                                        String animalCost = data.child("inputCost").getValue().toString().trim();

                                        output.setText(String.valueOf(totalReturn));
                                        input.setText(inputDetails);
                                        cost.setText(animalCost);

                                        //Calculate Revenue
                                        double Therevenue = totalReturn - Double.parseDouble(animalCost);

                                        revenue.setText(String.valueOf(Therevenue));
                                    }


                                }

                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }

                        });
                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });
        }
        else if(animal.equals("cattle")){
            ref=database.getReference().child("CattleRecords");
            ref.addListenerForSingleValueEvent(new ValueEventListener(){
                @Override
                public void onDataChange (DataSnapshot dataSnapshot) {
                    double totalReturn = 0;
                    for (DataSnapshot data : dataSnapshot.getChildren()) {

                        double dailyCash = Double.parseDouble(data.child("dailyCash").getValue().toString().trim());

                        totalReturn += dailyCash;


                        ref=database.getReference().child("animalCostInputs");

                        ref.addListenerForSingleValueEvent(new ValueEventListener(){
                            @Override
                            public void onDataChange (DataSnapshot dataSnapshot) {
                                double totalReturn = 0;
                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                    if(data.child("theRole").getValue().toString().trim().equals("Cattle")) {
                                        //Get Input Details
                                        double inputCost = Double.parseDouble(data.child("inputCost").getValue().toString().trim());
                                        String inputDetails = data.child("inputDetails").getValue().toString().trim();
                                        String animalCost = data.child("inputCost").getValue().toString().trim();

                                        output.setText(String.valueOf(totalReturn));
                                        input.setText(inputDetails);
                                        cost.setText(animalCost);

                                        //Calculate Revenue
                                        double Therevenue = totalReturn - Double.parseDouble(animalCost);

                                        revenue.setText(String.valueOf(Therevenue));
                                    }


                                }

                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }

                        });
                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });
        }
        else if(animal.equals("chicken")){
            ref=database.getReference().child("ChickenRecords");
            ref.addListenerForSingleValueEvent(new ValueEventListener(){
                @Override
                public void onDataChange (DataSnapshot dataSnapshot) {
                    double totalReturn = 0;
                    for (DataSnapshot data : dataSnapshot.getChildren()) {

                        double dailyCash = Double.parseDouble(data.child("dailyCash").getValue().toString().trim());

                        totalReturn += dailyCash;


                        ref=database.getReference().child("animalCostInputs");

                        ref.addListenerForSingleValueEvent(new ValueEventListener(){
                            @Override
                            public void onDataChange (DataSnapshot dataSnapshot) {
                                double totalReturn = 0;
                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                    if(data.child("theRole").getValue().toString().trim().equals("Chicken")) {
                                        //Get Input Details
                                        double inputCost = Double.parseDouble(data.child("inputCost").getValue().toString().trim());
                                        String inputDetails = data.child("inputDetails").getValue().toString().trim();
                                        String animalCost = data.child("inputCost").getValue().toString().trim();

                                        output.setText(String.valueOf(totalReturn));
                                        input.setText(inputDetails);
                                        cost.setText(animalCost);

                                        //Calculate Revenue
                                        double Therevenue = totalReturn - Double.parseDouble(animalCost);

                                        revenue.setText(String.valueOf(Therevenue));
                                    }


                                }

                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }

                        });
                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });
        }

    }
}