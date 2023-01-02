package bg.tu_varna.sit.si.socket_client_messenger.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import bg.tu_varna.sit.si.socket_client_messenger.R;
import bg.tu_varna.sit.si.socket_client_messenger.viewModels.SharedNewViewModel;

public class NewFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new, container, false);

        TextView tvTitle = view.findViewById(R.id.tvNotificationTitle);
        TextView tvContent = view.findViewById(R.id.tvNotificationContent);
        Button btnBack = view.findViewById(R.id.btnGoBack);

        tvTitle.setTextSize(50);
        tvContent.setTextSize(30);

        btnBack.setText("Go back");
        btnBack.setTextColor(getResources().getColor(R.color.white));

        btnBack.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_newFragment_to_newsFragment);
        });

        SharedNewViewModel sharedNewViewModel = new ViewModelProvider(requireActivity()).get(SharedNewViewModel.class);

        tvTitle.setText(sharedNewViewModel.title.getValue());
        tvContent.setText(sharedNewViewModel.content.getValue());

        return view;
    }
}
