package myapp.chessfinderapp.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONObject;

import myapp.chessfinderapp.repository.API_Repository;

public class RegistrationViewModel extends ViewModel {
    private API_Repository api_repository;

    public RegistrationViewModel() {
        api_repository = new API_Repository();
    }

    public void register(String email, String username, String password) {
        api_repository.register(email, username, password);
    }

    public LiveData<JSONObject> returnSuccessfulRegistration() {
        return api_repository.getRegistrationObject();
    }
}
