package com.vacay.vacayandroid;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventListHolder> {
    List<AppEvent> events = new ArrayList<>();

    public void setEvents(List<AppEvent> appEvents) {
        this.events = appEvents;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EventListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_item_event, parent, false);
        return new EventListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventListHolder holder, final int position) {
        holder.bindTo(events.get(position)); // that's how to bind
    }

    @Override
    public int getItemCount() {
        return events.size();
    }


    //packages -> adapters, holders, activities, fragments
    class EventListHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //for mainPage
        private TextView eventTitle;
        private ImageView eventImage;
        private Button discoverMoreButton;

        public EventListHolder(View itemView) {
            super(itemView);
            //for mainPage
            eventTitle = (TextView) itemView.findViewById(R.id.event_title);
            eventImage = (ImageView) itemView.findViewById(R.id.event_image);
            discoverMoreButton = itemView.findViewById(R.id.discover_more);
            discoverMoreButton.setOnClickListener(this);
        }

        public void bindTo(AppEvent appEvent) {
            eventTitle.setText(appEvent.getEventName());
            Picasso.get().load(appEvent.getEventImage()).error(R.drawable.ic_launcher_background).placeholder(R.drawable.ic_launcher_background).into(eventImage);
        }

        private void prepareIntent(Intent intent) {
            AppEvent appEvent = events.get(getAdapterPosition());

            intent.putExtra(Constants.EVENT_NAME, appEvent.getEventName());
            intent.putExtra(Constants.EVENT_DESCRIPTION, appEvent.getEventDescription());
            intent.putExtra(Constants.EVENT_CITY, appEvent.getEventCity());
            intent.putExtra(Constants.EVENT_PRICE, appEvent.getEventPrice());
            intent.putExtra(Constants.EVENT_WEBSITE, appEvent.getEventWebsite());
            intent.putExtra(Constants.EVENT_IMAGE, appEvent.getEventImage());
        }

        @Override
        public void onClick(View v) {
           switch (v.getId()) {
               case R.id.discover_more:
                   Intent intent  = new Intent(v.getContext() , EventDetail.class);
                   prepareIntent(intent);
                   discoverMoreButton.getContext().startActivity(intent);
                   break;

               case R.id.add_to_schedule:
                  /// Intent addToScheduleIntent  = new Intent(v.getContext() , AddToSchedule.class);
                   break;

           }
        }
    }
}