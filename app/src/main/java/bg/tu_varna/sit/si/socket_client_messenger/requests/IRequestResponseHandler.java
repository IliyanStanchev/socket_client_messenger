package bg.tu_varna.sit.si.socket_client_messenger.requests;

import bg.tu_varna.sit.si.requests.SocketRequests;

public interface IRequestResponseHandler {

    void onResponse(SocketRequests.SocketRequestType socketRequestType, Object response);
}
