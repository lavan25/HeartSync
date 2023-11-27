package com.example.heartsync;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.ClipData;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdaptar extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    List<item> items;

    public MyAdaptar(Context context, List<item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView17.setText(items.get(position).getText());

        if (items.get(position).getText().equals("Your Profile")) {
            animateYourProfileText(holder.textView17);
        } else {
            holder.textView17.clearAnimation();
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private void animateYourProfileText(View view) {
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 1.2f, 1.0f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 1.2f, 1.0f);

        scaleXAnimator.setInterpolator(new AccelerateInterpolator());
        scaleYAnimator.setInterpolator(new AccelerateInterpolator());

        scaleXAnimator.setDuration(500);
        scaleYAnimator.setDuration(500);

        // Set up a listener to restart the animation when it ends
        AnimatorListenerAdapter listener = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                scaleXAnimator.start();
                scaleYAnimator.start();
            }
        };

        scaleXAnimator.addListener(listener);
        scaleYAnimator.addListener(listener);

        scaleXAnimator.start();
        scaleYAnimator.start();
    }
}
