package bg.tu_varna.sit.si.socket_client_messenger.services;

import bg.tu_varna.sit.si.models.User;
import bg.tu_varna.sit.si.requests.SocketRequests;
import bg.tu_varna.sit.si.socket_client_messenger.interfaces.IRequestResponseHandler;
import bg.tu_varna.sit.si.socket_client_messenger.requests.RequestHandler;

public class UserService {

    public boolean login(User user, IRequestResponseHandler responseHandler ) {

        RequestHandler requestHandler = new RequestHandler(SocketRequests.SocketRequestType.LOGIN, responseHandler);
        requestHandler.SendRequest(user);
        return true;
    }

    public void editProfile(User user, IRequestResponseHandler responseHandler) {

        RequestHandler requestHandler = new RequestHandler(SocketRequests.SocketRequestType.EDIT_PROFILE, responseHandler);
        requestHandler.SendRequest(user);
    }

    public boolean register(User user, IRequestResponseHandler responseHandler) {

        RequestHandler requestHandler = new RequestHandler(SocketRequests.SocketRequestType.REGISTER, responseHandler);
        requestHandler.SendRequest(user);
        return true;
    }
}
