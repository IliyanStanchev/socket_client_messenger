package bg.tu_varna.sit.si.socket_client_messenger.factories;

import androidx.lifecycle.ViewModelProvider;

import bg.tu_varna.sit.si.socket_client_messenger.services.ChatService;
import bg.tu_varna.sit.si.socket_client_messenger.viewModels.ChatViewModel;

public class ChatViewModelFactory  implements ViewModelProvider.Factory{

    private ChatService chatService;

    public ChatViewModelFactory(ChatService chatService) {
        this.chatService = chatService;
    }

    @Override
    public <T extends androidx.lifecycle.ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ChatViewModel.class)) {
            return (T) new ChatViewModel(chatService);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
