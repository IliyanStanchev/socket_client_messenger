package bg.tu_varna.sit.si.socket_client_messenger.factories;

import androidx.lifecycle.ViewModelProvider;

import bg.tu_varna.sit.si.socket_client_messenger.services.NewsService;
import bg.tu_varna.sit.si.socket_client_messenger.viewModels.NewsViewModel;

public class NewsViewModelFactory implements ViewModelProvider.Factory {

    private NewsService newsService;

    public NewsViewModelFactory(NewsService newsService) {
        this.newsService = newsService;
    }

    @Override
    public <T extends androidx.lifecycle.ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(NewsViewModel.class)) {
            return (T) new NewsViewModel(newsService);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
