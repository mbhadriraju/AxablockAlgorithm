package src.lib;
import java.util.Random;

public class User {
    public int screenTime;
    public int illegalTime;
    public int startingBalance;
    public int currentBalance;
    public int earnings;

    public User(Random rand) {
        this.screenTime = rand.nextInt(13);
        this.illegalTime = rand.nextInt(3);
        this.startingBalance = 0;
        this.currentBalance = startingBalance;
        this.earnings = 0;
    }

    public int getScreenTime() {
        return screenTime;
    }

    public int getIllegalTime() {
        return illegalTime;
    }

    public int getStartingBalance() {
        return startingBalance;
    }

    public int getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(int currentBalance) {
        this.currentBalance = currentBalance;
    }

    public int getEarnings() {
        return earnings;
    }

    public void setEarnings(int earnings) {
        this.earnings = earnings;
    }

    public void updateScreenTime(int additionalTime) {
        this.screenTime += additionalTime;
        this.illegalTime += additionalTime;
        this.currentBalance -= additionalTime * 10;
    }

    public void addEarnings(int amount) {
        this.earnings += amount;
        this.currentBalance += amount;
    }
}

