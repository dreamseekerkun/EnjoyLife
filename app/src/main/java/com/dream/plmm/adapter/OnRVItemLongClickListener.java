
package com.dream.plmm.adapter;

import android.view.View;
import android.view.ViewGroup;

/**
 * 描述:RecyclerView的item长按事件监听器
 */
public interface OnRVItemLongClickListener {
    boolean onRVItemLongClick(ViewGroup parent, View itemView, int position);
}