package myapp.chessfinderapp.view;
import org.json.JSONObject;
import com.google.gson.Gson;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import myapp.chessfinderapp.R;
import myapp.chessfinderapp.repository.ChessUser;
import myapp.chessfinderapp.view_model.HomeActivityViewModel;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LifecycleOwner {
			
	private HomeActivityViewModel homeActivityViewModel;		
	private SharedPreferences sp;
	private ChessUser chessUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //We want to get the chess user class / object that is passed from the login activity
        //After a successful login, the server sends a chess user Json object which is automatically handled
        //By the Gson Converter and POJO generators in Android.
        //We can then use this object by passing through a Bundle (by making the class implement parcelable)
        Intent i = getIntent();
        chessUser = i.getParcelableExtra("ChessUser");
        Toast.makeText(this, "Welcome " + chessUser.getUserName(), Toast.LENGTH_LONG);

        setContentView(R.layout.activity_home2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null);
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
		
		homeActivityViewModel = ViewModelProviders.of(this).get(HomeActivityViewModel.class);
		sp = getSharedPreferences("Chess_User", Context.MODE_PRIVATE);
		
		homeActivityViewModel.getLogoutDetails().observe(this, new Observer<JSONObject>() {
            @Override
                public void onChanged(JSONObject jo) {
					Toast.makeText(HomeActivity.this, jo.toString(), Toast.LENGTH_LONG).show();
					Intent i = new Intent(HomeActivity.this, LoginActivity.class);
					startActivity(i);
					finish();
                }
        });
			
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
			Toast.makeText(this, "Say cheese!", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_gallery) {
			Toast.makeText(this, "Wanna see some pics?", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_slideshow) {
			Toast.makeText(this, "Sit back and relax", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_tools) {
			Toast.makeText(this, "Where is my toolbox?", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_share) {
			Toast.makeText(this, "Sharing is caring?", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_send) {
			Toast.makeText(this, "Send this somewhere, somehow...", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_logout) {
            
            //Logout the user
			homeActivityViewModel.logout(sp.getString("EMAIL", null));
			Toast.makeText(this, "Email is : " + sp.getString("EMAIL", null), Toast.LENGTH_LONG).show();
			
        } else if (id == R.id.nav_viewProfile) {
            //Start the profile activity
            Intent i = new Intent(HomeActivity.this, ProfileActivity.class);
            //i.putExtra("ChessUser", chessUser);
			//Converts the java object into a string
            i.putExtra("ChessUser", new Gson().toJson(chessUser));
			startActivity(i);

        }
	

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
