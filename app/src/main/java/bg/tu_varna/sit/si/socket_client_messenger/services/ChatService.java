package bg.tu_varna.sit.si.socket_client_messenger.services;

import bg.tu_varna.sit.si.models.Chat;
import bg.tu_varna.sit.si.models.ChatMessage;
import bg.tu_varna.sit.si.requests.SocketRequests;
import bg.tu_varna.sit.si.socket_client_messenger.interfaces.IRequestResponseHandler;
import bg.tu_varna.sit.si.socket_client_messenger.requests.RequestHandler;

public class ChatService {

    public boolean sendMessage(ChatMessage message, IRequestResponseHandler responseHandler) {

        RequestHandler requestHandler = new RequestHandler(SocketRequests.SocketRequestType.SEND_MESSAGE, responseHandler);
        requestHandler.SendRequest(message);
        return true;
    }

    public void loadMessages(Chat currentChat, IRequestResponseHandler responseHandler) {
        RequestHandler requestHandler = new RequestHandler(SocketRequests.SocketRequestType.GET_MESSAGES, responseHandler);
        requestHandler.SendRequest(currentChat);
    }
}
