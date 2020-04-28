package com.lavreniuk.campassistant.onboarding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.lavreniuk.campassistant.R;

public class StepFragment extends StepView {

    private TextView title;
    private TextView content;
    private ImageView imageView;
    private View layout;


    static StepFragment createFragment(Step step) {
        StepFragment fragment = new StepFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("step", step);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        step = getArguments().getParcelable("step");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layout = step.getViewType() > 0 ? step.getViewType() : R.layout.fragment_step;

        View view = inflater.inflate(layout, container, false);

        initViews(view);
        initData();

        return view;
    }

    private void initData() {
        if (title != null) {
            title.setText(step.getTitle());
        }
        if (content != null) {
            content.setText(step.getContent());
        }
        if (imageView != null) {
            imageView.setImageResource(step.getDrawable());
        }

        if (layout != null) {
            layout.setBackgroundColor(step.getBackgroundColor());
        }
    }

    private void initViews(View view) {
        title = view.findViewById(R.id.onboarding_step_fragment_title);
        content = view.findViewById(R.id.onboarding_step_fragment_content);
        imageView = view.findViewById(R.id.onboarding_step_fragment_image);
        layout = view.findViewById(R.id.onboarding_step_fragment_container);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (imageView != null) {
            imageView.setImageDrawable(null);
        }
    }
}
