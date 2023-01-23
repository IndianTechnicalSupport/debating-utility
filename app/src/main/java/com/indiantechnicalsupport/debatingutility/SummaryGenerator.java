package com.indiantechnicalsupport.debatingutility;

import java.util.ArrayList;

import com.indiantechnicalsupport.debatingutility.Model.Stopwatch;

public final class SummaryGenerator {
    
    public static String getCogitoSummaryString(String classCode, ArrayList<String> speakerNames, ArrayList<Stopwatch> speakerStopwatches, int bestTeam, int[] bestSpeakers) {
        String summaryString = "";

        summaryString += "Class " + classCode + ":\n\n";

        summaryString += "AFF:\n";

        for (int i = 0; i < speakerNames.size(); i += 2) { // Adding AFF Speaker times
            summaryString += (i / 2 + 1) + ". " + speakerNames.get(i) + " " + View.getFormattedTime((Integer) (int) (long) speakerStopwatches.get(i).getElapsed());
            summaryString += "\n";
        }

        summaryString += "\nNEG:\n";

        for (int i = 1; i < speakerNames.size(); i += 2) { // Adding NEG Speaker times
            summaryString += (i / 2 + 1) + ". " + speakerNames.get(i) + " " + View.getFormattedTime((Integer) (int) (long) speakerStopwatches.get(i).getElapsed());
            summaryString += "\n";
        }

        summaryString += "\nBest team: ";

        if (bestTeam == 0) {
            summaryString += "AFF\n";
        } else if (bestTeam == 1) {
            summaryString += "NEG\n";
        }

        summaryString += "Best speakers: " + speakerNames.get(bestSpeakers[0]) + " and " + speakerNames.get(bestSpeakers[1]);

        return summaryString;
    }

}
