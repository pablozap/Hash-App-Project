package com.jp.hashproject;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.room.Room;
import com.jp.hashproject.model.AppDataBase;
import com.jp.hashproject.model.Hash;
import com.jp.hashproject.model.User;
import com.jp.hashproject.util.RealPathUtil;

import java.io.File;
import java.io.FileInputStream;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.util.Calendar;

public class GetFileActivity extends AppCompatActivity {
    AppDataBase appDataBase;
    String fileName, date, filePath, hash;
    Hash file;
    TextView tvFileName, tvDate, tvFilePath, tvHash;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_file);

        //Database initialization
        appDataBase = Room.databaseBuilder(
                        this,
                        AppDataBase.class,
                        "dbProject"
                ).allowMainThreadQueries().
                build();
        if(getIntent().hasExtra("user")){
            user = (User) getIntent().getExtras().get("user");
            System.out.println(user);
        }

        tvFileName = findViewById(R.id.tvFileName);
        tvDate = findViewById(R.id.tvDate);
        tvFilePath = findViewById(R.id.tvFilePath);
        tvHash = findViewById(R.id.tvHash);


    }


    ActivityResultLauncher<Intent> sActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        // Solicita el permiso al usuario
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                        return;
                    }
                    Intent data = result.getData();
                    assert data != null;
                    Uri uri = data.getData();

                    fileName = getFileNameFromUri(uri); // Obtener el nombre del archivo

                    Context context = getApplicationContext();
                    filePath = RealPathUtil.getRealPath(context, uri); // Obtener la dirección del archivo
                    date = getActualDate();
                    hash = generateSHA256Hash(filePath);

                    file = new Hash(user.getId() ,fileName, filePath, date, hash);
                    // Mostrar los datos en los TextView
                    tvFileName.setText(file.fileName);
                    tvDate.setText(file.date);
                    tvFilePath.setText(file.filePath);
                    tvHash.setText(file.hash);
                }
            }
    );

    public void uploadHash(View view){
        try{
            if(file != null){
                appDataBase.hashDao().insert(file);
                Log.i("Hash", "Hash uploaded successfully");
                Intent hashListIntent = new Intent(this, HashListActivity.class);
                hashListIntent.putExtra("user", user);
                startActivity(hashListIntent);
            }else{
                Toast.makeText(this, "File couldn´t upload successfully", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(this, "File couldn´t upload successfully \n" + e.getMessage() , Toast.LENGTH_SHORT).show();
        }

    }
    public void openFileDialog(View view) {
        Intent data = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        data.setType("*/*");
        data = Intent.createChooser(data, "Choose a file");
        sActivityResultLauncher.launch(data);
    }

    private String getFileNameFromUri(Uri uri) {
        String fileName = null;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int displayNameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            fileName = cursor.getString(displayNameIndex);
            cursor.close();
        }
        return fileName;
    }

    // Método para obtener la fecha y hora actual del sistema
    private String getActualDate() {
        // Obtener la hora actual en milisegundos
        long tiempoActual = System.currentTimeMillis();

        // Convertir milisegundos a fecha y hora
        Calendar calendario = Calendar.getInstance();
        calendario.setTimeInMillis(tiempoActual);

        // Obtener año, mes, día, hora, minuto y segundo
        int año = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH) + 1; // Los meses van de 0 a 11, por lo que se suma 1
        int dia = calendario.get(Calendar.DAY_OF_MONTH);
        int hora = calendario.get(Calendar.HOUR_OF_DAY);
        int minuto = calendario.get(Calendar.MINUTE);
        int segundo = calendario.get(Calendar.SECOND);

        return String.format("%d-%02d-%02d %02d:%02d:%02d", año, mes, dia, hora, minuto, segundo);

    }

    //Método para obtener el hash del archivo
    public static String generateSHA256Hash(String filePath) {
        try {
            File file = new File(filePath);
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }
            fis.close();
            byte[] hashBytes = digest.digest();

            // Convert hash bytes to hexadecimal representation
            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception error) {
            Log.i("Error", String.valueOf(error.fillInStackTrace()));
            return null;
        }
    }
}