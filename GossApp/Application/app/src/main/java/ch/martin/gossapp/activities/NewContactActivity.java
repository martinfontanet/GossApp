package ch.martin.gossapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import ch.martin.gossapp.MyApplication;
import ch.martin.gossapp.R;
import ch.martin.gossapp.classes.ParametersPasser;
import ch.martin.gossapp.classes.User;

public class NewContactActivity extends AppCompatActivity {
    private TextView pseudoText;
    private TextView nicknameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);
        findViews();
    }

    public void addNewContact(View view){
        String pseudo = pseudoText.getText().toString();
        String nickname = nicknameText.getText().toString();
        if(nickname.isEmpty()){
            nickname = pseudo;
        }

        ((MyApplication) getApplicationContext()).addContact(pseudo,nickname);
        finish();
    }

    private void findViews(){
        this.pseudoText = findViewById(R.id.contact_pseudo);
        this.nicknameText = findViewById(R.id.contact_nickname);
    }
}
