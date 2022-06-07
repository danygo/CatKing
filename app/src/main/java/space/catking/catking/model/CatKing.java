package space.catking.catking.model;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import space.catking.catking.util.NumberUtils;
import space.catking.catking.util.StringUtils;

public class CatKing {
    @SerializedName("prices")
    private List<BigDecimal> prices;

    @SerializedName("transactions")
    private List<Transaction> transactions;

    public List<BigDecimal> getPrices() {
        return prices;
    }

    public List<BigInteger> getPricesAsUnscaledBigIntegers() {
        List<BigInteger> bigIntegers = new ArrayList<>();

        for (BigDecimal bigDecimal : prices) {
            bigIntegers.add(bigDecimal.unscaledValue());
        }

        return bigIntegers;
    }

    public List<Integer> getPricesAsIntegers() {
        List<Integer> integers = new ArrayList<>();

        for (BigInteger bigInteger : getPricesAsUnscaledBigIntegers()) {
            integers.add(NumberUtils.bigIntegerToInteger(bigInteger));
        }

        return integers;
    }

    public List<Double> getPricesAsDouble() {
        List<Double> doubles = new ArrayList<>();

        for (BigDecimal bigDecimal : prices) {
            doubles.add(bigDecimal.doubleValue());
        }

        return doubles;
    }

    public String getCurrentPrice() {
        if (!prices.isEmpty()) {
            return NumberUtils.DECIMAL_FORMATTER_0_000000000000.format(prices.get(prices.size() - 1));
        }

        return StringUtils.EMPTY;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    @NonNull
    @Override
    public String toString() {
        return "CatKing{" +
                "prices=" + prices +
                ", transactions=" + transactions +
                '}';
    }
}
