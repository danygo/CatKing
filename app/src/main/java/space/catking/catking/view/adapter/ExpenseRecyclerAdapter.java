package space.catking.catking.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import space.catking.catking.databinding.ItemExpenseBinding;
import space.catking.catking.model.Expense;
import space.catking.catking.util.NumberUtils;
import space.catking.catking.util.StringUtils;

public class ExpenseRecyclerAdapter extends RecyclerView.Adapter<ExpenseRecyclerAdapter.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemExpenseBinding binding;

        public ViewHolder(ItemExpenseBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void onBind(Expense expense) {
            binding.dateText.setText(expense.getDate());
            binding.descriptionText.setText(expense.getDescription());
            binding.valueText.setText(NumberUtils.DECIMAL_FORMATTER_0_00.format(expense.getValue()) + StringUtils.SPACE + expense.getAsset());
        }
    }

    private List<Expense> expenses;

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemExpenseBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(expenses.get(position));
    }

    @Override
    public int getItemCount() {
        return expenses == null ? 0 : expenses.size();
    }
}
