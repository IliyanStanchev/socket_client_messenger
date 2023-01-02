package bg.tu_varna.sit.si.socket_client_messenger.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bg.tu_varna.sit.si.models.ChatMessage;
import bg.tu_varna.sit.si.models.User;
import bg.tu_varna.sit.si.socket_client_messenger.R;


public class ChatAdapter extends BaseAdapter {

    private class ChatMessageViewHolder {

        public View avatar;
        public TextView name;
        public TextView content;
    }

    List<ChatMessage> messages = new ArrayList<ChatMessage>();
    User currentUser;
    Context context;

    public ChatAdapter(Context context, User currentUser) {
        this.context = context;
        this.currentUser = currentUser;
    }

    public void add(ChatMessage message) {
        this.messages.add(message);
        notifyDataSetChanged();
    }

    public void setMessages(List<ChatMessage> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int i) {
        return messages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ChatMessageViewHolder holder = new ChatMessageViewHolder();
        LayoutInflater messageInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        ChatMessage message = messages.get(i);

        if ( message.getSender().getId() == currentUser.getId()) {
            convertView = messageInflater.inflate(R.layout.my_message, null);
            holder.content = (TextView) convertView.findViewById(R.id.message_body);
            convertView.setTag(holder);
            holder.content.setText(message.getContent());
            return convertView;
        }

        convertView = messageInflater.inflate(R.layout.their_message, null);
        holder.avatar = (View) convertView.findViewById(R.id.avatar);
        holder.name = (TextView) convertView.findViewById(R.id.name);
        holder.content = (TextView) convertView.findViewById(R.id.message_body);
        convertView.setTag(holder);
        holder.name.setText(message.getSender().getFirstName());
        holder.content.setText(message.getContent());

        return convertView;
    }

}

