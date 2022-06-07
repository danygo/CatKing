package space.catking.catking.util;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class NumberUtils {
    public static final Integer MAX_PRECISION = 4;
    public static final Integer MAX_BITS = 9;

    public static final DecimalFormat DECIMAL_FORMATTER_0_0 = new DecimalFormat("0.0", new DecimalFormatSymbols(Locale.US));
    public static final DecimalFormat DECIMAL_FORMATTER_0_00 = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US));
    public static final DecimalFormat DECIMAL_FORMATTER_0_0000 = new DecimalFormat("0.0000", new DecimalFormatSymbols(Locale.US));
    public static final DecimalFormat DECIMAL_FORMATTER_0_000000000000 = new DecimalFormat("0.000000000000", new DecimalFormatSymbols(Locale.US));

    public static Integer bigIntegerToInteger(BigInteger bigInteger) {
        String bigIntegerString = bigInteger.toString();
        int length = bigIntegerString.length();

        if (length > MAX_PRECISION) {
            String mainPart = bigIntegerString.substring(0, MAX_PRECISION);
            String zeroPart = getZeros(length - MAX_BITS);
            BigInteger newBigInteger = new BigInteger(mainPart + zeroPart);
            return newBigInteger.intValue();
        }

        return bigInteger.intValue();
    }

    public static String getZeros(int zeros) {
        StringBuilder s = new StringBuilder();

        for (int i = 0; i <= zeros; i++) {
            s.append("0");
        }

        return s.toString();
    }
}
