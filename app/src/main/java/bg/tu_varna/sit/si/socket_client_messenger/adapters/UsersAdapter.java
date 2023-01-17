package bg.tu_varna.sit.si.socket_client_messenger.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import bg.tu_varna.sit.si.models.User;
import bg.tu_varna.sit.si.socket_client_messenger.R;
import bg.tu_varna.sit.si.socket_client_messenger.interfaces.IUserClickListener;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.MyViewHolder> {

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            final int position = getAdapterPosition();
            User user = users.get(position);
            userClickListener.onClick(user);
        }
    }

    private List<User> users = Collections.emptyList();
    private IUserClickListener userClickListener;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_users, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TextView tvUserEmail = holder.itemView.findViewById(R.id.tvUserEmail);
        tvUserEmail.setText(users.get(position).getEmail());

        TextView tvUserName = holder.itemView.findViewById(R.id.tvUserName);
        tvUserName.setText(users.get(position).getFirstName() + " " + users.get(position).getLastName());

        tvUserName.setTextSize(30);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setOnNewClickListener(IUserClickListener userClickListener) {
        this.userClickListener = userClickListener;
    }

    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }
}