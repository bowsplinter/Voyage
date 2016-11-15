package istd.team4.travelapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

//    public ArrayList getBestRoute(View v) {
//        return ItineraryPlanner.getBestRoute(v);
//    }
//
//    public ArrayList getApprox(View v) {
//        return ItineraryPlanner.getApprox(v);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        // load data into sqllite3
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        int stored = settings.getInt("stored",0);
        Log.v("stored", "" + stored);
        if (stored == 0) {
            InputStream inputStream = getResources().openRawResource(R.raw.singlish);
            BufferedReader read = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-16")));
            String line;
            try {
                while ((line = read.readLine()) != null) {
                    String[] cells = line.split("!@#\\$%", -1);
                    SinglishDatabaseHelper databaseHelper = SinglishDatabaseHelper.getInstance(this);
                    databaseHelper.addSinglish(cells[0], cells[1], cells[2], cells[3]);
                }
            } catch (IOException e) {
                System.out.println(e);
            }
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("stored", 1);
            editor.apply();
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ItineraryPlanner(), "ONE");
        adapter.addFragment(new TwoFragment(), "TWO");
        adapter.addFragment(new ThreeFragment(), "THREE");
        viewPager.setAdapter(adapter);
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

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public void changeSinglishDetail(long id){
        Intent intent = new Intent(this, SinglishDetailActivity.class);
        Log.v("change", "id " + id);
        intent.putExtra("singlish", id);
        startActivity(intent);
    }

//    public void setupListener(ListView wordList) {
//        wordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                changeSinglishDetail(id);
//            }
//        });
//    }
}