package in.ed.poonacollege.androidassignments;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Patterns;

import androidx.annotation.RawRes;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Pattern;

import in.ed.poonacollege.androidassignments.model.ImageData;

public class Utility {

    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
    private static final String NAME_REGEX = "^(?=.*[A-Za-z])[A-Za-z]{3,}$";
    private static final String PHONE_REGEX = "^((040|041|050|044)[\\d]{3,5})|(0400)[\\d]{2,4}$";

    public static boolean isValidPhone(String phone){
        return Pattern.compile(PHONE_REGEX).matcher(phone).matches();
    }

    public static boolean isValidEmail(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidName(String name){
        Pattern pattern = Pattern.compile(NAME_REGEX);
        return pattern.matcher(name).matches();
    }

    public static boolean isPasswordValid(String password){
        Pattern pattern  = Pattern.compile(PASSWORD_REGEX);
        return pattern.matcher(password).matches();
    }

    public static String getJsonStringFromRawFile(Context context, @RawRes int res){
        try (InputStream is = context.getResources().openRawResource(res)) {
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            Reader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
            return writer.toString();
        } catch (Exception ignored) {
        }
        return "";
    }

    public static ArrayList<ImageData> getImagesPath(Activity activity) {
        Uri uri;
        ArrayList<ImageData> listOfAllImages = new ArrayList<>();
        Cursor cursor;
        int column_index_data, column_index_date;
        String path;
        String date;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = { MediaStore.MediaColumns.DATA, MediaStore.MediaColumns.DATE_ADDED };

        cursor = activity.getContentResolver().query(uri, projection, null,
                null, null);
        if(cursor != null){
            try{
                column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                column_index_date = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_ADDED);
            }catch (Exception e){
                column_index_data = 0;
                column_index_date = 0;
            }
            while (cursor.moveToNext()) {
                path = cursor.getString(column_index_data);
                date = cursor.getString(column_index_date);

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(Long.valueOf(date) * 1000);
                listOfAllImages.add(new ImageData(path, calendar.getTime()));
            }
            cursor.close();
        }
        return listOfAllImages;
    }
}
