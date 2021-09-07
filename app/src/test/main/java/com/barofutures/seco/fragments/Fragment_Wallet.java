package com.barofutures.seco.fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.barofutures.seco.R;
import com.barofutures.seco.RecipeDetailActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Wallet extends Fragment {
    private ViewGroup viewGroup;
    private Button testButton, chartDetailButton, badgeExchangeButton,coinExchangeButton;
    // Chart
    private PieChart pieChart;
    private BarChart barChart;
    // Chart에 넣은 데이터
    private List<PieEntry> pieChartData;
    private ArrayList<BarEntry>barChartData;
    private Legend legend;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //뷰 초기화 및 연결
        viewGroup=(ViewGroup) inflater.inflate(R.layout.fragment_wallet, container, false);
        pieChart=viewGroup.findViewById(R.id.fragment_wallet_carbon_chart);
        barChart=viewGroup.findViewById(R.id.fragment_wallet_carbon_reduction_chart);
        chartDetailButton=viewGroup.findViewById(R.id.fragment_wallet_button_carbon_history);
        badgeExchangeButton=viewGroup.findViewById(R.id.fragment_wallet_button_badge_exchange);
        coinExchangeButton=viewGroup.findViewById(R.id.fragment_wallet_button_coin_exchange);

        /*
         * PieChart
         */
        // init ArrayList
        pieChartData = new ArrayList<>();

        // PieChart data
        pieChartData.add(new PieEntry(18.5f, "Green"));
        pieChartData.add(new PieEntry(26.7f, "Yellow"));
        pieChartData.add(new PieEntry(24.0f, "Red"));
        pieChartData.add(new PieEntry(30.8f, "Blue"));

        // show pie chart
        PieDataSet pieDataSet = new PieDataSet(pieChartData, "Test");
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(true);
        pieChart.setCenterText("당월 감축량\n2kg");
        pieChart.setRotationEnabled(false);
        pieChart.setTouchEnabled(false);
        pieChart.animateXY(2000,2000);

        /*
         *  Stacked Bar Chart
         */
        barChartData=new ArrayList<>();
        barChartData.add(new BarEntry(0, new float[]{2, 5.5f, 4}));
        barChartData.add(new BarEntry(1, new float[]{2, 5.5f, 4}));
        barChartData.add(new BarEntry(2, new float[]{2, 5.5f, 4}));
        barChartData.add(new BarEntry(3, new float[]{2, 5.5f, 4}));
        barChartData.add(new BarEntry(4, new float[]{2, 5.5f, 4}));

        BarDataSet barDataSet=new BarDataSet(barChartData, "Bar set");
        barDataSet.setColors(ColorTemplate.PASTEL_COLORS);
        BarData barData=new BarData(barDataSet);
        barChart.setData(barData);
        barChart.setTouchEnabled(false);

        // 범례 비활성화
        barChart.getLegend().setEnabled(false);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        barChart.getXAxis().setDrawAxisLine(false);
        barChart.getXAxis().setTextColor(R.color.seco_deepgray);
        barChart.getAxisLeft().setDrawAxisLine(false);
        barChart.getAxisRight().setDrawAxisLine(false);
        barChart.getAxisRight().setDrawLabels(false);
        barChart.getDescription().setEnabled(false);
        barChart.getBarData().setDrawValues(false);

        // TODO: 테스트용 버튼 - 나중에 지우기
        testButton = viewGroup.findViewById(R.id.to_recipe_detail_button);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RecipeDetailActivity.class);
//                intent.putExtra("recycler_position", position);
                getActivity().startActivity(intent);
            }
        });

        return viewGroup;
    }

}