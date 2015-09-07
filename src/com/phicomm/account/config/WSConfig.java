/**
 * 2011 Foxykeep (http://datadroid.foxykeep.com)
 * <p>
 * Licensed under the Beerware License : <br />
 * As long as you retain this notice you can do whatever you want with this stuff. If we meet some
 * day, and you think this stuff is worth it, you can buy me a beer in return
 */

package com.phicomm.account.config;

public final class WSConfig {

    private WSConfig() {
        // No public constructor
    }

    public static final String ROOT_LOGIN_URL = "http://172.17.100.197/cloudBackup/";
    public static final String ROOT_URL = "http://172.17.100.197:80/cloudBackup/";

    public static final String WS_LOGIN_URL = ROOT_LOGIN_URL + "login!userLogin.action";

    // CityList WS
    public static final String WS_SYNC_CONTACT_URL = ROOT_URL + "contact!syncContact.action;jsessionid=";
    //http://172.17.100.197:80/cloudBackup/contact!syncMapId.action;jsessionid=DE4FFAA52AC94FF43A64CC63295645E5

    public static final String WS_GET_MAP_URL = ROOT_URL + "contact!syncMapId.action;jsessionid=";
}
