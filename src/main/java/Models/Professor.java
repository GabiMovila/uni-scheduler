package Models;

import lombok.Data;

import java.text.MessageFormat;

@Data
public class Professor {
    public String name;
    public Course course;
    public Preferences preferences;

    @Override
    public String toString(){
        return MessageFormat.format("\nProfessor Name: {0} \n Course name: {1} \n Course duration: {2} \n Preferences: {3}",name, course.getName(),course.getDuration(), preferences);
    }
}
