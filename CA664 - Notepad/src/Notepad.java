import controller.NotepadController;
import view.NotepadView;
import model.NotepadModel;

public class Notepad {

	public static void main(String[] args) {
		NotepadView view = new NotepadView();
		NotepadModel model = new NotepadModel();
		NotepadController controller = new NotepadController(view, model);
	}

}
