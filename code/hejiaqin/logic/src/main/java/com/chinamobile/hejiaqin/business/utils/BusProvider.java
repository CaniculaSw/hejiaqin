/*
 * Copyright (c) 2015.  ShenZhen IncuBetter Information Technology Co.,Ltd.
 *  All rights reserved.
 *
 * This file contains valuable properties of  ShenZhen IncuBetter Information 
 * Technology Co.,Ltd.,  embodying  substantial  creative efforts  and 
 * confidential information, ideas and expressions.    No part of this 
 * file may be reproduced or distributed in any form or by  any  means,
 * or stored in a data base or a retrieval system,  without  the prior 
 * written permission of ShenZhen IncuBetter Information Technology Co.,Ltd.
 */

package com.chinamobile.hejiaqin.business.utils;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;


public class BusProvider {

    private static final Bus REST_BUS = new Bus(ThreadEnforcer.ANY);
    private static final Bus UI_BUS = new Bus();

    private BusProvider() {
    }

    public static Bus getRestBusInstance() {

        return REST_BUS;
    }

    public static Bus getUIBusInstance() {

        return UI_BUS;
    }
}