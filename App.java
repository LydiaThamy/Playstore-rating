package sg.edu.nus.iss;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class App {
    public static void main(String[] args) throws IOException {

        Map<String, List<String[]>> fullList = new HashMap<>();

        // 1. reading file

        File file = new File("C://Data/googleplaystore.csv");
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        
        // placeholder
        br.readLine();

        // read app data
        String line = "";
        while ((line = br.readLine()) != null) {

            String[] data = line.split(",");

            // clean data

            if (data[1].contains("1.9") || data[2].contains("NaN")) {
                continue;
            } else if (!fullList.containsKey(data[1])) {

                // set appData - String[name, rating]
                String[] appData = { data[0], data[2] };

                // create new List<String[]>
                List<String[]> categoryList = new ArrayList<>();
                categoryList.add(appData);

                // add <String, List<String[]>> to HashMap
                fullList.put(data[1], categoryList);

            } else {

                // set appData - String[name, rating]
                String[] appData = { data[0], data[2] };

                // get existing value from fullList
                // make a var for List<String[]> & add to it
                List<String[]> categoryList = fullList.get(data[1]);
                categoryList.add(appData);

                // add <String, List<String[]>> to HashMap
                fullList.put(data[1], categoryList);
            }

        }

        // 2. evaluating data

        // convert Set<String> to String[]
        Set<String> category = new HashSet<>(fullList.keySet());

        for (String c : category) {

            // System.out.println(c);

            String highestRatedApp = fullList.get(c).get(0)[0];
            Double highestRating = Double.parseDouble(fullList.get(c).get(0)[1]);

            String lowestRatedApp = fullList.get(c).get(0)[0];
            Double lowestRating = Double.parseDouble(fullList.get(c).get(0)[1]);

            Double totalScore = 0.0;
            Integer totalCount = 0;

            for (int i = 0; i < fullList.get(c).size(); i++) {

                String appName = fullList.get(c).get(i)[0];
                Double appRating = Double.parseDouble(fullList.get(c).get(i)[1]);
                // System.out.println(appName + " " + appRating);

                // find highest rating
                if (appRating > highestRating) {
                    highestRatedApp = appName;
                    highestRating = appRating;
                }
                ;

                // find lowest rating
                if (appRating < lowestRating) {
                    lowestRatedApp = appName;
                    lowestRating = appRating;
                }
                ;

                // find average rating
                totalScore += appRating;
                totalCount++;
            }

            Double averageRating = totalScore / totalCount;

            System.out.println("For category " + c + " :");
            System.out.println("The highest rated app: " + highestRatedApp);
            System.out.println("The highest rating: " + highestRating);
            System.out.println("The lowest rated app: " + lowestRatedApp);
            System.out.println("The lowest rating: " + lowestRating);
            System.out.printf("The average rating: %.2f\n\n", averageRating);
        }

    }
}
