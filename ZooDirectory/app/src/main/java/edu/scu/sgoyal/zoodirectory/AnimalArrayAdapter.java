package edu.scu.sgoyal.zoodirectory;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by shubhamgoyal on 4/29/16.
 */
public class AnimalArrayAdapter extends ArrayAdapter<Animal> {

    private final List<Animal> animals;
    private Context ctxt;

    public AnimalArrayAdapter(Context context, int resource, List<Animal> animals) {
        super(context, resource,animals);
        this.animals = animals;
        this.ctxt = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final ScrapViewHolder holder;
        View row = convertView;
        if(row == null)
        {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.animal_list_element, parent, false);
            holder = new ScrapViewHolder();
            holder.icon = (ImageView) row.findViewById(R.id.icon);
            holder.label = (TextView) row.findViewById(R.id.label);
            holder.menu = (ImageButton) row.findViewById(R.id.menu);

            row.setTag(holder);
        }
        else
        {
            holder = (ScrapViewHolder) row.getTag();
        }
        if(position%2==0) {
            row.setBackgroundColor(Color.LTGRAY);
        }
        else{
            row.setBackgroundColor(Color.DKGRAY);
        }

        holder.label.setText(animals.get(position).getName());
        try {
            String filename = animals.get(position).getFilename();
            InputStream inputStream = getContext().getAssets().open(filename);
            Drawable drawable = Drawable.createFromStream(inputStream, null);
            holder.icon.setImageDrawable(drawable);
            holder.icon.setScaleType(ImageView.ScaleType.CENTER_CROP);

        } catch (IOException e) {
            e.printStackTrace();
        }

        if(holder.fav)
        {
            holder.menu.setImageResource(R.drawable.favourite);
        }
        else
        {
            holder.menu.setImageResource(R.drawable.not_favourite);
        }

        holder.menu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                holder.fav = !holder.fav;
                if(holder.fav)
                {
                    holder.menu.setImageResource(R.drawable.favourite);
                }
                else
                {
                    holder.menu.setImageResource(R.drawable.not_favourite);
                }
            }
        });
        return row;

    }

    public class ScrapViewHolder {
        TextView label;
        ImageView icon;
        ImageButton menu;
        boolean fav = false;
    }


}
