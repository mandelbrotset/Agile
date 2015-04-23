package group9.agile.chalmers.com.agiletracker;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

public class MessageDisplay extends ActionBarActivity {


    TextView textView;

    /** Called when the user clicks the Send button */
    public void onCreate(Bundle savedStates) {
        super.onCreate(savedStates);
        setContentView(R.layout.activity_mdisplaymessage);

        int but = getIntent().getIntExtra("Number", -1);
        textView= (TextView) findViewById(R.id.textViewDisplay);
        textView.setText(" "+ but);

  /*  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }*/


   /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);*/
    }
}
