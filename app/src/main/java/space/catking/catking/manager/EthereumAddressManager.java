package space.catking.catking.manager;

import android.content.Context;
import android.content.SharedPreferences;

public class EthereumAddressManager {
    private static final String ETHEREUM_PREFERENCES = "ETHEREUM_PREFERENCES";
    private static final String ETHEREUM_ADDRESS = "ETHEREUM_ADDRESS";

    private final SharedPreferences preferences;

    public EthereumAddressManager(Context context) {
        preferences = context.getSharedPreferences(ETHEREUM_PREFERENCES, 0);
    }

    public String getEthereumAddress() {
        return preferences.getString(ETHEREUM_ADDRESS, null);
    }

    public void setEthereumAddress(String address) {
        preferences.edit()
                .putString(ETHEREUM_ADDRESS, address)
                .apply();
    }
}
