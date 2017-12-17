package ch.martin.gossapp.activities;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import java.util.ArrayList;

import ch.martin.gossapp.MyApplication;
import ch.martin.gossapp.R;
import ch.martin.gossapp.classes.Contact;

public class AddContactsActivity extends AppCompatActivity {
    private ArrayList<Contact> contacts;
    private ArrayList<CheckBox> checkBoxes = new ArrayList<>();

    private LinearLayout contactsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacts);

        findViews();
        loadContacts();
    }

    public void addContacts(View view){
        ArrayList<Contact> contactList = new ArrayList<>();
        MyApplication context = ((MyApplication) getApplicationContext());
        int conversationID = context.getCurrentConversation().getId();

        for(CheckBox check: checkBoxes){
            if(check.isChecked()){
                contactList.add(new Contact(check.getContentDescription().toString(),check.getText().toString()));
            }
        }

        context.addUsersToConversation(contactList, conversationID);

        finish();
    }

    private void findViews(){
        this.contactsLayout = findViewById(R.id.contacts_layout);
    }

    public void loadContacts(){
        contacts = ((MyApplication) getApplicationContext()).getContacts();
        //System.out.println("size : "+conversations.size());

        contactsLayout.removeAllViews();

        checkBoxes.clear();

        for(final Contact contact: contacts){
            CheckBox contact_box = new CheckBox(getApplicationContext());
            checkBoxes.add(contact_box);
            ConstraintLayout contact_layout = new ConstraintLayout(getApplicationContext());

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 10, 0, 10);

            contact_layout.setLayoutParams(lp);

            contact_box.setText(contact.getNickname());
            contact_box.setContentDescription(contact.getName());
            contact_box.setPadding(0,50,0,50);
            contact_box.setTextColor(Color.BLUE);

            contact_layout.addView(contact_box);
            contact_layout.setBackgroundColor(Color.GREEN);
/*
            conv_layout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    ((MyApplication) getApplicationContext()).setCurrentConversation(conversation.getId());
                    accessConversation(view);
                }
            });*/

            contactsLayout.addView(contact_layout);

        }
    }
}
