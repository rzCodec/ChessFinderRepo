package myapp.chessfinderapp.repository;

import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.*;


public interface iApiCalls {
    //Send the username and password via hashmap
    //Returns a chess user details in a JsonObject 
    @POST("/login")
	@Headers({"Accept: application/json", "Content-Type: application/json"
    })
    Call<ChessUser> login(@Body HashMap<String, String> map);
	
	@POST("/logout")
	@Headers({"Accept: application/json", "Content-Type: application/json"
    })
	Call<JSONObject> logout(@Body HashMap<String, String> map);
	
	@POST("/register")
	@Headers({"Accept: application/json", "Content-Type: application/json"
    })
    Call<JSONObject> register(@Body HashMap<String, String> map);

	@POST("/update_profile")
    @Headers({"Accept: application/json", "Content-Type: application/json"
    })
    Call<String> update_profile(@Body HashMap<String, String> map);

}
