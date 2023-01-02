package bg.tu_varna.sit.si.socket_client_messenger.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.io.IOException;
import java.util.List;

import bg.tu_varna.sit.si.models.Chat;
import bg.tu_varna.sit.si.models.ChatMessage;
import bg.tu_varna.sit.si.models.User;
import bg.tu_varna.sit.si.requests.SocketRequests;
import bg.tu_varna.sit.si.socket_client_messenger.R;
import bg.tu_varna.sit.si.socket_client_messenger.adapters.ChatAdapter;
import bg.tu_varna.sit.si.socket_client_messenger.factories.ChatViewModelFactory;
import bg.tu_varna.sit.si.socket_client_messenger.interfaces.IRequestResponseHandler;
import bg.tu_varna.sit.si.socket_client_messenger.services.ChatService;
import bg.tu_varna.sit.si.socket_client_messenger.viewModels.ChatViewModel;

public class ChatFragment extends Fragment implements IRequestResponseHandler {

    private ChatViewModel chatViewModel;

    private EditText editText;
    private AppCompatImageButton btnSendMessage;
    private ChatAdapter messageAdapter;
    private ListView messagesView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_chat, container, false);
        editText = root.findViewById(R.id.editText);

        User currentUser = (User) getActivity().getIntent().getSerializableExtra("user");
        Chat currentChat = (Chat) getActivity().getIntent().getSerializableExtra("chat");

        messageAdapter = new ChatAdapter(getContext(), currentUser);
        messagesView = (ListView) root.findViewById(R.id.messages_view);
        messagesView.setAdapter(messageAdapter);

        ChatService chatService = new ChatService();
        ChatViewModelFactory chatViewModelFactory = new ChatViewModelFactory(chatService);
        chatViewModel = new ViewModelProvider( this, chatViewModelFactory).get(ChatViewModel.class);

        try {
            chatViewModel.loadMessages(currentChat, this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        btnSendMessage = root.findViewById(R.id.btnSendMessage);

        btnSendMessage.setOnClickListener(v -> {
            String message = editText.getText().toString();
            if (message.length() <= 0) {
                return;
            }

            editText.getText().clear();
            try {
                ChatMessage chatMessage = new ChatMessage(currentUser, currentChat, message);
                messageAdapter.add( chatMessage);
                messagesView.setSelection(messagesView.getCount() - 1);
                chatViewModel.sendMessage(chatMessage, this);
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
                ChatMessage message = (ChatMessage) response;
                if( message == null ){
                    return;
                }

                messageAdapter.add(message);
                messagesView.setSelection(messagesView.getCount() - 1);
            });
        }

        if (socketRequestType == SocketRequests.SocketRequestType.GET_MESSAGES) {

            getActivity().runOnUiThread(() -> {
                List<ChatMessage> messages = (List<ChatMessage>) response;
                if( messages == null ){
                    return;
                }

                messageAdapter.setMessages(messages);
                messagesView.setSelection(messagesView.getCount() - 1);
            });
        }
    }
}
