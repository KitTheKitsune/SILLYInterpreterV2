import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 * Class that defines the memory space for the SILLY interpreter.
 *   @author Dave Reed
 *   @version 1/24/21
 */
public class MemorySpace {
    private Stack<HashMap<Token, VarPair>> stack;
    private ArrayList<DataValue> heap;
    private HashMap<Token,Subroutine> subStack;

    /**
     * Constructs an empty memory space.
     */
    public MemorySpace() {
        this.stack = new Stack<HashMap<Token, VarPair>>();
        this.stack.push(new HashMap<Token, VarPair>());
        this.heap = new ArrayList<DataValue>();
        this.subStack = new HashMap<Token,Subroutine>();
    }

    /**
     * Declares a variable (without storing an actual value).
     *   @param variable the variable to be declared
     */
    public void declare(Token variable, DataValue.Type varType) {
        this.stack.peek().put(variable, new VarPair(varType, null));
    }
    
    /**
     * Stores a subroutine in the subStack 
     * @param x
     * @param y
     */
    public void declare(Token id, Compound comp, HashMap<Token,Token> params) {
    	this.subStack.put(id, new Subroutine(comp,params));
    }

    /**
     * Determines whether a variable is already declared.
     *   @param variable the variable to look up
     *   @return true if the variable is declared (and active)
     */
    public boolean isDeclared(Token variable) {
        return this.stack.peek().containsKey(variable) || this.subStack.containsKey(variable);
    }
    
    /**
     * Pulls the compound from the identified subroutine.
     * 
     */
    public Compound getSubroutine(Token id) {
    	if(isDeclared(id)) {
    		return this.subStack.get(id).getComp();
    	}
    	return new Compound();
    }
    
    /**
     * Determines the type associated with a variable in memory.
     *   @param variable the variable to look up
     *   @return the type associated with that variable
     */    
    public DataValue.Type getType(Token variable) {
        return this.stack.peek().get(variable).getType();
    }

    /**
     * Determines the value associated with a variable in memory.
     *   @param variable the variable to look up
     *   @return the value associated with that variable
     */      
    public DataValue getValue(Token variable) {
        VarPair pair = this.stack.peek().get(variable);
        if (pair.getType() == DataValue.Type.STRING_VALUE) {
            return this.heap.get((Integer)(pair.getValue().getValue()));
        }
        else {
            return pair.getValue();
        }
    }
    
    /**
     * Stores a variable/value in the stack segment.
     *   @param variable the variable name
     *   @param val the value to be stored under that name
     */
    public void setValue(Token variable, DataValue val)  {
        VarPair pair = this.stack.peek().get(variable);
        if (pair.getType() == DataValue.Type.STRING_VALUE) {
            this.heap.add(val);
            pair.setValue(new IntegerValue(heap.size()-1));
        }
        else {
            pair.setValue(val);
        }
    }
    
   
    
    
    /**
     * Creates a new scope on the stack
     */
    public void newScope() {
    	HashMap<Token, VarPair> shallowCopy = new HashMap<Token, VarPair>();
    	shallowCopy.putAll(this.stack.peek());
    	this.stack.push(shallowCopy);
    }
    
    /**
     * 
     */
    public void endScope() {
    	this.stack.pop();
    }
    
    
    ////////////////////////////////////////////////////////////////////////////
    
    /**
     * Hidden class for storing variable type-value pairs.
     */
    private class VarPair {
        DataValue.Type varType;
        DataValue value;
    
        public VarPair(DataValue.Type type, DataValue val) {
            this.varType = type;
            this.value = val;
        }
    
        public DataValue.Type getType() {
            return this.varType;
        }
   
        public DataValue getValue() {
            return this.value;
        }
    
        public void setValue(DataValue val) {
            this.value = val;
        }
    }
    
    /**
     * Hidden class for storing subroutine parameters and compound.
     */
    private class Subroutine {
    	Compound comp;
    	HashMap<Token,Token> params;
    	
    	public Subroutine(Compound comp, HashMap<Token,Token> params) {
    		this.comp = comp;
    		this.params = params;
    	}
    	
    	public Compound getComp() {
    		return this.comp;
    	}
    	
    	public void setComp(Compound comp) {
    		this.comp = comp;
    	}
    
    }
    
    
    
}





























