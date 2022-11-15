package com.example.swip;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Insert extends AppCompatActivity {


    EditText name, message;
    Button insertButton;

    FirebaseFirestore db;

    //receiving data to be updated
    String recId, recName, recMessage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_write);

        //insert the data into the firestore db
        db = FirebaseFirestore.getInstance();

        name = findViewById(R.id.editTextName);
        message = findViewById(R.id.editTextMessage);
        insertButton = findViewById(R.id.insertButton);


        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            insertButton.setText("Update");

            recId = bundle.getString("id");
            recName = bundle.getString("name");
            recMessage = bundle.getString("message");

            //set the data
            name.setText(recName);
            message.setText(recMessage);

        } else {
            insertButton.setText("Insert");
        }

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = name.getText().toString();
                String userMessage = message.getText().toString();
                Toast.makeText(Insert.this, "게시글이 등록되었습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),ShowMessage.class);
                startActivity(intent);


                if (userMessage.equals("")) {
                    message.setError("Can't be empty");
                    return;
                }

                if (userName.equals("")) {
                    name.setError("Can't be empty");
                    return;
                }

                //check if user want to update the data or insert the data
                Bundle bundle1 = getIntent().getExtras();
                if (bundle1 != null) {
                    //user want to update the data
                    String id = recId;
                    updateTheData(id, userName, userMessage);

                } else {
                    //user want to insert the data

                    //create the random id
                    String id = UUID.randomUUID().toString();

                    saveToFireStore(id, userName, userMessage);

                    //clear the fields
                    name.setText("");
                    message.setText("");
                }
            }
        });

    }

    private void updateTheData(String id, String name, String message) {

        db.collection("초등학생").document(id).update("Name", name, "Message", message)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Insert.this, "게시글이 수정되었습니다.", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(Insert.this, "Something went wrong ::: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Insert.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveToFireStore(String id, String userName, String userMessage) {

        Map<String, Object> data = new HashMap<>();
        data.put("id", id);
        data.put("Name", userName);
        data.put("Message", userMessage);

        db.collection("초등학생").document(id).set(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Insert.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
