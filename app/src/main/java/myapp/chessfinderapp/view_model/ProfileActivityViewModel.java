package myapp.chessfinderapp.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import myapp.chessfinderapp.repository.*;

import myapp.chessfinderapp.repository.API_Repository;

public class ProfileActivityViewModel extends ViewModel {
    private API_Repository api_repository;

    public ProfileActivityViewModel() {
        api_repository = new API_Repository();
    }

    public void updateProfileDetails(String EloRating, String Wins, String Loses, String email, String draws,
	   String profileDesc) {
        api_repository.updateProfileDetails(EloRating, Wins, Loses, email, draws, profileDesc);
		api_repository.getChessUser(email);
    }
	
	public void getChessUserData(String email){
		api_repository.getChessUser(email);
	}
	
	public LiveData<ChessUser> returnChessUserData() {
		return api_repository.getLiveChessuser();
	}
	

    public LiveData<String> returnServerResponse() {
		return api_repository.getUpdateProfileResponse();
    }
}
