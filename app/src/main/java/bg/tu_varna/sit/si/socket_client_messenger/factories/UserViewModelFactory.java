package bg.tu_varna.sit.si.socket_client_messenger.factories;

import androidx.lifecycle.ViewModelProvider;

import bg.tu_varna.sit.si.socket_client_messenger.services.UserService;
import bg.tu_varna.sit.si.socket_client_messenger.viewModels.UserViewModel;

public class UserViewModelFactory implements ViewModelProvider.Factory {

    private UserService userService;

    public UserViewModelFactory(UserService userService) {
        this.userService = userService;
    }

    @Override
    public <T extends androidx.lifecycle.ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UserViewModel.class)) {
            return (T) new UserViewModel(userService);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
