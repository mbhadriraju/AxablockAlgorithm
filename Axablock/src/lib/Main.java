package src.lib;

import java.awt.*;
import java.util.Comparator;
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

                    for (int i = 0; i < d.size(); i++) {
                        int barHeight = (int) ((d.get(i) / max) * height);
                        g.fillRect(i * barWidth, height - barHeight, barWidth - 1, barHeight);
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

        System.out.println("Breach Percentage: " + competition.getBreachPercentage());

        u.sort(Comparator.comparingDouble(User::getCurrentBalance));

        double sum = u.stream().mapToDouble(User::getCurrentBalance).sum();
        double p = 0.5;
        double loss = 20;

        u = ProfitDistribution.calculateProfits(u, p, loss);

        int size = u.size();
        double updatedSum = u.stream().mapToDouble(User::getCurrentBalance).sum();
        double profitRatio = (sum - updatedSum) / size;
        while (profitRatio < 1.4 || profitRatio > 1.6) {
            for (User user : u) {
                user.setCurrentBalance(0);
            }
            if (profitRatio > 1.5) {
                p = p - 0.01;
                u = ProfitDistribution.calculateProfits(u, p, loss);
            } else if (profitRatio < 1.5) {
                p = p + 0.01;
                u = ProfitDistribution.calculateProfits(u, p, loss);
            }

            updatedSum = u.stream().mapToDouble(User::getCurrentBalance).sum();
            profitRatio = (sum - updatedSum) / size;
        }

        List<Double> earnings = u.stream()
        .map(user -> (double) (user.getCurrentBalance() - user.getStartingBalance()))
        .collect(Collectors.<Double>toList());



        System.out.println("\nUpdated Values:");
        System.out.println(earnings);
        System.out.println("Profit: " + (sum - updatedSum));
        System.out.println("Profit Ratio: " + ((sum - updatedSum) / size));

        SwingUtilities.invokeLater(() -> {
            Main demo = new Main("Earnings Histogram", earnings);
            demo.pack();
            demo.setVisible(true);
        });
    }
}
