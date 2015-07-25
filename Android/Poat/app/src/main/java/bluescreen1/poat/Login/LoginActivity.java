package bluescreen1.poat.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.ArrayList;

import bluescreen1.poat.MainActivity;
import bluescreen1.poat.R;

public class LoginActivity extends FragmentActivity implements bluescreen1.poat.Login.Sign_Up_Fragment.SignUpDialogListener, bluescreen1.poat.Login.Sign_In_Fragment.LoginDialogListener {

    ImageView login_image;
    private static int logo_state = 0;
    Button login, signup;
    Intent intent;
    private String username = "ERROR";
    private String password = "ERROR";
    private String su_username = "ERROR";
    private String su_password = "ERROR";
    private String su_email = "ERROR";
    private String su_ieeenum = "ERROR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            // do stuff with the user
            intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_login);
        login = (Button) findViewById(R.id.login_button);
        signup = (Button) findViewById(R.id.sign_up_button);
        login_image = (ImageView) findViewById(R.id.logo_image);

        final DialogFragment dialog = new Sign_In_Fragment();
        final DialogFragment sign_up_dialog = new Sign_Up_Fragment();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show(getSupportFragmentManager(),"SIGNIN");
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sign_up_dialog.show(getSupportFragmentManager(),"Sign Up");
            }
        });
        intent = new Intent(LoginActivity.this, MainActivity.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        // Remove the activity when its off the screen
        finish();
    }

    @Override
    public void onLoginDialogPositiveClick(DialogFragment dialog) {
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Fail", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onLoginDialogNegativeClick(DialogFragment dialog) {
        Toast.makeText(this, "Sign In Cancel", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSignUpDialogPositiveClick(DialogFragment dialog) {
        ParseUser user = new ParseUser();
        user.setUsername(su_username);
        user.setPassword(su_password);
        user.setEmail(su_email);
// other fields can be set just like with ParseObject
        user.put("ieeenum", su_ieeenum);
        ArrayList<String> groups = new ArrayList<String>();
        groups.add("General");
        user.put("Groups",groups);
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(LoginActivity.this, "You are now registered", Toast.LENGTH_LONG).show();
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                }
            }
        });
    }

    @Override
    public void onSignUpDialogNegativeClick(DialogFragment dialog) {
        Toast.makeText(this, "Sign In Cancel", Toast.LENGTH_LONG).show();
    }

    public void setData(String username, String Password){
        this.username= username;
        this.password = Password;
    }

    public void signUp(String username, String password, String ieeenum, String email){
        su_email = email;
        su_ieeenum = ieeenum;
        su_username = username;
        su_password = password;
    }
}