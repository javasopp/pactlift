// Generated by view binder compiler. Do not edit!
package com.sopp.pactlift.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sopp.pactlift.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentHabitBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final CardView cardTitle;

  @NonNull
  public final FloatingActionButton fabAddHabit;

  @NonNull
  public final ImageView imageViewLogo;

  @NonNull
  public final RecyclerView recyclerView;

  @NonNull
  public final TextView textViewHabitTitle;

  private FragmentHabitBinding(@NonNull ConstraintLayout rootView, @NonNull CardView cardTitle,
      @NonNull FloatingActionButton fabAddHabit, @NonNull ImageView imageViewLogo,
      @NonNull RecyclerView recyclerView, @NonNull TextView textViewHabitTitle) {
    this.rootView = rootView;
    this.cardTitle = cardTitle;
    this.fabAddHabit = fabAddHabit;
    this.imageViewLogo = imageViewLogo;
    this.recyclerView = recyclerView;
    this.textViewHabitTitle = textViewHabitTitle;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentHabitBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentHabitBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_habit, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentHabitBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.cardTitle;
      CardView cardTitle = ViewBindings.findChildViewById(rootView, id);
      if (cardTitle == null) {
        break missingId;
      }

      id = R.id.fabAddHabit;
      FloatingActionButton fabAddHabit = ViewBindings.findChildViewById(rootView, id);
      if (fabAddHabit == null) {
        break missingId;
      }

      id = R.id.imageViewLogo;
      ImageView imageViewLogo = ViewBindings.findChildViewById(rootView, id);
      if (imageViewLogo == null) {
        break missingId;
      }

      id = R.id.recyclerView;
      RecyclerView recyclerView = ViewBindings.findChildViewById(rootView, id);
      if (recyclerView == null) {
        break missingId;
      }

      id = R.id.textViewHabitTitle;
      TextView textViewHabitTitle = ViewBindings.findChildViewById(rootView, id);
      if (textViewHabitTitle == null) {
        break missingId;
      }

      return new FragmentHabitBinding((ConstraintLayout) rootView, cardTitle, fabAddHabit,
          imageViewLogo, recyclerView, textViewHabitTitle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
