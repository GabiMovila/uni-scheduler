package Models;

import lombok.Data;

import java.text.MessageFormat;
import java.util.UUID;

@Data
public class Professor {
    public UUID id;
    public String name;
    public Course course;
    public Preferences preferences;
    public Short numberOfCoursesPerWeek = 0;

    @Override
    public String toString(){
        return MessageFormat.format("\nProfessor Name: {0} \n Course name: {1} \n Course duration: {2} \n Preferences: {3}",name, course.getName(),course.getDuration(), preferences);
    }
}
