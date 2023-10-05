//package pertsev.VCS;
//
//import pertsev.VCS.ConsoleUtils.ConsoleManager;
//import pertsev.VCS.VCSUtils.Controller;
//import pertsev.VCS.VCSUtils.Repository;
//
//import java.io.FileNotFoundException;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//public class VersionControlSystem implements ControllableVCS {
//    private static final String PROJECT_DIR = System.getProperty("user.dir");
//    private static final Path DEFAULT_REPOSITORY_DIRECTORY = Paths.get(
//            PROJECT_DIR + "/resources");
//
//    private enum State {
//        RUNNING,
//        STOPPED
//    }
//
//    private ConsoleManager consoleManager = new ConsoleManager();
//    private Repository repository;
//    private State state;
//    private Controller controller;
//
//    public VersionControlSystem() throws FileNotFoundException {
//        this.state = State.RUNNING;
//        this.repository = new Repository(DEFAULT_REPOSITORY_DIRECTORY);
//        this.controller = new Controller(this, repository);
//
//        this.start();
//        this.launch();
//    }
//
//    public VersionControlSystem(Path repositoryPath) throws FileNotFoundException {
//        this.state = State.RUNNING;
//        this.repository = new Repository(repositoryPath);
//        this.controller = new Controller(this, repository);
//
//        this.start();
//        this.launch();
//    }
//
//    private void launch() throws FileNotFoundException {
//        while (this.state == State.RUNNING) {
//            String[] command = consoleManager.getCommand();
//            String response = controller.command(command);
//            consoleManager.printResponse(response);
//        }
//    }
//
//    public void start() {
//        this.state = State.RUNNING;
//    }
//
//    public void stop() {
//        this.state = State.STOPPED;
//    }
//}