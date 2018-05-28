package mobile.tiny_waste_management;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.telephony.TelephonyManager;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.timessquare.CalendarPickerView;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

import Fragments.acknowledge_fragment;
import Fragments.cancelled;
import Fragments.changed;
import Fragments.completed;
import Fragments.fragment;
import Fragments.inpro;
import Fragments.pulled;
import Fragments.shifted;
import Service_handler.HttpHandler;
import Service_handler.ServiceHandler;
import adapter.JobType_adapter;
import mobile.tiny_waste_management.app.Config;
import util.NotificationUtils;


import static com.squareup.timessquare.CalendarPickerView.SelectionMode.RANGE;


public class Home_screen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    ImageView img_Calender;
    RelativeLayout Rlv_Content;
    boolean isUp;
    AppBarLayout app_bar_layout;
    private ProgressDialog pDialog;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private Handler handler;
    private CalendarPickerView calendar;
    TextView date_now;
    ImageView img_refresh;
    String Str = "Nirmal";
    ArrayList<HashMap<String, String>> Arraylist_for_Search_assigned1 = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> Arraylist_for_Search_assigned2 = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> Arraylist_for_Search_acknowledge1 = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> Arraylist_for_Search_acknowledge2 = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> Arraylist_for_Search_pulled1 = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> Arraylist_for_Search_pulled2 = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> Arraylist_for_Search_shifted1 = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> Arraylist_for_Search_shifted2 = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> Arraylist_for_Search_inprogress1 = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> Arraylist_for_Search_inprogress2 = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> Arraylist_for_Search_changed1 = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> Arraylist_for_Search_changed2 = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> Arraylist_for_Search_completed1 = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> Arraylist_for_Search_competed2 = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> Arraylist_for_Search_cancelled1 = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> Arraylist_for_Search_cancelled2 = new ArrayList<HashMap<String, String>>();
    SharedPreferences shared;
    String Access_tocken = "", Driver_id = "", Search_withjob_number = "";
    Button BTn_search;
    String fist_date = "";
    String last_date = "";
    String Job_id = "";
    String Status = "Current", Check_screen = "", Next_status = "";
    String jsonStr = "";
    EditText EdtTxt_job_number;
    NavigationView navigationView;
    //CalendarView Calender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        img_Calender = (ImageView) findViewById(R.id.imageView5);
        Rlv_Content = (RelativeLayout) findViewById(R.id.Calender);
        date_now = (TextView) findViewById(R.id.date_);
        img_refresh = (ImageView) findViewById(R.id.imageView3);
        BTn_search = (Button) findViewById(R.id.button3);
        EdtTxt_job_number = (EditText) findViewById(R.id.editText3);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Format formatter = new SimpleDateFormat("dd MMMM yyyy");
        String today = formatter.format(new Date());
        //String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        date_now.setText(today);

        formatter = new SimpleDateFormat("dd-MM-yyyy");
        today = formatter.format(new Date());
//        String android_id = Settings.Secure.getString(Home_screen.this.getContentResolver(),
//                Settings.Secure.ANDROID_ID);
//        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
//        telephonyManager.getDeviceId();
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received
                    Next_status = "null";
                    String message = intent.getStringExtra("message");

                    String[] separated = message.split(":");

                    message = separated[1];
                    message = message.replaceAll("\\s+", "");
                    fist_date = "";
                    last_date = "";
                    EdtTxt_job_number.setText(message);
                    Search_withjob_number = EdtTxt_job_number.getText().toString();
                    date_now.setText(Search_withjob_number);
