package com.marekhakala.mynomadlifeapp.Utilities;

import io.realm.Realm;

public class UtilityHelper {
    public static void closeDatabase(Realm realm) {
        if(realm != null && !realm.isClosed())
            realm.close();
    }
}
