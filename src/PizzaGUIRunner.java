import javax.swing.*;

public class PizzaGUIRunner {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PizzaGUIFrame frame = new PizzaGUIFrame();
            frame.setTitle("Pizza Order Form");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