//                    if (Rlv_Content.getVisibility() == View.VISIBLE) {
//                        showView_slideup();
//                    } else if (Rlv_Content.getVisibility() == View.GONE) {
//                        showView();
//                    }
                    SharedPreferences.Editor editor = shared.edit();
                    editor.putString("First_date", "");
                    editor.putString("Last_date", "");
                    editor.putString("Job_number", Search_withjob_number);

                    editor.commit();
                    new Search().execute();

                    //Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                    //  txtMessage.setText(message);
                }
            }
        };


        shared = getSharedPreferences("Tidy_waste_management", MODE_PRIVATE);
        Access_tocken = (shared.getString("Acess_tocken", "nologin"));
        Driver_id = (shared.getString("Driver_id", "nologin"));
        Check_screen = (shared.getString("Check_screen", "nologin"));
        Next_status = (shared.getString("Next_status", "null"));
        Menu menuNav = navigationView.getMenu();
        MenuItem element = menuNav.findItem(R.id.alerts);
        String before = element.getTitle().toString();
        Set<String> set = new HashSet<String>();
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        set = pref.getStringSet("Notification_LIST", null);
        String counter = "";
        try {
            if (set.size() != 0) {
                counter = Integer.toString(set.size());
            } else {
                counter = "0";
            }
        } catch (java.lang.NullPointerException e) {
            e.printStackTrace();
            counter = "0";
        }
        String s = before + "   " + counter + " ";
        SpannableString sColored = new SpannableString(s);

        sColored.setSpan(new BackgroundColorSpan(Color.parseColor("#0b813f")), s.length() - (counter.length() + 2), s.length(), 0);
        sColored.setSpan(new ForegroundColorSpan(Color.WHITE), s.length() - (counter.length() + 2), s.length(), 0);


        element.setTitle(sColored);
        img_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(Home_screen.this, Home_screen.class);

                startActivity(i1);

                finish();
            }
        });
        BTn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Next_status = "null";
                if (EdtTxt_job_number.getText().toString().contentEquals("")) {
                    Search_withjob_number = "";
                    if (!fist_date.contentEquals("") && (!last_date.contentEquals(""))) {
                        if (fist_date.contentEquals(last_date)) {
                            date_now.setText(fist_date);
                        } else {
                            date_now.setText(fist_date + " - " + last_date);
                        }

                        if (Rlv_Content.getVisibility() == View.VISIBLE) {
                            showView_slideup();
                        } else if (Rlv_Content.getVisibility() == View.GONE) {
                            showView();
                        }
                        SharedPreferences.Editor editor = shared.edit();
                        editor.putString("First_date", fist_date);
                        editor.putString("Last_date", last_date);
                        editor.putString("Job_number", "");

                        editor.commit();

                        new Search().execute();
                    } else {
                        Toast.makeText(Home_screen.this, "Please select Start and end date", Toast.LENGTH_LONG).show();
                    }
                } else {
                    fist_date = "";
                    last_date = "";
                    Search_withjob_number = EdtTxt_job_number.getText().toString();
                    date_now.setText(Search_withjob_number);
                    if (Rlv_Content.getVisibility() == View.VISIBLE) {
                        showView_slideup();
                    } else if (Rlv_Content.getVisibility() == View.GONE) {
                        showView();
                    }
                    SharedPreferences.Editor editor = shared.edit();
                    editor.putString("First_date", "");
                    editor.putString("Last_date", "");
                    editor.putString("Job_number", Search_withjob_number);

                    editor.commit();
                    new Search().execute();
                }

            }
        });


        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager, 0, 0);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(3);
        isUp = false;
        img_Calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (Rlv_Content.getVisibility() == View.VISIBLE) {
                    showView_slideup();
                } else if (Rlv_Content.getVisibility() == View.GONE) {
                    showView();
                }
                // animate();
                // Toast.makeText(Home_screen.this,"vvfvf",Toast.LENGTH_LONG).show();
            }
        });


        final Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        final Calendar lastYear = Calendar.getInstance();
        lastYear.add(Calendar.YEAR, -1);

        calendar = (CalendarPickerView) findViewById(R.id.calendarView);
        calendar.init(lastYear.getTime(), nextYear.getTime()) //
                .inMode(CalendarPickerView.SelectionMode.RANGE) //
                .withSelectedDate(new Date());

        calendar.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                ArrayList<Date> selectedDates = (ArrayList<Date>) calendar
                        .getSelectedDates();
                fist_date = selectedDates.get(0).toString();
                int size = selectedDates.size();
                last_date = selectedDates.get(size - 1).toString();
                DateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.getDefault());
                Date datee_first = null;
                Date datee_Last = null;
                try {
                    datee_first = inputFormat.parse(fist_date);
                    datee_Last = inputFormat.parse(last_date);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy",
                        Locale.getDefault());
                // outputFormat.setTimeZone(TimeZone.getTimeZone("IST"));
                outputFormat.setTimeZone(TimeZone.getDefault());

                fist_date = outputFormat.format(datee_first);
                last_date = outputFormat.format(datee_Last);

            }

            @Override
            public void onDateUnselected(Date date) {
                // Toast.makeText(Home_screen.this, date.toString(), Toast.LENGTH_LONG).show();
            }
        });
