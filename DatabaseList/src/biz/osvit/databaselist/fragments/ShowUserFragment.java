package biz.osvit.databaselist.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import biz.osvit.databaselist.R;
import biz.osvit.databaselist.adapters.UserAdapter;
import biz.osvit.databaselist.database.Database;
import biz.osvit.databaselist.listeners.DialogFragmentListener;
import biz.osvit.databaselist.models.UserModelWrapper;
import biz.osvit.databaselist.utils.C;

/**
 * Title: Database List <br />
 * Copyright: Copyright @ 2014 <br />
 * 
 * @author Zoran Veres
 * @version 1.0.0
 */

public class ShowUserFragment extends BaseFragment {

	private ListView mUserListView;
	private UserAdapter mUserAdapter;

	private UserModelWrapper mUserModelWrapper;
	private Database mDatabase;

	private DialogFragmentListener mDialogFragmentListener;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Log.i(C.LOG_TAG, "Show user fragment, onAttach called");

		mDatabase = new Database(activity);

		try {
			mDialogFragmentListener = (DialogFragmentListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement interface listeners");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.i(C.LOG_TAG, "Show user fragment, onCreateView called");

		View parent = inflater.inflate(R.layout.show_user_fragment, container, false);
		initUi(parent);
		initListeners();

		return parent;
	}

	@Override
	protected void initUi(View parent) {

		mUserModelWrapper = mDatabase.getAllUsers();
		mUserListView = (ListView) parent.findViewById(R.id.show_user_fragment_listview);
		mUserAdapter = new UserAdapter(getActivity(), mUserModelWrapper);
		mUserListView.setAdapter(mUserAdapter);
	}

	@Override
	protected void initListeners() {

		mUserListView.setOnItemClickListener(mOnItemClickListener);
	}

	OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Log.i(C.LOG_TAG, "Show user fragment, onItemClick called");

			mDialogFragmentListener.dialogFragmentListener(id);
		}
	};

	public void notifyDataSetChanged() {
		if (mUserAdapter != null) {
			mUserModelWrapper = mDatabase.getAllUsers();
			mUserAdapter.setUsers(mUserModelWrapper.getUsers());
			mUserAdapter.notifyDataSetChanged();
		}
	}

	public static ShowUserFragment newInstance() {
		return new ShowUserFragment();
	}

	@Override
	public void onResume() {
		Log.i(C.LOG_TAG, "Show user fragment, onResume called");
		super.onResume();
	}

	public void onCreate(Bundle savedInstanceState) {
		Log.i(C.LOG_TAG, "Show user fragment, onCreate called");
		super.onCreate(savedInstanceState);
	};

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.i(C.LOG_TAG, "Show user fragment, onActivityCreated called");
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onStart() {
		Log.i(C.LOG_TAG, "Show user fragment, onStart called");
		super.onStart();
	}

	@Override
	public void onStop() {
		Log.i(C.LOG_TAG, "Show user fragment, onStop called");
		super.onStop();
	}

	@Override
	public void onDestroyView() {
		Log.i(C.LOG_TAG, "Show user fragment, onDestroyView called");
		super.onDestroyView();
	}

	@Override
	public void onDetach() {
		Log.i(C.LOG_TAG, "Show user fragment, onDetach called");
		super.onDetach();
	}
}
