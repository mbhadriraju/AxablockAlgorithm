package src.lib;
import java.util.List;

public class ProfitDistribution {
    public static List<User> calculateProfits(List<User> users, double companyProfitPercentage, double loss) {
        int n = users.size();
        double t = loss * n;
        double maxLoss = (companyProfitPercentage * t) / (1 - companyProfitPercentage);
        double[] xy;

        if (n > 50) {
            xy = linearProfitDistribution(users, companyProfitPercentage, t);
            double x = xy[0];
            double y = xy[1];

            for (int i = 0; i < n; i++) {
                User user = users.get(i);
                if (i != n - 1) {
                    user.setCurrentBalance(user.getCurrentBalance() - (int) Math.min(loss, maxLoss / (n - 1)));
                }
                user.setCurrentBalance(user.getCurrentBalance() + (int) x);
                user.setCurrentBalance(user.getCurrentBalance() + (int) (y * i));
            }
        } else {
            xy = exponentialProfitDistribution(users, companyProfitPercentage, t);
            double x = xy[0];
            double y = xy[1];
          
            for (int i = 0; i < n; i++) {
                User user = users.get(i);
                if (i != n - 1) {
                    user.setCurrentBalance(user.getCurrentBalance() - (int) Math.min(loss, maxLoss / (n - 1)));
                }
                user.setCurrentBalance(user.getCurrentBalance() + (int) x);
                user.setCurrentBalance(user.getCurrentBalance() + (int) ((Math.pow(2, i - 1) - 1) * y));
            }
        }
        return users;
    }

    private static double[] linearProfitDistribution(List<User> users, double companyProfitPercentage, double totalPrizePool) {
        int n = users.size();
        double x = (((1 - companyProfitPercentage) / n) * totalPrizePool);
        double y = ((2 * (1 - companyProfitPercentage)) / (n * (n - 1))) * totalPrizePool;
        return new double[]{x, y};
    }

    private static double[] exponentialProfitDistribution(List<User> users, double companyProfitPercentage, double totalPrizePool) {
        int n = users.size();
        double x = (((1 - companyProfitPercentage) / n) * totalPrizePool);
        double y = ((1 - companyProfitPercentage) / ((Math.pow(2, n - 1)) - 1)) * totalPrizePool;
        return new double[]{x, y};
    }
}

