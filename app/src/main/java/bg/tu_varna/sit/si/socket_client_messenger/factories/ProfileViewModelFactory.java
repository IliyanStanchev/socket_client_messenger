package bg.tu_varna.sit.si.socket_client_messenger.factories;

import androidx.lifecycle.ViewModelProvider;

import bg.tu_varna.sit.si.socket_client_messenger.services.UserService;
import bg.tu_varna.sit.si.socket_client_messenger.viewModels.ProfileViewModel;

public class ProfileViewModelFactory implements ViewModelProvider.Factory {

    private UserService userService;

    public ProfileViewModelFactory(UserService userService) {
        this.userService = userService;
    }

    @Override
    public <T extends androidx.lifecycle.ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ProfileViewModel.class)) {
            return (T) new ProfileViewModel(userService);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

