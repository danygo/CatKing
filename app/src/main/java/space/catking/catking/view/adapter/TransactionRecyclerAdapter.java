package space.catking.catking.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import space.catking.catking.R;
import space.catking.catking.databinding.ItemTransactionBinding;
import space.catking.catking.model.Transaction;
import space.catking.catking.util.NumberUtils;
import space.catking.catking.util.TimeUtils;

public class TransactionRecyclerAdapter extends RecyclerView.Adapter<TransactionRecyclerAdapter.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemTransactionBinding binding;

        public ViewHolder(ItemTransactionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void onBind(Transaction transaction) {
            binding.dateText.setText(TimeUtils.convertDate(transaction.getDate()));
            binding.typeText.setText(transaction.getType());
            binding.typeText.setTextColor(transaction.isSell() ? itemView.getContext().getColor(R.color.red) : itemView.getContext().getColor(R.color.green));
            binding.typeText.setBackgroundTintList(transaction.isSell() ? itemView.getContext().getColorStateList(R.color.holo_red) : itemView.getContext().getColorStateList(R.color.holo_green));
            binding.bnbText.setText(NumberUtils.DECIMAL_FORMATTER_0_0000.format(transaction.getBnb()));
        }
    }

    private List<Transaction> transactions;

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemTransactionBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(transactions.get(position));
    }

    @Override
    public int getItemCount() {
        return transactions == null ? 0 : transactions.size();
    }
}
