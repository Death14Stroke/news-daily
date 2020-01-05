package com.andruid.magic.newsdaily.headlines;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.andruid.magic.newsloader.api.NewsRepository;
import com.andruid.magic.newsloader.model.News;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import timber.log.Timber;

import static com.andruid.magic.newsloader.data.Constants.FIRST_PAGE;
import static com.andruid.magic.newsloader.data.Constants.PAGE_SIZE;

public class NewsDataSource extends PageKeyedDataSource<Integer, News> {
    private String country, category;

    NewsDataSource(String country, String category) {
        this.category = category;
        Timber.tag("dslog").d("datasource created");
        this.country = country;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, News> callback) {
        Timber.tag("pagelog").d("load initial");
        NewsRepository.getInstance().loadHeadlines(country, category, FIRST_PAGE, PAGE_SIZE, new NewsRepository.NewsLoadedListener() {
            @Override
            public void onSuccess(@NotNull List<News> newsList, boolean hasMore) {
                callback.onResult(newsList, null, FIRST_PAGE + 1);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, News> callback) {
        NewsRepository.getInstance().loadHeadlines(country, category, params.key, PAGE_SIZE, new NewsRepository.NewsLoadedListener() {
            @Override
            public void onSuccess(@NotNull List<News> newsList, boolean hasMore) {
                Integer adjacentKey = (params.key > FIRST_PAGE) ? params.key - 1 : null;
                callback.onResult(newsList, adjacentKey);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, News> callback) {
        Timber.tag("pagelog").d("load page %d", params.key);
        NewsRepository.getInstance().loadHeadlines(country, category, params.key, PAGE_SIZE, new NewsRepository.NewsLoadedListener() {
            @Override
            public void onSuccess(@NotNull List<News> newsList, boolean hasMore) {
                Integer key = hasMore ? params.key + 1 : null;
                callback.onResult(newsList, key);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }
}