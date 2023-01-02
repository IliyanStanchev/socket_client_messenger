package bg.tu_varna.sit.si.socket_client_messenger.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import bg.tu_varna.sit.si.models.UserNotification;
import bg.tu_varna.sit.si.requests.SocketRequests;
import bg.tu_varna.sit.si.socket_client_messenger.requests.IRequestResponseHandler;
import bg.tu_varna.sit.si.socket_client_messenger.services.NewsService;

public class NewsViewModel extends ViewModel implements IRequestResponseHandler {

    private NewsService newsService;

    public MutableLiveData<List<UserNotification>> news = new MutableLiveData<>();

    public NewsViewModel(NewsService newsService) {
        this.newsService = newsService;
    }

    public void loadNews() {
        try {
            newsService.loadNews(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResponse(SocketRequests.SocketRequestType socketRequestType, Object response) {
        if( socketRequestType == SocketRequests.SocketRequestType.GET_NEWS ) {
            news.postValue((List<UserNotification>) response);
        }
    }
}
