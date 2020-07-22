package myapp.chessfinderapp.view;

import android.content.Context;
import static android.content.Context.MODE_PRIVATE;
import android.preference.PreferenceManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import myapp.chessfinderapp.R;
import myapp.chessfinderapp.repository.ChessUser;
import myapp.chessfinderapp.repository.ChessData;
import myapp.chessfinderapp.view_model.LoginViewModel;

public class LoginActivity extends AppCompatActivity implements LifecycleOwner {

    private static final String USERNAME = "USERNAME";
    private static final String EMAIL = "EMAIL";
	private static final String PASSWORD = "PASSWORD";
    public static final String IS_LOGGED_IN = "IS_LOGGED_IN";


    private String email = "";
    private String password = "";
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //User shared preferences to store user data
        final EditText editText_Email = findViewById(R.id.editText_loginEmail);
        final EditText editText_Password = findViewById(R.id.editText_loginPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnSignUp = findViewById(R.id.btnSignUp);
        final SharedPreferences sharedPref = this.getSharedPreferences("Chess_User", MODE_PRIVATE);
		final SharedPreferences.Editor editor = sharedPref.edit();
		//final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final LoginViewModel loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
	

		//When the current user logs out, clear the contents of shared pref file and set is logged in to false
		//There might be multiple users who use the phone and we want to always have the shared pref file only
		//storing the details of one user, not multiple users
		
		/* Test code
		  "user_email":"regf@gmail.com",
	       "username":"jack",
	       "password":"pass"
		*/

        //if(sharedPref.getBoolean(IS_LOGGED_IN, false) == false){
            //User must login
            //After user logs in set it to true
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    email = editText_Email.getText().toString();
                    password = editText_Password.getText().toString();
                    //Pass the 2 values as arguments to view model
                    loginViewModel.login(email, password);
					//enable progress bar here
				
                    //startHomeActivity(new ChessUser(email, password,
                            //"tempName", "{Some object: bleh}"));
                }
            });

            btnSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Start registration screen activity
                    Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
                    startActivity(intent);
                }
            });

            //The onChanged method is invoked whenever the login method of view model is executed.
            //The chess user object is observable via live data
            //Whenever the object is changed, it automatically updates the UI
            loginViewModel.returnSuccessfulLogin_Details().observe(this, new Observer<ChessUser>() {
                @Override
                public void onChanged(ChessUser chessUser) {
                    //After user has logged in successfully,
                    //remember the user has logged in by storing their EMAIL and Password via shared preferences
					//chessUser != null
					//an alternative method
					//chessUser.getEmail() != null
					String email = chessUser.getEmail();
					
					if( (email != null) && (!email.isEmpty()) ){
						editor.putString(EMAIL, chessUser.getEmail());
						editor.putString(PASSWORD, chessUser.getPassword());
						editor.putBoolean(IS_LOGGED_IN, true);
						editor.apply();
						//Pass chess user to intent to start home activity after successful login
						//Toast.makeText(LoginActivity.this, "Email is : " + chessUser.getEmail(), Toast.LENGTH_LONG).show();
					    //Toast.makeText(LoginActivity.this, chessUser.getChessData().getiEloRating(), Toast.LENGTH_LONG).show(); 
						Toast.makeText(LoginActivity.this, "Successful Login!", Toast.LENGTH_LONG).show();
						//Disable progress bar here
						//Toast.makeText(LoginActivity.this, "Email is " + sharedPref.getString(EMAIL, ""), Toast.LENGTH_LONG).show();
						startHomeActivity(chessUser);
					}
					else {
						//Display toast - incorrect credentials, please try again.
						//Reset edit text fields
                        Toast.makeText(LoginActivity.this, "Incorrect login details.", Toast.LENGTH_LONG).show();
					}
                }
            });
        //} else {			//if the user is already logged in, get their details from shared pref 
            //Start home activity
            //startHomeActivity(chessUser);		
			//Toast.makeText(LoginActivity.this, "Logging in with shared pref data...", Toast.LENGTH_LONG).show();
			//loginViewModel.login(sharedPref.getString(EMAIL, null), sharedPref.getString(PASSWORD, null));
        //}

        //Get user data
        //Check if they are logged in, if they are
        //Display the home screen
        //Else check if the user exists
        //If not redirect to register screen, otherwise go to login
	}	

    public void startHomeActivity(ChessUser chessUser) {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        intent.putExtra("ChessUser", chessUser);
        startActivity(intent);
		finish();
    }
	


}//end of class
