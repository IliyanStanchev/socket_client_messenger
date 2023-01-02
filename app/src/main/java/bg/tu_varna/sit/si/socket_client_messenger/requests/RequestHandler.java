package bg.tu_varna.sit.si.socket_client_messenger.requests;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;

import bg.tu_varna.sit.si.requests.SocketRequests;

public class RequestHandler {

    private String clientId;
    private SocketRequests.SocketRequestType requestType;
    private IRequestResponseHandler responseHandler;

    private Socket socket;
    private ObjectOutputStream  output;
    private ObjectInputStream  input;

    public RequestHandler( SocketRequests.SocketRequestType requestType, IRequestResponseHandler responseHandler) {

        clientId = String.valueOf(UUID.randomUUID());
        this.requestType = requestType;
        this.responseHandler = responseHandler;
    }

    public void SendRequest( Object request) throws IOException {

        RequestSender requestSender = new RequestSender(requestType, request);
        Thread thread = new Thread(requestSender);
        thread.start();
    }

    public void SendRequest() throws IOException {

        RequestSender requestSender = new RequestSender(requestType);
        Thread thread = new Thread(requestSender);
        thread.start();
    }

    class ResponseListener implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    final Object socketRequestType = input.readObject();
                    if (socketRequestType == null || !socketRequestType.equals(requestType))
                        continue;

                    final Object clientId = input.readObject();
                    if( clientId == null || !clientId.equals(RequestHandler.this.clientId))
                        continue;

                    final Object response = input.readObject();
                    responseHandler.onResponse(requestType, response);
                    break;

                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }

    class RequestSender implements Runnable {

        private SocketRequests.SocketRequestType requestType;
        private Object request;

        public RequestSender(SocketRequests.SocketRequestType requestType, Object request) {
            this.requestType = requestType;
            this.request = request;
        }

        public RequestSender(SocketRequests.SocketRequestType requestType) {
            this.requestType = requestType;
        }

        @Override
        public void run() {

            try {
                socket = new Socket("10.0.2.2", 8080);
                output = new ObjectOutputStream(socket.getOutputStream());
                input = new ObjectInputStream(socket.getInputStream());
                new Thread(new ResponseListener()).start();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if( output != null) {
                try {
                    output.writeObject(requestType);
                    output.writeObject(clientId);
                    if( request != null) {
                        output.writeObject(request);
                    }
                    output.flush();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
