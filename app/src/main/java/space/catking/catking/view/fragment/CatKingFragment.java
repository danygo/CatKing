package space.catking.catking.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import dagger.hilt.android.AndroidEntryPoint;
import space.catking.catking.R;
import space.catking.catking.databinding.FragmentCkingBinding;
import space.catking.catking.model.CatKing;
import space.catking.catking.model.Resource;
import space.catking.catking.model.Transaction;
import space.catking.catking.util.NumberUtils;
import space.catking.catking.util.StringUtils;
import space.catking.catking.view.adapter.TransactionRecyclerAdapter;
import space.catking.catking.viewmodel.CatKingViewModel;

@AndroidEntryPoint
public class CatKingFragment extends Fragment {
    private Context context;

    private FragmentCkingBinding binding;

    private TransactionRecyclerAdapter transactionAdapter;

    private CatKingViewModel catKingViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCkingBinding.inflate(inflater);

        initView();

        initRecyclerView();

        return binding.getRoot();
    }

    private void initView() {
        binding.chart.setNoDataText("Please wait...");
        binding.chart.setNoDataTextColor(context.getColor(R.color.white));
        binding.chart.invalidate();

        binding.chart.getDescription().setEnabled(false);
        binding.chart.setDrawGridBackground(false);
        binding.chart.setPinchZoom(false);
        binding.chart.setDragEnabled(false);
        binding.chart.getLegend().setEnabled(false);
        binding.chart.getAxisLeft().setDrawGridLines(false);
        binding.chart.getXAxis().setDrawGridLines(false);
        binding.chart.getAxisRight().setDrawLabels(false);
        binding.chart.getAxisLeft().setDrawLabels(false);
        binding.chart.getAxisRight().setDrawLabels(false);
        binding.chart.getXAxis().setDrawLabels(false);
        binding.chart.getAxisLeft().setEnabled(false);
        binding.chart.getAxisRight().setEnabled(false);
        binding.chart.getXAxis().setEnabled(false);
        binding.chart.setTouchEnabled(false);

        binding.chipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.one_day_chip:
                    setDays(1);
                    break;
                case R.id.seven_days_chip:
                    setDays(7);
                    break;
                case R.id.fourteen_days_chip:
                    setDays(14);
                    break;
                case R.id.one_month_chip:
                    setDays(30);
                    break;
            }
        });
    }

    private void setDays(int days) {
        catKingViewModel.setDays(days);
    }

    private void initRecyclerView() {
        transactionAdapter = new TransactionRecyclerAdapter();
        binding.transactionRecycler.setAdapter(transactionAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        catKingViewModel = new ViewModelProvider(this).get(CatKingViewModel.class);

        observe();
    }

    private void observe() {
        catKingViewModel.getCatKing().observe(getViewLifecycleOwner(), this::handleCatKing);

        setDays(1);
    }

    private void handleCatKing(Resource<CatKing> resource) {
        switch (resource.getStatus()) {
            case OK:
                setCatKing(resource.getData());
                break;
            case LOADING:
                break;
            case ERROR:
                break;
        }
    }

    private void setCatKing(CatKing catKing) {
        Log.e("CKING", catKing.toString());
        Log.e("CKING", catKing.getPricesAsUnscaledBigIntegers().toString());
        Log.e("CKING", catKing.getPricesAsIntegers().toString());
        List<Integer> integers = catKing.getPricesAsIntegers();
        binding.chart.setData(createLineData(catKing.getPricesAsIntegers()));
        binding.chart.invalidate();
        binding.chart.animateXY(600, 1200);
        binding.priceText.setText(catKing.getCurrentPrice());
        setPercentageChange(integers);
        setTransactions(catKing.getTransactions());
        binding.transactionsLayout.setVisibility(View.VISIBLE);
    }

    private LineData createLineData(List<Integer> prices) {
        return new LineData(createLineDataSet(prices));
    }

    private LineDataSet createLineDataSet(List<Integer> prices) {
        LineDataSet lineDataSet = new LineDataSet(createEntries(prices), "entries");
        lineDataSet.setLineWidth(2f);
        lineDataSet.setColor(context.getColor(R.color.colorPrimary));
        lineDataSet.setDrawValues(false);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setCubicIntensity(0.1f);
        return lineDataSet;
    }

    private List<Entry> createEntries(List<Integer> prices) {
        List<Entry> entries = new ArrayList<>();

        for (int i = 0; i < prices.size(); i++) {
            entries.add(new Entry(i, prices.get(i)));
        }

        return entries;
    }

    private void setPercentageChange(List<Integer> prices) {
        if (!prices.isEmpty()) {
            Double percentageChange = getPercentageChange(prices);
            binding.percentageText.setText(getFormattedPercentageChange(percentageChange));
            binding.percentageText.setTextColor(percentageChange < 0 ? context.getColor(R.color.red) : context.getColor(R.color.green));
            binding.percentageText.setBackgroundTintList(percentageChange < 0 ? context.getColorStateList(R.color.holo_red) : context.getColorStateList(R.color.holo_green));
        }
    }

    private String getFormattedPercentageChange(Double percentageChange) {
        return getSign(percentageChange) + NumberUtils.DECIMAL_FORMATTER_0_00.format(percentageChange) + StringUtils.PERCENTAGE;
    }

    private String getSign(Double percentageChange) {
        return percentageChange >= 0 ? StringUtils.PLUS : StringUtils.EMPTY;
    }

    private Double getPercentageChange(List<Integer> prices) {
        Double oldPrice = prices.get(0).doubleValue();
        Double currentPrice = prices.get(prices.size() - 1).doubleValue();
        return ((currentPrice - oldPrice) * 100) / oldPrice;
    }

    private void setTransactions(List<Transaction> transactions) {
        transactionAdapter.setTransactions(transactions);
    }
}

