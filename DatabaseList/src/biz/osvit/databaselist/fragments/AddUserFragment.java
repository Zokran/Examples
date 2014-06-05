package biz.osvit.databaselist.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import biz.osvit.databaselist.R;
import biz.osvit.databaselist.database.Database;
import biz.osvit.databaselist.listeners.ShowUserFragmentListener;
import biz.osvit.databaselist.models.UserModel;

/**
 * Title: Database List <br />
 * Copyright: Copyright @ 2014 <br />
 * 
 * @author Zoran Veres
 * @version 1.0.0
 */

public class AddUserFragment extends BaseFragment {

	private Database mDatabase;

	private EditText mFirstNameEditText;
	private EditText mLastNameEditText;
	private EditText mPhoneNumberEditText;

	private RadioGroup mGenderRadioGroup;

	private Button mAddButton;
	private Button mOverviewButton;

	private ShowUserFragmentListener mShowUserFragmentListener;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		mDatabase = new Database(activity);

		try {
			mShowUserFragmentListener = (ShowUserFragmentListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement interface listeners");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View parent = inflater.inflate(R.layout.add_user_fragment, container, false);
		initUi(parent);
		initListeners();
		return parent;
	}

	@Override
	protected void initUi(View parent) {

		mFirstNameEditText = (EditText) parent.findViewById(R.id.add_user_fragment_first_name_edittext);
		mLastNameEditText = (EditText) parent.findViewById(R.id.add_user_fragment_last_name_edittext);
		mPhoneNumberEditText = (EditText) parent.findViewById(R.id.add_user_fragment_phone_number_edittext);

		mGenderRadioGroup = (RadioGroup) parent.findViewById(R.id.add_user_fragment_radiogroup);

		mAddButton = (Button) parent.findViewById(R.id.add_user_fragment_add_button);
		mOverviewButton = (Button) parent.findViewById(R.id.add_user_fragment_overview_button);
	}

	@Override
	protected void initListeners() {

		mAddButton.setOnClickListener(mOnClickListener);
		mOverviewButton.setOnClickListener(mOnClickListener);
	}

	private OnClickListener mOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {

			case R.id.add_user_fragment_add_button:

				String tempFirstName = mFirstNameEditText.getText().toString();
				String tempLastName = mLastNameEditText.getText().toString();
				String tempGender = getGenderFromRadioGroup();
				String tempPhoneNumber = mPhoneNumberEditText.getText().toString();

				UserModel userModel = createUser(tempFirstName, tempLastName, tempGender, tempPhoneNumber);

				mDatabase.insertUser(userModel);

				clearInputFieldsMethod();

				break;

			case R.id.add_user_fragment_overview_button:

				mShowUserFragmentListener.showUserFragmentListener();

				break;
			}
		}
	};

	private String getGenderFromRadioGroup() {

		int checkedRadioButtonId = mGenderRadioGroup.getCheckedRadioButtonId();

		switch (checkedRadioButtonId) {
		case R.id.add_user_fragment_male_radiobutton:
			return "musko";

		case R.id.add_user_fragment_female_radiobutton:
			return "zensko";
		}

		return null;
	}

	private UserModel createUser(String firstName, String lastName, String gender, String phoneNumber) {

		UserModel userModel = new UserModel();
		userModel.setFirstName(firstName);
		userModel.setLastName(lastName);
		userModel.setGender(gender);
		userModel.setPhoneNumber(phoneNumber);
		return userModel;
	}

	private void clearInputFieldsMethod() {

		mFirstNameEditText.setText("");
		mLastNameEditText.setText("");
		mGenderRadioGroup.clearCheck();
		mPhoneNumberEditText.setText("");
	}

	public static AddUserFragment newInstance() {
		return new AddUserFragment();
	}
}
