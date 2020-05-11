package com.lavreniuk.campassistant.onboarding;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.lavreniuk.campassistant.R;
import java.util.ArrayList;
import java.util.List;

public abstract class TutorialActivity extends AppCompatActivity
        implements View.OnClickListener, CurrentFragmentListener {

    private List<Step> steps;
    private StepPagerAdapter adapter;

    private ViewPager pager;
    private TextView next, prev;
    private LinearLayout indicatorLayout;
    private FrameLayout containerLayout;
    private RelativeLayout buttonContainer;
    private CurrentFragmentListener currentFragmentListener;

    private int currentItem;

    private String prevText, nextText, finishText, cancelText, givePermissionText;
    private int selectedIndicator = R.drawable.onboarding_fragment_indicator_selected;
    private int indicator = R.drawable.onboarding_fragment_indicator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.TutorialStyle);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        currentFragmentListener = this;
        init();
    }

    private void init() {
        steps = new ArrayList<>();
        initTexts();
        initViews();
        initAdapter();
    }

    private void initTexts() {
        prevText = getString(R.string.ui_back);
        cancelText = getString(R.string.ui_cancel);
        finishText = getString(R.string.ui_finish);
        nextText = getString(R.string.ui_next);
        givePermissionText = getString(R.string.ui_give);
    }

    private void initAdapter() {
        adapter = new StepPagerAdapter(getSupportFragmentManager(), steps);
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(
                new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(
                            int position, float positionOffset, int positionOffsetPixels) {}

                    @Override
                    public void onPageSelected(int position) {
                        currentItem = position;
                        currentFragmentListener.currentFragmentPosition(position);
                        controlPosition(position);
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {}
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void changeStatusBarColor(int backgroundColor) {
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(backgroundColor);
    }

    private void controlPosition(int position) {
        notifyIndicator();

        if (position == steps.size() - 1) {
            next.setText(finishText);
            prev.setText(prevText);
        } else if (position == 0) {
            prev.setText(cancelText);
            next.setText(nextText);
        } else {
            prev.setText(prevText);
            next.setText(nextText);
        }

        if (controlPermission()) {
            prepareNormalView();
        } else {
            preparePermissionView();
        }
        if (!steps.isEmpty()) {
            containerLayout.setBackgroundColor(steps.get(position).getBackgroundColor());
            buttonContainer.setBackgroundColor(steps.get(position).getBackgroundColor());
        }
    }

    private void prepareNormalView() {
        pager.setOnTouchListener(null);
    }

    private void preparePermissionView() {
        next.setText(givePermissionText);

        pager.setOnTouchListener((v, event) -> true);
    }

    private void initViews() {
        currentItem = 0;

        pager = findViewById(R.id.viewPager);
        next = findViewById(R.id.next);
        prev = findViewById(R.id.prev);
        indicatorLayout = findViewById(R.id.indicatorLayout);
        containerLayout = findViewById(R.id.containerLayout);
        buttonContainer = findViewById(R.id.buttonContainer);

        next.setOnClickListener(this);
        prev.setOnClickListener(this);
    }

    public void addFragment(Step step) {
        steps.add(step);
        adapter.notifyDataSetChanged();
        notifyIndicator();
        controlPosition(currentItem);
    }

    public void addFragment(Step step, int position) {
        steps.add(position, step);
        adapter.notifyDataSetChanged();
        notifyIndicator();
    }

    public void notifyIndicator() {
        if (indicatorLayout.getChildCount() > 0) indicatorLayout.removeAllViews();

        for (int i = 0; i < steps.size(); i++) {
            ImageView imageView = new ImageView(this);
            int padding =
                    (int)
                            getResources()
                                    .getDimension(R.dimen.onboarding_fragment_indicator_padding);
            imageView.setPadding(padding, padding, padding, padding);
            int drawable = indicator;
            if (i == currentItem) drawable = selectedIndicator;

            imageView.setImageResource(drawable);

            final int finalI = i;
            imageView.setOnClickListener(v -> changeFragment(finalI));

            indicatorLayout.addView(imageView);
        }
    }

    @Override
    public void onBackPressed() {
        if (currentItem == 0) {
            super.onBackPressed();
        } else {
            changeFragment(false);
        }
    }

    @SuppressLint("NewApi")
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.next) {
            if (controlPermission()) changeFragment(true);
            else
                requestPermissions(
                        ((PermissionStep) steps.get(pager.getCurrentItem())).getPermissions(),
                        1903);
        } else if (v.getId() == R.id.prev) {
            changeFragment(false);
        }
    }

    private void changeFragment(int position) {
        if (controlPermission()) pager.setCurrentItem(position, true);
    }

    private boolean controlPermission() {
        if (!steps.isEmpty() && steps.get(pager.getCurrentItem()) instanceof PermissionStep) {

            for (String permission :
                    ((PermissionStep) steps.get(pager.getCurrentItem())).getPermissions()) {
                int permissionResult = checkSelfPermission(permission);

                if (permissionResult != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void changeFragment(boolean isNext) {
        int item = currentItem;
        if (isNext) {
            item++;
        } else {
            item--;
        }

        if (item < 0 || item == steps.size()) {
            finishTutorial();
        } else pager.setCurrentItem(item, true);
    }

    public void finishTutorial() {
        finish();
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            changeFragment(true);
        }
    }
}
