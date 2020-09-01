package com.example.poojaapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.github.barteksc.pdfviewer.listener.OnTapListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int PICK_PDF_CODE =1000 ;
    EditText amountEt, noteEt, nameEt, upiIdEt;    //private static final String TAG = "MainActivity";
    ArrayList<String> pathHistory;   int count = 0;
    Button send,upload;   final int UPI_PAYMENT = 0;
Button bmalayalam,benglish,bkannada,bhindi,btelungu,btamil,bname,bstar,btnfile;
String language,name,star;
    ProgressDialog mProgressDialog;
    ListView lvInternalStorage;
    private String[] FilePathStrings;
    private String[] FileNameStrings;
    int last;
    private File[] listFile;
    // WebView web;
    File file;
    PDFView pdfView;
ImageView imageView;

    String lastDirectory;


    EditText nameedit,staredit;

LinearLayout linearlanguage,linearname,linearstar,linearimage;
    LinearLayout linearupload;

    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeViews();
        setContentView(R.layout.activity_main);
        bname= findViewById(R.id.bname);
        btnfile= findViewById(R.id.btnfile);
        linearlanguage=findViewById(R.id.linearlanguage);
        linearname=findViewById(R.id.linearname);
        linearstar=findViewById(R.id.linearstar);
        linearupload=findViewById(R.id.linearupload);
        linearimage=findViewById(R.id.linearimage);
        nameedit= findViewById(R.id.nameedit);
        pdfView = (PDFView) findViewById(R.id.pdfView);
        imageView= findViewById(R.id.imgview);
        staredit=findViewById(R.id.staredit);
        bmalayalam = findViewById(R.id.malayalam);
                benglish= findViewById(R.id.english);
        bkannada= findViewById(R.id.kannada);
                bhindi= findViewById(R.id.hindi);
        btelungu= findViewById(R.id.telungu);
                btamil= findViewById(R.id.tamil);

        bstar= findViewById(R.id.bstar);
