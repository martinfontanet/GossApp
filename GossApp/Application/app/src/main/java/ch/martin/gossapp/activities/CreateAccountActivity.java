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

public class CreateAccountActivity extends AppCompatActivity {
    private TextView pseudoText;
    private TextView passwordText;
    private TextView errorText;
    private Intent intent;

    private final Handler handler = new Handler();

    private boolean connected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        ((MyApplication) getApplicationContext()).setCreatedAccount(0);
        findViews();
        errorText.setText("");
        autoRefresh();
    }

    private void autoRefresh() {
        handler.postDelayed(new Runnable() {
            @Override

            // Method to execute every 300 milliseconds
            public void run() {

                if(((MyApplication) getApplicationContext()).getCreatedAccount() == 1 && isConnected() && !connected) {
                    connected = true;
                    ((MyApplication) getApplicationContext()).setCreatedAccount(0);
                    startActivity(intent);
                    finish();
                }
                else if(((MyApplication) getApplicationContext()).getCreatedAccount() == -1){
                    errorText.setText("This pseudo already exists");
                    ((MyApplication) getApplicationContext()).setCreatedAccount(0);
                }
                else if(((MyApplication) getApplicationContext()).getCreatedAccount() == -2){
                    errorText.setText("Connection problem");
                    ((MyApplication) getApplicationContext()).setCreatedAccount(0);
                }

                autoRefresh();


            }
        }, 100);
    }


    public void create(View view) {
        // Do something in response to button
        String pseudo = pseudoText.getText().toString();
        String password = passwordText.getText().toString();

        if(password.length()>=4 && pseudo.length() >= 4) {
            System.out.println(pseudo + " " + password);
            ((MyApplication) getApplicationContext()).createAccount(new ConnectionRequest(pseudo, password));

            intent = new Intent(this, MainActivity.class);
        }
        else if(pseudo.length()<4 && password.length()<4){
            errorText.setText("Pseudo and Password too short");
        }
        else if(password.length()<4){
            errorText.setText("Password too short");
        }
        else if(pseudo.length()<4){
            errorText.setText("Pseudo too short");
        }

    }

    public boolean isConnected(){
        return((MyApplication) getApplicationContext()).getUser()!=null;
    }

    private void findViews(){
        this.pseudoText = findViewById(R.id.create_pseudo_edit);
        this.passwordText = findViewById(R.id.create_password_edit);
        this.errorText = findViewById(R.id.error_edit);
    }
}
