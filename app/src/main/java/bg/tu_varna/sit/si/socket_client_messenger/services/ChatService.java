package bg.tu_varna.sit.si.socket_client_messenger.services;

import java.io.IOException;

import bg.tu_varna.sit.si.requests.SocketRequests;
import bg.tu_varna.sit.si.socket_client_messenger.requests.IRequestResponseHandler;
import bg.tu_varna.sit.si.socket_client_messenger.requests.RequestHandler;

public class ChatService {

    public boolean sendMessage(String message, IRequestResponseHandler responseHandler) throws IOException {

        RequestHandler requestHandler = new RequestHandler(SocketRequests.SocketRequestType.SEND_MESSAGE, responseHandler);
        requestHandler.SendRequest(message);
        return true;
    }
}
