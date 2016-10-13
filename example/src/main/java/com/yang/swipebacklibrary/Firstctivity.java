package com.yang.swipebacklibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.yang.swipeback.library.SwipeBackActivityImpl;

public class Firstctivity extends SwipeBackActivityImpl {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Firstctivity.this, SecondActivity.class));
            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setSwipeBackEnable(false);
    }

    @Override
    public boolean isTransparent() {
        return false;
    }
}
