package nathja.finalproject.asynctask;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MyAsyncTask extends AsyncTask<Void, Integer, Void> {
    Activity contextParent;
    private ProgressBar progressBar;
    private TextView textView;

    public MyAsyncTask(Activity contextParent) {
        this.contextParent = contextParent;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar = contextParent.findViewById(R.id.prbDemo);
        textView = contextParent.findViewById(R.id.txtStatus);
        Toast.makeText(contextParent, "test", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        for (int i = 0; i <= 100; i++) {
            SystemClock.sleep(100); // Thời gian nghỉ 100ms
            publishProgress(i); // Cập nhật tiến độ
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        int number = values[0];
        progressBar.setProgress(number); // Cập nhật ProgressBar
        textView.setText(number + "%"); // Cập nhật TextView
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Toast.makeText(contextParent, "Finished", Toast.LENGTH_SHORT).show();
    }
}
