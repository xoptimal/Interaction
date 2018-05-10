package com.xoptimal.interaction.helper;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

/**
 * Author Freddie on 2016/5/24
 * Description:
 */

public class RcvAdapterWrapper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_HEADER = 99930;
    public static final int TYPE_FOOTER = 99931;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter       mWrapped;
    private View headerView = null;
    private View footerView = null;

    public RcvAdapterWrapper(@NonNull RecyclerView.Adapter adapter, @NonNull RecyclerView.LayoutManager layoutManager) {
        this.mWrapped = adapter;
        this.mWrapped.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            public void onChanged() {
                RcvAdapterWrapper.this.notifyDataSetChanged();
            }

            public void onItemRangeChanged(int positionStart, int itemCount) {
                RcvAdapterWrapper.this.notifyItemRangeChanged(positionStart + RcvAdapterWrapper.this.getHeaderCount(), itemCount);
            }

            public void onItemRangeInserted(int positionStart, int itemCount) {
                RcvAdapterWrapper.this.notifyItemRangeInserted(positionStart + RcvAdapterWrapper.this.getHeaderCount(), itemCount);
            }

            public void onItemRangeRemoved(int positionStart, int itemCount) {
                RcvAdapterWrapper.this.notifyItemRangeRemoved(positionStart + RcvAdapterWrapper.this.getHeaderCount(), itemCount);
                if (RcvAdapterWrapper.this.getFooterCount() != 0 && positionStart + RcvAdapterWrapper.this.getFooterCount() + 1 == RcvAdapterWrapper.this.getItemCount()) {
                    RcvAdapterWrapper.this.notifyDataSetChanged();
                }
            }

            public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                RcvAdapterWrapper.this.notifyItemMoved(fromPosition + RcvAdapterWrapper.this.getHeaderCount(), RcvAdapterWrapper.this.getHeaderCount() + toPosition);
            }
        });
        setLayoutManager(layoutManager);
    }

    public int getItemCount() {
        int offset = 0;
        if (this.headerView != null) {
            ++offset;
        }

        if (this.footerView != null) {
            ++offset;
        }

        return offset + this.mWrapped.getItemCount();
    }

    public int getItemViewType(int position) {
        return this.headerView != null && position == 0 ? 99930 : (this.footerView != null && position == this.getItemCount() - 1 ? 99931 : this.mWrapped.getItemViewType(position - this.getHeaderCount()));
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return (RecyclerView.ViewHolder) (viewType == 99930 ? new SimpleViewHolder(this.headerView) : (viewType == 99931 ? new SimpleViewHolder(this.footerView) : this.mWrapped.onCreateViewHolder(parent, viewType)));
    }

    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        int type = this.getItemViewType(position);
        if (type != 99930 && type != 99931) {
            this.mWrapped.onBindViewHolder(viewHolder, position - this.getHeaderCount());
        }
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {

        this.layoutManager = layoutManager;

        if (layoutManager instanceof StaggeredGridLayoutManager) {

            if (headerView != null) {
                setFullSpan(headerView, layoutManager);
            }

            if (footerView != null) {
                setFullSpan(footerView, layoutManager);
            }

        } else if (layoutManager instanceof GridLayoutManager) {

            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;

            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    return type != TYPE_HEADER && type != TYPE_FOOTER ? 1 : gridLayoutManager.getSpanCount();
                }
            });
        }
    }

    public void setHeaderView(@NonNull View headerView) {
        this.headerView = headerView;
        this.setFullSpan(headerView, this.layoutManager);
    }

    public void setFooterView(@NonNull View footerView) {
        this.footerView = footerView;
        this.setFullSpan(footerView, this.layoutManager);
    }

    private void setFullSpan(View view, RecyclerView.LayoutManager layoutManager) {

        int itemHeight = view.getLayoutParams() != null ? view.getLayoutParams().height : -2;

        if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(-1, itemHeight);
            layoutParams.setFullSpan(true);
            view.setLayoutParams(layoutParams);
        } else if (layoutManager instanceof GridLayoutManager) {
            view.setLayoutParams(new ViewGroup.LayoutParams(-1, itemHeight));
        }
    }

    public void removeHeaderView() {
        this.headerView = null;
        this.notifyDataSetChanged();
    }

    public void removeFooterView() {
        this.footerView = null;
        int footerPos = this.getItemCount();
        this.notifyItemRemoved(footerPos);
    }

    public RecyclerView.Adapter getWrappedAdapter() {
        return this.mWrapped;
    }

    public int getHeaderCount() {
        return this.headerView != null ? 1 : 0;
    }

    public int getFooterCount() {
        return this.footerView != null ? 1 : 0;
    }

    static void setSpanSizeLookup(final RecyclerView.Adapter adapter, final GridLayoutManager layoutManager) {
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            public int getSpanSize(int position) {
                int type = adapter.getItemViewType(position);
                return type != 99930 && type != 99931 ? 1 : layoutManager.getSpanCount();
            }
        });
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return this.layoutManager;
    }

    public View getHeaderView() {
        return this.headerView;
    }

    public View getFooterView() {
        return this.footerView;
    }

    private static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public SimpleViewHolder(View itemView) {
            super(itemView);
        }
    }
}
