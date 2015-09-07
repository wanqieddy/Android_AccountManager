package com.phicomm.account.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public class Provider {

    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.phicomm.account";

    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.phicomm.account";

    public static final class PersonColumns implements BaseColumns {
        public static final String AUTHORITY = "com.phicomm.account";
        public static final Uri CONTENT_URI = Uri.parse("content://"
                + AUTHORITY + "/persons");
        public static final String TABLE_NAME = "person";
        public static final String DEFAULT_SORT_ORDER = "age desc";
        public static final String USER_ID = "userId";
        public static final String USER_KEY = "user_key";
        public static final String JSSESSIONID = "jssessionid";
        public static final String AGE = "age";
        public static final String NAME = "name";
        public static final String TRUE_NAME = "true_name";
        public static final String USER_SEX = "userSex";
        public static final String USER_BIRTH = "birth";
        public static final String USER_ADDRESS = "address";
        public static final String USER_EMAIL = "email";
        public static final String CREATETIME = "create_time";
        public static final String NICK_NAME = "nick_name";
        public static final String PHONE = "phone";
        public static final String USER_IMAGE = "image";
        public static final String PASSWORD = "password";
        public static final String SYNC_TIME = "sync_time";
        public static final String CONTACT_SWITCHER_SELECTED = "contact_switcher_selected";
    }

    public static final class CheckDataColumns implements BaseColumns {
        public static final String AUTHORITY = "com.phicomm.check";
        public static final Uri CONTENT_URI = Uri.parse("content://"
                + AUTHORITY + "/checkdata");
        public static final String TABLE_NAME = "checkdata";
        public static final String DEFAULT_SORT_ORDER = "contact_id desc";
        public static final String CONTACT_ID = "contact_id";
        public static final String VERSION = "version";
        public static final String CHANGE_TIME = "change_time";
        public static final String OPERATE_ID = "operate_id";
        public static final String DISPLAY_NAME = "display_name";
        public static final String NICK_NAME = "nick_name";
        public static final String WORK_ADDRESS = "work_address";
        public static final String HOME_ADDRESS = "home_address";
        public static final String ORIGANIZATION = "organization";
        public static final String MOBILE_PHONE = "mobile_phone";
        public static final String TELEPHONE = "telephone";
        public static final String WORK_PHONE = "work_phone";
        public static final String EMAIL = "email";
        public static final String WEBSITE = "website";
        public static final String PHOTO_IMG = "photo_img";
        public static final String NOTE = "note";
        public static final String IM = "im";
        public static final String GROUP_MEMBER = "group_member";
    }

    // add upload data columns.
    public static final class UploadDataColumns implements BaseColumns {
        public static final String AUTHORITY = "com.phicomm.upload";
        public static final Uri CONTENT_URI = Uri.parse("content://"
                + AUTHORITY + "/uploaddata");
        public static final String TABLE_NAME = "uploaddata";
        public static final String DEFAULT_SORT_ORDER = "change_time desc";
        public static final String CONTACT_ID = "contact_id";
        public static final String CHANGE_TIME = "change_time";
        public static final String OPERATE_ID = "operate_id";
        public static final String DISPLAY_NAME = "display_name";
        public static final String NICK_NAME = "nick_name";
        public static final String WORK_ADDRESS = "work_address";
        public static final String HOME_ADDRESS = "home_address";
        public static final String ORIGANIZATION = "organization";
        public static final String MOBILE_PHONE = "mobile_phone";
        public static final String TELEPHONE = "telephone";
        public static final String WORK_PHONE = "work_phone";
        public static final String EMAIL = "email";
        public static final String WEBSITE = "website";
        public static final String PHOTO_IMG = "photo_img";
        public static final String NOTE = "note";
        public static final String IM = "im";
        public static final String GROUP_MEMBER = "group_member";
        public static final String OLD_NAME = "old_name";
        public static final String OLD_PHONE = "old_phone";
        public static final String OLD_GROUP_MEMBER = "old_groupmember";
        public static final String OLD_NICK_NAME = "old_nick_name";
        public static final String OLD_WORK_ADDRESS = "old_workaddress";
        public static final String OLD_HOME_ADDRESS = "old_homeaddress";
        public static final String OLD_ORIGANIZATION = "old_origanization";
        public static final String OLD_TELEPHONE = "old_telephone";
        public static final String OLD_WORK_PHONE = "old_workphone";
        public static final String OLD_EMAIL = "old_email";
        public static final String OLD_WEBSITE = "old_website";
        public static final String OLD_PHOTO_IMG = "old_phoneimg";
        public static final String OLD_NOTE = "old_note";
        public static final String OLD_IM = "old_im";
        public static final String GLOBAL_ID = "global_id";
    }

    // add sync data columns
    public static final class SyncColumns implements BaseColumns {
        public static final String AUTHORITY = "com.phicomm.synchronicity";
        public static final Uri CONTENT_URI = Uri.parse("content://"
                + AUTHORITY + "/synchronicity");
        public static final String TABLE_NAME = "synchronicity";
        public static final String DEFAULT_SORT_ORDER = "contact_id desc";
        public static final String CONTACT_ID = "contact_id";
        public static final String CHANGE_TIME = "change_time";
        public static final String OPERATE_ID = "operate_id";
        public static final String DISPLAY_NAME = "display_name";
        public static final String NICK_NAME = "nick_name";
        public static final String WORK_ADDRESS = "work_address";
        public static final String HOME_ADDRESS = "home_address";
        public static final String ORGANIZATION = "organization";
        public static final String MOBILE_PHONE = "mobile_phone";
        public static final String TELEPHONE = "telephone";
        public static final String WORKPHONE = "workphone";
        public static final String EMAIL = "email";
        public static final String WEBSITE = "website";
        public static final String PHOTO_IMAGE = "photo_image";
        public static final String NOTE = "note";
        public static final String IM = "im";
        public static final String GROUP_MEMBER = "group_member";
        public static final String GLOBAL_ID = "global_id";
/*        public static final String OLD_NAME = "old_name";
        public static final String OLD_PHONE = "old_phone";
        public static final String OLD_EAMIL = "old_email";
        public static final String OLD_GROUPMEMBER = "old_groupmember";
        public static final String OLD_HOMEADDRESS = "old_homeaddress";*/
    }

    // add sync data columns
    public static final class MapColumns implements BaseColumns {
        public static final String AUTHORITY = "com.phicomm.mapping";
        public static final Uri CONTENT_URI = Uri.parse("content://"
                + AUTHORITY + "/mapping");
        public static final String TABLE_NAME = "mapping";
        public static final String DEFAULT_SORT_ORDER = "contact_id desc";
        public static final String CONTACT_ID = "contact_id";
        public static final String GLOBAL_ID = "global_id";
    }
}