Dexter.withActivity(this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new BaseMultiplePermissionsListener(){
    @Override
    public void onPermissionsChecked(MultiplePermissionsReport report) {
        super.onPermissionsChecked(report);
    }

    @Override
    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
        super.onPermissionRationaleShouldBeShown(permissions, token);
    }
}).check();

        lvInternalStorage = (ListView) findViewById(R.id.lvInternalStorage);
        send = findViewById(R.id.send);
        amountEt = findViewById(R.id.amount_et);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkFilePermissions();
        }

        bmalayalam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                language="malayalam";
                linearlanguage.setVisibility(View.GONE);
                linearname.setVisibility(View.VISIBLE);
            }
        });


        benglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                language="english";
                linearlanguage.setVisibility(View.GONE);
                linearname.setVisibility(View.VISIBLE);
            }
        });
        btamil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                language="tamil";
            }
        });
        bhindi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                language="hindi";
                linearlanguage.setVisibility(View.GONE);
                linearname.setVisibility(View.VISIBLE);
            }
        });
        bmalayalam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                language="malayalam";
            }
        });
        btelungu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                language="telungu";
                linearlanguage.setVisibility(View.GONE);
                linearname.setVisibility(View.VISIBLE);
            }
        });
        bkannada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                language="kannada";
                linearlanguage.setVisibility(View.GONE);
                linearname.setVisibility(View.VISIBLE);
            }
        });



        bname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=nameedit.getText().toString();
                linearname.setVisibility(View.GONE);
                linearstar.setVisibility(View.VISIBLE);
            }
        });


        bstar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                star=staredit.getText().toString();
                linearstar.setVisibility(View.GONE);
                linearupload.setVisibility(View.VISIBLE);
            }
        });

        btnfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent broswerpdf=new Intent(Intent.ACTION_GET_CONTENT);
                broswerpdf.setType("application/pdf");
                broswerpdf.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(Intent.createChooser(broswerpdf,"Select PDF"),PICK_PDF_CODE);
            }
        });




        mProgressDialog = new ProgressDialog(MainActivity.this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setTitle("Excel Upload");
        mProgressDialog.setMessage("Please wait, excel is uploading...");
        noteEt = findViewById(R.id.note);
        nameEt = findViewById(R.id.name);
        upload = findViewById(R.id.upload);
        upiIdEt = findViewById(R.id.upi_id);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Getting the values from the EditTexts
                String amount = amountEt.getText().toString();
                String note ="Dakshna";
                String name = "Dakshna";
                String upiId = "coolkrishna7235@oksbi";
                payUsingUpi(amount, upiId, name, note);
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = 0;
                pathHistory = new ArrayList<String>();
                upload.setVisibility(View.GONE);
                pathHistory.add(count, System.getenv("EXTERNAL_STORAGE"));
                Log.d(TAG, "btnSDCard: " + pathHistory.get(count));
                checkInternalStorage();
            }
        });

        lvInternalStorage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                lastDirectory = pathHistory.get(count);
                if (lastDirectory.equals(adapterView.getItemAtPosition(i))) {
                    Log.d(TAG, "lvInternalStorage: Selected a file for upload: " + lastDirectory);

                    //Execute method for reading the excel data.
                    readExcelData(lastDirectory);

                } else {
                    count++;
                    pathHistory.add(count, (String) adapterView.getItemAtPosition(i));
                    checkInternalStorage();
                    Log.d(TAG, "lvInternalStorage: " + pathHistory.get(count));
                }
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkFilePermissions() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            int permissionCheck = this.checkSelfPermission("Manifest.permission.READ_EXTERNAL_STORAGE");
            permissionCheck += this.checkSelfPermission("Manifest.permission.WRITE_EXTERNAL_STORAGE");
            if (permissionCheck != 0) {

                this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1001); //Any number
            }
        } else {
            Log.d(TAG, "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
        }
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void readExcelData(String filePath) {
        //mProgressDialog.show();
        Log.d(TAG, "readExcelData: Reading Excel File.");
        // toastMessage("excelstarted");
        //decarle input file


          Toast.makeText(MainActivity.this,filePath,Toast.LENGTH_SHORT).show();
        linearupload.setVisibility(View.GONE);




        File imgFile = new  File(filePath);
        pdfView.fromFile(imgFile);
//        if(imgFile.exists()){
//
//            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//
//
//
//            imageView.setImageBitmap(myBitmap);
//
//        }

        linearimage.setVisibility(View.VISIBLE);


       // File inputFile = new File(filePath);
       //inputFile.renameTo(new File("/sdcard/Pictures/newName.file"));

    }
    private void checkInternalStorage() {
        Log.d(TAG, "checkInternalStorage: Started.");
        try {
            if (!Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                toastMessage("No SD card found.");
            } else {
                // Locate the image folder in your SD Car;d
                file = new File(pathHistory.get(count));
                Log.d(TAG, "checkInternalStorage: directory path: " + pathHistory.get(count));
            }

            listFile = file.listFiles();

            // Create a String array for FilePathStrings
            FilePathStrings = new String[listFile.length];

            // Create a String array for FileNameStrings
            FileNameStrings = new String[listFile.length];

            for (int i = 0; i < listFile.length; i++) {
                // Get the path of the image file
                FilePathStrings[i] = listFile[i].getAbsolutePath();
                // Get the name image file
                FileNameStrings[i] = listFile[i].getName();
            }

            for (int i = 0; i < listFile.length; i++) {
                Log.d("Files", "FileName:" + listFile[i].getName());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, FilePathStrings);
            lvInternalStorage.setAdapter(adapter);

        } catch (NullPointerException e) {
            Log.e(TAG, "checkInternalStorage: NULLPOINTEREXCEPTION " + e.getMessage());
        }
    }
    void payUsingUpi(String amount, String upiId, String name, String note) {

        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .build();


        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

        // will always show a dialog to user to choose an app
        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

        // check if intent resolves
        if(null != chooser.resolveActivity(getPackageManager())) {
            startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            Toast.makeText(MainActivity.this,"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();
        }

    }

    void initializeViews() {
        send = findViewById(R.id.send);
        amountEt = findViewById(R.id.amount_et);
        noteEt = findViewById(R.id.note);
        nameEt = findViewById(R.id.name);
        upiIdEt = findViewById(R.id.upi_id);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PICK_PDF_CODE&&resultCode==RESULT_OK&&data!=null){
            {    linearupload.setVisibility(View.GONE);
                linearimage.setVisibility(View.VISIBLE);
                Uri selectpdf=data.getData();
                Uri pdffile=Uri.parse(selectpdf.toString());
                pdfView.fromUri(selectpdf).password(null).defaultPage(0).enableSwipe(true).swipeHorizontal(false).enableDoubletap(true)
                            .onDraw(new OnDrawListener() {
                                @Override
                                public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {

                                }
                            }).onDrawAll(new OnDrawListener() {
                    @Override
                    public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {

                    }
                }).onPageError(new OnPageErrorListener() {
                    @Override
                    public void onPageError(int page, Throwable t) {
                        Toast.makeText(MainActivity.this,"Eror",Toast.LENGTH_SHORT).show();
                    }
                }).onPageChange(new OnPageChangeListener() {
                    @Override
                    public void onPageChanged(int page, int pageCount) {

                    }
                }).onTap(new OnTapListener() {
                    @Override
                    public boolean onTap(MotionEvent e) {
                        return true;
                    }
                }).onRender(new OnRenderListener() {
                    @Override
                    public void onInitiallyRendered(int nbPages, float pageWidth, float pageHeight) {
                        pdfView.fitToWidth();
                    }
                }).enableAnnotationRendering(true).invalidPageColor(Color.WHITE).load()
                ;
            }
        }

























        switch (requestCode) {
            case UPI_PAYMENT:
                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                    if (data != null) {
                        String trxt = data.getStringExtra("response");
                        Log.d("UPI", "onActivityResult: " + trxt);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(trxt);
                        upiPaymentDataOperation(dataList);
                    } else {
                        Log.d("UPI", "onActivityResult: " + "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    Log.d("UPI", "onActivityResult: " + "Return data is null"); //when user simply back without payment
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
                break;
        }
    }
    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(MainActivity.this)) {
            String str = data.get(0);
            Log.d("UPIPAY", "upiPaymentDataOperation: "+str);
            String paymentCancel = "";
            if(str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if(equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    }
                    else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                }
                else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }

            if (status.equals("success")) {
                //Code to handle successful transaction here.
                Toast.makeText(MainActivity.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                Log.d("UPI", "responseStr: "+approvalRefNo);
            }
            else if("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(MainActivity.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(MainActivity.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(MainActivity.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }
    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }
}