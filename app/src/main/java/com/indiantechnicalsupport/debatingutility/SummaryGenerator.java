package com.indiantechnicalsupport.debatingutility;

import java.util.ArrayList;

import com.indiantechnicalsupport.debatingutility.Model.Stopwatch;

public final class SummaryGenerator {
    
    public static String getCogitoSummaryString(String classCode, ArrayList<String> speakerNames, ArrayList<Stopwatch> speakerStopwatches, int bestTeam, ArrayList<Integer> bestSpeakers) {
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

        if (bestTeam == 1) {
            summaryString += "AFF\n";
        } else if (bestTeam == 0) {
            summaryString += "NEG\n";
        }

        if (bestSpeakers.size() <= 1) {
            summaryString += "Best Speaker: " + speakerNames.get(bestSpeakers.get(0));
        } else { 
            summaryString += "Best speakers: ";
            if (bestSpeakers.size() == 2) {
                summaryString += speakerNames.get(bestSpeakers.get(0)) + " and " + speakerNames.get(bestSpeakers.get(1));
            } else {
                for (int i = 0; i < bestSpeakers.size(); i ++) {
                    if (i == bestSpeakers.size() - 1) {
                        summaryString += "and " + speakerNames.get(bestSpeakers.get(i));
                    } else {
                        summaryString += speakerNames.get(bestSpeakers.get(i)) + ", ";
                    }
                }
            }
        }

        return summaryString;
    }

}
