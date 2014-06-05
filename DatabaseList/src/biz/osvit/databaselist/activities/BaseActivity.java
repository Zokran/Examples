package biz.osvit.databaselist.activities;

import android.app.Activity;

/**
 * Title: Database List <br />
 * Copyright: Copyright @ 2014 <br />
 * 
 * @author Zoran Veres
 * @version 1.0.0
 */

public abstract class BaseActivity extends Activity {

	protected abstract void initUi();

	protected abstract void initListeners();
}
