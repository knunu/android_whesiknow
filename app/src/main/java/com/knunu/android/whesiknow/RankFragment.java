package com.knunu.android.whesiknow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class RankFragment extends Fragment {

    @OnClick(R.id.main_find_button) void onClick() { find(); }
    @OnClick(R.id.spicy_grilled_chicken) void onSpicyGrilledChickenClick() {
        Intent intent = new Intent(getActivity(), DetailInfoActivity.class);
        intent.putExtra("activityName", "rank");
        intent.putExtra("shopName", "윤재네 닭갈비");
        startActivity(intent); }
    @OnClick({R.id.pig_feets, R.id.pig_feets2}) void onPigsFeetClick() {
        Intent intent = new Intent(getActivity(), DetailInfo2Activity.class);
        intent.putExtra("activityName", "rank");
        intent.putExtra("shopName", "선우네 족발");
        startActivity(intent); }
    @OnClick(R.id.bulgogi) void onBulgogiClick() {
        Intent intent = new Intent(getActivity(), DetailInfo4Activity.class);
        intent.putExtra("activityName", "rank");
        intent.putExtra("shopName", "수민의 콩불");
        startActivity(intent); }

    private Unbinder unbinder;

    public RankFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rank, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void find() {
        startActivity(new Intent(getActivity(), FindActivity.class));
    }
}
