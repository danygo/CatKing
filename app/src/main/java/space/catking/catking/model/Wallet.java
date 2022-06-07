package space.catking.catking.model;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.List;

import androidx.annotation.NonNull;

public class Wallet {
    @SerializedName("cking_percentage")
    private BigDecimal catKingPercentage;

    @SerializedName("bnb")
    private BigDecimal bnb;

    @SerializedName("busd")
    private BigDecimal busd;

    @SerializedName("tokens")
    private List<Token> tokens;

    public BigDecimal getCatKingPercentage() {
        return catKingPercentage;
    }

    public BigDecimal getBnb() {
        return bnb;
    }

    public BigDecimal getBusd() {
        return busd;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    @NonNull
    @Override
    public String toString() {
        return "Account{" +
                "ckingPercentage=" + catKingPercentage +
                ", bnb=" + bnb +
                ", busd=" + busd +
                ", tokens=" + tokens +
                '}';
    }
}
