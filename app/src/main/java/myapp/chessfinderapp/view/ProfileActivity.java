package myapp.chessfinderapp.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import myapp.chessfinderapp.R;
import myapp.chessfinderapp.repository.ChessData;
import myapp.chessfinderapp.repository.ChessUser;
import myapp.chessfinderapp.view_model.ProfileActivityViewModel;

public class ProfileActivity extends AppCompatActivity implements LifecycleOwner{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temp_activity_profile);
		final ProfileActivityViewModel profileActivityViewModel = 
		ViewModelProviders.of(this).get(ProfileActivityViewModel.class);
		
		//TODO Implement the observation of live data
		
        //Intent intent = getIntent();
        //ChessUser chessUser = intent.getParcelableExtra("ChessUser");
		
		/*
		String strChessUser = "";
		Bundle extra = getIntent().getExtras();
		if(extra != null) {
			strChessUser = extra.getString("ChessUser");
		}
		ChessUser chessUser = new Gson().fromJson(strChessUser, ChessUser.class);*/
		
		//Get the object which is in string form...
		String target = getIntent().getStringExtra("ChessUser");
		//...and convert it back to a java object.
		// When there is composition, using Gson to convert objects stored as strings works well.
		ChessUser chessUser = new Gson().fromJson(target, ChessUser.class);
		
		Toast.makeText(this, "Welcome " + chessUser.getEmail() + "!", Toast.LENGTH_LONG).show();
        TextView textViewTempData = findViewById(R.id.txtChessRating);
        TextView textViewChessWins = findViewById(R.id.txtWins);
        TextView textViewChessLoses = findViewById(R.id.txtLoses);

		//There is another object in the object, (composition) so using Gson is appropriate
		//Using parcelable does not seem to work, hence I am using a Gson conversion.
		ChessData chessData = new Gson().fromJson(chessUser.get_chessDataObjectAsString(), ChessData.class);
		textViewTempData.setText("Elo Rating: " + chessData.getiEloRating());
		textViewChessWins.setText("Wins: " + chessData.getWins());
		textViewChessLoses.setText("Loses: " + chessData.getLoses());
		//textViewTempData.setText(chessUser.get_chessDataObjectAsString());


        //Setup the xml file and assign it to a variable
		LayoutInflater layoutInflater = LayoutInflater.from(ProfileActivity.this);
        final View customView = layoutInflater.inflate(R.layout.custom_edit_profile_options, null);
        //Using the custom view, find the components like edit texts, button, etc

		//Get the edit text view components 
		final EditText edt_EloRating = customView.findViewById(R.id.editTextEloRating);
		final EditText edt_Wins = customView.findViewById(R.id.editTextWins);
		final EditText edt_Loses = customView.findViewById(R.id.editTextLoses);
		final Button btnUpdateProfile = findViewById(R.id.btnUpdateProfile);
		
		edt_EloRating.setText(chessData.getiEloRating());
		edt_Wins.setText(chessData.getWins());
		edt_Loses.setText(chessData.getLoses());
		final String email = chessUser.getEmail();

		profileActivityViewModel.returnServerResponse().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(ProfileActivity.this, s, Toast.LENGTH_LONG).show();					
			}   
        });
		
		btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view){

        //Create custom dialog and set it to the customView variable
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Edit Profile Information");
        builder.setView(customView);
		
	

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Get the data from the components here
                //Pass the data using the MVVM architecture
				String newEloRating = edt_EloRating.getText().toString();
				String newWins = edt_Wins.getText().toString();
				String newLoses = edt_Loses.getText().toString(); 
				profileActivityViewModel.updateProfileDetails(newEloRating, newWins, newLoses, email);
		
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
				
			}
		});
		    }
}
