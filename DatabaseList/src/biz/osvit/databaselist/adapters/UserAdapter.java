package biz.osvit.databaselist.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import biz.osvit.databaselist.R;
import biz.osvit.databaselist.models.UserModel;
import biz.osvit.databaselist.models.UserModelWrapper;

/**
 * Title: Database List <br />
 * Copyright: Copyright @ 2014 <br />
 * 
 * @author Zoran Veres
 * @version 1.0.0
 */

public class UserAdapter extends BaseAdapter {

	private Context mContext;

	private ArrayList<UserModel> mUserList;

	private ViewHolder mViewHolder;

	public UserAdapter(Context context, UserModelWrapper wrapper) {
		mContext = context;
		mUserList = wrapper.getUsers();
	}

	public void setUsers(ArrayList<UserModel> users) {
		mUserList = users;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mUserList.size();
	}

	@Override
	public UserModel getItem(int position) {
		return mUserList.get(position);
	}

	public long getItemId(int position) {
		return mUserList.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.list_view_user_row, parent, false);
			mViewHolder = new ViewHolder();
			mViewHolder.mFirstNameTextView = (TextView) convertView.findViewById(R.id.list_view_user_row_first_name_pouplated_textview);
			mViewHolder.mLastNameTextView = (TextView) convertView.findViewById(R.id.list_view_user_row_last_name_populated_textview);
			mViewHolder.mGenderTextView = (TextView) convertView.findViewById(R.id.list_view_user_row_gender_populated_textview);
			mViewHolder.mPhoneNumberTextView = (TextView) convertView.findViewById(R.id.list_view_user_row_phone_number_populated_textview);
			convertView.setTag(mViewHolder);
		} else {
			mViewHolder = (ViewHolder) convertView.getTag();
		}

		UserModel user = getItem(position);
		mViewHolder.mFirstNameTextView.setText(user.getFirstName());
		mViewHolder.mLastNameTextView.setText(user.getLastName());
		mViewHolder.mGenderTextView.setText(user.getGender());
		mViewHolder.mPhoneNumberTextView.setText(user.getPhoneNumber());

		return convertView;
	}

	private static class ViewHolder {
		private TextView mFirstNameTextView;
		private TextView mLastNameTextView;
		private TextView mGenderTextView;
		private TextView mPhoneNumberTextView;
	}
}
