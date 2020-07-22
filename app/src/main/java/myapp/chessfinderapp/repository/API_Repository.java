package myapp.chessfinderapp.repository;

import androidx.lifecycle.MutableLiveData;
import com.google.gson.Gson;
import org.json.JSONObject;
import java.util.HashMap;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class API_Repository {
	private iApiCalls api;
    private MutableLiveData<ChessUser> live_chess_user;
	private MutableLiveData<JSONObject> sLogoutMessage;
    private MutableLiveData<JSONObject> registrationObject;
    private MutableLiveData<String> updateProfileResponse;

    private Call<ChessUser> loginCall;
    private Call<JSONObject> logoutCall; 
	private Call<JSONObject> registerCall;
	private Call<String> updateProfileCall;
    
    private static final String USER_EMAIL = "user_email";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    public API_Repository() {
        api = RetrofitClient.getRetrofitInstance().create(iApiCalls.class);
		//ALWAYS Initialize live data
        live_chess_user = new MutableLiveData<>();
		sLogoutMessage = new MutableLiveData<>();
		registrationObject = new MutableLiveData<>();
		updateProfileResponse = new MutableLiveData<>();
        //registerCall = api.register();
        
       // mRequestCall = mRestful_API.get_article_content();
       // articles = new MutableLiveData<>();
    }

    /**
     * @param email
     * @param password
     * @return a chess user object which is then passed to the activity
     */
    public void login(final String email, final String password) {
       //Make hashmap and put the username and password
	   //We use hashmap because the RESTful API is expecting {"email":"temp_activity_profile@gmail", "password": "123"}
        HashMap<String, String> loginMap = new HashMap<>();
        loginMap.put(USER_EMAIL, email);
        loginMap.put(PASSWORD, password);
        loginCall = api.login(loginMap);

        //Make the network call and return a chess user object
        //Use the clone method to make multiple login calls if necessary within a single activity instance
        //If clone is NOT used, then we can only invoke the loginCall method once per activity instance
        loginCall.clone().enqueue(new Callback<ChessUser>() {
            @Override
            public void onResponse(Call<ChessUser> call, Response<ChessUser> response) {
				ChessData chessData = response.body().getChessData();
				Gson gson = new Gson();
				String str_chessData = gson.toJson(chessData);
				//chessData.setiEloRating("Some text");
				
				ChessUser user = response.body();
				//user.setChessData(chessData);
				user.set_chessDataObjectAsString(str_chessData);
                live_chess_user.setValue(user);
            }

            @Override
            public void onFailure(Call<ChessUser> call, Throwable t) {
				live_chess_user.setValue(null);
				//We have internet so we don't need the below code but we'll keep it for now
                //Send a new chess user as temp_activity_profile data
                //There's no internet so let's simulate network data
                //Send back the data
                /*live_chess_user.setValue(new ChessUser(email, password,
                        "TempUsername", "{TempJSON_ObjectAsString: Some message}"));*/
            }
        });
    }
	
	public void logout(String userEmail) {
		HashMap<String, String> map = new HashMap<>();
		map.put(USER_EMAIL, userEmail);
		logoutCall = api.logout(map);
		
		logoutCall.clone().enqueue(new Callback<JSONObject>() {
			@Override
			public void onResponse(Call<JSONObject> call, Response<JSONObject> response){
				try {
					sLogoutMessage.setValue(response.body());					
				}
				catch(Exception ex) {
					
				}
				
			}
			
			@Override
			public void onFailure(Call<JSONObject> call, Throwable t) {
				sLogoutMessage.setValue(null);
			}
		});
	}

    /**
     * Register a new user and return a message via json object
     * @param email
     * @param username
     * @param password
     */
    public void register(String email, String username, String password) {
        HashMap<String, String> map = new HashMap<>();
        map.put(USER_EMAIL, email);
        map.put(USERNAME, username);
        map.put(PASSWORD, password);
        registerCall = api.register(map);

        //Make the registration call
        registerCall.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                registrationObject.setValue(response.body());
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                registrationObject.setValue(null);
                /*
                try {
                    registrationObject.setValue(new JSONObject().put("status", "Unsuccessful registration."));
                }
                catch (Exception e) {

                }*/
            }
        });
    }

    public void updateProfileDetails(String EloRating, String Wins, String Loses, String email) {
        HashMap<String, String> map = new HashMap<>();
        map.put("ELO_RATING", EloRating);
        map.put("WINS", Wins);
        map.put("LOSES", Loses);
		map.put(USER_EMAIL, email);

        updateProfileCall = api.update_profile(map);

        updateProfileCall.clone().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                updateProfileResponse.setValue(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    public MutableLiveData<ChessUser> getLiveChessuser() {
        return live_chess_user;
    }
    public MutableLiveData<JSONObject> getRegistrationObject() { return registrationObject; }
	
	public MutableLiveData<JSONObject> getLogoutMessage() { return sLogoutMessage; }

    public MutableLiveData<String> getUpdateProfileResponse() {
        return updateProfileResponse;
    }
}