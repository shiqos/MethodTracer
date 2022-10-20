package com.test.trace;

import android.os.Trace;

public class Tracer {

    public static void b(String tag) {
        Trace.beginSection(tag);
    }

    public static void e(String tag) {
        Trace.endSection();
    }

}
