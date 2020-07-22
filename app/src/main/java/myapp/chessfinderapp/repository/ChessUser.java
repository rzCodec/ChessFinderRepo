package myapp.chessfinderapp.repository;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class ChessUser implements Parcelable {

    @SerializedName("user_email")
    private String email;

    private String password;

    @SerializedName("username")
    private String userName;

    @SerializedName("chess_data")
    private ChessData chessData;

    @SerializedName("user_location_x_coordinates")
    private String x_coordinate;

    @SerializedName("user_location_y_coordinates")
    private String y_coordinate;
	
	private String chessDataObjectAsString;

	public void set_chessDataObjectAsString(String str) {
		this.chessDataObjectAsString = str;
	}
	
	public String get_chessDataObjectAsString() {
		return chessDataObjectAsString;
	}

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

	
    public ChessData getChessData() {
        return chessData;
    }
	
	public void setChessData(ChessData chessData) {
		this.chessData = chessData;
	}

    public String getY_coordinate() {
        return y_coordinate;
    }

    public void setY_coordinate(String y_coordinate) {
        this.y_coordinate = y_coordinate;
    }

    public String getX_coordinate() {
        return x_coordinate;
    }

    public void setX_coordinate(String x_coordinate) {
        this.x_coordinate = x_coordinate;
    }

    public ChessUser(String email,
                     String password,
                     String userName,
                     ChessData chessData,
					 String x_coordinate,
					 String y_coordinate) {

        this.email = email;
        this.password = password;
        this.userName = userName;
        this.chessData = chessData;
		this.x_coordinate = x_coordinate;
		this.y_coordinate = y_coordinate;
    }



    //Below are the methods which MUST be implemented when a class implements
    //The parcelable interface
    //===============================
	
	/*
    protected ChessUser(Parcel parcel) {
        this.email = parcel.readString();
        this.userName = parcel.readString();
        this.password = parcel.readString();
        this.chessData = (ChessData) parcel.readValue(ChessData.class.getClassLoader());
        this.x_coordinate = parcel.readString();
        this.y_coordinate = parcel.readString();
    }*/
	
	//https://www.codexpedia.com/android/using-android-parcelable-objects/
	
	
   

	
	private ChessUser(Parcel parcel) {
		this.email = parcel.readString();
		this.userName = parcel.readString();
		this.password = parcel.readString();
		//this.chessData = ((ChessData) parcel.readValue((ChessData.class.getClassLoader())));
		//this.chessData = parcel.readParcelable(ChessData.class.getClassLoader());
		this.x_coordinate = parcel.readString();
		this.y_coordinate = parcel.readString();
		this.chessDataObjectAsString = parcel.readString();
	}
	
	 @Override
    public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(this.email);
		parcel.writeString(this.userName);
		parcel.writeString(this.password);
		parcel.writeString(this.x_coordinate);
		parcel.writeString(this.y_coordinate);
		parcel.writeString(this.chessDataObjectAsString);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<ChessUser> CREATOR = new Parcelable.Creator<ChessUser>() {
        public ChessUser createFromParcel(Parcel in) {
            return new ChessUser(in); //Refer to the private Subject Constructor
        }

        public ChessUser[] newArray(int size) {
            return new ChessUser[size];
        }
    };
}