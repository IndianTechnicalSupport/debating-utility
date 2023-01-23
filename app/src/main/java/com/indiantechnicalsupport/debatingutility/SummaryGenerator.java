package com.indiantechnicalsupport.debatingutility;

import java.util.ArrayList;

import com.indiantechnicalsupport.debatingutility.Model.Stopwatch;

public final class SummaryGenerator {
    
    public static String getCogitoSummaryString(String classCode, ArrayList<String> speakerNames, ArrayList<Stopwatch> speakerStopwatches) {
        String summaryString = "";

        summaryString += "Class " + classCode + ":\n\n";

        summaryString += "AFF:\n";

        for (int i = 0; i < speakerNames.size(); i += 2) {
            summaryString += (i + 1) + ". " + speakerNames.get(i) + " ";
        }

        return summaryString;
    }

}
