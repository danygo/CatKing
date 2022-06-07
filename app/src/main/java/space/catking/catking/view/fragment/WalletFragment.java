package space.catking.catking.view.fragment;

import android.animation.LayoutTransition;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import dagger.hilt.android.AndroidEntryPoint;
import space.catking.catking.R;
import space.catking.catking.databinding.FragmentWalletBinding;
import space.catking.catking.manager.EthereumAddressManager;
import space.catking.catking.model.Resource;
import space.catking.catking.model.Wallet;
import space.catking.catking.util.DisplayUtils;
import space.catking.catking.util.EthereumUtils;
import space.catking.catking.util.NumberUtils;
import space.catking.catking.util.StringUtils;
import space.catking.catking.view.adapter.TokenRecyclerAdapter;
import space.catking.catking.viewmodel.WalletViewModel;

@AndroidEntryPoint
public class WalletFragment extends Fragment {
    private Context context;

    private EthereumAddressManager ethereumAddressManager;

    private FragmentWalletBinding binding;

    private BottomNavigationView bottomNavigationView;

    private TokenRecyclerAdapter adapter;

    private WalletViewModel walletViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getContext();

        ethereumAddressManager = new EthereumAddressManager(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentWalletBinding.inflate(inflater);

        initView();

        initRecyclerView();

        return binding.getRoot();
    }

    private void initRecyclerView() {
        adapter = new TokenRecyclerAdapter();
        binding.tokenRecycler.setAdapter(adapter);
    }

    private void initView() {
        binding.mainLayout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

        bottomNavigationView = getActivity().findViewById(R.id.main_activity_bottom_nav);

        binding.addressText.setOnClickListener(v -> {
            copyAddress();
            showToast("Copied to clipboard");
        });

        ViewCompat.setOnApplyWindowInsetsListener(binding.moreButton, (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            mlp.topMargin = insets.top + DisplayUtils.dpToPx(context, 16);
            v.setLayoutParams(mlp);
            return WindowInsetsCompat.CONSUMED;
        });

        binding.moreButton.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(context, v);
            popupMenu.getMenuInflater().inflate(R.menu.account_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {
                logout();
                openHomeFragment();
                return false;
            });
            popupMenu.show();
        });
    }

    private void copyAddress() {
        ClipboardManager clipboard = (ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("address", walletViewModel.getAddress().getValue());
        clipboard.setPrimaryClip(clip);
    }

    private void logout() {
        ethereumAddressManager.setEthereumAddress(null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        walletViewModel = new ViewModelProvider(requireActivity()).get(WalletViewModel.class);

        observe();
    }

    private void observe() {
        walletViewModel.getAddress().observe(getViewLifecycleOwner(), address -> {
            if (address != null) {
                binding.addressText.setText(EthereumUtils.getShortAddress(address));
            }
        });

        walletViewModel.getWallet().observe(getViewLifecycleOwner(), this::handleWallet);
    }

    private void handleWallet(Resource<Wallet> resource) {
        switch (resource.getStatus()) {
            case OK:
                setWallet(resource.getData());
                break;
            case LOADING:
                break;
            case ERROR:
                break;
        }
    }

    private void setWallet(Wallet wallet) {
        binding.bnbAmountText.setText(NumberUtils.DECIMAL_FORMATTER_0_0000.format(wallet.getBnb()));
        binding.ckingAmountText.setText(NumberUtils.DECIMAL_FORMATTER_0_0000.format(wallet.getCatKingPercentage()) + StringUtils.SPACE + StringUtils.PERCENTAGE);
        binding.busdAmountText.setText(NumberUtils.DECIMAL_FORMATTER_0_0000.format(wallet.getBusd()));
        adapter.setTokens(wallet.getTokens());
    }

    private void openHomeFragment() {
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
    }

    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
