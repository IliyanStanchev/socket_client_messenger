package bg.tu_varna.sit.si.socket_client_messenger.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.IOException;
import java.util.List;

import bg.tu_varna.sit.si.models.User;
import bg.tu_varna.sit.si.requests.SocketRequests;
import bg.tu_varna.sit.si.socket_client_messenger.interfaces.IRequestResponseHandler;
import bg.tu_varna.sit.si.socket_client_messenger.services.UserService;


public class UserViewModel extends ViewModel implements IRequestResponseHandler{

    private UserService userService;

    public MutableLiveData<List<User>> users = new MutableLiveData<>();

    public UserViewModel(UserService userService) {
        this.userService = userService;
    }

    public boolean login(User user, IRequestResponseHandler responseHandler ) throws IOException {

        if( !userService.login(user, responseHandler) ) {
            return false;
        }

        return true;
    }

    public boolean register(User user, IRequestResponseHandler responseHandler) throws IOException {

        if( !userService.register(user, responseHandler) ) {
            return false;
        }

        return true;
    }

    public void loadUsers(int userId) {

        try {
            userService.loadUsers(userId,this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResponse(SocketRequests.SocketRequestType socketRequestType, Object response) {

        if( socketRequestType == SocketRequests.SocketRequestType.GET_USERS ) {
            users.postValue((List<User>) response);
        }
    }
}
