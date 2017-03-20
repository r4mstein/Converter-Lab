package ua.r4mstein.converterlab.presentation.adapters.detail.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class ViewHolderBase<T extends DataHolderBase> extends RecyclerView.ViewHolder {

    private T data;

    public ViewHolderBase(final View _itemView) {
        super(_itemView);
    }

    public final T getData() {
        return data;
    }

    public void setData(final T _data) {
        this.data = _data;
    }
}
