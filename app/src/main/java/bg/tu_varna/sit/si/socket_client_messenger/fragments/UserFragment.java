package bg.tu_varna.sit.si.socket_client_messenger.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import bg.tu_varna.sit.si.models.Chat;
import bg.tu_varna.sit.si.models.User;
import bg.tu_varna.sit.si.requests.SocketRequests;
import bg.tu_varna.sit.si.socket_client_messenger.R;
import bg.tu_varna.sit.si.socket_client_messenger.interfaces.IRequestResponseHandler;
import bg.tu_varna.sit.si.socket_client_messenger.services.ChatService;


public class UserFragment extends Fragment implements IRequestResponseHandler {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        User selectedUser = (User) getActivity().getIntent().getSerializableExtra("selectedUser");
        if( selectedUser == null ) {
            return view;
        }

        User currentUser = (User) getActivity().getIntent().getSerializableExtra("user");
        if( currentUser == null ) {
            return view;
        }

        TextView tvUserName = view.findViewById(R.id.tvFragmentUserName);
        TextView tvEmail = view.findViewById(R.id.tvFragmentUserEmail);
        ImageView avatar = view.findViewById(R.id.ivUserAvatar);
        avatar.setImageResource(R.drawable.ic_account);
        Button btnBack = view.findViewById(R.id.btnGoBack);
        Button btnOpenChat = view.findViewById(R.id.btnOpenChat);

        tvUserName.setText(selectedUser.getFirstName() + " " + selectedUser.getLastName());
        tvEmail.setText(selectedUser.getEmail());

        tvUserName.setTextSize(30);
        tvEmail.setTextSize(20);

        btnBack.setText("Go back");
        btnBack.setTextColor(getResources().getColor(R.color.white));

        btnOpenChat.setText("Open chat");
        btnOpenChat.setTextColor(getResources().getColor(R.color.white));

        btnBack.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_userFragment_to_usersFragment);
        });

        btnOpenChat.setOnClickListener(v -> {

            ChatService chatService = new ChatService();
            chatService.searchChat(currentUser, selectedUser, this);

        });

        return view;
    }

    @Override
    public void onResponse(SocketRequests.SocketRequestType socketRequestType, Object response) {
        if( socketRequestType == SocketRequests.SocketRequestType.SEARCH_CHAT ) {
            if( response != null ) {

                getActivity().runOnUiThread(() -> {
                    Chat chat = (Chat) response;
                    getActivity().getIntent().putExtra("chat", chat);
                    getActivity().getIntent().putExtra("openedFromChats", false);

                    NavController navController = NavHostFragment.findNavController(this);
                    navController.navigate(R.id.action_userFragment_to_chatFragment);
                });
            }
        }
    }
}