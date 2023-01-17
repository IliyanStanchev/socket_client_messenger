package bg.tu_varna.sit.si.socket_client_messenger.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.io.IOException;

import bg.tu_varna.sit.si.models.User;
import bg.tu_varna.sit.si.requests.SocketRequests;
import bg.tu_varna.sit.si.socket_client_messenger.R;
import bg.tu_varna.sit.si.socket_client_messenger.factories.UserViewModelFactory;
import bg.tu_varna.sit.si.socket_client_messenger.interfaces.IRequestResponseHandler;
import bg.tu_varna.sit.si.socket_client_messenger.services.UserService;
import bg.tu_varna.sit.si.socket_client_messenger.viewModels.UserViewModel;

public class LoginActivity extends AppCompatActivity implements IRequestResponseHandler {

    private EditText etEmail;
    private EditText etPassword;

    private TextView tvMessage;
    private Button btnSignIn;
    private Button btnRegister;

    private UserViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        tvMessage = findViewById(R.id.tvMessage);
        btnSignIn = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnMakeRegistration);

        UserService userService = new UserService();
        UserViewModelFactory loginViewModelFactory = new UserViewModelFactory(userService);
        loginViewModel = new ViewModelProvider( this, loginViewModelFactory).get(UserViewModel.class);
        
        btnSignIn.setOnClickListener(v -> {
            try {
                login();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        
        btnRegister.setOnClickListener(v -> {
            register();
        });
    }

    private void register() {

        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    private void login() throws IOException {

        loginViewModel.login( new User(etEmail.getText().toString(), etPassword.getText().toString()), this);
        //loginViewModel.login( new User("ench3r@gmail.com", "sach2@Password"), this);
    }

    @Override
    public void onResponse(SocketRequests.SocketRequestType socketRequestType, Object response) {
        if (socketRequestType.equals(SocketRequests.SocketRequestType.LOGIN)) {
            runOnUiThread(() -> {
                handleLogin((User) response);
            });
        }
    }

    private void handleLogin(User response) {

        if (response == null) {
            tvMessage.setText("Invalid credentials!");
            return;
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("user", response);

        startActivity(intent);
    }
}