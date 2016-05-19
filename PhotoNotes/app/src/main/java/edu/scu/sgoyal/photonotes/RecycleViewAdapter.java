package edu.scu.sgoyal.photonotes;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by shubhamgoyal on 5/18/16.
 */
public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.PhotoViewHolder>
{

    public CursorAdapter mCursorAdapter;

    public Context mContext;

    public Cursor c;

    public SQLiteDatabase myDB;


    public RecycleViewAdapter(Context context, Cursor c)
    {
        this.c = c;
        this.mCursorAdapter = new CursorAdapter(mContext, c, 0)
        {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent)
            {
                return LayoutInflater.from(context).inflate(R.layout.custom_row, parent, false);

            }

            @Override
            public void bindView(View view, Context context, Cursor cursor)
            {
                String caption = cursor.getString(cursor.getColumnIndex("caption"));
                String path = cursor.getString(cursor.getColumnIndex("imagePath"));

                Log.i("sgoyal", "Bind View");
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 10;
                Bitmap img = BitmapFactory.decodeFile(path, options);

                ((TextView) view.findViewById(R.id.textView2)).setText(caption);
                ((ImageView) view.findViewById(R.id.imageView2)).setImageBitmap(img);

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


    public void onItemDismissed(int position)
    {
        mCursorAdapter.getCursor().moveToPosition(position);
        myDB = myDB.openOrCreateDatabase("shubham", null);
        myDB.delete("photo", "_id=?", new String[]{String.valueOf(position)});
        displayMsg.toast(mContext, "onItemDissmissed");
        notifyItemRemoved(position);
    }

    // called by touch helper callback
//    public boolean onItemMove(int fromPosition, int toPosition) {
//        ContactInfo temp = contactList.get(fromPosition);
//        contactList.set(fromPosition, contactList.get(toPosition));
//        contactList.set(toPosition, temp);
//        notifyItemMoved(fromPosition, toPosition);
//        return true;
//    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder
    {

        TextView caption;
        ImageView imagePath;

        public PhotoViewHolder(View itemView)
        {
            super(itemView);
            this.imagePath = (ImageView) itemView.findViewById(R.id.imageView);
            this.caption = (TextView) itemView.findViewById(R.id.textView);
            if(this.caption != null)
            {
                this.caption.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        String path = c.getString(c.getColumnIndex("caption"));
                        String caption = c.getString(c.getColumnIndex("caption"));
                        final Intent intent = new Intent(mContext, photoDetailActivity.class);
                        intent.putExtra("currentPath", path);
                        intent.putExtra("currentCaption", caption);
                        mContext.startActivity(intent);
                    }
                });
            }

        }


    }


}
