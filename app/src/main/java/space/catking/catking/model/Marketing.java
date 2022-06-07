package space.catking.catking.model;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.List;

import androidx.annotation.NonNull;
import space.catking.catking.util.NumberUtils;

public class Marketing {
    @SerializedName("cking")
    private BigDecimal cking;

    @SerializedName("cking_percentage")
    private BigDecimal ckingPercentage;

    @SerializedName("bnb")
    private BigDecimal bnb;

    @SerializedName("busd")
    private BigDecimal busd;

    @SerializedName("expenses")
    private List<Expense> expenses;

    public BigDecimal getCKING() {
        return cking;
    }

    public BigDecimal getCKINGPercentage() {
        return ckingPercentage;
    }

    public BigDecimal getBNB() {
        return bnb;
    }

    public BigDecimal getBUSD() {
        return busd;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public Float getMarketingPercentage() {
        return Float.valueOf(NumberUtils.DECIMAL_FORMATTER_0_00.format(ckingPercentage));
    }

    public float getTotalSupplyPercentage() {
        return 100.00f - getMarketingPercentage();
    }

    @NonNull
    @Override
    public String toString() {
        return "Marketing{" +
                "cking=" + cking +
                ", ckingPercentage=" + ckingPercentage +
                ", bnb=" + bnb +
                ", usdt=" + busd +
                ", expenses=" + expenses +
                '}';
    }
}
