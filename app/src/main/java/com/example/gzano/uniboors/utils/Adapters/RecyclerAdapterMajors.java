package com.example.gzano.uniboors.utils.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gzano.uniboors.R;

import java.util.ArrayList;

/**
 * Created by gzano on 16/10/2017.
 */

public class RecyclerAdapterMajors extends RecyclerView.Adapter<RecyclerAdapterMajors.MajorViewHolder> {
    private ArrayList<String> majorsNames = new ArrayList<>();

    public RecyclerAdapterMajors() {
        majorsNames.add("Tecnologie Alimentari");
        majorsNames.add("Psicologia");
        majorsNames.add("Ingegneria Biomedica");
        majorsNames.add("Ingegenria e Scienze Informatiche");
    }

    @Override
    public MajorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.majors_row_layout, parent, false);
        return new MajorViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(MajorViewHolder holder, int position) {

        TextView title = holder.itemView.findViewById(R.id.major_title);
        title.setVisibility(View.VISIBLE);
        title.setText(majorsNames.get(position));

    }

    @Override
    public int getItemCount() {
        return majorsNames.size();
    }

    public class MajorViewHolder extends RecyclerView.ViewHolder {

        public MajorViewHolder(View itemView) {
            super(itemView);
        }

    }
}
