package com.futuremedicalassistance.fma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GuideViewActivity extends AppCompatActivity {

    ProgressBar progressBar;
    PDFView pdfView;

    DatabaseReference reference;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_view);

        progressBar = findViewById(R.id.progressBar);
        pdfView = findViewById(R.id.pdfView);

        intent = getIntent();
        String guideId = intent.getStringExtra("guideId");

        reference = FirebaseDatabase.getInstance().getReference("Guides");
        reference.child(guideId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Guide guide = snapshot.getValue(Guide.class);

                if(guide != null){
                    String guideURL = guide.getGuideURL();
                    new ReceivePdf(pdfView, progressBar).execute(guideURL);
                }else{
                    Toast.makeText(GuideViewActivity.this, "Error, guia no encontrada", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(GuideViewActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
