package bg.tu_varna.sit.si.socket_client_messenger.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import bg.tu_varna.sit.si.models.Chat;
import bg.tu_varna.sit.si.models.User;
import bg.tu_varna.sit.si.socket_client_messenger.R;
import bg.tu_varna.sit.si.socket_client_messenger.adapters.ChatsAdapter;
import bg.tu_varna.sit.si.socket_client_messenger.factories.ChatViewModelFactory;
import bg.tu_varna.sit.si.socket_client_messenger.interfaces.IChatClickListener;
import bg.tu_varna.sit.si.socket_client_messenger.services.ChatService;
import bg.tu_varna.sit.si.socket_client_messenger.viewModels.ChatViewModel;

public class ChatsFragment extends Fragment implements IChatClickListener {

    private ChatViewModel chatViewModel;
    private ChatsAdapter chatsAdapter = new ChatsAdapter();

    private User user;

    public ChatsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chats, container, false);

        user = (User) getActivity().getIntent().getSerializableExtra("user");
        if( user == null ) {
            return view;
        }

        RecyclerView recyclerView = view.findViewById(R.id.rvChats);

        ChatService chatService = new ChatService();
        ChatViewModelFactory chatViewModelFactory = new ChatViewModelFactory(chatService);

        initRecyclerView(recyclerView);

        chatViewModel = new ViewModelProvider(this, chatViewModelFactory).get(ChatViewModel.class);
        chatViewModel.loadChats(user.getId());
        chatViewModel.chats.observe(getViewLifecycleOwner(), chats -> {
            chatsAdapter.setChats(chats);
            chatsAdapter.setOnNewClickListener(this);
        });

        return view;
    }

    private void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setAdapter(chatsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onClick(Chat chat) {

        getActivity().getIntent().putExtra("chat", chat);
        getActivity().getIntent().putExtra("openedFromChats", true);

        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.action_chatsFragment_to_chatFragment);
    }
}