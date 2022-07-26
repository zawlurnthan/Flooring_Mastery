package com.zthan.ui;

public interface UserIO {
    void print(String msg);

    String readString(String prompt);

    boolean askQuestion(String message);

    int readInt(String prompt);

    int readInt(String prompt, int min, int max);

    float readFloat(String prompt);

    float readFloat(String prompt, float min, float max);

    double readDouble(String prompt);

    double readDouble(String prompt, double min, double max);

    long readLong(String prompt);

    long readLong(String prompt, long min, long max);

    String readDate();

    String readFutureDate();
}
