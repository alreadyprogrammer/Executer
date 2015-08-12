package com.uber.executer;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.uber.executer.models.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BookRide extends AppCompatActivity {

  Dialog dialog;
  static final String UBER_BLACK = "UBER BLACK";
  static final String UBER_X = "UBER X";
  static final String UBER_TAXI = "UBER TAXI";
  LinearLayout cars;
  Toolbar toolbar;
  Button bookARide;
  TableLayout tableLayout;
  Button chooseAride;
  TableRow row;
  ImageView uberx;
  ImageView uberBlack;
  ImageView ubertaxi;
  TextView carType;
  Animation animation;
  @Override
  protected void onCreate (Bundle savedInstanceState) {
    super.onCreate (savedInstanceState);

    //set up layout
    setContentView (R.layout.activity_book_ride);


    //set up toolbar
    toolbar = (Toolbar)findViewById (R.id.toolbar);
    setSupportActionBar (toolbar);
    getSupportActionBar().setDisplayShowTitleEnabled (false);
    TextView toolbarTitle = (TextView)findViewById (R.id.toolbar_title);
    toolbarTitle.setText ("EXECUTER");
    Typeface tf = Typeface.createFromAsset (getAssets (),"MuseoSans-300.otf");
    toolbarTitle.setTypeface (tf);


    //get data from EventPageFragment and populate the view
    Intent gotten = getIntent ();
    String location = gotten.getStringExtra ("location");
    String summary = gotten.getStringExtra ("summary");
    String time = gotten.getStringExtra ("startTime");
    TextView eventsummary = (TextView)findViewById (R.id.event_title_text);
    TextView eventLocation = (TextView)findViewById (R.id.event_location);
    TextView eventStarts = (TextView)findViewById (R.id.event_start);
    eventsummary.setText (summary);
    eventLocation.setText (location);
    eventStarts.setText (time);


    //make cars layout invisible
    cars = (LinearLayout)findViewById (R.id.cars);
    cars.setVisibility (View.INVISIBLE);

    //get instance of the tablelayout
    tableLayout = (TableLayout)findViewById (R.id.table);
    row = (TableRow)tableLayout.findViewById (R.id.uber_types);
    row.setVisibility (View.INVISIBLE);
    chooseAride = (Button)tableLayout.findViewById (R.id.choose_a_ride);
    carType = (TextView)tableLayout.findViewById (R.id.uber_type_here);


    // book a ride
    bookARide = (Button)findViewById (R.id.book_a_ride);
    bookARide.setOnClickListener (new View.OnClickListener () {
      @Override
      public void onClick (View v) {
        //get the car type
        String car = carType.getText ().toString ();
        // get the current time of request
        long time = new Date ().getTime ();
        DateFormat formatter = new SimpleDateFormat ("HH:mm:ss:SSS");
        String formattedTime = formatter.format (time);
        //get user location
        //get user destination
        //get user longitude
        //get user latitude
        RequestQueue queue = Volley.newRequestQueue (getApplicationContext ());
        StringRequest stringRequest = new StringRequest (Request.Method.POST, "http://andelahack.herokuapp.com/users/35c2856e-068b-43ba-94d3-3840af926b36/requests", new Response.Listener<String> () {
          @Override
          public void onResponse (String response) {
            Toast.makeText (getApplicationContext (), response.toString (),Toast.LENGTH_LONG).show ();

          }
        }, new Response.ErrorListener () {
          @Override
          public void onErrorResponse (VolleyError error) {
            Toast.makeText (getApplicationContext (), "Error: " +error,Toast.LENGTH_LONG).show ();
          }
        }){
          @Override
          protected Map<String, String> getParams () throws AuthFailureError {
            super.getParams ();
            Map<String,String> params = new HashMap<String, String> ();
            params.put("taxi type", "UBER BLACK");
            params.put("location", "location");
            return params;

          }

          @Override
          public Map<String, String> getHeaders () throws AuthFailureError {
            super.getHeaders ();
            Map<String,String> params = new HashMap<String, String>();
            params.put("Content-Type","application/x-www-form-urlencoded");
            return params;
          }
        };
        queue.add (stringRequest);
      }
    });


    //setup taxies
    uberBlack = (ImageView)findViewById (R.id.uber_black_one);
    uberx = (ImageView)findViewById (R.id.uber_x);
    ubertaxi = (ImageView)findViewById (R.id.uber_taxi);


    //setup listeners for taxies
    uberBlack.setOnClickListener (new View.OnClickListener () {
      @Override
      public void onClick (View v) {
        choose(UBER_BLACK);
        zoomIn (v);
        toastMessage ("Long Click UBER BLACK to see UBER BLACK details");

      }
    });


    uberBlack.setOnLongClickListener (new View.OnLongClickListener () {
      @Override
      public boolean onLongClick (View v) {
        dialog = new Dialog (BookRide.this);
        dialog.setContentView (R.layout.car_detail_dialog);
        Toolbar toolbar = (Toolbar) dialog.findViewById (R.id.toolbar);
        dialog.setTitle ("");
        ImageView carImage = (ImageView) dialog.findViewById (R.id.uber_black_detail_car);
        carImage.setImageResource (R.drawable.uber_black);
        TextView carTitle = (TextView) dialog.findViewById (R.id.car_title);
        carTitle.setText ("UBER BLACK");
        TextView carProperty = (TextView) dialog.findViewById (R.id.car_property);
        carProperty.setText (R.string.uber_black_details);
        Button gotIt = (Button) dialog.findViewById (R.id.gotit);
        gotIt.setOnClickListener (new View.OnClickListener () {
          @Override
          public void onClick (View v) {
            dialog.hide ();
          }
        });
        dialog.show ();
        return true;
      }
    });


    uberx.setOnClickListener (new View.OnClickListener () {
      @Override
      public void onClick (View v) {
        choose (UBER_X);
        zoomIn (v);
        toastMessage ("Long Click UBER X to see UBER X details");
      }
    });


    uberx.setOnLongClickListener (new View.OnLongClickListener () {
      @Override
      public boolean onLongClick (View v) {
        dialog = new Dialog (BookRide.this);
        dialog.setContentView (R.layout.car_detail_dialog);
        Toolbar toolbar = (Toolbar) dialog.findViewById (R.id.toolbar);
        dialog.setTitle ("");
        ImageView carImage = (ImageView) dialog.findViewById (R.id.uber_black_detail_car);
        carImage.setImageResource (R.drawable.new_uber);
        TextView carTitle = (TextView) dialog.findViewById (R.id.car_title);
        carTitle.setText ("UBER X");
        TextView carProperty = (TextView) dialog.findViewById (R.id.car_property);
        carProperty.setText (R.string.uber_black_details);
        Button gotIt = (Button) dialog.findViewById (R.id.gotit);
        gotIt.setOnClickListener (new View.OnClickListener () {
          @Override
          public void onClick (View v) {
            dialog.hide ();
          }
        });
        dialog.show ();
        return true;
      }
    });


    ubertaxi.setOnClickListener (new View.OnClickListener () {
      @Override
      public void onClick (View v) {
        choose (UBER_TAXI);
        zoomIn (v);
        toastMessage ("Long Click UBER TAXI to see UBER TAXI details");
      }
    });


    ubertaxi.setOnLongClickListener (new View.OnLongClickListener () {
      @Override
      public boolean onLongClick (View v) {
        dialog = new Dialog (BookRide.this);
        dialog.setContentView (R.layout.car_detail_dialog);
        Toolbar toolbar = (Toolbar) dialog.findViewById (R.id.toolbar);
        dialog.setTitle ("");
        ImageView carImage = (ImageView) dialog.findViewById (R.id.uber_black_detail_car);
        carImage.setImageResource (R.drawable.uber_taxi);
        TextView carTitle = (TextView) dialog.findViewById (R.id.car_title);
        carTitle.setText ("UBER TAXI");
        TextView carProperty = (TextView) dialog.findViewById (R.id.car_property);
        carProperty.setText (R.string.uber_black_details);
        Button gotIt = (Button) dialog.findViewById (R.id.gotit);
        gotIt.setOnClickListener (new View.OnClickListener () {
          @Override
          public void onClick (View v) {
            dialog.hide ();
          }
        });
        dialog.show ();
        return true;
      }
    });


    chooseAride.setOnClickListener (new View.OnClickListener () {
      @Override
      public void onClick (View v) {
        cars.setVisibility (View.VISIBLE);
        chooseAride.setVisibility (View.INVISIBLE);


      }
    });
  }


  @Override
  public boolean onCreateOptionsMenu (Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater ().inflate (R.menu.menu_book_ride, menu);
    return true;
  }
  public Calendar getCalendarAtPosion(Calendar[] calendars, int position){
    return calendars[position];
  }


  @Override
  public boolean onOptionsItemSelected (MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId ();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected (item);
  }

  public void choose(String type){
    row.setVisibility (View.VISIBLE);
    carType.setText (type);
  }
  public void zoomIn(View view){
    Animation animation =  AnimationUtils.loadAnimation (getApplicationContext (), R.anim.zoom_out);
    view.setAnimation (animation);
    animation.start ();
  }
  private void toastMessage(String message){
    Toast.makeText (this, message, Toast.LENGTH_SHORT).show ();
  }

}