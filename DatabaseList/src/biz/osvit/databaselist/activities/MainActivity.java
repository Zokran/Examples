package biz.osvit.databaselist.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import biz.osvit.databaselist.R;
import biz.osvit.databaselist.fragments.AddUserFragment;
import biz.osvit.databaselist.fragments.EditDialogFragment;
import biz.osvit.databaselist.fragments.EditDialogFragment.OnNotifyDataChangedListener;
import biz.osvit.databaselist.fragments.ShowUserFragment;
import biz.osvit.databaselist.listeners.DialogFragmentListener;
import biz.osvit.databaselist.listeners.ShowUserFragmentListener;
import biz.osvit.databaselist.utils.C;

/**
 * Title: Database List <br />
 * Copyright: Copyright @ 2014 <br />
 * 
 * @author Zoran Veres
 * @version 1.0.0
 */

public class MainActivity extends BaseActivity implements DialogFragmentListener, ShowUserFragmentListener {

	private static final String TAG_SHOW_USER_FRAGMENT = "tag_show_user_fragment";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(C.APPLICATION_NAME, "Main activity, onCreate called");
		setContentView(R.layout.activity_main);

		initUi();
		initListeners();
	}

	@Override
	protected void initUi() {

		AddUserFragment addUserFragment = AddUserFragment.newInstance();
		FragmentManager manager = getFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		hideKeyboard(getCurrentFocus());
		transaction.add(R.id.activity_main_fragment_holder, addUserFragment);
		transaction.commit();
	}

	@Override
	public void showUserFragmentListener() {

		ShowUserFragment showUserFragment = ShowUserFragment.newInstance();
		FragmentManager manager = getFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		hideKeyboard(getCurrentFocus());
		transaction.replace(R.id.activity_main_fragment_holder, showUserFragment, TAG_SHOW_USER_FRAGMENT);
		transaction.addToBackStack(null);
		transaction.commit();
	}

	@Override
	public void dialogFragmentListener(long id) {
		EditDialogFragment editDialogFragment = EditDialogFragment.newInstance(id, mNotifyDataChangedListener);
		FragmentManager manager = getFragmentManager();
		hideKeyboard(getCurrentFocus());
		FragmentTransaction transaction = manager.beginTransaction();
		editDialogFragment.show(transaction, "editFragment");
	}

	private OnNotifyDataChangedListener mNotifyDataChangedListener = new OnNotifyDataChangedListener() {

		@Override
		public void notifyDataChanged() {
			Fragment fragment = getFragmentManager().findFragmentByTag(TAG_SHOW_USER_FRAGMENT);
			if (fragment != null && fragment instanceof ShowUserFragment) {
				ShowUserFragment showUserFragment = (ShowUserFragment) fragment;
				showUserFragment.notifyDataSetChanged();
			}
		}
	};

	private void hideKeyboard(View currentFocusView) {
		if (currentFocusView instanceof EditText) {
			InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(currentFocusView.getWindowToken(), 0);
		}
	}

	@Override
	protected void initListeners() {

	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.i(C.LOG_TAG, "Main activity, onStart called");
	}

	@Override
	protected void onResume() {
		Log.i(C.LOG_TAG, "Main activity, onResume called");
		super.onResume();
	}

	@Override
	protected void onRestart() {
		Log.i(C.LOG_TAG, "Main activity, onRestart called");
		super.onRestart();
	}

	@Override
	protected void onPause() {
		Log.i(C.LOG_TAG, "Main activity, onPause called");
		super.onPause();
	}

	@Override
	protected void onStop() {
		Log.i(C.LOG_TAG, "Main activity, onStop called");
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		Log.i(C.LOG_TAG, "Main activity, onDestroy called");
		super.onDestroy();
	}
}
