package bg.tu_varna.sit.si.socket_client_messenger.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import bg.tu_varna.sit.si.requestModels.ChatViewData;
import bg.tu_varna.sit.si.socket_client_messenger.R;
import bg.tu_varna.sit.si.socket_client_messenger.interfaces.IChatClickListener;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.MyViewHolder> {

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            final int position = getAdapterPosition();
            ChatViewData chat = chats.get(position);
            chatClickListener.onClick(chat.getChat());
        }
    }

    private List<ChatViewData> chats = Collections.emptyList();
    private IChatClickListener chatClickListener;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_chats, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TextView tvChatName = holder.itemView.findViewById(R.id.tvChatName);
        TextView tvLastMessage = holder.itemView.findViewById(R.id.tvLastMessage);
        tvChatName.setText(chats.get(position).getChat().getName());
        tvLastMessage.setText(chats.get(position).getLastMessageData());
        tvChatName.setTextSize(30);
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public void setOnNewClickListener(IChatClickListener chatClickListener) {
        this.chatClickListener = chatClickListener;
    }

    public void setChats(List<ChatViewData> chats) {
        this.chats = chats;
        notifyDataSetChanged();
    }
}