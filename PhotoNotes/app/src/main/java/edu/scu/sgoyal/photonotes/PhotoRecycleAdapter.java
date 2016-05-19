package edu.scu.sgoyal.photonotes;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by shubhamgoyal on 5/18/16.
 */
public class PhotoRecycleAdapter extends RecyclerView.Adapter<PhotoRecycleAdapter.ContactViewHolder>
{
    public PhotoRecycleAdapter(Context context, Cursor c, int flags)
    {
        this.photoList = photoList;
    }

    private List<viewPhotoDetails> photoList;


    public boolean onItemMove(int fromPosition, int toPosition) {
        viewPhotoDetails temp = photoList.get(fromPosition);
        photoList.set(fromPosition, photoList.get(toPosition));
        photoList.set(toPosition, temp);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    public void onItemDismissed(int position) {
        photoList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.custom_row, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position)
    {

    }

    @Override
    public int getItemCount()
    {
        return photoList.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {

        String caption;
        String imagePath;

        public ContactViewHolder(View itemView)
        {
            super(itemView);
            this.caption = caption;
            this.imagePath = imagePath;
        }


    }


}
