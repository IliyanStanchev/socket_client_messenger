package bg.tu_varna.sit.si.socket_client_messenger.viewModels;

import androidx.lifecycle.ViewModel;

import java.io.IOException;

import bg.tu_varna.sit.si.models.Chat;
import bg.tu_varna.sit.si.models.ChatMessage;
import bg.tu_varna.sit.si.socket_client_messenger.interfaces.IRequestResponseHandler;
import bg.tu_varna.sit.si.socket_client_messenger.services.ChatService;

public class ChatViewModel extends ViewModel {

    private ChatService chatService;

    public ChatViewModel(ChatService chatService) {
        this.chatService = chatService;
    }

    public void sendMessage(ChatMessage message, IRequestResponseHandler responseHandler) throws IOException {
        chatService.sendMessage(message, responseHandler);
    }

    public void loadMessages(Chat currentChat, IRequestResponseHandler responseHandler) throws IOException {
        chatService.loadMessages(currentChat, responseHandler);
    }
}
