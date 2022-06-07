package space.catking.catking.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import dagger.hilt.android.AndroidEntryPoint;
import space.catking.catking.databinding.FragmentHomeBinding;
import space.catking.catking.manager.EthereumAddressManager;
import space.catking.catking.util.DisplayUtils;
import space.catking.catking.util.EthereumUtils;
import space.catking.catking.viewmodel.WalletViewModel;

@AndroidEntryPoint
public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";

    private Context context;

    private FragmentManager fragmentManager;

    private EthereumAddressManager ethereumAddressManager;

    private FragmentHomeBinding binding;

    private WalletViewModel walletViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getContext();

        fragmentManager = getChildFragmentManager();

        ethereumAddressManager = new EthereumAddressManager(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater);

        initView();

        return binding.getRoot();
    }

    private void initView() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.profileLogoCard, (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            mlp.topMargin = insets.top + DisplayUtils.dpToPx(context, 16);
            v.setLayoutParams(mlp);
            return WindowInsetsCompat.CONSUMED;
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        manageEthereumAddress();
    }

    private void manageEthereumAddress() {
        if (ethereumAddressManager.getEthereumAddress() == null) {
            openLoginFragment();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        walletViewModel = new ViewModelProvider(requireActivity()).get(WalletViewModel.class);

        observe();
    }

    private void observe() {
        walletViewModel.setAddress(ethereumAddressManager.getEthereumAddress());

        walletViewModel.getAddress().observe(getViewLifecycleOwner(), address -> {
            if (address != null) {
                binding.addressText.append(EthereumUtils.getShortAddress(address));
            }
        });
    }

    private void openLoginFragment() {
        LoginFragment loginFragment = new LoginFragment();
        loginFragment.show(fragmentManager, TAG);
    }
}

