package ch.martin.gossapp.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import ch.martin.gossapp.MyApplication;
import ch.martin.gossapp.classes.Conversation;
import ch.martin.gossapp.R;
import ch.martin.gossapp.classes.User;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Conversation> conversations;
    private LinearLayout conversationsLayout;

    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

        try {
            ((MyApplication) getApplicationContext()).connectUser();
        } catch (IOException e) {
            e.printStackTrace();
        }

        autoRefresh();
    }

    private void autoRefresh() {
        handler.postDelayed(new Runnable() {
            @Override

            // Method to execute every 300 milliseconds
            public void run() {
                renameTitle();
                loadConversations();

                autoRefresh();
            }
        }, 300);
    }

    public void renameTitle(){
        TextView title = findViewById(R.id.textView2);
        title.setText("Hello "+((MyApplication) getApplicationContext()).getUser().getName()+"!");
    }

    private void loadConversations(){
        conversations = ((MyApplication) getApplicationContext()).getConversations();
        //System.out.println("size : "+conversations.size());

        conversationsLayout.removeAllViews();

        for(final Conversation conversation: conversations){
            TextView conv_text = new TextView(getApplicationContext());
            ConstraintLayout conv_layout = new ConstraintLayout(getApplicationContext());

            String conversationTitle = conversation.getName() + ": ";

            for(User user: conversation.getUsers()){
                conversationTitle += user.getName() + " ";
            }

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 10, 0, 10);

            conv_layout.setLayoutParams(lp);

            conv_text.setText(conversationTitle);
            conv_text.setPadding(0,50,0,50);
            conv_text.setTextColor(Color.BLUE);

            conv_layout.addView(conv_text);
            conv_layout.setBackgroundColor(Color.GREEN);

            conv_layout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    ((MyApplication) getApplicationContext()).setCurrentConversation(conversation.getId());
                    accessConversation(view);
                }
            });

            conversationsLayout.addView(conv_layout);

        }
    }

    /** Called when the user taps the conversation */
    public void accessConversation(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, ConversationActivity.class);
        startActivity(intent);

    }

    public void changeUser(int i){
        renameTitle();

        loadConversations();
        ((MyApplication) getApplicationContext()).setCurrentUser(new User(i, "test"));
    }

    public void goToUser1(View view){
        Intent intent = getIntent();
        changeUser(1);
    }
    public void goToUser2(View view){
        Intent intent = getIntent();
        changeUser(2);
    }

    private void findViews(){
        this.conversationsLayout = findViewById(R.id.conversations_layout);
    }
/*
    private ArrayList<Conversation> getConversations(){
        ArrayList<User> users = new ArrayList<>();

        users.add(new User("martin",1));
        users.add(new User("nathan", 2));
        Conversation conversation = new Conversation(1, users, "super conv");

        ArrayList<Conversation> conversations = new ArrayList<>();
        conversations.add(conversation);

        return conversations;
    }*/

}
