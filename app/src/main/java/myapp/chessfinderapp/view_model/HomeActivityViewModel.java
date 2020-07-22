package myapp.chessfinderapp.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONObject;


import myapp.chessfinderapp.repository.API_Repository;
import myapp.chessfinderapp.repository.ChessUser;

public class HomeActivityViewModel extends ViewModel {
    private API_Repository api_repository;

    public HomeActivityViewModel() {
        api_repository = new API_Repository();
    }
	
	//Logs the current user out if they are logged in.
    public void logout(String email) {
		api_repository.logout(email);
    }
	
	public LiveData<JSONObject> getLogoutDetails() {
		return api_repository.getLogoutMessage();
	}

}


