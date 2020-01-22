package in.ed.poonacollege.androidassignments.fragments;


import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import com.bumptech.glide.util.Util;

import java.io.File;
import java.util.ArrayList;

import in.ed.poonacollege.androidassignments.R;
import in.ed.poonacollege.androidassignments.Utility;
import in.ed.poonacollege.androidassignments.model.ImageData;

public class Question8Fragment extends Fragment {

    ArrayList<ImageData> images;
    private int currentIndex = 0;

    public Question8Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question8, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        images = Utility.getImagesPath(getActivity());

        ImageSwitcher imageSwitcher = view.findViewById(R.id.imageSwitcher);
        imageSwitcher.setFactory(() -> {
            ImageView imageView = new ImageView(getContext());
            imageView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return imageView;
        });

        imageSwitcher.setImageURI(getImageUri());
        Animation in = AnimationUtils.loadAnimation(getActivity(),R.anim.slide_in_right);
        Animation out = AnimationUtils.loadAnimation(getActivity(),R.anim.slide_out_left);
        imageSwitcher.setInAnimation(in);
        imageSwitcher.setOutAnimation(out);

        Button prev = view.findViewById(R.id.prev);
        Button next = view.findViewById(R.id.next);

        prev.setOnClickListener(v -> {
            if(currentIndex > 0){
                currentIndex--;
                imageSwitcher.setImageURI(getImageUri());
            }
        });

        next.setOnClickListener(v -> {
            if(currentIndex < images.size()-1){
                currentIndex++;
                imageSwitcher.setImageURI(getImageUri());
            }
        });
    }

    private Uri getImageUri() {
        File file = new File(images.get(currentIndex).path);
        return Uri.fromFile(file);
    }
}
