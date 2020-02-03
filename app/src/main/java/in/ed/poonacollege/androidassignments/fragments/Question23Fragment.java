package in.ed.poonacollege.androidassignments.fragments;


import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.ed.poonacollege.androidassignments.R;
import in.ed.poonacollege.androidassignments.model.Sms;

public class Question23Fragment extends Fragment {

    private List<Sms> smsList = new ArrayList<>();
    private RecyclerView smsRv;

    public Question23Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question23, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        smsRv = view.findViewById(R.id.sms_rv);

        if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_SMS) == PackageManager.PERMISSION_DENIED){
            requestPermissions(new String[]{ Manifest.permission.READ_SMS }, 1);
            return;
        }

        smsList = getAllSms();
        smsRv.setAdapter(new SmsAdapter());
        smsRv.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private List<Sms> getAllSms() {
        List<Sms> lstSms = new ArrayList<>();
        Sms objSms;
        Uri message = Uri.parse("content://sms/");
        ContentResolver cr = getActivity().getContentResolver();

        Cursor c = cr.query(message, null, null, null, null);
        int totalSMS = c.getCount();

        if (c.moveToFirst()) {
            for (int i = 0; i < totalSMS; i++) {

                objSms = new Sms();
                objSms.setId(c.getString(c.getColumnIndexOrThrow("_id")));
                objSms.setAddress(c.getString(c
                        .getColumnIndexOrThrow("address")));
                objSms.setMsg(c.getString(c.getColumnIndexOrThrow("body")));
                objSms.setReadState(c.getString(c.getColumnIndex("read")));
                objSms.setTime(c.getString(c.getColumnIndexOrThrow("date")));
                if (c.getString(c.getColumnIndexOrThrow("type")).contains("1")) {
                    objSms.setFolderName("inbox");
                } else {
                    objSms.setFolderName("sent");
                }

                lstSms.add(objSms);
                c.moveToNext();
            }
        }
        c.close();

        return lstSms;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            smsList = getAllSms();
            smsRv.setAdapter(new SmsAdapter());
            smsRv.setLayoutManager(new LinearLayoutManager(getContext()));
        }

    }

    class SmsAdapter extends RecyclerView.Adapter<SmsAdapter.SmsViewHolder>{

        @NonNull
        @Override
        public SmsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new SmsViewHolder(inflater.inflate(R.layout.sms_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull SmsViewHolder holder, int position) {
            Sms sms = smsList.get(position);
            holder.text.setText(sms.getMsg());
            holder.folder.setText(sms.getFolderName());
            holder.address.setText("Address : " + sms.getAddress());
            Date date = new Date();
            date.setTime(Long.valueOf(sms.getTime()));
            holder.time.setText(new SimpleDateFormat("dd MMM yyyy  hh:mm:ss a").format(date));
        }

        @Override
        public int getItemCount() {
            return smsList.size();
        }

        class SmsViewHolder extends RecyclerView.ViewHolder{

            TextView text, address, folder, time;
            SmsViewHolder(@NonNull View itemView) {
                super(itemView);

                text = itemView.findViewById(R.id.text);
                time = itemView.findViewById(R.id.time);
                address = itemView.findViewById(R.id.address);
                folder = itemView.findViewById(R.id.folder);
            }
        }
    }

}
