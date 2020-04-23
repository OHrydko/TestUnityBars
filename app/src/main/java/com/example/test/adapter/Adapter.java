package com.example.test.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;
import com.example.test.databinding.ItemBinding;
import com.example.test.model.JsonModel;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.AdapterViewHolder> {
    private List<JsonModel> jsonModels;
    private onItemClick onItemClick;

    public Adapter(List<JsonModel> jsonModels, onItemClick onItemClick) {
        this.jsonModels = jsonModels;
        this.onItemClick = onItemClick;
    }

    public interface onItemClick {
        void onClick(int position, String type, String content);
    }

    @NonNull
    @Override
    public Adapter.AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBinding employeeListItemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item, parent, false);
        return new AdapterViewHolder(employeeListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.AdapterViewHolder holder, int position) {
        JsonModel data = jsonModels.get(position);
        holder.itemBinding.name.setText(data.getName());
        if (data.getType().equals("FOLDER")) {
            holder.itemBinding.image.setImageResource(R.drawable.ic_folder_24dp);
        } else {
            String extension = "";
            if (data.getName().lastIndexOf(".") != -1 && data.getName().lastIndexOf(".") != 0)
                extension = data.getName().substring(data.getName().lastIndexOf(".") + 1);
            switch (extension) {
                case "xlsx":
                    holder.itemBinding.image.setImageResource(R.drawable.ic_xlsx);
                    break;
                case "docx":
                    holder.itemBinding.image.setImageResource(R.drawable.ic_word);
                    break;
                case "txt":
                    holder.itemBinding.image.setImageResource(R.drawable.ic_txt);
                    break;
                case "pdf":
                    holder.itemBinding.image.setImageResource(R.drawable.ic_pdf);
                    break;
                default:
                    holder.itemBinding.image.setImageResource(R.drawable.ic_file_24dp);


            }
        }
        holder.itemBinding.container.setOnClickListener(v -> onItemClick
                .onClick(position, data.getType(), data.getContent()));
    }

    @Override
    public int getItemCount() {
        return jsonModels.size();
    }

    public void setJsonModels(List<JsonModel> jsonModels) {
        this.jsonModels = jsonModels;
        notifyDataSetChanged();
    }

    class AdapterViewHolder extends RecyclerView.ViewHolder {
        private ItemBinding itemBinding;

        AdapterViewHolder(@NonNull ItemBinding itemBinding) {
            super(itemBinding.getRoot());

            this.itemBinding = itemBinding;
        }
    }
}
