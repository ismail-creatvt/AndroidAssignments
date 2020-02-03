package in.ed.poonacollege.androidassignments.fragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

import in.ed.poonacollege.androidassignments.R;
import in.ed.poonacollege.androidassignments.Utility;
import in.ed.poonacollege.androidassignments.model.ImageData;

public class Question7Fragment extends Fragment {

    private static final int REQUEST_EXTERNAL_PERMISSION = 2;
    ArrayList<DateCategory> images = new ArrayList<>();
    RecyclerView imageCategoryRecyclerview;

    public Question7Fragment() {
        // Required empty public constructor
    }

    class DateCategory{
        String date;
        ArrayList<ImageData> images = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question7, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getActivity() != null){
            ArrayList<ImageData> imageData = Utility.getImagesPath(getActivity());
            createImageList(imageData);
        }

        if(!isPermissionGranted()){
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_PERMISSION);
        }
        imageCategoryRecyclerview = view.findViewById(R.id.imageCategoryRecyclerview);
        imageCategoryRecyclerview.setAdapter(new ImageCategoryAdapter());
        imageCategoryRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void createImageList(ArrayList<ImageData> imageData) {
        Collections.sort(imageData);
        String date_format = "dd/MM/yyyy";
        String date = "";
        DateCategory dateCategory = null;
        images.clear();
        for(ImageData i:imageData){
            SimpleDateFormat dateFormat = new SimpleDateFormat(date_format);
            String d = dateFormat.format(i.date);
            if(!d.equals(date)){
                if(dateCategory != null){
                    images.add(dateCategory);
                }
                dateCategory = new DateCategory();
                dateCategory.date = d;
                date = d;
                dateCategory.images.add(i);
            } else if(dateCategory != null){
                dateCategory.images.add(i);
            }
        }
        if(dateCategory != null){
            images.add(dateCategory);
        }

    }

    private boolean isPermissionGranted(){
        if(getContext() == null) return false;
        return ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ImageViewHolder>{

        ArrayList<ImageData> imageData;

        public ImageListAdapter(ArrayList<ImageData> imageData) {
            this.imageData = imageData;
        }

        @NonNull
        @Override
        public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = getLayoutInflater().inflate(R.layout.image_item_layout, parent, false);
            return new ImageViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
            Glide.with(holder.image).load(imageData.get(position).path).into(holder.image);
        }

        @Override
        public int getItemCount() {
            return imageData.size();
        }

        private class ImageViewHolder extends RecyclerView.ViewHolder{
            public ImageView image;
            public ImageViewHolder(@NonNull View itemView) {
                super(itemView);

                image = itemView.findViewById(R.id.image);
            }
        }

    }

    class ImageCategoryAdapter extends RecyclerView.Adapter<ImageCategoryAdapter.ImageCategoryHolder>{

        @NonNull
        @Override
        public ImageCategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = getLayoutInflater().inflate(R.layout.image_category_layout, parent, false);
            return new ImageCategoryHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ImageCategoryHolder holder, int position) {
            holder.date.setText(images.get(position).date);
            holder.imagesRecyclerView.setAdapter(new ImageListAdapter(images.get(position).images));
            holder.imagesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        }

        @Override
        public int getItemCount() {
            return images.size();
        }

        private class ImageCategoryHolder extends RecyclerView.ViewHolder{
            public TextView date;
            public RecyclerView imagesRecyclerView;
            public ImageCategoryHolder(@NonNull View itemView) {
                super(itemView);
                date = itemView.findViewById(R.id.date);
                imagesRecyclerView = itemView.findViewById(R.id.imagesRecyclerView);
            }
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_EXTERNAL_PERMISSION){
            if(isPermissionGranted()){
                ArrayList<ImageData> imageData = Utility.getImagesPath(getActivity());
                createImageList(imageData);
                imageCategoryRecyclerview.getAdapter().notifyDataSetChanged();
            }
        }
    }
}
