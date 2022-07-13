package com.example.smartfarm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class OwnerActivity extends AppCompatActivity {
    TextView textDate,totalCost,totalProfit,input,cost,output,revenue; Button activateStatus,listen;

    SharedPreferences sharedPreferences;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference ref,refl,reffw;
    double totalReturn = 0;
    TextToSpeech t1;    //initialize

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
        listen = findViewById(R.id.Listen);

        sharedPreferences = getSharedPreferences("myPreferences", MODE_PRIVATE);
        String animalPig= sharedPreferences.getString("animalPig", null);
        String animalGoat= sharedPreferences.getString("animalGoat", null);
        String animalChicken= sharedPreferences.getString("animalChicken", null);
        Bundle bundle = getIntent().getExtras();
        String animal = bundle.getString("animal");

        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });


        listen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String toSpeak = totalProfit.getText().toString();
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        activateStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 reffw = FirebaseDatabase.getInstance().getReference().child("ownerStatusOnBTNActivation");
                reffw.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                            for (DataSnapshot data : dataSnapshot.getChildren()) {

                                if (data.exists() && !data.equals("") && data != null) {

                                    String BTNStatus = data.child("btnstatus").getValue().toString();
                                    if (BTNStatus.equals("INACTIVE")) {
                                        String keyId = data.getKey();
                                        ref.child(keyId).child("btnstatus").setValue("ACTIVATE");
                                        Toast.makeText(getApplicationContext(), "Status Activated successfully", Toast.LENGTH_SHORT).show();
                                        activateStatus.setText("DEACTIVATE EMPLOYEE STATUS");
                                        activateStatus.setBackgroundColor(Color.RED);

                                    } else {
                                        String keyId = data.getKey();
                                        ref.child(keyId).child("btnstatus").setValue("INACTIVE");
                                        Toast.makeText(getApplicationContext(), "Status Inactivated", Toast.LENGTH_SHORT).show();
                                        activateStatus.setText("ACTIVATE EMPLOYEE STATUS");
                                        activateStatus.setBackgroundColor(Color.BLUE);
                                    }
                                } else {

                                    String id = reffw.push().getKey();
                                    Status status = new Status("ACTIVATE");
                                    reffw.child(id).setValue(status);
                                    Toast.makeText(getApplicationContext(), "Status Changed", Toast.LENGTH_SHORT).show();
                                    activateStatus.setText("DEACTIVATE EMPLOYEE STATUS");
                                    activateStatus.setBackgroundColor(Color.RED);
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


            ref = FirebaseDatabase.getInstance().getReference().child("GoatsRecords");

            ref.addListenerForSingleValueEvent(new ValueEventListener(){
                @Override
                public void onDataChange (DataSnapshot dataSnapshot) {

                    for (DataSnapshot data : dataSnapshot.getChildren()) {

                        if(data.exists() && !data.equals("") && data!=null) {
                            double dailyCash = Double.parseDouble(data.child("dailyCash").getValue().toString().trim());

                            totalReturn += dailyCash;

                            ref = FirebaseDatabase.getInstance().getReference().child("animalCostInputs");



                            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {


                                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                                        if (data.exists() && !data.equals("") && data != null) {
                                            if (data.child("role").getValue().toString().trim().equals("Goat")) {
                                                //Get Input Details
                                                double inputCost = Double.parseDouble(data.child("cost").getValue().toString().trim());
                                                String inputDetails = data.child("inputDetails").getValue().toString().trim();


                                                output.setText(String.valueOf(totalReturn));
                                                input.setText(inputDetails);
                                                cost.setText(String.valueOf(inputCost));

                                                //Calculate Revenue
                                                double Therevenue = totalReturn - inputCost;

                                                revenue.setText(String.valueOf(Therevenue));

                                                totalCost.setText(String.valueOf(inputCost));
                                                totalProfit.setText(String.valueOf(Therevenue));
                                            }
                                        }

                                    }


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }

                            });
                        }
                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });

        }
        else if(animal.equals("pigs")){


            ref = FirebaseDatabase.getInstance().getReference().child("PigsRecords");

            ref.addListenerForSingleValueEvent(new ValueEventListener(){
                @Override
                public void onDataChange (DataSnapshot dataSnapshot) {

                    for (DataSnapshot data : dataSnapshot.getChildren()) {

                        if(data.exists() && !data.equals("") && data!=null) {
                            double dailyCash = Double.parseDouble(data.child("dailyCash").getValue().toString().trim());

                            totalReturn += dailyCash;

                            ref = FirebaseDatabase.getInstance().getReference().child("animalCostInputs");


                            double finalTotalReturn = totalReturn;
                            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {


                                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                                        if (data.exists() && !data.equals("") && data != null) {
                                            if (data.child("role").getValue().toString().trim().equals("Pigs")) {
                                                //Get Input Details
                                                double inputCost = Double.parseDouble(data.child("cost").getValue().toString().trim());
                                                String inputDetails = data.child("inputDetails").getValue().toString().trim();


                                                output.setText(String.valueOf(totalReturn));
                                                input.setText(inputDetails);
                                                cost.setText(String.valueOf(inputCost));

                                                //Calculate Revenue
                                                double Therevenue = totalReturn - inputCost;

                                                revenue.setText(String.valueOf(Therevenue));

                                                totalCost.setText(String.valueOf(inputCost));
                                                totalProfit.setText(String.valueOf(Therevenue));
                                            }
                                        }

                                    }


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }

                            });
                        }
                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });
        }
        else if(animal.equals("cattle")){


            ref = FirebaseDatabase.getInstance().getReference().child("CattleRecords");

            ref.addListenerForSingleValueEvent(new ValueEventListener(){
                @Override
                public void onDataChange (DataSnapshot dataSnapshot) {

                    for (DataSnapshot data : dataSnapshot.getChildren()) {

                        if(data.exists() && !data.equals("") && data!=null) {
                            double dailyCash = Double.parseDouble(data.child("dailyCash").getValue().toString().trim());

                            totalReturn += dailyCash;

                            ref = FirebaseDatabase.getInstance().getReference().child("animalCostInputs");


                            double finalTotalReturn = totalReturn;
                            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {


                                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                                        if (data.exists() && !data.equals("") && data != null) {
                                            if (data.child("role").getValue().toString().trim().equals("Cattle")) {
                                                //Get Input Details
                                                double inputCost = Double.parseDouble(data.child("cost").getValue().toString().trim());
                                                String inputDetails = data.child("inputDetails").getValue().toString().trim();


                                                output.setText(String.valueOf(totalReturn));
                                                input.setText(inputDetails);
                                                cost.setText(String.valueOf(inputCost));

                                                //Calculate Revenue
                                                double Therevenue = totalReturn - inputCost;

                                                revenue.setText(String.valueOf(Therevenue));

                                                totalCost.setText(String.valueOf(inputCost));
                                                totalProfit.setText(String.valueOf(Therevenue));
                                            }
                                        }

                                    }


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }

                            });
                        }
                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });
        }

        else if(animal.equals("chicken")) {
            ref = FirebaseDatabase.getInstance().getReference().child("ChickenRecords");

            ref.addListenerForSingleValueEvent(new ValueEventListener(){
                @Override
                public void onDataChange (DataSnapshot dataSnapshot) {

                    for (DataSnapshot data : dataSnapshot.getChildren()) {

                        if(data.exists() && !data.equals("") && data!=null) {
                            double dailyCash = Double.parseDouble(data.child("dailyCash").getValue().toString().trim());

                            totalReturn += dailyCash;

                            ref = FirebaseDatabase.getInstance().getReference().child("animalCostInputs");


                            double finalTotalReturn = totalReturn;
                            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {


                                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                                        if (data.exists() && !data.equals("") && data != null) {
                                            if (data.child("role").getValue().toString().trim().equals("Chicken")) {
                                                //Get Input Details
                                                double inputCost = Double.parseDouble(data.child("cost").getValue().toString().trim());
                                                String inputDetails = data.child("inputDetails").getValue().toString().trim();


                                                output.setText(String.valueOf(totalReturn));
                                                input.setText(inputDetails);
                                                cost.setText(String.valueOf(inputCost));

                                                //Calculate Revenue
                                                double Therevenue = totalReturn - inputCost;

                                                revenue.setText(String.valueOf(Therevenue));

                                                totalCost.setText(String.valueOf(inputCost));
                                                totalProfit.setText(String.valueOf(Therevenue));
                                            }
                                        }

                                    }


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }

                            });
                        }
                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });
        }

    }
    public void onPause(){
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }
}

