package mortgage;

/**
 * CalculatorApp is the entry point for the Mortgage Calculator. It's main
 * responsibility is to handle initialization from any CLI arguments (there are
 * none at present) and start the UI in the event-dispatching thread.
 */
public class CalculatorApp {
    private CalculatorGui gui;

    /**
     * Constructor, so it's possible to run many of these in the same program
     * execution, though right now we only run one.
     */
    public CalculatorApp() {
        this.gui = new CalculatorGui();
    }

    /**
     * run is called to start the UI for a specific app instance
     */
    public void run() {
        this.gui.show();
    }

    /**
     * Create the App instance and tell it to start the window. For thread safety,
     * this method should be invoked from the event-dispatching thread.
     */
    private static void createAndShowGUI() {
        CalculatorApp app = new CalculatorApp();
        app.run();
    }

    /**
     * Program entry point.
     * 
     * @param args Any command line arguments to configure the app (none are
     *             consumed yet)
     */
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