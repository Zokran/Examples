package biz.osvit.databaselist.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import biz.osvit.databaselist.R;
import biz.osvit.databaselist.database.Database;
import biz.osvit.databaselist.listeners.DialogFragmentListener;
import biz.osvit.databaselist.models.UserModel;
import biz.osvit.databaselist.utils.C;

/**
 * Title: Database List <br />
 * Copyright: Copyright @ 2014 <br />
 * 
 * @author Zoran Veres
 * @version 1.0.0
 */

public class EditDialogFragment extends DialogFragment implements DialogFragmentListener {

	private UserModel mUser;

	private Database mDatabase;

	long mId;

	private EditText mFirstNameEditText;
	private EditText mLastNameEditText;
	private EditText mPhoneNumberEditText;

	private RadioGroup mGenderRadioGroup;

	private Button mEditButton;
	private Button mDeleteButton;
	private Button mCancelButton;

	private OnNotifyDataChangedListener mNotifyDataChangedListener;

	public EditDialogFragment() {

	}

	@Override
	public void onAttach(Activity activity) {
		Log.i(C.LOG_TAG, "Edit dialog fragment, onAttach called");
		super.onAttach(activity);

		mDatabase = new Database(activity);
	}

	@Override
	public void onDismiss(DialogInterface dialog) {
		super.onDismiss(dialog);
		mNotifyDataChangedListener.notifyDataChanged();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.i(C.LOG_TAG, "Edit dialog fragment, onCreateView called");

		View parent = inflater.inflate(R.layout.edit_dialog_fragment, container, false);

		initUi(parent);
		initListeners();

		Bundle args = getArguments();
		if (args != null) {
			initDialog(args);
		}

		setUserData();

		return parent;
	}

	protected void initUi(View parent) {

		mFirstNameEditText = (EditText) parent.findViewById(R.id.edit_dialog_fragment_first_name_edittext);
		mLastNameEditText = (EditText) parent.findViewById(R.id.edit_dialog_fragment_last_name_edittext);
		mGenderRadioGroup = (RadioGroup) parent.findViewById(R.id.edit_dialog_fragment_gender_radiogroup);

		mPhoneNumberEditText = (EditText) parent.findViewById(R.id.edit_dialog_fragment_phone_number_edittext);

		mEditButton = (Button) parent.findViewById(R.id.edit_dialog_fragment_edit_button);
		mDeleteButton = (Button) parent.findViewById(R.id.edit_dialog_fragment_delete_button);
		mCancelButton = (Button) parent.findViewById(R.id.edit_dialog_fragment_cancel_button);
	}

	protected void initListeners() {

		mEditButton.setOnClickListener(mOnClickListener);
		mDeleteButton.setOnClickListener(mOnClickListener);
		mCancelButton.setOnClickListener(mOnClickListener);
	}

	private void initDialog(Bundle args) {

		Dialog dialog = getDialog();
		dialog.setTitle("Edit user");
		dialog.setCanceledOnTouchOutside(false);

		mId = args.getLong("id");
		mUser = mDatabase.getUser(mId);
	}

	public OnClickListener mOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {

			case R.id.edit_dialog_fragment_edit_button:
				Log.i(C.LOG_TAG, "Edit dialog fragment, edit button called");

				String tempFirstName = mFirstNameEditText.getText().toString();
				String tempLastName = mLastNameEditText.getText().toString();
				String tempGender = getGenderFromRadioGroup();
				String tempPhoneNumber = mPhoneNumberEditText.getText().toString();

				UserModel userModel = createUser(tempFirstName, tempLastName, tempGender, tempPhoneNumber);

				mDatabase.updateUser(mId, userModel);

				dismiss();

				break;

			case R.id.edit_dialog_fragment_delete_button:
				Log.i(C.LOG_TAG, "Edit user fragment, delete button called");

				mDatabase.deleteUser(mId);

				dismiss();

				break;

			case R.id.edit_dialog_fragment_cancel_button:
				Log.i(C.LOG_TAG, "Edit dialog fragment, cancel button called");

				dismiss();

				break;
			}
		}
	};

	public void setNotifyDataChangedListener(OnNotifyDataChangedListener listener) {
		mNotifyDataChangedListener = listener;
	}

	private UserModel createUser(String firstName, String lastName, String gender, String phoneNumber) {

		UserModel userModel = new UserModel();
		userModel.setFirstName(firstName);
		userModel.setLastName(lastName);
		userModel.setGender(gender);
		userModel.setPhoneNumber(phoneNumber);
		return userModel;
	}

	private void setUserData() {

		mFirstNameEditText.setText(mUser.getFirstName());
		mLastNameEditText.setText(mUser.getLastName());
		mGenderRadioGroup = setGenderRadioGroup();
		mPhoneNumberEditText.setText(mUser.getPhoneNumber());
	}

	private RadioGroup setGenderRadioGroup() {

		String gender = mUser.getGender();

		if (gender.equalsIgnoreCase("musko")) {
			mGenderRadioGroup.check(R.id.edit_dialog_fragment_male_radiobutton);
		} else if (gender.equalsIgnoreCase("zensko")) {
			mGenderRadioGroup.check(R.id.edit_dialog_fragment_female_radiobutton);
		}

		return mGenderRadioGroup;
	}

	private String getGenderFromRadioGroup() {
		int checkedRadioButtonId = mGenderRadioGroup.getCheckedRadioButtonId();

		switch (checkedRadioButtonId) {
		case R.id.edit_dialog_fragment_male_radiobutton:
			return "musko";

		case R.id.edit_dialog_fragment_female_radiobutton:
			return "zensko";
		}

		return null;
	}

	public static EditDialogFragment newInstance(long id, OnNotifyDataChangedListener notifyListener) {

		EditDialogFragment editDialogFragment = new EditDialogFragment();
		Bundle args = new Bundle();
		args.putLong("id", id);
		editDialogFragment.setArguments(args);
		editDialogFragment.setNotifyDataChangedListener(notifyListener);

		return editDialogFragment;
	}

	@Override
	public void dialogFragmentListener(long id) {
		this.mId = id;
	}

	public interface OnNotifyDataChangedListener {

		public void notifyDataChanged();
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.i(C.LOG_TAG, "Edit user fragment, onResume called");
	}

	public void onCreate(Bundle savedInstanceState) {
		Log.i(C.LOG_TAG, "Edit user fragmetn, onCreate called");
		super.onCreate(savedInstanceState);
	};

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.i(C.LOG_TAG, "Edit user fragment, onActivityCreated called");
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onStart() {
		Log.i(C.LOG_TAG, "Edit user fragment, onStart called");
		super.onStart();
	}

	@Override
	public void onStop() {
		Log.i(C.LOG_TAG, "Edit user fragment, onStop called");
		super.onStop();
	}

	@Override
	public void onDestroyView() {
		Log.i(C.LOG_TAG, "Edit user fragment, onDestroyView called");
		super.onDestroyView();
	}

	@Override
	public void onDetach() {
		Log.i(C.LOG_TAG, "Edit user fragment, onDetach called");
		super.onDetach();
	}
}
