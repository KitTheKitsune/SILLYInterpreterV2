import java.util.HashMap;

public class subCall extends Statement{
	Token id;
	private HashMap<Token,Token> params;
	
	/**
     * Reads in a while statement from the specified stream
     *   @param input the stream to be read from
     */
    public subCall(TokenStream input) throws Exception {
        Token keyword = input.next();
        if (!keyword.toString().equals("call")) {
            throw new Exception("SYNTAX ERROR: Malformed subroutine call");
        }
        this.id = input.next();
        input.next();
        while (true) {
        	if (input.lookAhead().equals(new Token(")"))) {
        		input.next();
        		break;
        	}else {
        		input.next();
        	}
        	Token type = input.next(); // get the data type
        	Token id = input.next(); // get the name of the param
        	this.params.put(type, id);
        }
        input.next();
    
    }

	 /**
     * Executes the current while statement.
     */
	public void execute() throws Exception {
		Interpreter.MEMORY.newScope();
		
		Interpreter.MEMORY.getSubroutine(this.id).execute();
		
	}

	/**
     * Converts the current while statement into a String.
     *   @return the String representation of this statement
     */
	public String toString() {
		// TODO Auto-generated method stub
		return "call ( "+")";
	}

}
