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
    private List<AppEvent> events = new ArrayList<>();

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

        holder.discoverMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("APP_DEBUG", "The item " + position + " has been clicked");

                Intent intent  = new Intent( v.getContext() , EventDetail.class);
                intent.putExtra("EVENT_NAME", events.get(position).getEventName());
                intent.putExtra("EVENT_DESCRIPTION", events.get(position).getEventDescription());
                intent.putExtra("EVENT_CITY", events.get(position).getEventCity());


                v.getContext().startActivity(intent);
            }
        });


        //add onclick listeners here for buttons


    }

    @Override
    public int getItemCount() {
        return events.size();
    }


    //packages -> adapters, holders, activities, fragments
    class EventListHolder extends RecyclerView.ViewHolder {
        //for home display
        private TextView eventTitle;
        private ImageView eventImage;
        private Button discoverMoreButton;

        //for detailed
//            private TextView eventName;
//            private TextView eventDescription;
//            private TextView eventCity;
//            private TextView eventPrice;
//            private TextView eventWebsite;

        public EventListHolder(View itemView) {
            super(itemView);
            //for mainPage
            eventTitle = (TextView) itemView.findViewById(R.id.event_title);
            eventImage = (ImageView) itemView.findViewById(R.id.event_image);
            discoverMoreButton = itemView.findViewById(R.id.discover_more);

            //for detailed
//                eventName = (TextView) itemView.findViewById(R.id.event_name);
//                eventDescription = (TextView) itemView.findViewById(R.id.event_description);
//                eventCity = (TextView) itemView.findViewById(R.id.event_city);
//                eventPrice = (TextView) itemView.findViewById(R.id.event_price);
//                eventWebsite = (TextView) itemView.findViewById(R.id.event_website);

        }

        public void bindTo(AppEvent appEvent) {
            eventTitle.setText(appEvent.getEventName());
            Picasso.get().load(appEvent.getEventImage()).error(R.drawable.ic_launcher_background).placeholder(R.drawable.ic_launcher_background).into(eventImage);
        }


    }
}