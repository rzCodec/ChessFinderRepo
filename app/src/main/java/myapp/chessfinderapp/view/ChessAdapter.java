package myapp.chessfinderapp.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import myapp.chessfinderapp.R;
import myapp.chessfinderapp.repository.ChessUser;

public class ChessAdapter extends RecyclerView.Adapter<ChessAdapter.MyViewHolder> {
    private final Context context;
    private ArrayList<ChessUser> items;

    public ChessAdapter(ArrayList<ChessUser> items, Context context) {
        this.items = items;
        this.context = context;
    }

    /*
     * Constructor to create the card
     * The layout is a layout file which should be the card
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.material_card, parent, false);
        return new MyViewHolder(v);
    }

    /*
     *Method to handle displaying and showing of text views, etc
     */
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ChessUser item = items.get(position);
        //TODO Fill in your logic for binding the view.
    }

    /*
     *Method to return the size of the list of items
     */
    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    //Inner class to find the components on the XML file
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View v) {
            super(v);
        }
    }
}//end of class