import java.util.ArrayList;
import java.util.HashMap;

public class subDecl extends Statement{
	private Token id;
	private HashMap<Token,Token> params;
	private Compound compound;
	

	/**
     * Reads in a while statement from the specified stream
     *   @param input the stream to be read from
     */
    public subDecl(TokenStream input) throws Exception {
        Token keyword = input.next();
        if (!keyword.toString().equals("sub")) {
            throw new Exception("SYNTAX ERROR: Malformed subroutine declaration");
        }
        this.id = input.next();
        input.next();
        this.params = new HashMap<Token,Token>();
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
        //input.next();
        //this.pList.add(input.next());
        this.compound = new Compound(input);
    }
    
    /**
     * Executes the current while statement.
     */
	public void execute() throws Exception {
		Interpreter.MEMORY.declare(id,compound,params);
		
	}

	/**
     * Converts the current while statement into a String.
     *   @return the String representation of this statement
     */
	public String toString() {
		return "sub " + this.id + "(" + ")\n" + this.compound;
	}

}
