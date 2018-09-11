package com.vacay.vacayandroid;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SavedEventsAdapter extends RecyclerView.Adapter<SavedEventsAdapter.SavedEventListHolder>{
    List<SavedEvent> savedEvents = new ArrayList<>();

    public void setEvents(List<SavedEvent> appEvents) {
        this.savedEvents = appEvents;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SavedEventListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_saved_schedule, parent, false);
        return new SavedEventListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedEventListHolder holder, final int position) {
        holder.bindTo(savedEvents.get(position)); // that's how to bind
    }

    @Override
    public int getItemCount() {
        return savedEvents.size();
    }

    public void removeItemAt(int position) {
        savedEvents.remove(position);
        notifyDataSetChanged();
    }

    //packages -> adapters, holders, activities, fragments
    class SavedEventListHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //for mainPage
        private ImageView eventImage;
        private TextView eventName;
        private  TextView eventDescription;
        private ImageView delete;
        private SavedEvent mSavedEvent;
        public SavedEventListHolder(View itemView)  {
            super(itemView);
            eventName = itemView.findViewById(R.id.event_name);
            eventImage = itemView.findViewById(R.id.event_image);
            delete = itemView.findViewById(R.id.delete_event);
            eventDescription= itemView.findViewById(R.id.event_description);
            delete.setOnClickListener(this);
        }

        public void bindTo(SavedEvent appEvent) {
            this.mSavedEvent = appEvent;
            eventName.setText(appEvent.getEventName());
            eventDescription.setText(appEvent.getEventDescription());
            Picasso.get().load(appEvent.getEventImage()).error(R.drawable.ic_launcher_background).placeholder(R.drawable.ic_launcher_background).into(eventImage);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.delete_event:
                    //mSavedEvent.getEventId()
                    Toast.makeText(v.getContext(), "full implementation in progress", Toast.LENGTH_SHORT).show();
                   //make delete request here with VOLLEY
                    removeItemAt(getAdapterPosition());
                    //delete event here
                    break;

            }
        }
    }
}
