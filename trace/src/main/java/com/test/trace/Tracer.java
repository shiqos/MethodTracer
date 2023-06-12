package com.test.trace;

import android.os.Trace;

public class Tracer {

    public static void i(int id) {
        Trace.beginSection(String.valueOf(id));
    }

    public static void o(int id) {
        Trace.endSection();
    }

}
