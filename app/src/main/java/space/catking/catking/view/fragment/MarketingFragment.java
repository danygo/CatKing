package space.catking.catking.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import dagger.hilt.android.AndroidEntryPoint;
import space.catking.catking.R;
import space.catking.catking.databinding.FragmentMarketingBinding;
import space.catking.catking.model.Marketing;
import space.catking.catking.model.Resource;
import space.catking.catking.util.NumberUtils;
import space.catking.catking.util.StringUtils;
import space.catking.catking.view.adapter.ExpenseRecyclerAdapter;
import space.catking.catking.viewmodel.MarketingViewModel;

@AndroidEntryPoint
public class MarketingFragment extends Fragment {
    private Context context;

    private FragmentMarketingBinding binding;

    private ExpenseRecyclerAdapter expenseAdapter;

    private MarketingViewModel marketingViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMarketingBinding.inflate(inflater);

        initView();

        initRecyclerView();

        return binding.getRoot();
    }

    private void initView() {
        binding.chart.setNoDataText("Please wait...");
        binding.chart.setNoDataTextColor(context.getColor(R.color.white));
        binding.chart.invalidate();
        binding.chart.getDescription().setEnabled(false);
        binding.chart.getLegend().setEnabled(false);
        binding.chart.setTouchEnabled(false);
        binding.chart.setDrawHoleEnabled(false);
        binding.chart.setDrawMarkers(false);
        binding.chart.setDrawEntryLabels(false);
    }

    private void initRecyclerView() {
        expenseAdapter = new ExpenseRecyclerAdapter();
        binding.expenseRecycler.setAdapter(expenseAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        marketingViewModel = new ViewModelProvider(this).get(MarketingViewModel.class);

        observe();
    }

    private void observe() {
        marketingViewModel.getMarketing().observe(getViewLifecycleOwner(), this::handleMarketing);

        marketingViewModel.fetchMarketing();
    }

    private void handleMarketing(Resource<Marketing> resource) {
        switch (resource.getStatus()) {
            case OK:
                setMarketingChart(resource.getData());
                break;
            case LOADING:
                break;
            case ERROR:
                break;
        }
    }

    private void setMarketingChart(Marketing marketing) {
        Log.e("MARKETING", marketing.toString());
        binding.chart.setData(createPieData(marketing));
        binding.chart.invalidate();
        binding.chart.animateY(1200, Easing.EaseInOutQuad);
        String text = "<font color=#39040a><b>" + NumberUtils.DECIMAL_FORMATTER_0_00.format(marketing.getCKINGPercentage()) + StringUtils.PERCENTAGE + "</b></font> <font color=#FFFFFF> of supply</font>";
        binding.ckingText.setText(Html.fromHtml(text));
        binding.bnbText.setText(NumberUtils.DECIMAL_FORMATTER_0_00.format(marketing.getBNB()));
        binding.busdText.setText(NumberUtils.DECIMAL_FORMATTER_0_00.format(marketing.getBUSD()));
        expenseAdapter.setExpenses(marketing.getExpenses());
        binding.expensesLayout.setVisibility(View.VISIBLE);
    }

    private PieData createPieData(Marketing marketing) {
        PieData pieData = new PieData(createPieDataSet(marketing));
        pieData.setDrawValues(false);
        return pieData;
    }

    private PieDataSet createPieDataSet(Marketing marketing) {
        PieDataSet pieDataSet = new PieDataSet(createEntries(marketing), "Marketing");
        pieDataSet.setColors(getColors());
        pieDataSet.setSliceSpace(2.5f);
        return pieDataSet;
    }

    private List<Integer> getColors() {
        List<Integer> colors = new ArrayList<>();
        colors.add(context.getColor(R.color.colorPrimary));
        colors.add(context.getColor(R.color.lightGrey));
        return colors;
    }

    private List<PieEntry> createEntries(Marketing marketing) {
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(marketing.getMarketingPercentage(), "Marketing"));
        entries.add(new PieEntry(marketing.getTotalSupplyPercentage(), "Total Supply"));
        return entries;
    }
}
