package bg.tu_varna.sit.si.socket_client_messenger.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.IOException;
import java.util.List;

import bg.tu_varna.sit.si.models.Chat;
import bg.tu_varna.sit.si.models.ChatMessage;
import bg.tu_varna.sit.si.requestModels.ChatViewData;
import bg.tu_varna.sit.si.requests.SocketRequests;
import bg.tu_varna.sit.si.socket_client_messenger.interfaces.IRequestResponseHandler;
import bg.tu_varna.sit.si.socket_client_messenger.services.ChatService;

public class ChatViewModel extends ViewModel implements IRequestResponseHandler {

    private ChatService chatService;

    public MutableLiveData<List<ChatViewData>> chats = new MutableLiveData<>();

    public ChatViewModel(ChatService chatService) {
        this.chatService = chatService;
    }

    public void sendMessage(ChatMessage message, IRequestResponseHandler responseHandler) throws IOException {
        chatService.sendMessage(message, responseHandler);
    }

    public void loadMessages(Chat currentChat, IRequestResponseHandler responseHandler) throws IOException {
        chatService.loadMessages(currentChat, responseHandler);
    }

    public void loadChats(int userId)
    {
        try {
            chatService.loadChats(userId,this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResponse(SocketRequests.SocketRequestType socketRequestType, Object response) {

        if(socketRequestType == SocketRequests.SocketRequestType.GET_CHATS) {
            chats.postValue((List<ChatViewData>) response);
        }
    }
}
