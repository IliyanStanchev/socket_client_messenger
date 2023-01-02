package bg.tu_varna.sit.si.socket_client_messenger.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import bg.tu_varna.sit.si.models.UserNotification;
import bg.tu_varna.sit.si.socket_client_messenger.R;
import bg.tu_varna.sit.si.socket_client_messenger.interfaces.INewClickListener;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
           final int position = getAdapterPosition();
           String title = news.get(position).getTitle();
           String content = news.get(position).getContent();
           onNewClickListener.onClick(title, content);
        }
    }

    private List<UserNotification> news = Collections.emptyList();
    private INewClickListener onNewClickListener;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_news, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TextView tvText = holder.itemView.findViewById(R.id.tvTitle);
        tvText.setText(news.get(position).getTitle());

        TextView tvContent = holder.itemView.findViewById(R.id.tvContent);

        if (news.get(position).getContent().length() > 50) {
            tvContent.setText(news.get(position).getContent().substring(0, 50) + "...");
        } else {
            tvContent.setText(news.get(position).getContent());
        }
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public void setOnNewClickListener(INewClickListener onNewClickListener) {
        this.onNewClickListener = onNewClickListener;
    }

    public void setNews(List<UserNotification> news) {
        this.news = news;
        notifyDataSetChanged();
    }
}
