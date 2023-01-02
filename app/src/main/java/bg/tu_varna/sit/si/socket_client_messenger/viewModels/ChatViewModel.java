package bg.tu_varna.sit.si.socket_client_messenger.viewModels;

import androidx.lifecycle.ViewModel;

import java.io.IOException;

import bg.tu_varna.sit.si.socket_client_messenger.requests.IRequestResponseHandler;
import bg.tu_varna.sit.si.socket_client_messenger.services.ChatService;

public class ChatViewModel extends ViewModel {

    private ChatService chatService;

    public ChatViewModel(ChatService chatService) {
        this.chatService = chatService;
    }

    public void sendMessage(String message, IRequestResponseHandler responseHandler) throws IOException {
        chatService.sendMessage(message, responseHandler);
    }
}
