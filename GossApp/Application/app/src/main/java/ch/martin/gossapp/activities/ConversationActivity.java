package ch.martin.gossapp.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

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
    private ArrayList<Message> messages;


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

        refresh(0);
    }

    public void sendMessage(View view) {
        // Do something in response to button
        Intent intent = getIntent();

        //String message = messageText.getText().toString() + '\n';
        String message = messageText.getText().toString();
        messageText.setText("");
        int authorID = ((MyApplication) getApplicationContext()).getUserID();
        conversation.addMessage(new Message(authorID, conversation.getId(), message));


        refresh(messages.size()-1);
    }


    private void refresh(int from){
        ArrayList<Message> newMessages = conversation.getFreshMessages(from);
        for(Message mess: newMessages){
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

            if(mess.getAuthorID() == ((MyApplication) getApplicationContext()).getUserID()){
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
    }

    private void findViews(){
        this.sv = findViewById(R.id.scroll_view);
        this.linear_layout = findViewById(R.id.layout_conv);
        this.messageText =  findViewById(R.id.message_text);
    }
}
