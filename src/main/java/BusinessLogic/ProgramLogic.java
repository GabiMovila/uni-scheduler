package BusinessLogic;

import Models.Course;
import Models.Preferences;
import Models.Professor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.MessageFormat;
import java.util.*;

@Slf4j
public class ProgramLogic {

    private static final Short numberOfDays = 5;
    private static final Short numberOfClassesInADay = 6;
    private static final String[][] weekGrid = new String[numberOfDays][numberOfClassesInADay];
    private static final List<Professor> professorList = new ArrayList<>();
    private static final List<String> roomsList = new ArrayList<>();
    private static final Scanner keyboardDataReader = new Scanner(System.in);
    private static final Short durationOfCourse = 2;
    private static final Map<Integer, Integer> classNumberToHourMap = new HashMap<>();
    public static Short numberOfGroups;

    public static void startProgram() {
        defineHourMap();
        readProfessorsAndCourses();
        readNumberOfGroups();
        assignProfessorsToRooms();
        printGrid();
        cleanUp();
    }

    /**
     * Maps the class number to the starting hour (first class starts at 8, second at 10 and so on)
     */
    public static void defineHourMap() {
        classNumberToHourMap.put(0, 8);
        classNumberToHourMap.put(1, 10);
        classNumberToHourMap.put(2, 12);
        classNumberToHourMap.put(3, 14);
        classNumberToHourMap.put(4, 16);
        classNumberToHourMap.put(5, 18);

    }

    public static void readNumberOfGroups() {
        System.out.println("Input number of Groups: ");
        numberOfGroups = keyboardDataReader.nextShort();
    }

    public static void readProfessorsAndCourses() {

        try {
            File professorFile = new File("professors.txt");
            Scanner dataReader = new Scanner(professorFile);
            while (dataReader.hasNextLine()) {
                String data = dataReader.nextLine();
                //Splits the string by whitespaces
                var splitString = data.split("\\s+");
                var professor = new Professor();
                professor.setName(MessageFormat.format("{0} {1}", splitString[0], splitString[1]));
                var course = new Course();
                course.setName(splitString[2]);
                course.setDuration(durationOfCourse);
                professor.setCourse(course);
                System.out.println(professor);
                professor.setPreferences(readProfessorPreferences());
                professorList.add(professor);
            }
            System.out.println(professorList.toArray().length + " professors and courses read successfully");
            dataReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }

    public static Preferences readProfessorPreferences() {
        Preferences preferences = new Preferences();
        preferences.setUnavailableDays(readUnavailableDays());
       // preferences.setWantedHours(readWantedHours());
        return preferences;
    }

    public static ArrayList<Integer> readUnavailableDays() {
        System.out.printf("What days is the professor unavailable? [1-5] separated by blank space (eg: 1 3 5): ");
        String data = keyboardDataReader.nextLine();
        ArrayList<Integer> unavailableDays = new ArrayList<>();
        if (data.isEmpty()) {
            return unavailableDays;
        }

        if (data.length() == 1) {
            unavailableDays.add(Integer.parseInt(data));
        } else {
            var splitString = data.split("\\s+");
            for (var day : splitString) {
                unavailableDays.add(Integer.parseInt(day));
            }
        }
        return unavailableDays;
    }

    public static String readWantedHours() {
        String hours;
        System.out.printf("What hours does the professor want? >X or <X, X being an HOUR (eg: >12): ");
        hours = keyboardDataReader.nextLine();
        return hours;
    }

    public static void assignProfessorsToRooms() {
        //classNumber represents the first, second, etc. class of the day
        for (int dayInWeek = 0; dayInWeek < numberOfDays; dayInWeek++)
            for (int classNumber = 0; classNumber < numberOfClassesInADay; classNumber++) {
                for (var professor : professorList) {
                    if (isRoomAvailable(dayInWeek, classNumber) && isPreferencesOk(professor, dayInWeek, classNumber) && isProfessorStillAvailable(professor)) {
                        weekGrid[dayInWeek][classNumber] = professor.course.name;
                    }
                }
            }
    }

    /**
     * Checks if the professor is available in that day and if the hour matches his preference
     */
    private static boolean isPreferencesOk(Professor professor, int day, int classNumber) {
        return !professor.preferences.unavailableDays.contains(day);
        //return isHourMatchingPreferedHour(professor.preferences.wantedHours, convertClassNumberIntoHour(classNumber));
    }

    //TODO add logic for wanted hours
    private static boolean isHourMatchingPreferedHour(String preferedHour, int actualHour) {
        if (preferedHour.isEmpty()) {
            return true;
        }
        var array = preferedHour.toCharArray();
        if (array[0] == '<') {
            if (actualHour < Integer.parseInt(String.valueOf(array[1]))) {
                return true;
            }
        }

        if (array[0] == '>') {
            return actualHour > Integer.parseInt(String.valueOf(array[1]));
        }

        return false;
    }

    private static int convertClassNumberIntoHour(int classNumber) {
        return classNumberToHourMap.get(classNumber);
    }

    private static boolean isRoomAvailable(int day, int classNumber) {
        return weekGrid[day][classNumber] == null;
    }

    private static boolean isProfessorStillAvailable(Professor professor) {
        if (professor.numberOfCoursesPerWeek < numberOfGroups) {
            professor.numberOfCoursesPerWeek++;
            return true;
        }
        return false;
    }

    public static void printGrid() {
        for (int dayInWeek = 0; dayInWeek < numberOfDays; dayInWeek++) {
            for (int classNumber = 0; classNumber < numberOfClassesInADay; classNumber++) {
                System.out.println(weekGrid[dayInWeek][classNumber] + "\t");
            }
            System.out.println("\n");
        }
    }


    public static void assignStudentsToRooms() {
        //TODO ASSIGN GROUPS TO CLASSES
    }

    protected static void cleanUp() {
        keyboardDataReader.close();
    }

    //TODO CHANGE from static CLI to REST
    //TODO Create GUI
}
