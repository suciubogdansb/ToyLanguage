package View.Commands;

import Controller.Service;
import Module.Exception.*;

public class RunExample extends Command {
    private Service service;

    public RunExample(String key, String description, Service service) {
        super(key, description);
        this.service = service;
    }

    @Override
    public void execute() {
        try {
            this.service.allStep();
        } catch (DivisionException | TypeException | StackException | ExpressionException | DictionaryException |
                 IOException | RepositoryException e) {
            System.out.println(e.getClass() + ": " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}
