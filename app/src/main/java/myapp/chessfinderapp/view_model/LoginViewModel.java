package myapp.chessfinderapp.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import myapp.chessfinderapp.repository.API_Repository;
import myapp.chessfinderapp.repository.ChessUser;

public class LoginViewModel extends ViewModel {
    private API_Repository api_repository;

    public LoginViewModel() {
        api_repository = new API_Repository();
    }

    public void login(String email, String password) {
        api_repository.login(email, password);
    }

    //User live data to decouple the UI logic from the business logic
    public LiveData<ChessUser> returnSuccessfulLogin_Details() {
        return api_repository.getLiveChessuser();
    }
	
}


