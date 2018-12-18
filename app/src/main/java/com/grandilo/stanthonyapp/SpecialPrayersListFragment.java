/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.grandilo.stanthonyapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.grandilo.stanthonyapp.db.DBAdapter;

import java.util.ArrayList;
import java.util.List;

public class SpecialPrayersListFragment extends Fragment {
    DBAdapter db;
    String title;
    String body;
    int prayerCounter = 0;
    public static int itemPosition ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView rv = (RecyclerView) inflater.inflate(
                R.layout.prayer_list, container, false);
        setupRecyclerView(rv);
        db = new DBAdapter(getActivity());

        return rv;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(getActivity(),
                Prayers.specialPrayerList));
    }


    private List<String> getSpecialPrayerList(String[] array) {
        ArrayList<String> list = new ArrayList<>(23);
        while (list.size() < 23) {
            list.add(getPrayerArray()[prayerCounter]);
            prayerCounter++;
        }
        prayerCounter=0;
        return list;
    }
    public String[] getPrayerArray(){
        String [] prayerArray ={};
        db.open();
        Cursor c = null;
        while(prayerCounter<=23){
            c = db.getSpecialPrayer(prayerCounter);
            prayerArray[prayerCounter]=c.getString(1);
            prayerCounter++;
        }
        prayerCounter=0;
        db.close();
        return prayerArray;
    }
    public String getPrayerTitle(){
        db.open();
        Cursor c = db.getSpecialPrayer(prayerCounter);
        title=c.getString(1);
        db.close();
        return title;
    }
    public String getPrayerBody(){
        db.open();
        Cursor c = db.getSpecialPrayer(prayerCounter);
        body=c.getString(2);
        db.close();
        return body;
    }
    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {

        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        private List<String> mValues;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public String mBoundString;

            public final View mView;
            public final ImageView mImageView;
            public final TextView mTextView;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (ImageView) view.findViewById(R.id.avatar);
                mTextView = (TextView) view.findViewById(android.R.id.text1);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mTextView.getText();
            }
        }

        public String getValueAt(int position) {
            return mValues.get(position);
        }

        public SimpleStringRecyclerViewAdapter(Context context, List<String> items) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            view.setBackgroundResource(mBackground);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.mBoundString = mValues.get(position);
            holder.mTextView.setText(mValues.get(position));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, SpecialPrayerDetailActivity.class);
                    intent.putExtra(SpecialPrayerDetailActivity.EXTRA_NAME, holder.mBoundString);
                    itemPosition = position;
                    context.startActivity(intent);
                }


            });

            Glide.with(holder.mImageView.getContext())
                    .load(Prayers.getRandomCheeseDrawable())
                    .apply(new RequestOptions().placeholder(R.mipmap.ic_launcher).fitCenter())
                    .into(holder.mImageView);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }
    }
}
