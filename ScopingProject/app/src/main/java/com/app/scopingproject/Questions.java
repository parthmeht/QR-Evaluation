package com.app.scopingproject;

import java.util.Collections;
import java.util.HashMap;

public class Questions {

    static {
        HashMap<Integer, String> ALL_QUESTIONS = new HashMap<>();
        ALL_QUESTIONS.put(1, "Question 1");
        ALL_QUESTIONS.put(2, "Question 2");
        ALL_QUESTIONS.put(3, "Question 3");
        ALL_QUESTIONS.put(4, "Question 4");
        ALL_QUESTIONS.put(5, "Question 5");
//        ALL_QUESTIONS = (HashMap<Integer, String>) Collections.unmodifiableMap(aMap);
    }
    static final int NUMBER_OF_QUESTIONS = 5;

}
