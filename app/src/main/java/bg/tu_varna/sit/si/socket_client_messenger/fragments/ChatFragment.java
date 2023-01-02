package bg.tu_varna.sit.si.socket_client_messenger.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.io.IOException;

import bg.tu_varna.sit.si.requests.SocketRequests;
import bg.tu_varna.sit.si.socket_client_messenger.R;
import bg.tu_varna.sit.si.socket_client_messenger.factories.ChatViewModelFactory;
import bg.tu_varna.sit.si.socket_client_messenger.requests.IRequestResponseHandler;
import bg.tu_varna.sit.si.socket_client_messenger.services.ChatService;
import bg.tu_varna.sit.si.socket_client_messenger.viewModels.ChatViewModel;


public class ChatFragment extends Fragment implements IRequestResponseHandler {

    private ChatViewModel chatViewModel;
    private TextView tvChatMessage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_chat, container, false);
        tvChatMessage = root.findViewById(R.id.tvChatMessage);

        TextView tvError = root.findViewById(R.id.tvProfileError);
        EditText etMessage = root.findViewById(R.id.etMessage);
        Button btnSend = root.findViewById(R.id.btnEdit);

        ChatService chatService = new ChatService();
        ChatViewModelFactory chatViewModelFactory = new ChatViewModelFactory(chatService);
        chatViewModel = new ViewModelProvider( this, chatViewModelFactory).get(ChatViewModel.class);

        btnSend.setOnClickListener(v -> {
            String message = etMessage.getText().toString();

            tvError.setVisibility(View.GONE);
            if (message.isEmpty()) {
                tvError.setVisibility(View.VISIBLE);
                tvError.setText("Message is empty!");
                return;
            }
            try {
                chatViewModel.sendMessage(message, this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return root;
    }

    @Override
    public void onResponse(SocketRequests.SocketRequestType socketRequestType, Object response) {

        if (socketRequestType == SocketRequests.SocketRequestType.SEND_MESSAGE) {

            getActivity().runOnUiThread(() -> {
                tvChatMessage.setText(response.toString());
            });
        }
    }
}
