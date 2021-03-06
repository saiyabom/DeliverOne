package com.dizarale.deliverone.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

    public String testPhone="11111111";

    // Operation type (what is being executed)
    public static final int MENU = 0;
    public static final int FOOD_DETAIL=5;
    public static final int SHOPPING_CART = 6;
    public static final int MAP = 7;
    public static final int SUMMARY = 8;
    public static final int SETTINGS = 9;
    public static final int HISTORY_ORDER=10;


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

    public static int mType = MENU;

    protected Toolbar mToolbar;

    private NavigationDrawerFragment mDrawerFragment;

    public void actAsMenu(){
        mType = MENU;
        mTitle = getString(R.string.text_menutype_home);
    }
    public void actAsShoppingCart(){
        mType = SHOPPING_CART;
        mTitle = getString(R.string.activity_shoppingcart);
    }
    public void actAsMaps(){
        mType = MAP;
        mTitle = getString(R.string.activity_map);
    }
    public void actAsFoodDetail(String name){
        mType = FOOD_DETAIL;
        if(null != name){
            mTitle = name;
        }else{mTitle = AppConstant.FOOD_DETAIL;}
    }
    public void actAsSummary(){
        mType = SUMMARY;
        mTitle = getString(R.string.activity_summary);
    }
    public void actAsSnack(){
        mType = SNACK;
        mTitle = getString(R.string.text_menutype_snack);
    }
    public void actAsMeal(){
        mType = MEAL;
        mTitle = getString(R.string.text_menutype_meal);
    }
    public void actAsSweet(){
        mType = SWEET;
        mTitle = getString(R.string.text_menutype_sweet);
    }
    public void actAsDrink(){
        mType = DRINK;
        mTitle = getString(R.string.text_menutype_drink);
    }
    public void actAsHistoryOrder(){
        mType = HISTORY_ORDER;
        mTitle = getString(R.string.text_menutype_history);
    }

    public Toolbar activateToolbar(){
        if(mToolbar == null){
            mToolbar = (Toolbar) findViewById(R.id.app_bar);
            if(mToolbar != null){
                setSupportActionBar(mToolbar);
                switch(mType){
                    case MENU:
                        getSupportActionBar().setTitle(getString(R.string.text_menutype_home));

                        break;
                    case SNACK:
                        getSupportActionBar().setTitle(getString(R.string.text_menutype_snack));

                        break;
                    case MEAL:
                        getSupportActionBar().setTitle(getString(R.string.text_menutype_meal));

                    break;
                    case SWEET:
                        getSupportActionBar().setTitle(getString(R.string.text_menutype_sweet));

                        break;
                    case DRINK:
                        getSupportActionBar().setTitle(getString(R.string.text_menutype_drink));

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
                    getSupportActionBar().setTitle(mTitle);
                    break;
                case MAP:
                    getSupportActionBar().setTitle(mTitle);
                    break;
                case SUMMARY:
                    getSupportActionBar().setTitle(mTitle);
                    break;
                case FOOD_DETAIL:
                    getSupportActionBar().setTitle(mTitle);
                    break;
                case HISTORY_ORDER:
                    getSupportActionBar().setTitle(mTitle);
                    break;
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

        items.add(new NavigationDrawerItem(android.R.drawable.ic_menu_gallery, getString(R.string.text_menutype_home)));
        items.add(new NavigationDrawerItem(R.drawable.snack, getString(R.string.text_menutype_snack)));
        items.add(new NavigationDrawerItem(R.drawable.meal, getString(R.string.text_menutype_meal)));
        items.add(new NavigationDrawerItem(R.drawable.sweet, getString(R.string.text_menutype_sweet)));
        items.add(new NavigationDrawerItem(R.drawable.drinking, getString(R.string.text_menutype_drink)));
        items.add(new NavigationDrawerItem(android.R.drawable.ic_menu_recent_history, getString(R.string.text_menutype_history)));

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
                        /*AppSharedPreferences.setHasPhoneNumber(BaseActivity.this,AppConstant.PREF_PHONE,false);
                        mNextActivity = MainActivity.class;*/
                        mNextActivity = HistoryOrderAcitvity.class;
                        actAsHistoryOrder();
                        break;
                    /*case 6:
                        mNextActivity = HistoryOrderAcitvity.class;
                        actAsHistoryOrder();
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
    public void showDialog(final Context context){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_box_fragment);
        dialog.setTitle("Setting");
        CheckBox checkNotification = (CheckBox) dialog.findViewById(R.id.check_notification);
                /* if(PreferenceNotification ==true){*/
        checkNotification.setChecked(AppSharedPreferences.getNotifaction(context));
        checkNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AppSharedPreferences.setNotification(context,isChecked);
            }
        });


        // set the custom dialog components - text, image and button
        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
