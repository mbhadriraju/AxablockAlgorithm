import java.awt.*;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import javax.swing.*;

public class Main extends JFrame {

    private List<Double> d;

    public Main(String title, List<Double> data) {
        super(title);
        this.d = data;

        JPanel chartPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (d != null && !d.isEmpty()) {
                    int width = getWidth();
                    int height = getHeight();
                    int barWidth = width / d.size();
                    double max = d.stream().mapToDouble(Double::doubleValue).max().orElse(1);
                    double min = d.stream().mapToDouble(Double::doubleValue).min().orElse(0);
                    double range = max - min;

                    // Draw Y axis labels
                    for (int i = 0; i <= 10; i++) {
                        int y = (int) ((i / 10.0) * height);
                        g.drawLine(0, height - y, 5, height - y);
                        String label = String.format("%.1f", min + (range * i / 10.0));
                        g.drawString(label, 10, height - y + 5);
                    }

                    // Draw bars
                    for (int i = 0; i < d.size(); i++) {
                        int barHeight = (int) ((d.get(i) / range) * height);
                        if (d.get(i) >= 0) {
                            g.fillRect(i * barWidth, (int) ((max / range) * height) - barHeight, barWidth - 1, barHeight);
                        } else {
                            g.fillRect(i * barWidth, (int) ((max / range) * height), barWidth - 1, -barHeight);
                        }
                    }
                }
            }
        };

        chartPanel.setPreferredSize(new Dimension(800, 600));
        setContentPane(chartPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        Random rand = new Random();
        Competition competition = new Competition(rand.nextInt(501), rand);
        List<User> u = competition.participants;

        double userContribution = 10;
        double totalPrizePool = userContribution * u.size();

        double p = 0.5;
        double loss = 20;
        u = ProfitDistribution.calculateProfits(u, p, loss, totalPrizePool);

        double sum = u.stream().mapToDouble(User::getCurrentBalance).sum();
        int size = u.size();
        double updatedSum = sum;
        double profitRatio = (sum - updatedSum) / size;

        if (profitRatio < 1.4) {
            while (profitRatio < 1.4) {
                p += 0.01;
                u = ProfitDistribution.calculateProfits(u, p, loss, totalPrizePool);
                updatedSum = u.stream().mapToDouble(User::getCurrentBalance).sum();
                profitRatio = (sum - updatedSum) / size;
            }
        } else if (profitRatio > 1.6) {
            while (profitRatio > 1.6) {
                p -= 0.01;
                u = ProfitDistribution.calculateProfits(u, p, loss, totalPrizePool);
                updatedSum = u.stream().mapToDouble(User::getCurrentBalance).sum();
                profitRatio = (sum - updatedSum) / size;
            }
        }

        List<Double> earnings = u.stream()
                .map(user -> (double) (user.getCurrentBalance() - user.getStartingBalance()))
                .collect(Collectors.toList());

        System.out.println("\nUpdated Values:");
        for (User user : u) {
            System.out.println("User - Screen Time: " + user.getScreenTime() +
                               ", Starting Balance: " + user.getStartingBalance() +
                               ", Current Balance: " + user.getCurrentBalance() +
                               ", Earnings: " + (user.getCurrentBalance() - user.getStartingBalance()));
        }

        System.out.println("Profit: " + (sum - updatedSum));
        System.out.println("Profit Ratio: " + ((sum - updatedSum) / size));

        SwingUtilities.invokeLater(() -> {
            Main demo = new Main("Earnings Histogram", earnings);
            demo.pack();
            demo.setVisible(true);
        });
    }
}
