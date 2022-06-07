package space.catking.catking.view.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import space.catking.catking.R;
import space.catking.catking.databinding.FragmentLoginBinding;
import space.catking.catking.manager.EthereumAddressManager;
import space.catking.catking.util.EthereumUtils;
import space.catking.catking.viewmodel.WalletViewModel;

public class LoginFragment extends DialogFragment {
    private Context context;

    private EthereumAddressManager ethereumAddressManager;

    private FragmentLoginBinding binding;

    private WalletViewModel walletViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setCancelable(false);

        setStyle(STYLE_NORMAL, R.style.FullScreenDialogTheme);

        context = getContext();

        ethereumAddressManager = new EthereumAddressManager(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater);

        initView();

        return binding.getRoot();
    }

    private void initView() {
        binding.rememberText.setOnClickListener(v -> binding.rememberCheck.setChecked(!binding.rememberCheck.isChecked()));

        binding.loginButton.setOnClickListener(v -> checkEthereumAddress());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        walletViewModel = new ViewModelProvider(requireActivity()).get(WalletViewModel.class);
    }

    private void checkEthereumAddress() {
        String address = binding.ethereumAddressField.getText().toString();

        if (address.isEmpty()) {
            binding.ethereumAddressField.setError("Field is empty");
            playWalletAddressFieldErrorAnim();
            return;
        }

        if (!EthereumUtils.isValidAddress(address)) {
            binding.ethereumAddressField.setError("Invalid Wallet address");
            playWalletAddressFieldErrorAnim();
            return;
        }

        loginEthereumAddress(address);
        dismiss();
    }

    private void loginEthereumAddress(String address) {
        if (binding.rememberCheck.isChecked()) {
            saveEthereumAddress(address);
        }

        setEthereumAddress(address);
    }

    private void saveEthereumAddress(String address) {
        ethereumAddressManager.setEthereumAddress(address);
    }

    private void setEthereumAddress(String address) {
        walletViewModel.setAddress(address);
    }

    private void playWalletAddressFieldErrorAnim() {
        binding.ethereumAddressField.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_shake));
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), getTheme()) {
            @Override
            public void onBackPressed() {
                requireActivity().finish();
            }
        };
    }
}
