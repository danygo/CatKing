package space.catking.catking.view.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import space.catking.catking.databinding.ItemTokenBinding;
import space.catking.catking.model.Token;
import space.catking.catking.util.NumberUtils;

public class TokenRecyclerAdapter extends RecyclerView.Adapter<TokenRecyclerAdapter.ViewHolder> {
    private List<Token> tokens;

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemTokenBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(tokens.get(position));
    }

    @Override
    public int getItemCount() {
        return tokens == null ? 0 : tokens.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemTokenBinding binding;

        public ViewHolder(ItemTokenBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void onBind(Token token) {
            binding.logoCard.setCardBackgroundColor(Color.parseColor(token.getColor()));
            binding.nameText.setText(token.getName());
            binding.amountText.setText(NumberUtils.DECIMAL_FORMATTER_0_0000.format(token.getAmount()));
        }
    }
}
