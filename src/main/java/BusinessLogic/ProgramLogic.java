package BusinessLogic;

import Models.Course;
import Models.Preferences;
import Models.Professor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Slf4j
public class ProgramLogic {
    private static final short durationOfCourse = 2;
    private static final List<Professor> professorList = new ArrayList<>();
    private static final List<String> roomsList = new ArrayList<>();
    private static final int[][] calendar = new int[5][12 / durationOfCourse];
    public static Short numberOfGroups;
    private static final Scanner keyboardDataReader = new Scanner(System.in);

    public static void startProgram() {
        readProfessorsAndCourses();
        readNumberOfGroups();
        assignProfessorsToRooms();
        cleanUp();
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
            System.out.println(professorList);
            dataReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }

    public static Preferences readProfessorPreferences() {
        Preferences preferences = new Preferences();
        preferences.setUnavailableDays(readUnavailableDays());
        preferences.setWantedHours(readWantedHours());
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
        System.out.println("KAKA: " + calendar[0][0]);
        //TODO ADD PROFESSORS ACCORDING TO PREFERENCES
    }

    public static void assignStudentsToRooms() {
        //TODO ASSIGN GROUPS TO CLASSES
    }

    protected static void cleanUp() {
        keyboardDataReader.close();
    }
}
