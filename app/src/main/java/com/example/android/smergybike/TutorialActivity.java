package com.example.android.smergybike;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TutorialActivity extends AppCompatActivity {

    private ViewPager mSlideViewPager;
    private LinearLayout mDotsLayout;
    private SlideAdapter slideAdapter;
    private TextView[] mDots;
    private Button mNextBtn;
    private Button mPrevBtn;
    private int mCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        mSlideViewPager = findViewById(R.id.SlideViewPager);
        mDotsLayout = findViewById(R.id.dotsLayout);
        mNextBtn = findViewById(R.id.nextBtn);
        mPrevBtn = findViewById(R.id.prevBtn);
        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCurrentPage < 3){
                    mSlideViewPager.setCurrentItem(mCurrentPage + 1);
                }else{
                    changeActivity();
                }
            }
        });
        mPrevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSlideViewPager.setCurrentItem(mCurrentPage - 1);
            }
        });

        slideAdapter = new SlideAdapter(this);
        mSlideViewPager.setAdapter(slideAdapter);
        addDotsIndicator(0);
        mSlideViewPager.addOnPageChangeListener(viewListener);
        this.setTitle("Tutorial");
    }

    private void changeActivity() {
        this.finish();
//        Intent intent = new Intent(this, Main2Activity.class);
//        startActivity(intent);
    }

    public void addDotsIndicator(int position){
        mDots = new TextView[4];
        mDotsLayout.removeAllViews();

        for(int i = 0; i < mDots.length; i++){
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.transparantish));
            mDotsLayout.addView(mDots[i]);
        }

        if(mDots.length > 0){
            mDots[position].setTextColor(getResources().getColor(R.color.white));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
            mCurrentPage = position;

            if(mCurrentPage == 0){
                mPrevBtn.setEnabled(false);
                mNextBtn.setEnabled(true);
                mPrevBtn.setVisibility(View.INVISIBLE);
                mNextBtn.setText("Next");
                mPrevBtn.setText("");
            }else if(position == mDots.length - 1){
                mPrevBtn.setEnabled(true);
                mNextBtn.setEnabled(true);
                mPrevBtn.setVisibility(View.VISIBLE);
                mNextBtn.setText("Finish");
                mPrevBtn.setText("Back");
            }else{
                mPrevBtn.setEnabled(true);
                mNextBtn.setEnabled(true);
                mPrevBtn.setVisibility(View.VISIBLE);
                mNextBtn.setText("Next");
                mPrevBtn.setText("Back");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
