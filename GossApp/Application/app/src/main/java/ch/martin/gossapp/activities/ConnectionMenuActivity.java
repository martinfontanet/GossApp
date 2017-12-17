package ch.martin.gossapp.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import ch.martin.gossapp.MyApplication;
import ch.martin.gossapp.R;
import ch.martin.gossapp.classes.ConnectionRequest;

public class ConnectionMenuActivity extends AppCompatActivity {
    private TextView pseudoText;
    private TextView passwordText;


    private final Handler handler = new Handler();
    private Intent intent;
    private boolean connected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_menu);
        findViews();
        autoRefresh();
        intent = null;
    }

//TODO CHANGE THAT
    private void autoRefresh() {
        handler.postDelayed(new Runnable() {
            @Override

            // Method to execute every 300 milliseconds
            public void run() {

                if(isConnected() && !connected){
                    connected = true;
                    if(intent != null) {
                        startActivity(intent);
                        finish();
                    }
                }

                autoRefresh();


            }
        }, 100);
    }


    public void connect(View view) {
        // Do something in response to button
        connected = false;
        String pseudo = pseudoText.getText().toString();
        String password = passwordText.getText().toString();
        System.out.println(pseudo+" "+password);
        ((MyApplication) getApplicationContext()).connectToAccount(new ConnectionRequest(pseudo, password));

        //while(!isConnected()){
        //}
        System.out.println("aa");
        intent = new Intent(this, MainActivity.class);
    }



    private void findViews(){
        this.pseudoText = findViewById(R.id.pseudo_edit);
        this.passwordText = findViewById(R.id.password_edit);
    }

    public boolean isConnected(){
        return((MyApplication) getApplicationContext()).getUser()!=null && ((MyApplication) getApplicationContext()).getUser().getID()!=0;
    }

    public void newAccount(View view){
        Intent new_intent = new Intent(this,CreateAccountActivity.class);
        startActivity(new_intent);
    }


}
