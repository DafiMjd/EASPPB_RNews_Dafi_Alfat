package id.prodigy.rnews;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.Objects;

import id.prodigy.rnews.models.Article;

public class ArticleViewModel extends ViewModel {
    private MutableLiveData<List<Article>> articles;

    public MutableLiveData<List<Article>> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles.setValue(articles);
    }
}
