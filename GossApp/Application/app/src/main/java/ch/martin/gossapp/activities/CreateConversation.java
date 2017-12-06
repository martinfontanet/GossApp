package ch.martin.gossapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import ch.martin.gossapp.MyApplication;
import ch.martin.gossapp.R;
import ch.martin.gossapp.classes.Contact;
import ch.martin.gossapp.classes.ParametersPasser;
import ch.martin.gossapp.classes.User;

public class CreateConversation extends AppCompatActivity {
    private TextView nameText;
    private TextView errorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_conversation);

        findViews();
    }

    public void create(View view){
        String name = nameText.getText().toString();
        ArrayList<User> contactList = new ArrayList<>();
        contactList.add(((MyApplication) getApplicationContext()).getUser());

        System.out.println(((MyApplication) getApplicationContext()).getUser());

        ((MyApplication) getApplicationContext()).newConversation(new ParametersPasser<>(name,contactList,0,0));
        finish();
    }

    private void findViews(){
        this.nameText = findViewById(R.id.conversation_name);
        this.errorText = findViewById(R.id.error_edit);
    }
}
