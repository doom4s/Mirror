package com.maric.vlajko.mirror;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    private boolean flag = false;
    private TextView ourText,cancelAction, setaction;
    private EditText userText;
    private ImageView myImageView;
    private ShareActionProvider mShareActionProvider;
    private LinearLayout ourLinearLayout;
    private RelativeLayout myLayout;
    private Dialog dialog;
    private String imagePath;
    private FloatingActionButton floatingActionButton,fab1,fab2,fab3,fab4,fab5;
    private File file,f;
    private static int RESULT_LOAD_IMAGE = 1;
    private static int CAPTURE_NEW_PICTURE = 2;
    private static int SELECT_NEW_BACKGROUND = 3;
    private SingleMediaScanner mediaScanner;
    private String[] hdImages = {
            "background10",
            "background11",
            "background12",};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ourLinearLayout = (LinearLayout)findViewById(R.id.ourTextBox);
        ourText = (TextView)findViewById(R.id.ourText);
        myImageView = (ImageView)findViewById(R.id.backgroundImageView);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton)findViewById(R.id.fab1);
        fab2 = (FloatingActionButton)findViewById(R.id.fab2);
        fab3 = (FloatingActionButton)findViewById(R.id.fab3);
        fab4 = (FloatingActionButton)findViewById(R.id.fab4);
        fab5 = (FloatingActionButton)findViewById(R.id.fab5);

        floatingActionButton.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        fab3.setOnClickListener(this);
        fab4.setOnClickListener(this);
        fab5.setOnClickListener(this);
    }
    private void saveImage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                myLayout =  (RelativeLayout)findViewById(R.id.saveImageLayout);
                myLayout.setDrawingCacheEnabled(true);
                Bitmap bitmap = myLayout.getDrawingCache();
                if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
                {
                    file =new File(android.os.Environment.getExternalStorageDirectory(),"/Pictures");
                    if(!file.exists())
                    {
                        file.mkdirs();
                    }
                    Random random = new Random();
                    int ran = random.nextInt(10);
                    f = new File(file.getAbsolutePath()+file.separator+ "image"+ran+".png");
                    imagePath = f.toString();
                }
                FileOutputStream ostream = null;
                try {
                    ostream = new FileOutputStream(f);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 10, ostream);
                    ostream.close();
                    mediaScanner = new SingleMediaScanner(getApplicationContext(),f);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Toast.makeText(getApplicationContext(),"Image saved.",Toast.LENGTH_LONG).show();
    }
    @Nullable
    private void createDialog(){
        dialog = new Dialog(Main2Activity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_layout);
        dialog.setCancelable(true);
        userText = (EditText) dialog.findViewById(R.id.userText);
        cancelAction = (TextView) dialog.findViewById(R.id.cancel_action);
        setaction = (TextView) dialog.findViewById(R.id.setText);
        cancelAction.setOnClickListener(this);
        setaction.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.equals(floatingActionButton)){
            if(!flag) {

                fab5.setVisibility(View.VISIBLE);
                fab4.setVisibility(View.VISIBLE);
                fab3.setVisibility(View.VISIBLE);
                fab2.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);

                floatingActionButton.setImageResource(R.drawable.ic_remove_white_24dp);
                flag=!flag;
            }else{
                fab5.setVisibility(View.GONE);
                fab4.setVisibility(View.GONE);
                fab3.setVisibility(View.GONE);
                fab2.setVisibility(View.GONE);
                fab1.setVisibility(View.GONE);
                floatingActionButton.setImageResource(R.drawable.ic_add_white_24dp);
                flag=!flag;}
        }
        if(v.equals(fab1)){
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            File file = new File(Environment.getExternalStorageDirectory()+File.separator + "image.jpg");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            startActivityForResult(intent, CAPTURE_NEW_PICTURE);
        }
        if(v.equals(fab2)){
            createDialog();
            dialog.show();}
        if(v.equals(fab3)){
            Intent intent = new Intent(getApplicationContext(),ListActivity.class);
            startActivityForResult(intent,SELECT_NEW_BACKGROUND);
        }
        if(v.equals(fab4)){
            saveImage();
        }
        if(v.equals(fab5)){
            Intent i = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, RESULT_LOAD_IMAGE);
        }
        if(v.equals(cancelAction)){
            ourText.setText(userText.getText().toString());
            dialog.dismiss();
        }
        if(v.equals(setaction)){
            dialog.dismiss();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_NEW_BACKGROUND) {
            if(resultCode == Activity.RESULT_OK){
                int result=data.getIntExtra("result",0);
                int resId = getApplicationContext().getResources().getIdentifier(hdImages[result],"drawable",getApplicationContext().getPackageName());
                myImageView.setImageResource(resId);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(),"Nothing got selected.",Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();



            Bitmap bmp = null;
            try {
                bmp = getBitmapFromUri(selectedImage);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            myImageView.setImageBitmap(bmp);

        }
        if (requestCode == CAPTURE_NEW_PICTURE && resultCode == RESULT_OK && null != data) {
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + "image.jpg");
            myImageView.setImageBitmap(decodeSampledBitmapFromFile(file.getAbsolutePath(), 500, 250));
        }
    }
    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }
    public static Bitmap decodeSampledBitmapFromFile(String path,
                                                     int reqWidth, int reqHeight) { // BEST QUALITY MATCH

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        int inSampleSize = 1;

        if (height > reqHeight) {
            inSampleSize = Math.round((float)height / (float)reqHeight);
        }

        int expectedWidth = width / inSampleSize;

        if (expectedWidth > reqWidth) {
            //if(Math.round((float)width / (float)reqWidth) > inSampleSize) // If bigger SampSize..
            inSampleSize = Math.round((float)width / (float)reqWidth);
        }

        options.inSampleSize = inSampleSize;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(path, options);
    }

}