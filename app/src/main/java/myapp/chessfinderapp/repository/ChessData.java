package myapp.chessfinderapp.repository;

import com.google.gson.annotations.SerializedName;
import android.os.Parcel;
import android.os.Parcelable;


public class ChessData implements Parcelable {

    @SerializedName("chess_rating")
    private String iEloRating;

    @SerializedName("wins")
    private String wins;

    @SerializedName("loses")
    private String loses;
	
	@SerializedName("draws")
	private String draws;
	
	@SerializedName("profile_description")
	private String profile_description;

    public ChessData(String iEloRating, String wins, String loses, String draws, String profile_description) {
        this.iEloRating = iEloRating;
        this.wins = wins;
        this.loses = loses;
		this.draws = draws;
		this.profile_description = profile_description;
    }

    public String getiEloRating() {
        return iEloRating;
    }

    public void setiEloRating(String iEloRating) {
        this.iEloRating = iEloRating;
    }

    public String getWins() {
        return wins;
    }

    public void setWins(String wins) {
        this.wins = wins;
    }

    public String getLoses() {
        return loses;
    }

    public void setLoses(String loses) {
        this.loses = loses;
    }
	
	public void setDraws(String draws) {
		this.draws = draws;
	}
	
	public String getDraws() {
		return this.draws;
	}
	
	public void setProfileDescription(String desc) {
		this.profile_description = desc;
	}
	
	public String getProfileDescription() {
		return this.profile_description;
	}
	
	//==========
	
	protected ChessData(Parcel parcel) {
		this.iEloRating = parcel.readString();
		this.wins = parcel.readString();
		this.loses = parcel.readString();
	}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(iEloRating);
		parcel.writeString(wins);
		parcel.writeString(loses);
    }

    public static final Parcelable.Creator<ChessData> CREATOR = new Parcelable.Creator<ChessData>() {
        public ChessData createFromParcel(Parcel in) {
            return new ChessData(in); //Refer to the private Subject Constructor
        }

        public ChessData[] newArray(int size) {
            return new ChessData[size];
        }
    };
	
}
