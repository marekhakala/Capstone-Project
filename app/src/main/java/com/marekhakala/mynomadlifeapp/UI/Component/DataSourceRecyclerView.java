package com.marekhakala.mynomadlifeapp.UI.Component;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;

import com.marekhakala.mynomadlifeapp.R;
import com.marekhakala.mynomadlifeapp.UI.Adapter.AbstractDataSourceRecyclerViewAdapter;

/**
 * A DataSourceRecyclerView that has a extra features
 *
 * - Loading state
 * - Refreshing state
 * - Load more state
 * - Empty state
 * - Error state
 *
 **/
public class DataSourceRecyclerView extends FrameLayout {

    public interface OnActionListener {
        void refreshData();
        void onLoadMore();
    }

    private enum Type { LinearLayout, Grid }

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    private ViewStub stateLoadingContentContainer;
    private ViewStub stateEmptyContentContainer;
    private ViewStub stateErrorContentContainer;

    private AbstractDataSourceRecyclerViewAdapter adapter;

    private boolean isRefreshable;

    private int loadingViewId;
    private int emptyViewId;
    private int errorViewId;

    private Type type;
    private int gridSpanCount;
    private int gridWidthPx;

    private GridLayoutManager gridManager;
    private int lastMeasuredWidth = -1;

    private OnActionListener onActionListener;

    public DataSourceRecyclerView(Context context) {
        super(context);
        init(context, null);
    }

