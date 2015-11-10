package com.dizarale.deliverone.activity;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dizarale.deliverone.R;
import com.dizarale.deliverone.config.APIService;
import com.dizarale.deliverone.config.AppConstant;
import com.dizarale.deliverone.config.AppSharedPreferences;
import com.dizarale.deliverone.navigation.NavigationDrawerAdapter;
import com.dizarale.deliverone.navigation.NavigationDrawerFragment;
import com.dizarale.deliverone.navigation.NavigationDrawerItem;
import com.google.zxing.client.android.CaptureActivity;
import com.google.zxing.client.android.Intents;


import java.util.ArrayList;
import java.util.List;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by s84021369 on 8/28/2015.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 200;

    public String testPhone="0874969990";
    public String testDes="Sound Bad";
    public String testNum="6";

    // Operation type (what is being executed)
    public static final int MENU = 0;
    public static final int FOOD_DETAIL=1;
    public static final int SHOPPING_CART = 2;
    public static final int MAP = 3;
    public static final int SUMMARY = 4;
    public static final int SETTINGS = 5;





    //Menu Catagories List
    public static final int SNACK = 1;
    public static final int MEAL = 2;
    public static final int SWEET = 3;
    public static final int DRINK= 4;

    //URL
    public static final String BASE_URL = "http://um-project.com/projectx/index.php/example_api/";

    protected Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    protected APIService service = retrofit.create(APIService.class);





    private Class mNextActivity;

    public static String mTitle = AppConstant.TOOLBAR_TITLE;

    public static int mType = SNACK;

    protected Toolbar mToolbar;

    private NavigationDrawerFragment mDrawerFragment;

    public void actAsMenu(){
        mType = MENU;
        mTitle = AppConstant.TOOLBAR_TITLE;
    }
    public void actAsShoppingCart(){
        mType = SHOPPING_CART;
        mTitle = AppConstant.SHOPPING_CART;
    }
    public void actAsMaps(){
        mType = MAP;
        mTitle = AppConstant.MAP;
    }
    public void actAsFoodDetail(String name){
        mType = FOOD_DETAIL;
        if(null != name){
            mTitle = name;
        }else{mTitle = AppConstant.FOOD_DETAIL;}
    }
    public void actAsSummary(){
        mType = SUMMARY;
        mTitle = AppConstant.SUMMARY;
    }
    public void actAsSnack(){
        mType = SNACK;
        mTitle = AppConstant.DRAWER_SNACK;
    }
    public void actAsMeal(){
        mType = MEAL;
        mTitle = AppConstant.DRAWER_MEAL;
    }
    public void actAsSweet(){
        mType = SWEET;
        mTitle = AppConstant.DRAWER_SWEET;
    }
    public void actAsDrink(){
        mType = DRINK;
        mTitle = AppConstant.DRAWER_DRINK;
    }

    public Toolbar activateToolbar(){
        if(mToolbar == null){
            mToolbar = (Toolbar) findViewById(R.id.app_bar);
            if(mToolbar != null){
                setSupportActionBar(mToolbar);
                switch(mType){
                    case MENU:
                        getSupportActionBar().setTitle(AppConstant.TOOLBAR_TITLE);

                        break;
                    case SNACK:
                        getSupportActionBar().setTitle(AppConstant.DRAWER_SNACK);

                        break;
                    case MEAL:
                        getSupportActionBar().setTitle(AppConstant.DRAWER_MEAL);

                    break;
                    case SWEET:
                        getSupportActionBar().setTitle(AppConstant.DRAWER_SWEET);

                        break;
                    case DRINK:
                        getSupportActionBar().setTitle(AppConstant.DRAWER_DRINK);

                        break;
                }
            }
        }
        return mToolbar;
    }
    public Toolbar activateToolbarWithHomeEnabled(){
        activateToolbar();
        if(mToolbar !=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            switch(mType){
                case SHOPPING_CART:
                    getSupportActionBar().setTitle(AppConstant.SHOPPING_CART);
                    break;
                case MAP:
                    getSupportActionBar().setTitle(AppConstant.MAP);
                    break;
                case SUMMARY:
                    getSupportActionBar().setTitle(AppConstant.SUMMARY);
                    break;
                case FOOD_DETAIL:
                    getSupportActionBar().setTitle(mTitle);

                    break;
            }

        }
        return mToolbar;
    }
    public Toolbar activateToolbarWithHomeEnabled(String name){
        activateToolbar();
        if(mToolbar !=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if(mType == FOOD_DETAIL){
                getSupportActionBar().setTitle(name);
            }else{
                getSupportActionBar().setTitle(AppConstant.FOOD_DETAIL);
            }

        }
        return mToolbar;
    }


    /*protected void removeAction(){
        CardView cardView;
        cardView = (CardView) findViewById(R.id.card_view);
        cardView.setVisibility(View.GONE);
    }*/

    protected void setUpNavigationDrawer(){
        ListView navigationListView;
        //Few items, so added manually

        List<NavigationDrawerItem> items = new ArrayList<>();
        items.add(new NavigationDrawerItem(android.R.drawable.ic_lock_power_off, AppConstant.DRAWER_HOME));
        items.add(new NavigationDrawerItem(android.R.drawable.ic_menu_gallery, AppConstant.DRAWER_SNACK));
        items.add(new NavigationDrawerItem(android.R.drawable.ic_btn_speak_now, AppConstant.DRAWER_MEAL));
        items.add(new NavigationDrawerItem(android.R.drawable.ic_menu_send, AppConstant.DRAWER_SWEET));
        items.add(new NavigationDrawerItem(android.R.drawable.ic_btn_speak_now, AppConstant.DRAWER_DRINK));
        items.add(new NavigationDrawerItem(android.R.drawable.ic_menu_preferences, AppConstant.DRAWER_SETTINGS));
        items.add(new NavigationDrawerItem(android.R.drawable.ic_menu_help, AppConstant.DRAWER_HELP_AND_FEEDBACK));

        mDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        mDrawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);

        NavigationDrawerAdapter navigationDrawerAdapter = new NavigationDrawerAdapter(getApplicationContext(), items);
        //Initialize the list view for navigation drawer
        navigationListView = (ListView) findViewById(R.id.navigation_list);
        navigationListView.setAdapter(navigationDrawerAdapter);

        navigationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        mNextActivity = MenuActivity.class;
                        actAsMenu();
                        break;
                    case 1:
                        mNextActivity = MenuActivity.class;
                        actAsSnack();
                        break;
                    case 2:
                        mNextActivity = MenuActivity.class;
                        actAsMeal();
                        break;
                    case 3:
                        mNextActivity = MenuActivity.class;
                        actAsSweet();
                        break;
                    case 4:
                        mNextActivity = MenuActivity.class;
                        actAsDrink();
                        break;
                    case 5:
                        AppSharedPreferences.setHasPhoneNumber(BaseActivity.this,AppConstant.PREF_PHONE,false);
                        mNextActivity = MainActivity.class;
                        break;
                    /*case 6:
                        mNextActivity = HelpFeedActivity.class;
                        break;*/
                    /*default:
                        mNextActivity = HelpFeedActivity.class;
                        break;*/
                }
                AppSharedPreferences.setUserLearned(getApplicationContext(), AppConstant.KEY_USER_LEARNED_DRAWER,
                        AppConstant.TRUE);
                Intent intent = new Intent(BaseActivity.this, mNextActivity);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivityForResult(intent, 0);
                overridePendingTransition(0, 0);
                mDrawerFragment.closeDrawer();
            }
        });


    }
    protected void callCapture(String characterSet) {

        Intent intent = new Intent();
        intent.setAction(Intents.Scan.ACTION);
        // intent.putExtra(Intents.Scan.MODE, Intents.Scan.QR_CODE_MODE);
        intent.putExtra(Intents.Scan.CHARACTER_SET, characterSet);
        intent.putExtra(Intents.Scan.WIDTH, 800);
        intent.putExtra(Intents.Scan.HEIGHT, 600);
        // intent.putExtra(Intents.Scan.PROMPT_MESSAGE, "type your prompt message");
        intent.setClass(this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }
}
