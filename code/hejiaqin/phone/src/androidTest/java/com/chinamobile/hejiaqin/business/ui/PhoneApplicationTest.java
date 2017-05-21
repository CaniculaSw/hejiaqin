package com.chinamobile.hejiaqin.business.ui;

import android.app.Application;
import android.content.Context;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.SmallTest;


/**
 * Created by Administrator on 2017/5/21 0021.
 */
public class PhoneApplicationTest extends ApplicationTestCase<Application> {
    public PhoneApplicationTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @SmallTest
    public void testPreconditions() {
        Context appContext = getSystemContext();
        assertNotNull(appContext);
    }
}