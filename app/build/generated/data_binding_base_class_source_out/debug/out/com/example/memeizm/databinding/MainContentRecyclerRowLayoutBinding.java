// Generated by view binder compiler. Do not edit!
package com.example.memeizm.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import com.example.memeizm.R;
import com.google.android.material.button.MaterialButton;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class MainContentRecyclerRowLayoutBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageView favImageView;

  @NonNull
  public final ImageView imageView;

  @NonNull
  public final MaterialButton recreateButton;

  @NonNull
  public final ImageView saveImageView;

  @NonNull
  public final TextView shareCountTextView;

  @NonNull
  public final ImageView shareImageView;

  private MainContentRecyclerRowLayoutBinding(@NonNull ConstraintLayout rootView,
      @NonNull ImageView favImageView, @NonNull ImageView imageView,
      @NonNull MaterialButton recreateButton, @NonNull ImageView saveImageView,
      @NonNull TextView shareCountTextView, @NonNull ImageView shareImageView) {
    this.rootView = rootView;
    this.favImageView = favImageView;
    this.imageView = imageView;
    this.recreateButton = recreateButton;
    this.saveImageView = saveImageView;
    this.shareCountTextView = shareCountTextView;
    this.shareImageView = shareImageView;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static MainContentRecyclerRowLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static MainContentRecyclerRowLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.main_content_recycler_row_layout, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static MainContentRecyclerRowLayoutBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.favImageView;
      ImageView favImageView = rootView.findViewById(id);
      if (favImageView == null) {
        break missingId;
      }

      id = R.id.imageView;
      ImageView imageView = rootView.findViewById(id);
      if (imageView == null) {
        break missingId;
      }

      id = R.id.recreateButton;
      MaterialButton recreateButton = rootView.findViewById(id);
      if (recreateButton == null) {
        break missingId;
      }

      id = R.id.saveImageView;
      ImageView saveImageView = rootView.findViewById(id);
      if (saveImageView == null) {
        break missingId;
      }

      id = R.id.shareCountTextView;
      TextView shareCountTextView = rootView.findViewById(id);
      if (shareCountTextView == null) {
        break missingId;
      }

      id = R.id.shareImageView;
      ImageView shareImageView = rootView.findViewById(id);
      if (shareImageView == null) {
        break missingId;
      }

      return new MainContentRecyclerRowLayoutBinding((ConstraintLayout) rootView, favImageView,
          imageView, recreateButton, saveImageView, shareCountTextView, shareImageView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}