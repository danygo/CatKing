package space.catking.catking.model;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

import androidx.annotation.NonNull;

public class Transaction {
    @SerializedName("date")
    private String date;

    @SerializedName("type")
    private String type;

    @SerializedName("bnb")
    private BigDecimal bnb;

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public boolean isSell() {
        return type.equals("SELL");
    }

    public BigDecimal getBnb() {
        return bnb;
    }

    @NonNull
    @Override
    public String toString() {
        return "Transaction{" +
                "date='" + date + '\'' +
                ", type='" + type + '\'' +
                ", bnb='" + bnb + '\'' +
                '}';
    }
}
