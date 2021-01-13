// Generated by view binder compiler. Do not edit!
package com.example.memeizm.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewpager2.widget.ViewPager2;
import com.example.memeizm.CustomScrollView;
import com.example.memeizm.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentRecreationBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ConstraintLayout constrait;

  @NonNull
  public final MaterialButton downloadbutton;

  @NonNull
  public final ImageView mainEditableImageView;

  @NonNull
  public final CustomScrollView nestedScroll;

  @NonNull
  public final ImageView shareimageview;

  @NonNull
  public final TabLayout tablayout;

  @NonNull
  public final ViewPager2 viewpager2;

  private FragmentRecreationBinding(@NonNull ConstraintLayout rootView,
      @NonNull ConstraintLayout constrait, @NonNull MaterialButton downloadbutton,
      @NonNull ImageView mainEditableImageView, @NonNull CustomScrollView nestedScroll,
      @NonNull ImageView shareimageview, @NonNull TabLayout tablayout,
      @NonNull ViewPager2 viewpager2) {
    this.rootView = rootView;
    this.constrait = constrait;
    this.downloadbutton = downloadbutton;
    this.mainEditableImageView = mainEditableImageView;
    this.nestedScroll = nestedScroll;
    this.shareimageview = shareimageview;
    this.tablayout = tablayout;
    this.viewpager2 = viewpager2;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentRecreationBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentRecreationBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_recreation, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentRecreationBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.constrait;
      ConstraintLayout constrait = rootView.findViewById(id);
      if (constrait == null) {
        break missingId;
      }

      id = R.id.downloadbutton;
      MaterialButton downloadbutton = rootView.findViewById(id);
      if (downloadbutton == null) {
        break missingId;
      }

      id = R.id.mainEditableImageView;
      ImageView mainEditableImageView = rootView.findViewById(id);
      if (mainEditableImageView == null) {
        break missingId;
      }

      id = R.id.nestedScroll;
      CustomScrollView nestedScroll = rootView.findViewById(id);
      if (nestedScroll == null) {
        break missingId;
      }

      id = R.id.shareimageview;
      ImageView shareimageview = rootView.findViewById(id);
      if (shareimageview == null) {
        break missingId;
      }

      id = R.id.tablayout;
      TabLayout tablayout = rootView.findViewById(id);
      if (tablayout == null) {
        break missingId;
      }

      id = R.id.viewpager2;
      ViewPager2 viewpager2 = rootView.findViewById(id);
      if (viewpager2 == null) {
        break missingId;
      }

      return new FragmentRecreationBinding((ConstraintLayout) rootView, constrait, downloadbutton,
          mainEditableImageView, nestedScroll, shareimageview, tablayout, viewpager2);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
