import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Competition {
    public static final int BREAK_TIME_MINUTES = 30;
    public List<User> participants;
    private final double breachPercentage;

    public Competition(int numParticipants, Random rand) {
        this.participants = new ArrayList<>();
        int usersWithBreaches = 0;

        for (int i = 0; i < numParticipants; i++) {
            User user = new User(rand);
            participants.add(user);
            if (user.getIllegalTime() > 0) {
                usersWithBreaches++;
            }
        }

        Collections.sort(participants, Comparator.comparingInt(User::getScreenTime).reversed());

        this.breachPercentage = (double) usersWithBreaches / numParticipants;
    }

    public double getBreachPercentage() {
        return breachPercentage;
    }
}
