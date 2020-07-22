package myapp.chessfinderapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import org.json.JSONObject;

import myapp.chessfinderapp.R;
import myapp.chessfinderapp.view_model.RegistrationViewModel;

//Whenever we create an activity include this import and replace the v7





public class RegistrationActivity extends AppCompatActivity implements LifecycleOwner {

    private EditText edtRegisterEmail;
    private EditText edtRegisterUsername;
    private EditText edtRegisterPassword;
    private Button btnRegister;
    private RegistrationViewModel registrationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edtRegisterEmail = findViewById(R.id.editText_registerEmail);
        edtRegisterUsername = findViewById(R.id.editText_registerUsername);
        btnRegister = findViewById(R.id.btnRegister);

        registrationViewModel = ViewModelProviders.of(this).get(RegistrationViewModel.class);
        
		btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrationViewModel.register(edtRegisterEmail.getText().toString(),
                edtRegisterUsername.getText().toString(),
                edtRegisterPassword.getText().toString());
            }
        });

		//Error is here
        registrationViewModel.returnSuccessfulRegistration().observe(this, new Observer<JSONObject>() {
            @Override
            public void onChanged(JSONObject jsonObject) {
                //Display the json object message
                if(jsonObject != null) {
                    try {
                        Toast.makeText(RegistrationActivity.this, jsonObject.getString("status"), Toast.LENGTH_LONG);
                        //After telling the user a successful registration, redirect to the login screen
                        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                    catch (Exception e) {
                    }
                }else {
                    //Unsuccessful registration
                    Toast.makeText(RegistrationActivity.this, "Could not register. Please try again.", Toast.LENGTH_LONG);
                }
            }
        });
    }
}
