package space.catking.catking.model;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

import androidx.annotation.NonNull;

public class Token {
    @SerializedName("color")
    private String color;

    @SerializedName("name")
    private String name;

    @SerializedName("amount")
    private BigDecimal amount;

    public String getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @NonNull
    @Override
    public String toString() {
        return "Token{" +
                "color='" + color + '\'' +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                '}';
    }
}
