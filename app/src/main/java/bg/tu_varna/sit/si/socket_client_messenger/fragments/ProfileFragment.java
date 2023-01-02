package bg.tu_varna.sit.si.socket_client_messenger.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.io.IOException;

import bg.tu_varna.sit.si.models.User;
import bg.tu_varna.sit.si.requests.SocketRequests;
import bg.tu_varna.sit.si.socket_client_messenger.R;
import bg.tu_varna.sit.si.socket_client_messenger.factories.ProfileViewModelFactory;
import bg.tu_varna.sit.si.socket_client_messenger.interfaces.IRequestResponseHandler;
import bg.tu_varna.sit.si.socket_client_messenger.services.UserService;
import bg.tu_varna.sit.si.socket_client_messenger.viewModels.ProfileViewModel;

public class ProfileFragment extends Fragment implements IRequestResponseHandler {

    private ProfileViewModel profileViewModel;
    private User user;
    private TextView tvError;

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        UserService userService = new UserService();
        ProfileViewModelFactory profileViewModelFactory = new ProfileViewModelFactory(userService);
        profileViewModel = new ViewModelProvider(this, profileViewModelFactory).get(ProfileViewModel.class);

        user = (User) getActivity().getIntent().getSerializableExtra("user");
        if( user == null ) {
            return root;
        }

        ImageView ivProfileImage = root.findViewById(R.id.ivAvatar);
        ivProfileImage.setImageResource(R.drawable.ic_account);
        TextView textView = root.findViewById(R.id.tvEmail);
        EditText etFirstName = root.findViewById(R.id.etFirstName);
        EditText etLastName = root.findViewById(R.id.etLastName);
        EditText etPhoneNumber = root.findViewById(R.id.etPhoneNumber);
        EditText etDescription = root.findViewById(R.id.etDescription);
        Button btnEdit = root.findViewById(R.id.btnEdit);

        tvError = root.findViewById(R.id.tvProfileError);

        textView.setText(user.getEmail());
        etFirstName.setText(user.getFirstName());
        etLastName.setText(user.getLastName());
        etPhoneNumber.setText(user.getPhoneNumber());
        etDescription.setText(user.getDescription());

        btnEdit.setOnClickListener(v -> {
            String firstName = etFirstName.getText().toString();
            String lastName = etLastName.getText().toString();
            String phoneNumber = etPhoneNumber.getText().toString();
            String description = etDescription.getText().toString();

            tvError.setVisibility(View.GONE);
            if( firstName.isEmpty() || lastName.isEmpty() || phoneNumber.isEmpty() || description.isEmpty() ) {
                tvError.setVisibility(View.VISIBLE);
                tvError.setText("All fields are required!");
                return;
            }

            try {
                handleEditProfile(firstName, lastName, phoneNumber, description);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return root;
    }

    private void handleEditProfile(String firstName, String lastName, String phoneNumber, String Description) throws IOException {

        if( user == null ) {
            return;
        }

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhoneNumber(phoneNumber);
        user.setDescription(Description);

        profileViewModel.editProfile(user, this );
    }

    @Override
    public void onResponse(SocketRequests.SocketRequestType socketRequestType, Object response) {
        if( socketRequestType == SocketRequests.SocketRequestType.EDIT_PROFILE ) {
            getActivity().runOnUiThread(() -> {
                tvError.setVisibility(View.VISIBLE);
                tvError.setText("Profile edited successfully!");
            });
        }
    }
}
