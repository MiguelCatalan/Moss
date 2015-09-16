package com.miguelcatalan.android.views.adapters;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.miguelcatalan.android.R;
import com.miguelcatalan.android.utils.RecyclerViewClickListener;
import com.miguelcatalan.model.entities.Book;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Miguel Catalan Bañuls
 */
public class BooksAdapter extends RecyclerView.Adapter<BookViewHolder> {

    private Context mContext;
    private List<Book> mBookList;
    private RecyclerViewClickListener mRecyclerClickListener;

    public BooksAdapter() {
        mBookList = new ArrayList<>();
    }

    public void setBookList(List<Book> bookList) {
        if (bookList != null) {
            mBookList = bookList;
            notifyDataSetChanged();
        }
    }

    public List<Book> getBookList() {

        return mBookList;
    }

    public void setRecyclerListListener(RecyclerViewClickListener mRecyclerClickListener) {
        this.mRecyclerClickListener = mRecyclerClickListener;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View rowView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_book_grid, viewGroup, false);

        this.mContext = viewGroup.getContext();

        return new BookViewHolder(rowView, mRecyclerClickListener);
    }

    @Override
    public void onBindViewHolder(final BookViewHolder holder, final int position) {

        Book selectedBook = mBookList.get(position);

        holder.titleTextView.setText(selectedBook.getTitle());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            holder.coverImageView.setTransitionName("cover" + position);

        Picasso.with(mContext)
                .load(selectedBook.getImage())
                .fit()
                .centerCrop()
                .into(holder.coverImageView);
    }

    public boolean isBookReady(int position) {

        return mBookList.get(position).isBookReady();
    }

    @Override
    public int getItemCount() {

        return mBookList.size();
    }

    public void appendBooks(List<Book> bookList) {
        if (bookList != null) {
            mBookList.addAll(bookList);
            notifyDataSetChanged();
        }
    }

    public void clearBooks() {
        mBookList = new ArrayList<>();
        notifyDataSetChanged();
    }
}

class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final RecyclerViewClickListener onClickListener;

    TextView titleTextView;
    TextView releaseTextView;
    ImageView coverImageView;
    CardView container;

    public BookViewHolder(View itemView, RecyclerViewClickListener onClickListener) {

        super(itemView);
        container = (CardView) itemView.findViewById(R.id.item_book_container);
        titleTextView = (TextView) itemView.findViewById(R.id.item_book_title);
        releaseTextView = (TextView) itemView.findViewById(R.id.item_book_release);
        coverImageView = (ImageView) itemView.findViewById(R.id.item_book_cover);
        container.setOnClickListener(this);
        coverImageView.setDrawingCacheEnabled(true);
        //coverImageView.setOnTouchListener(this);
        this.onClickListener = onClickListener;
    }

/*
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_UP && event.getAction() != MotionEvent.ACTION_MOVE) {

            onClickListener.onClick(v, getPosition(), event.getX(), event.getY());
        }
        return true;
    }*/

    @Override
    public void onClick(View view) {
        onClickListener.onClick(view, getPosition(), 0, 0);
    }
}