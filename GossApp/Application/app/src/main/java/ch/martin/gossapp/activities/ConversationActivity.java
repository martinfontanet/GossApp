package ch.martin.gossapp.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.TreeSet;

import ch.martin.gossapp.classes.Conversation;
import ch.martin.gossapp.classes.Message;
import ch.martin.gossapp.MyApplication;
import ch.martin.gossapp.R;

public class ConversationActivity extends AppCompatActivity {

    private ScrollView sv;
    private LinearLayout linear_layout;
    private TextView messageText;

    // PERSONNAL CLASSES

    private Conversation conversation;
    private TreeSet<Message> messages;

    private int messagesPrinted = 0;

    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);


        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();

        // Capture the layout's TextView and set the string as its text
        findViews();

        conversation = ((MyApplication) getApplicationContext()).getCurrentConversation();
        messages = conversation.getMessages();

        autoRefresh();
    }

    private void autoRefresh() {
        handler.postDelayed(new Runnable() {
            @Override

            // Method to execute every 300 milliseconds
            public void run() {

                // Load messages from time 0 or from the time of the 10th last message
                Date from = new Date(Math.max(0,conversation.getMessages().size() - 10));

                ((MyApplication) getApplicationContext()).refreshMessages(conversation, from);

                conversation = ((MyApplication) getApplicationContext()).getCurrentConversation();
                messages = conversation.getMessages();
                System.out.println("SIZE : "+messages.size()+ " / "+messagesPrinted);
                if(messages.size() > messagesPrinted) {
                    refresh();
                }

                autoRefresh();


            }
        }, 300);
    }


    //TODO REQUEST NEW MESSAGE
    public void sendMessage(View view) {
        // Do something in response to button
        Intent intent = getIntent();

        //String message = messageText.getText().toString() + '\n';
        String message = messageText.getText().toString();
        messageText.setText("");
        int authorID = ((MyApplication) getApplicationContext()).getUser().getID();
        conversation.addMessage(new Message(authorID, conversation.getId(), message, 1));


        //refresh();
    }


    private void refresh(){
        //Conversation.MessagePack newMessages = conversation.getFreshMessages(new Date(1));
        linear_layout.removeAllViews();

        for(Message mess: messages){ //newMessages.getPack()){
            TextView text = new TextView(getApplicationContext());

            String message = conversation.getNameByID(mess.getAuthorID()) +": "+ mess.toString();

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(10, 10, 10, 10);
            lp.gravity = Gravity.CENTER;

            text.setText(message);
            text.setPadding(10,10,10,10);

            text.setTextColor(Color.BLACK);
            text.setLayoutParams(lp);

            LinearLayout inner_layout = new LinearLayout(getApplicationContext());//(LinearLayout) findViewById(R.id.bubble_layout);
            inner_layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            if(mess.getAuthorID() == ((MyApplication) getApplicationContext()).getUser().getID()){
                inner_layout.setGravity(Gravity.TOP | Gravity.RIGHT);
                text.setBackgroundColor(0xC98BF3E7);
            }
            else{
                inner_layout.setGravity(Gravity.TOP | Gravity.LEFT);
                text.setBackgroundColor(0xC9D5D6DE);
            }

            inner_layout.addView(text);

            linear_layout.addView(inner_layout);

            sv.post(new Runnable() {
                @Override
                public void run() {
                    sv.fullScroll(View.FOCUS_DOWN);
                }
            });
        }

        messagesPrinted = messages.size();
    }

    private void findViews(){
        this.sv = findViewById(R.id.scroll_view);
        this.linear_layout = findViewById(R.id.layout_conv);
        this.messageText =  findViewById(R.id.message_text);
    }
}
