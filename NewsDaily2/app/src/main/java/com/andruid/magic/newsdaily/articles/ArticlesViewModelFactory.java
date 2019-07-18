package com.andruid.magic.newsdaily.articles;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ArticlesViewModelFactory implements ViewModelProvider.Factory {
    private String language, query;

    public ArticlesViewModelFactory(String language, String query) {
        this.language = language;
        this.query = query;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ArticlesViewModel(language, query);
    }
}