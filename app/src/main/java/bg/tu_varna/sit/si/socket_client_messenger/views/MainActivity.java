package bg.tu_varna.sit.si.socket_client_messenger.views;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ActivityKt;
import androidx.navigation.NavController;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import bg.tu_varna.sit.si.socket_client_messenger.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigationView = (BottomNavigationView)this.findViewById(R.id.bottomNavigation);
        NavController navController = ActivityKt.findNavController(this, R.id.fragmentController);

        NavigationUI.setupWithNavController((NavigationBarView)navigationView, navController);
    }
}