//
        calendar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        if (Check_screen.contentEquals("Login")) {
            fist_date = (shared.getString("First_date", ""));
            last_date = (shared.getString("Last_date", ""));
            Search_withjob_number = (shared.getString("Job_number", ""));
            if (fist_date.contentEquals("")) {
                fist_date = today;
                last_date = today;
                if (!Search_withjob_number.contentEquals("")) {
                    fist_date = "";
                    last_date = "";
                    date_now.setText(Search_withjob_number);
                }
            } else {

                if (!Search_withjob_number.contentEquals("")) {
                    fist_date = "";
                    last_date = "";
                    date_now.setText(Search_withjob_number);
                } else {
                    if (fist_date.contentEquals(last_date)) {
                        date_now.setText(fist_date);
                    } else {
                        date_now.setText(fist_date + " - " + last_date);
                    }
                }
            }
            new Search().execute();
        } else if (Check_screen.contentEquals("Adapter")) {
            fist_date = (shared.getString("First_date", ""));
            last_date = (shared.getString("Last_date", ""));
            Search_withjob_number = (shared.getString("Job_number", ""));
            if (fist_date.contentEquals("")) {
                fist_date = today;
                last_date = today;

            }
            if (!Search_withjob_number.contentEquals("")) {
                fist_date = "";
                last_date = "";
                date_now.setText(Search_withjob_number);
            } else {
                if (fist_date.contentEquals(last_date)) {
                    date_now.setText(fist_date);
                } else {
                    date_now.setText(fist_date + " - " + last_date);
                }
            }

            new Search().execute();
        }
    }


    private Date getDateWithYearAndMonthForDay(int year, int month, int day) {
        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void slideDown(View view) {
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    public void slideUp(View view) {
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    public void slideToTop(View view) {
        TranslateAnimation animate = new TranslateAnimation(0, 0, 0, -view.getHeight());
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.GONE);
    }

    public void slideToBottom(View view) {
        TranslateAnimation animate = new TranslateAnimation(0, 0, 0, view.getHeight());
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.GONE);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent i1 = new Intent(Home_screen.this, Login_screen.class);
            startActivity(i1);
            finish();
            // Handle the camera action
        } else if (id == R.id.alerts) {
            Intent i1 = new Intent(Home_screen.this, notification_listt.class);
            startActivity(i1);
            finish();
        } else if (id == R.id.nav_slideshow) {
            SharedPreferences settings = getSharedPreferences("Tidy_waste_management", Context.MODE_PRIVATE);
            settings.edit().clear().commit();
            Intent i1 = new Intent(Home_screen.this, Login_screen.class);
            startActivity(i1);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//
//

    private void setupViewPager(ViewPager viewPager, int pos, int size) {
        //  String str_count = String.valueOf(size);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new fragment(Arraylist_for_Search_assigned1, Arraylist_for_Search_assigned2, Status), "Assigned (" + Arraylist_for_Search_assigned1.size() + ")");
        adapter.addFrag(new acknowledge_fragment(Arraylist_for_Search_acknowledge1, Arraylist_for_Search_acknowledge2, Status), "Acknowledged (" + Arraylist_for_Search_acknowledge1.size() + ")");
        adapter.addFrag(new inpro(Arraylist_for_Search_inprogress1, Arraylist_for_Search_inprogress2, Status), "In Progress (" + Arraylist_for_Search_inprogress1.size() + ")");
        adapter.addFrag(new pulled(Arraylist_for_Search_pulled1, Arraylist_for_Search_pulled2, Status), "Pulled (" + Arraylist_for_Search_pulled1.size() + ")");
        adapter.addFrag(new changed(Arraylist_for_Search_changed1, Arraylist_for_Search_changed2, Status), "Changed (" + Arraylist_for_Search_changed1.size() + ")");
        adapter.addFrag(new shifted(Arraylist_for_Search_shifted1, Arraylist_for_Search_shifted2, Status), "Shifted (" + Arraylist_for_Search_shifted1.size() + ")");
        adapter.addFrag(new completed(Arraylist_for_Search_completed1, Arraylist_for_Search_competed2, Status), "Completed (" + Arraylist_for_Search_completed1.size() + ")");
        adapter.addFrag(new cancelled(Arraylist_for_Search_cancelled1, Arraylist_for_Search_cancelled2, Status), "Cancelled (" + Arraylist_for_Search_cancelled1.size() + ")");


        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(pos);

        //adapter.s
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    int getScreenHeight() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.heightPixels;
    }

    public void animateOnScreen(View view) {
        final int screenHeight = getScreenHeight();
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "y", screenHeight, (screenHeight * 0.8F));
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
    }

    public static void animateViewFromBottomToTop(final View view) {

        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {

                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                final int TRANSLATION_Y = view.getHeight();
                view.setTranslationY(TRANSLATION_Y);
                view.setVisibility(View.GONE);
                view.animate()
                        .translationYBy(-TRANSLATION_Y)
                        .setDuration(500)
                        .setStartDelay(200)
                        .setListener(new AnimatorListenerAdapter() {

                            @Override
                            public void onAnimationStart(final Animator animation) {

                                view.setVisibility(View.VISIBLE);
                            }
                        })
                        .start();
            }
        });
    }

    private void showView() {
        handler = new Handler();
        Animation slide_down = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.in_animation);
