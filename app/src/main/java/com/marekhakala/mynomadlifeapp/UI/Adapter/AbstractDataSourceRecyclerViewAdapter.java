package com.marekhakala.mynomadlifeapp.UI.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.marekhakala.mynomadlifeapp.UI.Component.DataSourceRecyclerViewHolder;
import com.marekhakala.mynomadlifeapp.UI.Component.LoadMoreItemView;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDataSourceRecyclerViewAdapter
        <ET, VH extends DataSourceRecyclerViewHolder>
        extends RecyclerView.Adapter<DataSourceRecyclerViewHolder> {

    protected Context context;
    protected LayoutInflater inflater;
    protected List<ET> results;

    protected StateType currentState;

    public enum StateType { LOADING_STATE, EMPTY_STATE, ERROR_STATE, REFRESHING_STATE, LOAD_MORE_STATE, NORMAL_STATE }
    protected final int LOAD_MORE_VIEW_TYPE = 100;

    public AbstractDataSourceRecyclerViewAdapter(Context context, List<ET> results) {
        if (context == null)
            throw new IllegalArgumentException("Context can't be null.");

        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.currentState = StateType.EMPTY_STATE;

        updateData(results);
    }

    public abstract VH onCreateDataSourceViewHolder(ViewGroup viewGroup, int viewType);
    public abstract void onBindDataSourceViewHolder(VH holder, int position);

    public Context getContext() {
        return context;
    }

    @Override
    public final DataSourceRecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        if (viewType == LOAD_MORE_VIEW_TYPE)
            return new DataSourceRecyclerViewHolder(new LoadMoreItemView(viewGroup.getContext()));

        return onCreateDataSourceViewHolder(viewGroup, viewType);
    }

    @Override
    @SuppressWarnings("unchecked")
    public final void onBindViewHolder(DataSourceRecyclerViewHolder holder, int position) {

        if (getItemViewType(position) == LOAD_MORE_VIEW_TYPE) {
            holder.loadMoreView.showSpinner();
        } else {
            onBindDataSourceViewHolder((VH) holder, position);
        }
    }

    public Object getLastItem() {
        return results.get(results.size() - 1);
    }

    public Object getItem(int index) {
        if(index >= 0 && index < results.size())
            return results.get(index);

        return null;
    }

    @Override
    public int getItemCount() {

        if (results == null)
            return 0;

        return results.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(currentState == StateType.LOAD_MORE_STATE && position == (getItemCount() - 1))
            return LOAD_MORE_VIEW_TYPE;

        return super.getItemViewType(position);
    }

    public void close() {
        updateData(null);
    }

    public void addData(List<ET> results) {
        this.results.addAll(results);
        setCurrentState(StateType.NORMAL_STATE);
    }

    public void updateData(List<ET> results) {
        this.results = results;

        if(results.isEmpty())
            setCurrentState(StateType.EMPTY_STATE);
        else
            setCurrentState(StateType.NORMAL_STATE);
    }

    public abstract void removeItem(ET item);

    public void removeData() {
        setCurrentState(StateType.EMPTY_STATE);
    }

    public StateType getCurrentState() {
        return this.currentState;
    }

    public void setCurrentState(StateType stateValue) {
        if(stateValue.equals(this.currentState))
            return;

        this.currentState = stateValue;

        if (stateValue == StateType.LOADING_STATE || stateValue == StateType.EMPTY_STATE || stateValue == StateType.ERROR_STATE)
            this.results = new ArrayList<>();

        notifyDataSetChanged();
    }

    public boolean isWaitingForData() {
        return (this.currentState == StateType.LOADING_STATE
                || this.currentState == StateType.REFRESHING_STATE
                || this.currentState == StateType.LOAD_MORE_STATE);
    }
}
