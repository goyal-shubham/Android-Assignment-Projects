package edu.scu.sgoyal.photonotes;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by shubhamgoyal on 5/18/16.
 */
public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.PhotoViewHolder>
{

    public CursorAdapter mCursorAdapter;

    public Context mContext;

    public Cursor c;


    public RecycleViewAdapter(Context context, Cursor c)
    {
        this.c = c;
        this.mCursorAdapter = new CursorAdapter(mContext, c, 0)
        {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent)
            {
                return LayoutInflater.from(context).inflate(R.layout.cutome_row, parent, false);

            }

            @Override
            public void bindView(View view, Context context, Cursor cursor)
            {
                String caption = cursor.getString(cursor.getColumnIndex("caption"));
                String path = cursor.getString(cursor.getColumnIndex("imagePath"));

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 10;
                Bitmap img = BitmapFactory.decodeFile(path, options);

                ((TextView)view.findViewById(R.id.textView2)).setText(caption);
                ((ImageView)view.findViewById(R.id.imageView2)).setImageBitmap (img);

            }
        };
        this.mContext = context;

    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = mCursorAdapter.newView(mContext, mCursorAdapter.getCursor(), parent);
        return new PhotoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position)
    {
        mCursorAdapter.getCursor().moveToPosition(position);
        mCursorAdapter.bindView(holder.itemView, mContext, mCursorAdapter.getCursor());

    }

    @Override
    public int getItemCount()
    {
        return mCursorAdapter.getCount();
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {

        TextView caption;
        ImageView imagePath;

        public PhotoViewHolder(View itemView)
        {
            super(itemView);
            this.imagePath = (ImageView) itemView.findViewById(R.id.imageView);
            this.caption = (TextView) itemView.findViewById(R.id.textView);
        }


    }


}
