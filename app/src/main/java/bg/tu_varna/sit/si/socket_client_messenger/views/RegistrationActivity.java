package bg.tu_varna.sit.si.socket_client_messenger.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import bg.tu_varna.sit.si.models.User;
import bg.tu_varna.sit.si.requests.ResponseCodes;
import bg.tu_varna.sit.si.requests.SocketRequests;
import bg.tu_varna.sit.si.socket_client_messenger.R;
import bg.tu_varna.sit.si.socket_client_messenger.factories.UserViewModelFactory;
import bg.tu_varna.sit.si.socket_client_messenger.notifiers.ToastNotifier;
import bg.tu_varna.sit.si.socket_client_messenger.interfaces.IRequestResponseHandler;
import bg.tu_varna.sit.si.socket_client_messenger.services.UserService;
import bg.tu_varna.sit.si.socket_client_messenger.viewModels.UserViewModel;

public class RegistrationActivity extends AppCompatActivity implements IRequestResponseHandler {

    private EditText etEmail;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private EditText etFirstName;
    private EditText etLastName;
    private EditText ePhoneNumber;

    private TextView tvMessage;
    private Button btnBackToLogin;
    private Button btnRegister;

    private UserViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etPassword = findViewById(R.id.etRegistrationPassword);
        etConfirmPassword = findViewById(R.id.etRegistrationConfirmPassword);
        etEmail = findViewById(R.id.etRegistrationEmail);
        etFirstName = findViewById(R.id.etRegistrationFirstName);
        etLastName = findViewById(R.id.etRegistrationLastName);
        ePhoneNumber = findViewById(R.id.etRegistrationPhoneNumber);

        tvMessage = findViewById(R.id.tvRegistrationMessage);
        btnBackToLogin = findViewById(R.id.btnBackToLogin);
        btnRegister = findViewById(R.id.btnRegister);

        UserService userService = new UserService();
        UserViewModelFactory loginViewModelFactory = new UserViewModelFactory(userService);
        loginViewModel = new ViewModelProvider( this, loginViewModelFactory).get(UserViewModel.class);

        btnBackToLogin.setOnClickListener(v -> {
            try {
                backToLogin();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btnRegister.setOnClickListener(v -> {
            try {
                register();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void register() throws IOException {

        if( !validateControls() )
            return;

        User user = new User(etEmail.getText().toString(), etPassword.getText().toString());
        user.setFirstName(etFirstName.getText().toString());
        user.setLastName(etLastName.getText().toString());
        user.setPhoneNumber(ePhoneNumber.getText().toString());

        if( !loginViewModel.register( user, this) )
        {
            tvMessage.setText("Registration failed!");
            return;
        }
    }

    private boolean validateControls() {

        if( etEmail.getText().toString().isEmpty() ) {
            etEmail.setError("Email is required!");
            return false;
        }

        if( etPassword.getText().toString().isEmpty() ) {
            etPassword.setError("Password is required!");
            return false;
        }

        if( etConfirmPassword.getText().toString().isEmpty() ) {
            etConfirmPassword.setError("Confirm password is required!");
            return false;
        }

        if( etEmail.getText().toString().isEmpty() ) {
            etEmail.setError("Email is required!");
            return false;
        }

        if( etFirstName.getText().toString().isEmpty() ) {
            etFirstName.setError("First name is required!");
            return false;
        }

        if( etLastName.getText().toString().isEmpty() ) {
            etLastName.setError("Last name is required!");
            return false;
        }

        if( ePhoneNumber.getText().toString().isEmpty() ) {
            ePhoneNumber.setError("Phone number is required!");
            return false;
        }

        if( !etPassword.getText().toString().equals(etConfirmPassword.getText().toString()) ) {
            etPassword.setError("Password and confirm password must be the same!");
            return false;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches())
        {
            etEmail.setError("Email is not valid!");
            return false;
        }

        if( !etPassword.getText().toString().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$") ) {
            etPassword.setError("Password must contain at least 8 characters, at least one digit, one lower case letter, one upper case letter and one special character!");
            return false;
        }

        if( !ePhoneNumber.getText().toString().matches("[0-9]+") ) {
            ePhoneNumber.setError("Phone number must contain only digits!");
            return false;
        }

        return true;
    }

    private void backToLogin() throws IOException {

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResponse(SocketRequests.SocketRequestType socketRequestType, Object response) {
        if (socketRequestType.equals(SocketRequests.SocketRequestType.REGISTER)) {
            runOnUiThread(() -> {
                handleRegistration((ResponseCodes.ResponseCodeTypes) response);
            });
        }
    }

    private void handleRegistration(ResponseCodes.ResponseCodeTypes response) {

        if (response == null) {
            tvMessage.setText("Registration failed!");
            return;
        }

        if( !response.equals(ResponseCodes.ResponseCodeTypes.SUCCESS) ) {
            tvMessage.setText(response.description);
            return;
        }

        ToastNotifier.notifyUser(this, "Registration successful!");
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }, 1000);
    }
}
