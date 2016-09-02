
package com.dream.plmm.adapter;

import android.view.View;
import android.view.ViewGroup;

/**
 * 描述:RecyclerView的item点击事件监听器
 */
public interface OnRVItemClickListener {
    void onRVItemClick(ViewGroup parent, View itemView, int position);
}