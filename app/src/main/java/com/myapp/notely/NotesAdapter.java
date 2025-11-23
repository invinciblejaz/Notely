package com.myapp.notely;
import com.google.firebase.Timestamp;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    public interface OnNoteClickListener {
        void onNoteClick(Note note);
    }

    private final List<Note> notes;
    private final LayoutInflater inflater;
    private final OnNoteClickListener listener;

    public NotesAdapter(Context context, List<Note> notes, OnNoteClickListener listener) {

        this.inflater = LayoutInflater.from(context);
        this.notes = notes;
        this.listener = listener;
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {

        TextView title, content, time;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvNoteTitle);
            content = itemView.findViewById(R.id.tvNoteContent);
            time = itemView.findViewById(R.id.tvNoteTime);
        }
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notes.get(position);

        holder.title.setText(note.getTitle());
        holder.content.setText(note.getContent());

        Timestamp ts = note.getTimestamp();
        if (ts != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault());
            holder.time.setText(sdf.format(ts.toDate()));
        } else {
            holder.time.setText("");
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onNoteClick(note);
            }
        });
    }



    @Override
    public int getItemCount() {
        return notes == null ? 0 : notes.size();
    }
}
