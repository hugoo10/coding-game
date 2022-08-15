
public class Action {
	public ActionType actionType;
	public String parameters;
	public String comment;

	public Action(final ActionType actionType, final String parameters, final String comment){
		this.actionType = actionType;
		this.parameters = parameters;
		this.comment = comment;
	}

	public Action(final ActionType actionType, final String parameters){
		this(actionType, parameters, null);
	}

	public Action(final ActionType actionType){
		this(actionType, null);
	}

	public enum ActionType{
		RELEASE, MOVE, BUST, STUN;
	}

	public void execute(){
		final String command = this.actionType.name() + ((this.parameters==null || this.parameters.isEmpty())?"":(" "+this.parameters)) + ((this.comment==null || this.comment.isEmpty())?"":(" "+this.comment));
		System.out.println(command);
	}
}
