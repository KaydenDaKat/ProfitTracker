package com.example.profittracker;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.List;

public class ListViewAdapter extends ArrayAdapter<MainCellItemClass> {

    Context context;
    int resource;
    List<MainCellItemClass> cellItemList;

    public ListViewAdapter(@NonNull Context context, int resource, List<MainCellItemClass> cellItemList) {
        super(context, resource, cellItemList);
        this.context = context;
        this.resource = resource;
        this.cellItemList = cellItemList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

       // if (convertView == null){
            convertView = inflater.inflate(resource, parent, false);
       // }

            TextView textViewCellName = convertView.findViewById(R.id.textView_cellName_itemCell);
            TextView textViewProfit = convertView.findViewById(R.id.textView_cellProfit_itemCell);

            textViewCellName.setText(cellItemList.get(position).getName());
        Log.e("potato",textViewCellName.getText().toString());
            textViewProfit.setText("Profit - $"+ cellItemList.get(position).getMoney());

        return convertView;
    }
}
