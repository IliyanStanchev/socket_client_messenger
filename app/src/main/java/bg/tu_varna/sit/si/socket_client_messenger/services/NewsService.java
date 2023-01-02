package bg.tu_varna.sit.si.socket_client_messenger.services;

import bg.tu_varna.sit.si.requests.SocketRequests;
import bg.tu_varna.sit.si.socket_client_messenger.interfaces.IRequestResponseHandler;
import bg.tu_varna.sit.si.socket_client_messenger.requests.RequestHandler;

public class NewsService {

    public void loadNews(IRequestResponseHandler responseHandler) {

        RequestHandler requestHandler = new RequestHandler(SocketRequests.SocketRequestType.GET_NEWS, responseHandler );
        requestHandler.SendRequest();
    }
}
