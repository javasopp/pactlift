// Generated by view binder compiler. Do not edit!
package com.sopp.pactlift.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.sopp.pactlift.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class DialogAddHabitBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final Button btnCancel;

  @NonNull
  public final Button btnConfirm;

  @NonNull
  public final EditText editTextHabitDescription;

  @NonNull
  public final EditText editTextHabitName;

  @NonNull
  public final EditText editTextTargetDays;

  private DialogAddHabitBinding(@NonNull CardView rootView, @NonNull Button btnCancel,
      @NonNull Button btnConfirm, @NonNull EditText editTextHabitDescription,
      @NonNull EditText editTextHabitName, @NonNull EditText editTextTargetDays) {
    this.rootView = rootView;
    this.btnCancel = btnCancel;
    this.btnConfirm = btnConfirm;
    this.editTextHabitDescription = editTextHabitDescription;
    this.editTextHabitName = editTextHabitName;
    this.editTextTargetDays = editTextTargetDays;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static DialogAddHabitBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static DialogAddHabitBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.dialog_add_habit, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static DialogAddHabitBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnCancel;
      Button btnCancel = ViewBindings.findChildViewById(rootView, id);
      if (btnCancel == null) {
        break missingId;
      }

      id = R.id.btnConfirm;
      Button btnConfirm = ViewBindings.findChildViewById(rootView, id);
      if (btnConfirm == null) {
        break missingId;
      }

      id = R.id.editTextHabitDescription;
      EditText editTextHabitDescription = ViewBindings.findChildViewById(rootView, id);
      if (editTextHabitDescription == null) {
        break missingId;
      }

      id = R.id.editTextHabitName;
      EditText editTextHabitName = ViewBindings.findChildViewById(rootView, id);
      if (editTextHabitName == null) {
        break missingId;
      }

      id = R.id.editTextTargetDays;
      EditText editTextTargetDays = ViewBindings.findChildViewById(rootView, id);
      if (editTextTargetDays == null) {
        break missingId;
      }

      return new DialogAddHabitBinding((CardView) rootView, btnCancel, btnConfirm,
          editTextHabitDescription, editTextHabitName, editTextTargetDays);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