////
        Rlv_Content.startAnimation(slide_down);
        Rlv_Content.setVisibility(View.VISIBLE);

//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                //Rlv_Content.setVisibility(View.VISIBLE);
//
//                Rlv_Content.setVisibility(View.VISIBLE);
//            }
//        }, 600);


//etc
    }

    private void showView_slideup() {
        handler = new Handler();
        Animation slide_down = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.anim_dismiss);
////
        Rlv_Content.startAnimation(slide_down);
        //  Rlv_Content.setVisibility(View.GONE);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Rlv_Content.setVisibility(View.VISIBLE);

                Rlv_Content.setVisibility(View.GONE);
            }
        }, 600);


//etc
    }

    private void animate() {
        // ImageView imageView = (ImageView) findViewById(R.id.ImageView01);

        ScaleAnimation scale = new ScaleAnimation((float) 1.0, (float) 1.5, (float) 1.0, (float) 1.5);
        scale.setFillAfter(true);
        scale.setDuration(500);
        Rlv_Content.startAnimation(scale);
        Rlv_Content.setVisibility(View.VISIBLE);
    }

    private class Search extends AsyncTask<Void, String, Void> implements DialogInterface.OnCancelListener {


        JSONObject jsonnode, json_User;
        String jobstatus = "";


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // mProgressHUD = ProgressHUD.show(Login_Screen.this, "Connecting", true, true, this);
            // Showing progress dialog
            pDialog = new ProgressDialog(Home_screen.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
            Arraylist_for_Search_assigned1.clear();
            Arraylist_for_Search_assigned2.clear();
            Arraylist_for_Search_acknowledge1.clear();
            Arraylist_for_Search_acknowledge2.clear();
            Arraylist_for_Search_pulled1.clear();
            Arraylist_for_Search_pulled2.clear();
            Arraylist_for_Search_shifted1.clear();
            Arraylist_for_Search_shifted2.clear();
            Arraylist_for_Search_inprogress1.clear();
            Arraylist_for_Search_inprogress2.clear();
            Arraylist_for_Search_changed1.clear();
            Arraylist_for_Search_changed2.clear();
            Arraylist_for_Search_completed1.clear();
            Arraylist_for_Search_competed2.clear();
            Arraylist_for_Search_cancelled1.clear();
            Arraylist_for_Search_cancelled2.clear();


        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            publishProgress("Please wait...");


            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            //String url = "http://api.androidhive.info/contacts/";
            jsonStr = sh.makeServiceCall("http://112.196.3.42:8298/api/Job?Driverid=" + Driver_id + "&startDate=" + fist_date + "&endDate=" + last_date + "&jobNumber=" + Search_withjob_number, Access_tocken);
            //jsonStr = CharMatcher.anyOf(" *#&").removeFrom(dirtyString);
            // String newurl =jsonStr.replaceAll("null"," ");
            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                JSONObject jsonObj = null;
                JSONObject Json_category = null;

//                try {
//                    jsonObj = new JSONObject(jsonStr);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                JSONArray jArr = null;
                try {
                    //String Str_response = jsonObj.getString("data");
//                    Json_category = new JSONObject(Str_response);
//                    Str_response = Json_category.getString("categories");
                    jArr = new JSONArray(jsonStr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//
                try {


                    for (int count = 0; count < jArr.length(); count++) {
                        JSONObject jsonObjj = null;
                        String amount_tocllect = "";
                        String Project_site = "";
                        try {
                            jsonObjj = jArr.getJSONObject(count);
                            String jobtype = jsonObjj.getString("JobType");
                            jobstatus = jsonObjj.getString("JobStatus");
                            String Price = jsonObjj.getString("Price");
                            String JobDate = jsonObjj.getString("JobDateShort") + " " + jsonObjj.getString("JobTime");
                            String Nextstatus = jsonObjj.getString("Nextstatus");
                            String Job_additional_charges = jsonObjj.getString("JobAdditionalCharges");
                            // String ProjectSiteCharges = jsonObjj.getString("ProjectSiteCharges");

                            String DriverRemark = jsonObjj.getString("DriverRemark");
                            // JobDate = convertDate(JobDate);
                            String JobNumber = jsonObjj.getString("JobNumber");
                            String Project_site_id = jsonObjj.getString("ProjectSiteID");

                            String id = jsonObjj.getString("JobId");
                            String Customerr = jsonObjj.getString("Customer");
                            String JOBSteps = jsonObjj.getString("JobSteps");
                            Project_site = jsonObjj.getString("ProjectSite");
                            //JOBSteps = JOBSteps.replace("null"," ");
                            JSONArray jArr_jobs_steps = null;
                            try {
                                jArr_jobs_steps = new JSONArray(JOBSteps);
                                String PersonInCharge1 = "";
                                String ContactNo1 = "";
                                String BinType1 = "";
                                String WasteType1 = "";
                                String Remarks1 = "";
                                String Address1 = "";
                                String job_step_id1 = "";
                                String bin_number1 = "";
                                String Collect_payment = "";
                                String Collection_method1 = "";
                                String AmountCollected1 = "";
                                String DriverNotes1 = "";
                                String CompletedTime1 = "";
                                String CustomerSignature1 = "";
                                String PhotoFile1 = "";
                                String signedby1 = "";


                                //////////////////////////////////////////////////////
                                for (int count_step = 0; count_step < jArr_jobs_steps.length(); count_step++) {
                                    JSONObject jsonObj_steps = null;
                                    try {
                                        // BinNumber
                                        jsonObj_steps = jArr_jobs_steps.getJSONObject(count_step);
                                        String PersonInCharge = jsonObj_steps.getString("PersonInCharge");
                                        String ContactNo = jsonObj_steps.getString("ContactNo");
                                        String BinType = jsonObj_steps.getString("BinType");
                                        String WasteType = jsonObj_steps.getString("WasteType");
                                        String Remarks = jsonObj_steps.getString("Remarks");
                                        String Amount_collected = jsonObj_steps.getString("AmountToCollect");
                                        String Location_BlockNo = jsonObj_steps.getString("Location_BlockNo");
                                        String Location_RoadName = jsonObj_steps.getString("Location_RoadName");
                                        String JobStepID = jsonObj_steps.getString("JobStepID");
                                        String BinNumber = jsonObj_steps.getString("BinNumber");
                                        String Collect_paymentt = jsonObj_steps.getString("CollectPayment");
                                        String Collection_method = jsonObj_steps.getString("CollectionMethod");
                                        String AmountCollected = jsonObj_steps.getString("AmountCollected");
                                        String driverjsonobjectnode = jsonObj_steps.getString("DriverNote");
                                        //String DriverNotes = jsonObj_steps.getString("DriverNotes");
                                        String CompletedTime = jsonObj_steps.getString("CompletedTime");
                                        String CustomerSignature = jsonObj_steps.getString("CustomerSignature");
                                        String PhotoFile = jsonObj_steps.getString("PhotoFile");
                                        String signed_by = jsonObj_steps.getString("SignedBy");
                                        String DriverNotes="";
                                        try {
                                            JSONObject Json_object = null;
                                            Json_object = new JSONObject(driverjsonobjectnode);
                                            DriverNotes = Json_object.getString("DriverNotes");

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        //String signed_by = jsonObj_steps.getString("SignedBy");

                                        if (PersonInCharge.contentEquals("null")) {
                                            PersonInCharge = " ";
                                        }
                                        if (ContactNo.contentEquals("null")) {
                                            ContactNo = " ";
                                        }
                                        if (BinType.contentEquals("null")) {
                                            BinType = " ";
                                        }
                                        if (WasteType.contentEquals("null")) {
                                            WasteType = " ";
                                        }
                                        if (Remarks.contentEquals("null")) {
                                            Remarks = " ";
                                        }
                                        if (Amount_collected.contentEquals("null")) {
                                            Amount_collected = " ";
                                        }
                                        if (Location_BlockNo.contentEquals("null")) {
                                            Location_BlockNo = " ";
                                        }
                                        if (Location_RoadName.contentEquals("null")) {
                                            Location_RoadName = " ";
                                        }
                                        if (BinNumber.contentEquals("null")) {
                                            BinNumber = " ";
                                        }
                                        if (Collection_method.contentEquals("null")) {
                                            Collection_method = " ";
                                        }
                                        if (AmountCollected.contentEquals("null")) {
                                            AmountCollected = " ";
                                        }
                                        if (DriverNotes.contentEquals("null")) {
                                            DriverNotes = " ";
                                        }
                                        if (CompletedTime.contentEquals("null")) {
                                            CompletedTime = " ";
                                        }
                                        if (CustomerSignature.contentEquals("null")) {
                                            CustomerSignature = " ";
                                        }
                                        if (PhotoFile.contentEquals("null")) {
                                            PhotoFile = " ";
                                        }
                                        if (signed_by.contentEquals("null")) {
                                            signed_by = " ";
                                        }
                                        String Adress = Location_BlockNo + " " + Location_RoadName;

                                        String wastetype_name = "";
                                        String bin_type_name = "";
                                        try {
                                            JSONObject Json_object = null;
                                            Json_object = new JSONObject(BinType);
                                            bin_type_name = Json_object.getString("BinTypeName");

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        try {
                                            JSONObject Json_object = null;
                                            Json_object = new JSONObject(WasteType);
                                            wastetype_name = Json_object.getString("WasteTypeName");

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        if (count_step == 0) {
                                            PersonInCharge1 = PersonInCharge;
                                            ContactNo1 = ContactNo;
                                            BinType1 = bin_type_name;
                                            WasteType1 = wastetype_name;
                                            Remarks1 = Remarks;
                                            amount_tocllect = Amount_collected;
                                            Address1 = Adress;
                                            job_step_id1 = JobStepID;
                                            bin_number1 = BinNumber;
                                            Collect_payment = Collect_paymentt;
                                            Collection_method1 = Collection_method;
                                            AmountCollected1 = AmountCollected;
                                            DriverNotes1 = DriverNotes;
                                            CompletedTime1 = CompletedTime;
                                            CustomerSignature1 = CustomerSignature;
                                            PhotoFile1 = PhotoFile;
                                            signedby1 = signed_by;
                                        }
                                        if (count_step == 1) {
                                            PersonInCharge1 = PersonInCharge1 + ",," + PersonInCharge;
                                            ContactNo1 = ContactNo1 + ",," + ContactNo;
                                            BinType1 = BinType1 + ",," + bin_type_name;
                                            WasteType1 = WasteType1 + ",," + wastetype_name;
                                            Remarks1 = Remarks1 + ",," + Remarks;
                                            Address1 = Address1 + ",," + Adress;
                                            bin_number1 = bin_number1 + ",," + BinNumber;
                                            job_step_id1 = job_step_id1 + ",," + JobStepID;
                                            int amount1 = Integer.parseInt(amount_tocllect);
                                            int amount2 = Integer.parseInt(Amount_collected);
                                            int final_int = amount1 + amount2;
                                            amount_tocllect = String.valueOf(final_int);
                                            Collect_payment = Collect_payment + ",," + Collect_paymentt;
                                            Collection_method1 = Collection_method1 + ",," + Collection_method;
                                            AmountCollected1 = AmountCollected1 + ",," + AmountCollected;
                                            DriverNotes1 = DriverNotes1 + ",," + DriverNotes;
                                            CompletedTime1 = CompletedTime1 + ",," + CompletedTime;
                                            CustomerSignature1 = CustomerSignature1 + ",," + CustomerSignature;
                                            PhotoFile1 = PhotoFile1 + ",," + PhotoFile;
                                            signedby1 = signedby1 + ",," + signed_by;
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                HashMap<String, String> jobs_steps = new HashMap<String, String>();

                                jobs_steps.put("PersonInCharge", PersonInCharge1);
                                jobs_steps.put("ContactNo", ContactNo1);
                                jobs_steps.put("Remarks", Remarks1);
                                jobs_steps.put("Collect_payment", Collect_payment);


                                jobs_steps.put("bin_number", bin_number1);
                                jobs_steps.put("wastetype_name", WasteType1);
                                jobs_steps.put("bin_type_name", BinType1);
                                jobs_steps.put("adress", Address1);
                                jobs_steps.put("job_step_id1", job_step_id1);
                                jobs_steps.put("Collection_method1", Collection_method1);
                                jobs_steps.put("AmountCollected1", AmountCollected1);
                                jobs_steps.put("DriverNotes1", DriverNotes1);
                                jobs_steps.put("CompletedTime1", CompletedTime1);
                                jobs_steps.put("CustomerSignature1", CustomerSignature1);
                                jobs_steps.put("PhotoFile1", PhotoFile1);
                                jobs_steps.put("signedby1", signedby1);
                                if (jobstatus.contentEquals("Assigned")) {
                                    Arraylist_for_Search_assigned1.add(jobs_steps);
                                } else if (jobstatus.contentEquals("Acknowledged")) {
                                    Arraylist_for_Search_acknowledge1.add(jobs_steps);
                                } else if (jobstatus.contentEquals("In Progress")) {
                                    Arraylist_for_Search_inprogress1.add(jobs_steps);
                                } else if (jobstatus.contentEquals("Pulled")) {
                                    Arraylist_for_Search_pulled1.add(jobs_steps);
                                } else if (jobstatus.contentEquals("Changed")) {
                                    Arraylist_for_Search_changed1.add(jobs_steps);
                                } else if (jobstatus.contentEquals("Shifted")) {
                                    Arraylist_for_Search_shifted1.add(jobs_steps);
                                } else if (jobstatus.contentEquals("Completed")) {
                                    Arraylist_for_Search_completed1.add(jobs_steps);
                                } else if (jobstatus.contentEquals("Cancelled")) {
                                    Arraylist_for_Search_cancelled1.add(jobs_steps);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            JSONObject Json_Cutomer = null;
                            String customer_name = "";
                            String Address = "";
                            String payment_term = "";


                            try {
                                Json_Cutomer = new JSONObject(Customerr);
                                customer_name = Json_Cutomer.getString("CustomerName");
                                Address = Json_Cutomer.getString("AddressLine1");
                                payment_term = Json_Cutomer.getString("PaymentTerms");


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            HashMap<String, String> Search_result = new HashMap<String, String>();

                            // adding each child node to HashMap key => value
                            Search_result.put("jobtype", jobtype);
                            Search_result.put("Price", amount_tocllect);
                            Search_result.put("customer_name", customer_name);
                            Search_result.put("Payment_term", payment_term);
                            Search_result.put("Project_site", Project_site);
                            Search_result.put("JobDate", JobDate);
                            Search_result.put("Nextstatus", Nextstatus);
                            Search_result.put("Jobnumber", JobNumber);
                            Search_result.put("Job_additional_charges", Job_additional_charges);
                            Search_result.put("Project_site_id", Project_site_id);

                            //Job_additional_charges
                            Search_result.put("id", id);
                            Search_result.put("jobstatus", jobstatus);
                            Search_result.put("DriverRemark", DriverRemark);
                            // DriverRemark
                            Search_result.put("Address", Address);

                            if (jobstatus.contentEquals("Assigned")) {
                                Search_result.put("Check_fragment", "Assigned");
                            } else if (jobstatus.contentEquals("Acknowledged")) {
                                Search_result.put("Check_fragment", "Acknowledge");
                            } else if (jobstatus.contentEquals("In Progress")) {
                                Search_result.put("Check_fragment", "In Progress");
                            } else if (jobstatus.contentEquals("Pulled")) {
                                Search_result.put("Check_fragment", "pulled");
                            } else if (jobstatus.contentEquals("Changed")) {
                                Search_result.put("Check_fragment", "Changed");
                            } else if (jobstatus.contentEquals("Shifted")) {
                                Search_result.put("Check_fragment", "Shifted");
                            } else if (jobstatus.contentEquals("Completed")) {
                                Search_result.put("Check_fragment", "Completed");
                            } else if (jobstatus.contentEquals("Cancelled")) {
                                Search_result.put("Check_fragment", "cancelled");
                            }


                            // adding hashmap to arraylist
                            if (jobstatus.contentEquals("Assigned")) {
                                Arraylist_for_Search_assigned2.add(Search_result);
                            } else if (jobstatus.contentEquals("Acknowledged")) {
                                Arraylist_for_Search_acknowledge2.add(Search_result);
                            } else if (jobstatus.contentEquals("In Progress")) {
                                Arraylist_for_Search_inprogress2.add(Search_result);
                            } else if (jobstatus.contentEquals("Pulled")) {
                                Arraylist_for_Search_pulled2.add(Search_result);
                            } else if (jobstatus.contentEquals("Changed")) {
                                Arraylist_for_Search_changed2.add(Search_result);
                            } else if (jobstatus.contentEquals("Shifted")) {
                                Arraylist_for_Search_shifted2.add(Search_result);
                            } else if (jobstatus.contentEquals("Completed")) {
                                Arraylist_for_Search_competed2.add(Search_result);
                            } else if (jobstatus.contentEquals("Cancelled")) {
                                Arraylist_for_Search_cancelled2.add(Search_result);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (java.lang.NullPointerException e) {
                    e.printStackTrace();
                    Log.e("ServiceHandler", "Couldn't get any data from the url");
                }
                // Getting JSON Array node
                // JSONArray array1 = null;

            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            pDialog.dismiss();
            if (Rlv_Content.getVisibility() == View.VISIBLE) {
                showView_slideup();
            }
            if (jsonStr == null) {
                open_loginWindow();
            } else if (jsonStr.contentEquals("Invalid_Token")) {
                open_loginWindow();
            }
            Status = "Search";
            int position = 0;
            int size = 0;
            if (!Next_status.contentEquals("null")) {

                if (Next_status.contentEquals("Assigned")) {
                    position = 0;

                } else if (Next_status.contentEquals("Acknowledged")) {
                    position = 1;
                } else if (Next_status.contentEquals("In Progress")) {
                    position = 2;
                } else if (Next_status.contentEquals("cancelled")) {
                    position = 7;
                } else if (Next_status.contentEquals("Changed")) {
                    position = 4;
                } else if (Next_status.contentEquals("Completed")) {
                    position = 6;
                } else if (Next_status.contentEquals("pulled")) {
                    position = 3;
                } else if (Next_status.contentEquals("Shifted")) {
                    position = 5;
                }

            } else {
//                if (jobstatus.contentEquals("Assigned")) {
//                    Arraylist_for_Search_assigned1.add(jobs_steps);
//                } else if (jobstatus.contentEquals("Acknowledged")) {
//                    Arraylist_for_Search_acknowledge1.add(jobs_steps);
//                } else if (jobstatus.contentEquals("In Progress")) {
//                    Arraylist_for_Search_inprogress1.add(jobs_steps);
//                } else if (jobstatus.contentEquals("Pulled")) {
//                    Arraylist_for_Search_pulled1.add(jobs_steps);
//                } else if (jobstatus.contentEquals("Changed")) {
//                    Arraylist_for_Search_changed1.add(jobs_steps);
//                } else if (jobstatus.contentEquals("Shifted")) {
//                    Arraylist_for_Search_shifted1.add(jobs_steps);
//                } else if (jobstatus.contentEquals("Completed")) {
//                    Arraylist_for_Search_completed1.add(jobs_steps);
//                } else if (jobstatus.contentEquals("Cancelled")) {
//                    Arraylist_for_Search_cancelled1.add(jobs_steps);
//                }

                if (Arraylist_for_Search_assigned1.size() > 0) {
                    position = 0;
                    size = Arraylist_for_Search_assigned1.size();
                } else if (Arraylist_for_Search_acknowledge1.size() > 0) {
                    position = 1;
                    size = Arraylist_for_Search_acknowledge1.size();
                } else if (Arraylist_for_Search_inprogress1.size() > 0) {
                    size = Arraylist_for_Search_inprogress1.size();
                    position = 2;
                } else if (Arraylist_for_Search_pulled1.size() > 0) {
                    size = Arraylist_for_Search_pulled1.size();
                    position = 3;
                } else if (Arraylist_for_Search_changed1.size() > 0) {
                    size = Arraylist_for_Search_changed1.size();
                    position = 4;
                } else if (Arraylist_for_Search_shifted1.size() > 0) {
                    size = Arraylist_for_Search_shifted1.size();
                    position = 5;
                } else if (Arraylist_for_Search_completed1.size() > 0) {
                    size = Arraylist_for_Search_completed1.size();
                    position = 6;
                } else if (Arraylist_for_Search_cancelled1.size() > 0) {
                    size = Arraylist_for_Search_cancelled1.size();
                    position = 7;
                }


//                if (jobstatus.contentEquals("Assigned")) {
//                    position = 0;
//
//                } else if (jobstatus.contentEquals("Acknowledged")) {
//                    position = 1;
//                } else if (jobstatus.contentEquals("In Progress")) {
//                    position = 2;
//                } else if (jobstatus.contentEquals("cancelled")) {
//                    position = 7;
//                } else if (jobstatus.contentEquals("Changed")) {
//                    position = 4;
//                } else if (jobstatus.contentEquals("Competed")) {
//                    position = 6;
//                } else if (jobstatus.contentEquals("pulled")) {
//                    position = 3;
//                } else if (jobstatus.contentEquals("Shifted")) {
//                    position = 5;
//                }

            }
            setupViewPager(viewPager, position, size);
        }

        @Override
        public void onCancel(DialogInterface dialog) {
            if (jsonStr.contentEquals("Invalid Token")) {
                open_loginWindow();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static String convertDate(String strDate) {
        //for strdate = 2017 July 25
//Caused by: java.lang.NoClassDefFoundError
        DateTimeFormatter f = new DateTimeFormatterBuilder().appendPattern("yyyyMMdd'T'hhmmss'Z'")
                .toFormatter();

        LocalDate parsedDate = LocalDate.parse(strDate, f);
        DateTimeFormatter f2 = DateTimeFormatter.ofPattern("MM/d/yyyy");

        String newDate = parsedDate.format(f2);

        return newDate;
    }

    public void open_loginWindow() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Your session has expired. Please log in again");
        alertDialogBuilder.setPositiveButton("ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        SharedPreferences settings = getSharedPreferences("Tidy_waste_management", Context.MODE_PRIVATE);
                        settings.edit().clear().commit();
                        Intent i1 = new Intent(Home_screen.this, Login_screen.class);
                        startActivity(i1);
                        finish();
                    }
                });


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        // Log.e(TAG, "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId))
            Toast.makeText(getApplicationContext(), regId, Toast.LENGTH_LONG).show();
            //txtRegId.setText("Firebase Reg Id: " + regId);
        else
            Toast.makeText(getApplicationContext(), "Firebase Reg Id is not received yet!", Toast.LENGTH_LONG).show();
        //txtRegId.setText("Firebase Reg Id is not received yet!");
    }

}

