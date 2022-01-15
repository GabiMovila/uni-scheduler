package Models;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Preferences {
    public ArrayList<Integer> unavailableDays;
    public String wantedHours;
}
