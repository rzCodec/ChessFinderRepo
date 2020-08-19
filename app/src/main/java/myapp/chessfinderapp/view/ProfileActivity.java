package myapp.chessfinderapp.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;
import android.content.Intent;
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
		//String target = getIntent().getStringExtra("ChessUser");
		//...and convert it back to a java object.
		// When there is composition, using Gson to convert objects stored as strings works well.
		//ChessUser chessUser = new Gson().fromJson(target, ChessUser.class);
		
		//Toast.makeText(this, "Welcome " + chessUser.getEmail() + "!", Toast.LENGTH_LONG).show();
        
		final TextView textViewTempData = findViewById(R.id.txtChessRating);
        final TextView textViewChessWins = findViewById(R.id.txtWins);
        final TextView textViewChessLoses = findViewById(R.id.txtLoses);
		final TextView textViewChessDraws = findViewById(R.id.txtDraws);
		final TextView textViewChessProfileDescription = findViewById(R.id.txtProfileDescription);

		//There is another object in the object, (composition) so using Gson is appropriate
		//Using parcelable does not seem to work, hence I am using a Gson conversion.
		
		//ChessData chessData = new Gson().fromJson(chessUser.get_chessDataObjectAsString(), ChessData.class);
		
		/*
		textViewChessProfileDescription.setText("Profile Description " + chessData.getProfileDescription());
		textViewTempData.setText("Elo Rating: " + chessData.getiEloRating());
		textViewChessWins.setText("Wins: " + chessData.getWins());
		textViewChessLoses.setText("Loses: " + chessData.getLoses());
		textViewChessDraws.setText("Draws: " + chessData.getDraws());
		//textViewTempData.setText(chessUser.get_chessDataObjectAsString());
		*/
		
			Intent i = getIntent();
		final String email = i.getStringExtra("Email");
		
		profileActivityViewModel.returnChessUserData().observe(this, new Observer<ChessUser>() {
            @Override
            public void onChanged(ChessUser chessUser) {
                ChessData chessData = chessUser.getChessData();
				textViewChessProfileDescription.setText("Profile Description " + chessData.getProfileDescription());
				textViewTempData.setText("Elo Rating: " + chessData.getiEloRating());
				textViewChessWins.setText("Wins: " + chessData.getWins());
				textViewChessLoses.setText("Loses: " + chessData.getLoses());
				textViewChessDraws.setText("Draws: " + chessData.getDraws());
				//disable progress bar here
				
        //Setup the xml file and assign it to a variable
		LayoutInflater layoutInflater = LayoutInflater.from(ProfileActivity.this);
        final View customView = layoutInflater.inflate(R.layout.custom_edit_profile_options, null);
        //Using the custom view, find the components like edit texts, button, etc

		//Get the edit text view components 
		final EditText edt_ProfileDesc = customView.findViewById(R.id.editTextProfileDescription);
		final EditText edt_EloRating = customView.findViewById(R.id.editTextEloRating);
		final EditText edt_Wins = customView.findViewById(R.id.editTextWins);
		final EditText edt_Loses = customView.findViewById(R.id.editTextLoses);
		final EditText edt_Draws = customView.findViewById(R.id.editTextDraws);
		final Button btnUpdateProfile = findViewById(R.id.btnUpdateProfile);
		
		edt_EloRating.setText(chessData.getiEloRating());
		edt_Wins.setText(chessData.getWins());
		edt_Loses.setText(chessData.getLoses());
		edt_Draws.setText(chessData.getDraws());
		edt_ProfileDesc.setText(chessData.getProfileDescription());
		//final String email = chessUser.getEmail();

		/*
		profileActivityViewModel.returnServerResponse().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(ProfileActivity.this, s, Toast.LENGTH_LONG).show();					
			}   
        });*/
		
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
				String draws = edt_Draws.getText().toString();
				String profileDesc = edt_ProfileDesc.getText().toString();
				profileActivityViewModel.updateProfileDetails(newEloRating, newWins, newLoses, email, draws, profileDesc);
                //enable progress bar here
			}
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
				
			AlertDialog alertDialog = builder.create();
            alertDialog.show();
			
			}
		});
			}   
        });
		
	
		profileActivityViewModel.getChessUserData(email);
		

    }//end of onCreate
}//end of class
