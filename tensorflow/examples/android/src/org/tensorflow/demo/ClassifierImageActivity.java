package org.tensorflow.demo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by rossukhon on 6/16/2017 AD.
 */

public class ClassifierImageActivity extends Activity {
    private static int REQ_PICK_IMAGE = 111;

    private static final int INPUT_SIZE = 299;
    private static final int IMAGE_MEAN = 128;
    private static final float IMAGE_STD = 128;

    private static final String INPUT_NAME = "Mul";
    private static final String OUTPUT_NAME = "final_result";
    private static final String MODEL_FILE = "file:///android_asset/wizard_graph.pb";
    private static final String LABEL_FILE = "file:///android_asset/wizard_names.txt";

    private Handler handler;
    private Classifier classifier;

    private Button pickImageButton;
    private ImageView imageView;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classifier_image);

        pickImageButton = (Button) findViewById(R.id.classfier_bt_pick_image);
        imageView = (ImageView) findViewById(R.id.classfier_iv_image);
        resultTextView = (TextView) findViewById(R.id.classfier_tv_result);

        pickImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, REQ_PICK_IMAGE);
            }
        });

        handler = new Handler();
        classifier = TensorFlowImageClassifier.create(
                getAssets(),
                MODEL_FILE, LABEL_FILE,
                INPUT_SIZE, IMAGE_MEAN, IMAGE_STD,
                INPUT_NAME, OUTPUT_NAME);
    }

    private void identify(final Bitmap bitmap) {
        Runnable classifierRunnable = new Runnable() {
            @Override
            public void run() {
                final List<Classifier.Recognition> results = classifier.recognizeImage(bitmap);

                StringBuilder resultString = new StringBuilder();
                for (Classifier.Recognition result : results) {
                    resultString.append(result.getTitle());
                    resultString.append(" / ");
                    resultString.append(result.getConfidence());
                    resultString.append(System.lineSeparator());
                }
                resultTextView.setText(resultString.toString());
                resultTextView.setVisibility(View.VISIBLE);
            }
        };
        handler.post(classifierRunnable);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "Error, No image data found", Toast.LENGTH_SHORT);
                return;
            }
            try {
                InputStream imageIs = getContentResolver().openInputStream(data.getData());
                Bitmap selectedBitmap = BitmapFactory.decodeStream(imageIs);
                imageView.setImageBitmap(selectedBitmap);
                identify(selectedBitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
