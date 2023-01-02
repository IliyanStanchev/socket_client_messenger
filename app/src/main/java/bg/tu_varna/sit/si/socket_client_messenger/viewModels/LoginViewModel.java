package bg.tu_varna.sit.si.socket_client_messenger.viewModels;

import androidx.lifecycle.ViewModel;

import java.io.IOException;

import bg.tu_varna.sit.si.models.User;
import bg.tu_varna.sit.si.socket_client_messenger.requests.IRequestResponseHandler;
import bg.tu_varna.sit.si.socket_client_messenger.services.UserService;


public class LoginViewModel extends ViewModel {

    private UserService userService;

    public LoginViewModel(UserService userService) {
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
}
