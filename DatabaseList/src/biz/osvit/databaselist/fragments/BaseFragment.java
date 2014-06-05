package biz.osvit.databaselist.fragments;

import android.app.Fragment;
import android.view.View;

/**
 * Title: Database List <br />
 * Copyright: Copyright @ 2014 <br />
 * 
 * @author Zoran Veres
 * @version 1.0.0
 */

public abstract class BaseFragment extends Fragment {

	protected abstract void initUi(View parent);

	protected abstract void initListeners();
}