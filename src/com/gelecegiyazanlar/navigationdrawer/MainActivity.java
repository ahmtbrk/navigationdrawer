package com.gelecegiyazanlar.navigationdrawer;

import com.gelecegiyazanlar.navigationdrawe.fragments.FragmentAndroid;
import com.gelecegiyazanlar.navigationdrawe.fragments.FragmentHome;
import com.gelecegiyazanlar.navigationdrawe.fragments.FragmentIOS;
import com.gelecegiyazanlar.navigationdrawe.fragments.FragmentWindowsPhone;
import com.gelecegiyazanlar.navigationdrawer.R;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	// Sol Slider için Yapýlmýþ özel layout android.support.v4 ün içinde
	private DrawerLayout mDrawerLayout;

	// Sol Slider Açýldýðýnda Görünecek ListView
	private ListView mDrawerList;

	// Navigation Drawer nesnesini ActionBar'da gösterir.
	private ActionBarDrawerToggle mDrawerToggle;

	// ActionBar'ýn titlesi dinamik olarak deðiþecek draweri açýp kapattýkça
	private String mTitle = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Content alanýna fragment yüklemek için
		FragmentManager fragmentManager = getFragmentManager();

		
		FragmentTransaction ft = fragmentManager.beginTransaction();
		
		FragmentHome fragmentHome = new FragmentHome();
		ft.add(R.id.content_frame, fragmentHome);
		ft.commit();
		
		mTitle = "Geleceði Yazanlar";
		getActionBar().setTitle(mTitle);

		
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		mDrawerList = (ListView) findViewById(R.id.drawer_list);

		// iconu ve açýlýp kapandýðýnda görünecek texti veriyoruz.
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {

			// drawer kapatýldýðýnda tetiklenen method
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu();

			}

			// drawer açýldýðýnda tetiklenen method
			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle("Navigation Drawer GY");
				invalidateOptionsMenu();
			}

		};

		// Açýlýp kapanmayý dinlemek için register
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		// Navigationdaki Drawer için listview adapteri
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), 
				R.layout.drawer_list_item, getResources().getStringArray(R.array.menu));

		// adapteri listviewe set ediyoruz
		mDrawerList.setAdapter(adapter);

		// actionbar home butonunu aktif ediyoruz
		getActionBar().setHomeButtonEnabled(true);

		// navigationu týklanabilir hale getiriyoruz
		getActionBar().setDisplayHomeAsUpEnabled(true);

		// sol slider açýldýðýnda gelen listviewin týklama eventi
		mDrawerList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// itemleri arraya tekrar aldýk
				String[] menuItems = getResources().getStringArray(R.array.menu);

				// dinamik title yapmak için actionbarda týklananýn titlesi görünecek
				mTitle = menuItems[position];
				
				FragmentManager fragmentManager = getFragmentManager();
				FragmentTransaction ft = fragmentManager.beginTransaction();

				// fragmenti contente yerleþtirme.
				if(position==0){
					FragmentHome fragmentHome = new FragmentHome();
					ft.replace(R.id.content_frame, fragmentHome);
					ft.commit();
				}else if(position==1){
					FragmentAndroid fragmentAndroid = new FragmentAndroid();
					ft.replace(R.id.content_frame, fragmentAndroid);
					ft.commit();
				}else if(position==2){
					FragmentIOS fragmentIOS = new FragmentIOS();
					ft.replace(R.id.content_frame, fragmentIOS);
					ft.commit();
				}else if(position==3){
					FragmentWindowsPhone fragmentWindowsPhone = new FragmentWindowsPhone();
					ft.replace(R.id.content_frame, fragmentWindowsPhone);
					ft.commit();
				}
				
				// draweri kapat
				mDrawerLayout.closeDrawer(mDrawerList);

			}
		});
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//draweri sadece swipe ederek açma yerine sol tepedeki butona basarak açmak için
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		// navigationDrawer açýldýðýnda ayarlarý gizlemek için
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