    public DataSourceRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DataSourceRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);

        if (gridWidthPx != -1 && gridManager != null && lastMeasuredWidth != getMeasuredWidth()) {
            gridManager.setSpanCount(Math.max(1, getMeasuredWidth() / gridWidthPx));
            lastMeasuredWidth = getMeasuredWidth();
        }
    }

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.data_source_recycler_view, this);
        initAttrs(context, attrs);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.data_source_swipe_refresh_layout);
        recyclerView = (RecyclerView) findViewById(R.id.data_source_recycler_view);
        stateLoadingContentContainer = (ViewStub) findViewById(R.id.data_source_loading_content_container);
        stateEmptyContentContainer = (ViewStub) findViewById(R.id.data_source_empty_content_container);
        stateErrorContentContainer = (ViewStub) findViewById(R.id.data_source_error_content_container);

        swipeRefreshLayout.setEnabled(isRefreshable);

        if (isRefreshable)
            swipeRefreshLayout.setOnRefreshListener(recyclerViewRefreshListener);

        if (loadingViewId != 0) {
            stateLoadingContentContainer.setLayoutResource(loadingViewId);
            stateLoadingContentContainer.inflate();
        }

        if (emptyViewId != 0) {
            stateEmptyContentContainer.setLayoutResource(emptyViewId);
            stateEmptyContentContainer.inflate();
        }

        if (errorViewId != 0) {
            stateErrorContentContainer.setLayoutResource(errorViewId);
            stateErrorContentContainer.inflate();
        }

        if (type == null)
            throw new IllegalStateException("A type has to be specified via XML attr.");

        switch (type) {
            case LinearLayout:
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                break;

            case Grid:
                if (gridSpanCount == -1 && gridWidthPx == -1) {
                    throw new IllegalStateException("For GridLayout, a span count or item width has to be set.");
                } else if(gridSpanCount != -1 && gridWidthPx != -1) {
                    throw new IllegalStateException("For GridLayout, a span count and item width can not both be set.");
                }

                gridManager = new GridLayoutManager(getContext(), gridSpanCount == -1 ? 1 : gridSpanCount);
                recyclerView.setLayoutManager(gridManager);
                break;

            default:
                throw new IllegalStateException("The type attribute has to be set.");
        }

        recyclerView.setHasFixedSize(true);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                    }
                }
        );

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        fireLoadMore();
                    }
                }
        );
    }

    public void setOrientation(int orientation) {
        if(gridManager == null)
            throw new IllegalStateException("Error init of GridLayoutManager.");

        gridManager.setOrientation(orientation);
    }

    public void setRefreshEnabled(boolean value) {
        isRefreshable = value;
        swipeRefreshLayout.setEnabled(isRefreshable);
    }

    private void fireLoadMore() {
        if(adapter != null && adapter.isWaitingForData())
            return;

        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int firstVisibleItemPosition = findFirstVisibleItemPosition();

        if (totalItemCount == 0)
            return;

        if (firstVisibleItemPosition + visibleItemCount >= totalItemCount) {
            if(onActionListener != null)
                onActionListener.onLoadMore();
        }
    }

    public int findFirstVisibleItemPosition() {
        switch (type) {
            case LinearLayout:
                return ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
            case Grid:
                return ((GridLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
            default:
                throw new IllegalStateException("Type of layoutManager unknown."
                        + "In this case this method needs to be overridden.");
        }
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DataSourceRecyclerView);

        isRefreshable = typedArray.getBoolean(R.styleable.DataSourceRecyclerView_dsIsRefreshable, false);
        loadingViewId = typedArray.getResourceId(R.styleable.DataSourceRecyclerView_dsLoadingLayoutId, 0);
        emptyViewId = typedArray.getResourceId(R.styleable.DataSourceRecyclerView_dsEmptyLayoutId, 0);
        errorViewId = typedArray.getResourceId(R.styleable.DataSourceRecyclerView_dsErrorLayoutId, 0);

        int typeValue = typedArray.getInt(R.styleable.DataSourceRecyclerView_dsLayoutType, -1);

        if (typeValue != -1)
            type = Type.values()[typeValue];

        gridSpanCount = typedArray.getInt(R.styleable.DataSourceRecyclerView_dsGridLayoutSpanCount, -1);
        gridWidthPx = typedArray.getDimensionPixelSize(R.styleable.DataSourceRecyclerView_dsGridLayoutItemWidth, -1);
        typedArray.recycle();
    }

    public void addItemDecoration(RecyclerView.ItemDecoration decor) {
        recyclerView.addItemDecoration(decor);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration decor, int index) {
        recyclerView.addItemDecoration(decor, index);
    }

    public void removeItemDecoration(RecyclerView.ItemDecoration decor) {
        recyclerView.removeItemDecoration(decor);
    }

    public void setAdapter(AbstractDataSourceRecyclerViewAdapter adapter) {
        this.adapter = adapter;
        recyclerView.setAdapter(adapter);

        if (adapter != null) {
            adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                        @Override
                        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
                            update();
                        }

                        @Override
                        public void onItemRangeRemoved(int positionStart, int itemCount) {
                            super.onItemRangeRemoved(positionStart, itemCount);
                            update();
                        }

                        @Override
                        public void onItemRangeInserted(int positionStart, int itemCount) {
                            super.onItemRangeInserted(positionStart, itemCount);
                            update();
                        }

                        @Override
                        public void onItemRangeChanged(int positionStart, int itemCount) {
                            super.onItemRangeChanged(positionStart, itemCount);
                            update();
                        }

                        @Override
                        public void onChanged() {
                            super.onChanged();
                            update();
                        }

                        private void update() {
                            updateStateContentContainerVisibility(adapter);
                        }
                    }
            );

            updateStateContentContainerVisibility(adapter);
        }
    }

    private void updateStateContentContainerVisibility(AbstractDataSourceRecyclerViewAdapter adapter) {

        switch (adapter.getCurrentState()) {
            case LOADING_STATE:
                stateLoadingContentContainer.setVisibility(View.VISIBLE);
                stateEmptyContentContainer.setVisibility(View.GONE);
                stateErrorContentContainer.setVisibility(View.GONE);

                if (isRefreshable)
                    swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(false));
                break;

            case EMPTY_STATE:
                stateLoadingContentContainer.setVisibility(View.GONE);
                stateEmptyContentContainer.setVisibility(View.VISIBLE);
                stateErrorContentContainer.setVisibility(View.GONE);

                if (isRefreshable)
                    swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(false));
                break;

            case ERROR_STATE:
                stateLoadingContentContainer.setVisibility(View.GONE);
                stateEmptyContentContainer.setVisibility(View.GONE);
                stateErrorContentContainer.setVisibility(View.VISIBLE);

                if (isRefreshable)
                    swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(false));
                break;

            case REFRESHING_STATE:
                stateLoadingContentContainer.setVisibility(View.GONE);
                stateEmptyContentContainer.setVisibility(View.GONE);
                stateErrorContentContainer.setVisibility(View.GONE);

                if (isRefreshable)
                    swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(true));
                break;

            default:
                stateLoadingContentContainer.setVisibility(View.GONE);
                stateEmptyContentContainer.setVisibility(View.GONE);
                stateErrorContentContainer.setVisibility(View.GONE);

                if (isRefreshable)
                    swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(false));
                break;
        }
    }

    public void setItemViewCacheSize(int size) {
        recyclerView.setItemViewCacheSize(size);
    }

    public void smoothScrollToPosition(int position) {
        recyclerView.smoothScrollToPosition(position);
    }

    public void scrollToPosition(int position) {
        recyclerView.scrollToPosition(position);
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setOnActionListener(OnActionListener onActionListener) {
        this.onActionListener = onActionListener;
    }

    private AbstractDataSourceRecyclerViewAdapter getRecyclerViewAdapter() {
        return this.adapter;
    }

    private DataSourceRecyclerView getDataSourceRecyclerView() {
        return this;
    }

    private SwipeRefreshLayout.OnRefreshListener recyclerViewRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    DataSourceRecyclerView rv = getDataSourceRecyclerView();

                    if (rv.getRecyclerViewAdapter() != null && !rv.getRecyclerViewAdapter().isWaitingForData() && onActionListener != null) {
                        onActionListener.refreshData();
                    }
                }
            };

    public int getCurrentPosition() {
        return findFirstVisibleItemPosition();
    }
}
