package com.example.impressmap.adapter.comment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.impressmap.databinding.ItemCommentBinding;
import com.example.impressmap.model.data.Comment;

import java.text.DateFormat;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder>
{
    private final CommentsAdapterViewModel viewModel;

    private OnCommentsButtonClickListener onCommentsButtonClickListener;

    public CommentsAdapter(ViewModelStoreOwner viewModelStoreOwner)
    {
        viewModel = new ViewModelProvider(viewModelStoreOwner).get(CommentsAdapterViewModel.class);
        clear();
    }

    @NonNull
    @Override
    public CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                 int viewType)
    {
        return new CommentsViewHolder(
                ItemCommentBinding.inflate(LayoutInflater.from(parent.getContext()), parent,
                        false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsViewHolder holder,
                                 int position)
    {
        Comment comment = viewModel.getComment(position);

        holder.binding.textView.setText(comment.getText());
        holder.binding.fullNameView.setText(comment.getOwnerUser().getFullName());
        String dateString = DateFormat.getDateInstance(DateFormat.DATE_FIELD)
                                      .format(comment.getDate());
        holder.binding.dateView.setText(dateString);

        /*holder.binding.showReactionsButton.setOnClickListener(v ->
        {

        });*/

        holder.binding.showCommentsButton.setOnClickListener(v ->
        {
            onCommentsButtonClickListener.onClick(v, comment);
        });
    }

    public void setOnCommentsButtonClickListener(OnCommentsButtonClickListener listener)
    {
        onCommentsButtonClickListener = listener;
    }

    @Override
    public int getItemCount()
    {
        return viewModel.getCommentsCount();
    }

    public void addComment(Comment comment)
    {
        viewModel.addComment(comment);
        notifyItemInserted(getItemCount());
    }

    public void clear()
    {
        int size = getItemCount();
        viewModel.clearCache();
        notifyItemRangeRemoved(0, size);
    }

    public interface OnCommentsButtonClickListener
    {
        void onClick(View view,
                     Comment comment);
    }

    protected static class CommentsViewHolder extends RecyclerView.ViewHolder
    {
        private final ItemCommentBinding binding;

        public CommentsViewHolder(ItemCommentBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
