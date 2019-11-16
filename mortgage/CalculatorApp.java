package mortgage;

// REF: https://www.cs.tut.fi/lintula/manual/java/tutorial/uiswing/components/textfield.html
public class CalculatorApp {
    private CalculatorGui gui;

    public CalculatorApp() {
        this.gui = new CalculatorGui();
    }

    public void run() {
        this.gui.show();
    }

    /**
     * Create the GUI and show it. For thread safety, this method should be invoked
     * from the event-dispatching thread.
     */
    private static void createAndShowGUI() {
        CalculatorApp app = new CalculatorApp();
        app.run();
    }

    public static void main(String[] args) {
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}