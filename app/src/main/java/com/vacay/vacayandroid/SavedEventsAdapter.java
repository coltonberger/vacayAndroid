package com.vacay.vacayandroid;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SavedEventsAdapter extends RecyclerView.Adapter<SavedEventsAdapter.ViewHolder>{


    private static final String TAG = "SavedEventsAdapter";

    private ArrayList<String> mEventName = new ArrayList<>();
    private ArrayList<String> mEventImage = new ArrayList<>();
    private ArrayList<String> mEventDescription = new ArrayList<>();
    private Context mContext;

    public SavedEventsAdapter(Context context, ArrayList<String> eventName, ArrayList<String> eventDescription, ArrayList<String> images) {
        mEventName = eventName;
        mEventImage = images;
        mEventDescription = eventDescription;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_event_detail, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");
        //Picasso.get().load(appEvent.getEventImage()).error(R.drawable.ic_launcher_background).placeholder(R.drawable.ic_launcher_background)
        holder.eventName.setText(mEventName.get(position));
        holder.eventDescription.setText(mEventDescription.get(position));
    }

    @Override
    public int getItemCount() {
        return mEventName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        CircleImageView image;
        TextView eventName;
        TextView eventDescription;
        RelativeLayout parent_layout;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.event_image);
            eventName = itemView.findViewById(R.id.event_name);
            eventDescription = itemView.findViewById(R.id.event_description);
            parent_layout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
