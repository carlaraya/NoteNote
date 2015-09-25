package com.example.carl.notenote;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by icecream on 9/13/15.
 *
 * I DO NOT UNDERSTAND HOW THIS THING WORKS
 *
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private List<Note> dataset;

    public static class ViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener, View.OnLongClickListener {
        public CardView cardView;
        public TextView titleTextView;
        public TextView contentTextView;

        private ClickListener clickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            titleTextView = (TextView) itemView.findViewById(R.id.note_title);
            contentTextView = (TextView) itemView.findViewById(R.id.note_content);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onClick(v, getAdapterPosition(), true);
            return false;
        }

        public interface ClickListener {
            void onClick(View v, int position, boolean isLongClick);
        }

        public void setClickListener(ClickListener clickListener) {
            this.clickListener = clickListener;
        }
    }

    public NoteAdapter(List<Note> dataset) {
        this.dataset = dataset;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_layout, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, int i) {
        vh.titleTextView.setText(dataset.get(i).title);
        vh.contentTextView.setText(dataset.get(i).content);
        vh.setClickListener(new NoteClickListener());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    private void editNote(View v, int pos) {
        Context c = v.getContext();

        Intent intent = new Intent(c, Compose.class);
        intent.putExtra(c.getString(R.string.extra_note_id), dataset.get(pos).id);

        c.startActivity(intent);
    }

    public class NoteClickListener implements ViewHolder.ClickListener {
        public int index;

        @Override
        public void onClick(View v, int pos, boolean isLongClick) {
            if (isLongClick) {
                Log.d("NOTENOTE", "longclick");
            } else {
                Log.d("NOTENOTE", "tap");
                editNote(v, pos);
            }
        }
    }
}
