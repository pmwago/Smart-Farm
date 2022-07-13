package com.example.smartfarm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Home extends AppCompatActivity {

    private EditText textMilk,textEggs,textGoats,textPigs,milkAmount,eggsAmount,goatsAmount,pigsAmount;
    private TextView price;
    Button submit,ToInputCost;
    TextView logout;
    private DatePicker datePicker;
    SharedPreferences sharedPreferences;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference ref;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        submit=findViewById(R.id.submit1);
        logout=findViewById(R.id.logout);
        textMilk=findViewById(R.id.cattle);
        textGoats=findViewById(R.id.goats);
        textPigs=findViewById(R.id.pigs);
        textEggs=findViewById(R.id.chicken);
        price=findViewById(R.id.price);
        ToInputCost = findViewById(R.id.ToInputCost);
        milkAmount=findViewById(R.id.milkPrice);
        eggsAmount=findViewById(R.id.eggsPrice);
        goatsAmount=findViewById(R.id.muttonPrice);
        pigsAmount=findViewById(R.id.porkPrice);
        datePicker=findViewById(R.id.calendar);

        //To check If Date in DB and current Date match to restrict duplicate monthly entries

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String TheDate = dtf.format(now);

        DatabaseReference refr = FirebaseDatabase.getInstance().getReference().child("animalCostInputs");
        refr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    if(data!=null && !data.equals("") && !data.exists()) {

                        String DBDate = data.child("TheDate").getValue().toString();
                        if (TheDate.equals(DBDate)) {
                            ToInputCost.setVisibility(View.GONE);
                        }else if(!TheDate.equals(DBDate)) {

                            //check if owner has activated BTN


                            DatabaseReference ownerREF = FirebaseDatabase.getInstance().getReference().child("ownerStatusOnBTNActivation");
                            ownerREF.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                                        String status = data.child("BTNStatus").getValue().toString();
                                        if (status.equals("ACTIVATE")) {
                                            ToInputCost.setVisibility(View.VISIBLE);
                                        }else {
                                            ToInputCost.setVisibility(View.GONE);
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });



        ToInputCost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), inputActivity.class);
                startActivity(intent);
            }
        });



        String day = "Day = " + datePicker.getDayOfMonth();
        String month = "Month = " + (datePicker.getMonth() + 1);
        String year = "Year = " + datePicker.getYear();
        String date=day + " " + month + " " + year;


        sharedPreferences = getSharedPreferences("myPreferences", MODE_PRIVATE);
        String email= sharedPreferences.getString("email", null);
        String role= sharedPreferences.getString("role", null);
        String name= sharedPreferences.getString("name", null);

        if (role.equals("Cattle employee")){
            textEggs.setVisibility(View.GONE);
            textPigs.setVisibility(View.GONE);
            textGoats.setVisibility(View.GONE);
            eggsAmount.setVisibility(View.GONE);
            goatsAmount.setVisibility(View.GONE);
            pigsAmount.setVisibility(View.GONE);

            price.setVisibility(View.GONE);

        }
       else  if (role.equals("Chicken employee")){
            textMilk.setVisibility(View.GONE);
            textPigs.setVisibility(View.GONE);
            textGoats.setVisibility(View.GONE);
            milkAmount.setVisibility(View.GONE);
            goatsAmount.setVisibility(View.GONE);
            pigsAmount.setVisibility(View.GONE);

            price.setVisibility(View.GONE);

        }
        else if (role.equals("Pig employee")){
            textEggs.setVisibility(View.GONE);
            textMilk.setVisibility(View.GONE);
            textGoats.setVisibility(View.GONE);
            eggsAmount.setVisibility(View.GONE);
            goatsAmount.setVisibility(View.GONE);
            milkAmount.setVisibility(View.GONE);

            price.setVisibility(View.GONE);

        }
       else  if (role.equals("Goat employee")){
            textEggs.setVisibility(View.GONE);
            textPigs.setVisibility(View.GONE);
            textMilk.setVisibility(View.GONE);
            eggsAmount.setVisibility(View.GONE);
            milkAmount.setVisibility(View.GONE);
            pigsAmount.setVisibility(View.GONE);

            price.setVisibility(View.GONE);

        }




        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (role.equals("Cattle employee")) {
                    double milkQty = Double.parseDouble(textMilk.getText().toString().trim());
                    double milkPrice = Double.parseDouble(milkAmount.getText().toString().trim());
                    double amt = milkPrice * milkQty;
                    price.setText(String.valueOf(amt));
                    price.setVisibility(View.VISIBLE);

                    ref = database.getReference().child("CattleRecords");
                    ref.orderByChild("date").equalTo(date).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                Toast.makeText(getApplicationContext(), "Today's records already saved", Toast.LENGTH_SHORT).show();
                            } else {
                                String id = ref.push().getKey();
                                AnimalModel model = new AnimalModel(name, email, date, milkQty, milkPrice, amt);
                                ref.child(id).setValue(model);
                                Toast.makeText(getApplicationContext(), "Data inserted", Toast.LENGTH_SHORT).show();
                                textMilk.setText("");
                                milkAmount.setText("");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
                if (role.equals("Goat employee")) {
                    double goatsQty = Double.parseDouble(textGoats.getText().toString().trim());
                    double goatsPrice = Double.parseDouble(goatsAmount.getText().toString().trim());
                    double amt = goatsPrice * goatsQty;
                    price.setText(String.valueOf(amt));
                    price.setVisibility(View.VISIBLE);

                    ref = database.getReference().child("GoatsRecords");
                    ref.orderByChild("date").equalTo(date).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                Toast.makeText(getApplicationContext(), "Today's records already saved", Toast.LENGTH_SHORT).show();
                            } else {
                                String id = ref.push().getKey();
                                AnimalModel model = new AnimalModel(name, email, date, goatsQty, goatsPrice, amt);
                                ref.child(id).setValue(model);
                                Toast.makeText(getApplicationContext(), "Data inserted", Toast.LENGTH_SHORT).show();
                                textGoats.setText("");
                                goatsAmount.setText("");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                if (role.equals("Pig employee")) {
                    double pigsQty = Double.parseDouble(textPigs.getText().toString().trim());
                    double pigsPrice = Double.parseDouble(pigsAmount.getText().toString().trim());
                    double amt = pigsPrice * pigsQty;
                    price.setText(String.valueOf(amt));
                    price.setVisibility(View.VISIBLE);

                    ref = database.getReference().child("PigsRecords");
                    ref.orderByChild("date").equalTo(date).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                Toast.makeText(getApplicationContext(), "Today's records already saved", Toast.LENGTH_SHORT).show();
                            } else {
                                String id = ref.push().getKey();
                                AnimalModel model = new AnimalModel(name, email, date, pigsQty, pigsPrice, amt);
                                ref.child(id).setValue(model);
                                Toast.makeText(getApplicationContext(), "Data inserted", Toast.LENGTH_SHORT).show();
                                textPigs.setText("");
                                pigsAmount.setText("");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                if (role.equals("Chicken employee")) {
                    double eggsQty = Double.parseDouble(textEggs.getText().toString().trim());
                    double eggsPrice = Double.parseDouble(eggsAmount.getText().toString().trim());
                    double amt = eggsPrice * eggsQty;
                    price.setText(String.valueOf(amt));
                    price.setVisibility(View.VISIBLE);

                    ref = database.getReference().child("ChickenRecords");
                    ref.orderByChild("date").equalTo(date).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                Toast.makeText(getApplicationContext(), "Today's records already saved", Toast.LENGTH_SHORT).show();
                            } else {
                                String id = ref.push().getKey();
                                AnimalModel model = new AnimalModel(name, email, date, eggsQty, eggsPrice, amt);
                                ref.child(id).setValue(model);
                                Toast.makeText(getApplicationContext(), "Data inserted", Toast.LENGTH_SHORT).show();
                                textEggs.setText("");
                                eggsAmount.setText("");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
       logout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               // This will clear the session for all values
               sharedPreferences.edit().clear();
               sharedPreferences.edit().commit();
               Intent login = new Intent(getApplicationContext(), MainActivity.class);
               startActivity(login);
           }
       });
    }
}