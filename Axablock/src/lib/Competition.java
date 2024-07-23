package src.lib;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Competition {
    public static final int BREAK_TIME_MINUTES = 30;
    public List<User> participants;
    private double breachPercentage;


    public Competition(int numParticipants, Random rand) {
        this.participants = new ArrayList<>();
        int usersWithBreaches = 0;

        for (int i = 0; i < numParticipants; i++) {
            User user = new User(rand);
            if (user.getIllegalTime() > 0) {
                participants.add(user);
                usersWithBreaches++;
            }
        }

        this.breachPercentage = (double) usersWithBreaches / numParticipants;
    }

    public double getBreachPercentage() {
        return breachPercentage;
    }
}
