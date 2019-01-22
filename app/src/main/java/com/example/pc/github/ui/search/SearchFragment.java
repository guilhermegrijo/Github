package com.example.pc.github.ui.search;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.pc.github.R;
import com.example.pc.github.domain.model.Item;
import com.example.pc.github.domain.model.Response;
import com.example.pc.github.util.EndlessRecyclerViewScrollListener;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnEditorAction;
import dagger.android.support.AndroidSupportInjection;

public class SearchFragment extends Fragment {

    @BindView(R.id.search_editText)
    EditText search;
    @BindView(R.id.textView)
    TextView title;
    @BindView(R.id.textView2)
    TextView subtitle;
    @Inject
    SearchViewModelFactory factory;
    @Nullable
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @Nullable
    @BindView(R.id.loadingIndicator)
    ProgressBar progressBar;
    private SearchViewModel mViewModel;
    private ItemAdapter mAdapter;
    private EndlessRecyclerViewScrollListener mScrollListener;
    private View view;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.search_fragment, container, false);

        ButterKnife.bind(this, view);
        mAdapter = new ItemAdapter();
        setupView();

        Animation up = AnimationUtils.loadAnimation(getContext(), R.anim.up);
        title.startAnimation(up);
        subtitle.startAnimation(up);
        search.startAnimation(up);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AndroidSupportInjection.inject(this);

        mViewModel = ViewModelProviders.of(this, factory).get(SearchViewModel.class);
        mViewModel.response().observe(this, this::processResponse);

    }

    @OnEditorAction(R.id.search_editText)
    protected boolean search(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            String input = search.getText().toString();
            if (!TextUtils.isEmpty(input)) {
                mViewModel.doSearch(input);
                animate();
                keyboardDismiss();
                return true;
            }
            return true;
        }
        return false;
    }


    private void processResponse(Response response) {
        switch (response.status) {
            case LOADING:
                renderLoadingState();
                break;

            case SUCCESS:
                renderDataState(response.data);
                break;

            case ERROR:
                renderErrorState(response.error);
                break;
        }
    }

    private void renderLoadingState() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void renderDataState(List<Item> result) {
        mAdapter.setItemList(result);
        progressBar.setVisibility(View.GONE);
    }

    private void renderErrorState(Throwable throwable) {
        Log.e("Loading error", throwable.getMessage());

    }

    private void setupView() {
        search.clearFocus();

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                mLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);

        mScrollListener = new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                mViewModel.loadMore();
            }
        };
        mAdapter.setListener(item -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.url.trim()));
            startActivity(browserIntent);
        });
        recyclerView.addOnScrollListener(mScrollListener);
    }

    private void keyboardDismiss() {
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void animate() {

        title.animate()
                .translationY(-this.getView().getHeight())
                .alpha(0.0f)
                .setDuration(500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        title.setVisibility(View.GONE);
                    }
                });
        subtitle.animate()
                .translationY(-this.getView().getHeight())
                .alpha(0.0f)
                .setDuration(500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        subtitle.setVisibility(View.GONE);
                    }
                });
    }


}
