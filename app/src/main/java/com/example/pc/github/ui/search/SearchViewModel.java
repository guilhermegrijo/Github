package com.example.pc.github.ui.search;

import com.example.pc.github.domain.interactors.LoadRepositoriesUseCase;
import com.example.pc.github.domain.model.Item;
import com.example.pc.github.domain.model.Response;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class SearchViewModel extends ViewModel {

    private final LoadRepositoriesUseCase loadRepositoriesUseCase;

    private final CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<Response> response = new MutableLiveData<>();

    private List<Item> mItemList;


    private String query = "";

    private int PAGE = 1;


    SearchViewModel(LoadRepositoriesUseCase loadRepositoriesUseCase) {
        this.loadRepositoriesUseCase = loadRepositoriesUseCase;

    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }

    MutableLiveData<Response> response() {
        return response;
    }

    void doSearch(String search) {
        String input = search.toLowerCase().trim();
        if (input.equals(query)) {
            return;
        }
        if (mItemList != null) {
            mItemList.clear();
        }
        query = input;
        PAGE = 1;
        getRepos();
    }

    private void getRepos() {
        disposables.add(loadRepositoriesUseCase.execute(query, PAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(__ -> response.setValue(Response.loading()))
                .subscribe(
                        result -> setItemList(result.getItems()),
                        throwable -> response.setValue(Response.error(throwable))
                ));
    }

    void loadMore() {
        PAGE++;
        getRepos();
    }

    private void setItemList(final List<Item> itemList) {
        if (mItemList == null) {
            mItemList = itemList;
            response.setValue(Response.success(mItemList));

        } else {
            mItemList.addAll(itemList);
            response.setValue(Response.success(mItemList));
        }
    }


}
