package space.catking.catking.util;

public class EthereumUtils {
    private static final String ETHEREUM_REGEX = "^0x[0-9a-fA-F]{40}$";

    public static boolean isValidAddress(String address) {
        return address.matches(ETHEREUM_REGEX);
    }

    public static String getShortAddress(String address) {
        String start = address.substring(0, 5 );
        String end = address.substring(address.length() - 4);
        return start + StringUtils.THREE_DOTS + end;
    }
}
