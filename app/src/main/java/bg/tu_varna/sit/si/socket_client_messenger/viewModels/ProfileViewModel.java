package bg.tu_varna.sit.si.socket_client_messenger.viewModels;

import androidx.lifecycle.ViewModel;

import java.io.IOException;

import bg.tu_varna.sit.si.models.User;
import bg.tu_varna.sit.si.socket_client_messenger.interfaces.IRequestResponseHandler;
import bg.tu_varna.sit.si.socket_client_messenger.services.UserService;

public class ProfileViewModel extends ViewModel {

    private UserService userService;

    public ProfileViewModel(UserService userService) {
        this.userService = userService;
    }

    public void editProfile( User user, IRequestResponseHandler responseHandler ) throws IOException {
        userService.editProfile( user, responseHandler );
    }
}
