package com.chinachino.mvvm.Adapters;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chinachino.mvvm.R;

public class ErrorViewHolder extends RecyclerView.ViewHolder {
    TextView error;
    Button button;
    public ErrorViewHolder(@NonNull View itemView) {
        super(itemView);
        error = itemView.findViewById(R.id.error_text);
        button = itemView.findViewById(R.id.error_button);
    }
}
