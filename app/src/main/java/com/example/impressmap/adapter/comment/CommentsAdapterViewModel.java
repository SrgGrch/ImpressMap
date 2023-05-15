package com.example.impressmap.adapter.comment;

import androidx.lifecycle.ViewModel;

import com.example.impressmap.model.data.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentsAdapterViewModel extends ViewModel
{
    private final List<Comment> commentsCache;

    public CommentsAdapterViewModel()
    {
        commentsCache = new ArrayList<>();
    }

    public boolean addComment(Comment comment)
    {
        commentsCache.add(comment);
        return true;
    }

    public Comment getComment(int index)
    {
        return commentsCache.get(index);
    }

    public int getCommentsCount()
    {
        return commentsCache.size();
    }

    public void clearCache()
    {
        commentsCache.clear();
    }
}
