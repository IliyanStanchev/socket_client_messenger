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

import bg.tu_varna.sit.si.models.User;
import bg.tu_varna.sit.si.socket_client_messenger.R;
import bg.tu_varna.sit.si.socket_client_messenger.adapters.UsersAdapter;
import bg.tu_varna.sit.si.socket_client_messenger.factories.UserViewModelFactory;
import bg.tu_varna.sit.si.socket_client_messenger.interfaces.IUserClickListener;
import bg.tu_varna.sit.si.socket_client_messenger.services.UserService;
import bg.tu_varna.sit.si.socket_client_messenger.viewModels.UserViewModel;

public class UsersFragment extends Fragment implements IUserClickListener {

    private UserViewModel userViewModel;
    private UsersAdapter usersAdapter = new UsersAdapter();

    private User user;

    public UsersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        user = (User) getActivity().getIntent().getSerializableExtra("user");
        if( user == null ) {
            return view;
        }

        RecyclerView recyclerView = view.findViewById(R.id.rvUsers);

        UserService userService = new UserService();
        UserViewModelFactory userViewModelFactory = new UserViewModelFactory(userService);

        initRecyclerView(recyclerView);

        userViewModel = new ViewModelProvider(this, userViewModelFactory).get(UserViewModel.class);
        userViewModel.loadUsers(user.getId());
        userViewModel.users.observe(getViewLifecycleOwner(), users -> {
            usersAdapter.setUsers(users);
            usersAdapter.setOnNewClickListener(this);
        });

        return view;
    }

    private void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setAdapter(usersAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onClick(User user) {

        getActivity().getIntent().putExtra("selectedUser", user);
        Navigation.findNavController(requireView()).navigate(R.id.action_usersFragment_to_userFragment);
    }
}