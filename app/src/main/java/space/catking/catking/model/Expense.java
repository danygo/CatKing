package space.catking.catking.model;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

import androidx.annotation.NonNull;

public class Expense {
    @SerializedName("date")
    private String date;

    @SerializedName("description")
    private String description;

    @SerializedName("value")
    private BigDecimal value;

    @SerializedName("asset")
    private String asset;

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getAsset() {
        return asset;
    }

    @NonNull
    @Override
    public String toString() {
        return "Expense{" +
                "date='" + date + '\'' +
                ", description='" + description + '\'' +
                ", value=" + value +
                ", asset='" + asset + '\'' +
                '}';
    }
}
