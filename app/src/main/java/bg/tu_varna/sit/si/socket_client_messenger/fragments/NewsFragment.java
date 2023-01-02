package bg.tu_varna.sit.si.socket_client_messenger.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import bg.tu_varna.sit.si.socket_client_messenger.R;
import bg.tu_varna.sit.si.socket_client_messenger.adapters.NewsAdapter;
import bg.tu_varna.sit.si.socket_client_messenger.factories.NewsViewModelFactory;
import bg.tu_varna.sit.si.socket_client_messenger.interfaces.INewClickListener;
import bg.tu_varna.sit.si.socket_client_messenger.interfaces.INewRefreshListener;
import bg.tu_varna.sit.si.socket_client_messenger.services.NewsService;
import bg.tu_varna.sit.si.socket_client_messenger.tasks.NewsRefresher;
import bg.tu_varna.sit.si.socket_client_messenger.viewModels.NewsViewModel;
import bg.tu_varna.sit.si.socket_client_messenger.viewModels.SharedNewViewModel;

public class NewsFragment extends Fragment implements INewClickListener, INewRefreshListener {

    private NewsViewModel newsViewModel;
    private NewsAdapter newsAdapter = new NewsAdapter();

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rvNews);

        NewsService newsService = new NewsService();
        NewsViewModelFactory newsViewModelFactory = new NewsViewModelFactory(newsService);

        initRecyclerView(recyclerView);

        newsViewModel = new ViewModelProvider(this, newsViewModelFactory).get(NewsViewModel.class);
        newsViewModel.loadNews();
        newsViewModel.news.observe(getViewLifecycleOwner(), news -> {
            newsAdapter.setNews(news);
            newsAdapter.setOnNewClickListener(this);
        });

        Thread thread = new Thread(new NewsRefresher(this));
        thread.start();

        return view;
    }

    private void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setAdapter(newsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onClick(String title, String content) {

        SharedNewViewModel sharedNewViewModel = new ViewModelProvider(requireActivity()).get(SharedNewViewModel.class);
        sharedNewViewModel.title.setValue(title);
        sharedNewViewModel.content.setValue(content);

        Navigation.findNavController(requireView()).navigate(R.id.actionNavToFullNews);
    }

    @Override
    public void onRefresh() {

        if( newsViewModel == null || newsAdapter == null ) {
            return;
        }

        newsViewModel.loadNews();
        getActivity().runOnUiThread(() -> newsAdapter.notifyDataSetChanged());
    }
}
