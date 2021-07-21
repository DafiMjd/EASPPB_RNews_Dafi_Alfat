package id.prodigy.rnews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import id.prodigy.rnews.models.Article;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private List<Article> articles;
    private Context context;
    private OnItemClickListener onItemClickListener;


    public NewsAdapter(List<Article> articles, Context context) {
        this.articles = articles;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holders, int position) {
        final NewsViewHolder holder = holders;
        Article model = articles.get(position);
        String title = Utils.getTitle(model.getTitle());
//        String publisher = Utils.getPublisher(model.getTitle());
        String publisher = model.getSource().getName();

        // Dikarenakan thread kelamaan nge render data array string, maka dilakukan pengecekan
        if (title == null || title =="") {
            holder.title.setText(model.getTitle());
        } else {
            holder.title.setText(title);
        }


        if (!(publisher == model.getTitle())) {
            holder.publisher.setText(publisher);
        }
        // Batas pengecekan dan assignment

        holder.publishedAt.setText(Utils.DateFormat(model.getPublishedAt()));

        if (Utils.isNoImageAvailable(model.getUrlToImage())) {
            holder.newsImage.setImageResource(R.drawable.ic_baseline_image_not_available);
        } else {
            Glide.with(context).load(model.getUrlToImage()).into(holder.newsImage);
        }


    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, publisher, publishedAt;
        ImageView newsImage;
        OnItemClickListener onItemClickListener;

        public NewsViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = itemView.findViewById(R.id.tv_title);
            publisher = itemView.findViewById(R.id.tv_publisher);
            publishedAt = itemView.findViewById(R.id.tv_publish_date);
            newsImage = itemView.findViewById(R.id.iv_image);
            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public void onClick(View v) {
             onItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }
}
