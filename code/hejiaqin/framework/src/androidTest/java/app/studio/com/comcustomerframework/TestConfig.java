package app.studio.com.comcustomerframework;

import android.content.Context;

/**
 * Created by Administrator on 2017/4/23 0023.
 */
public class TestConfig {
    private Context appContext;

    public static final TestConfig instance = new TestConfig();

    private TestConfig() {

    }

    public static TestConfig getInstance() {
        return instance;
    }

    public void setAppContext(Context appContext) {
        this.appContext = appContext;
    }

    public Context getAppContext() {
        return this.appContext;
    }

}
