package myapp.chessfinderapp.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import myapp.chessfinderapp.repository.API_Repository;

public class ProfileActivityViewModel extends ViewModel {
    private API_Repository api_repository;

    public ProfileActivityViewModel() {
        api_repository = new API_Repository();
    }

    public void updateProfileDetails(String EloRating, String Wins, String Loses, String email) {
        api_repository.updateProfileDetails(EloRating, Wins, Loses, email);
    }

    /*
    public LiveData<String> returnServerResponse() {

    }*/
}
