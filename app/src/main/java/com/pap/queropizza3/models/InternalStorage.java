package com.pap.queropizza3.models;

import android.content.Context;
import android.content.ContextWrapper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Rodrigo on 21/05/2016.
 * https://androidresearch.wordpress.com/2013/04/07/caching-objects-in-android-internal-storage/
 */
public final class InternalStorage {

    private InternalStorage() {
    }

    public class MyContextWrapper extends ContextWrapper {

        public MyContextWrapper(Context base) {
            super(base);
        }

        public void writeObject(Context context, String key, Object object) throws IOException {
            FileOutputStream fos = context.openFileOutput(key, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            oos.close();
            fos.close();
        }


    public Object readObject(Context context, String key) throws IOException,
            ClassNotFoundException {
        FileInputStream fis = context.openFileInput(key);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object object = ois.readObject();
        return object;
    }

}
}

//http://itekblog.com/android-context-in-non-activity-class/
