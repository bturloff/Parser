
package A2;
import java.util.Vector;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author javiergs
 */


public class Parser {

  private static DefaultMutableTreeNode root;
  private static Vector<Token> tokens;
  private static int currentToken;

  public static DefaultMutableTreeNode run(Vector<Token> t) {
    tokens = t;
    currentToken = 0;
    root = new DefaultMutableTreeNode("expression");
    //
    rule_R(root);
    //
    return root;
  }
  
  private static boolean rule_Y(DefaultMutableTreeNode parent){
	  
	  
	    boolean error;
	    DefaultMutableTreeNode node;
	    node = new DefaultMutableTreeNode("R");
	    parent.add(node);
	    error = rule_E(node);
	    
	    
	    return error;
	  
  }

  
  private static boolean rule_R(DefaultMutableTreeNode parent){
	  
	    boolean error;
	    DefaultMutableTreeNode node;
	    node = new DefaultMutableTreeNode("R");
	    parent.add(node);
	    error = rule_E(node);
	    while ( currentToken < tokens.size() && (tokens.get(currentToken).getWord().equals("<") || tokens.get(currentToken).getWord().equals(">")
			    || tokens.get(currentToken).getWord().equals("==") || tokens.get(currentToken).getWord().equals("!=")))
	    {
		    		if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("<")) {
				        node = new DefaultMutableTreeNode("<");
				        parent.add(node);
				        currentToken++;
				        node = new DefaultMutableTreeNode("E");
				        parent.add(node);
				        error = rule_E(node);
			      } else if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals(">")) {
				        node = new DefaultMutableTreeNode(">");
				        parent.add(node);
				        currentToken++;
				        node = new DefaultMutableTreeNode("E");
				        parent.add(node);
				        error = rule_E(node);
			      } else if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("==")) {
				        node = new DefaultMutableTreeNode("==");
				        parent.add(node);
				        currentToken++;
				        node = new DefaultMutableTreeNode("E");
				        parent.add(node);
				        error = rule_E(node);
			      } else if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("!=")) {
				        node = new DefaultMutableTreeNode("!=");
				        parent.add(node);
				        currentToken++;
				        node = new DefaultMutableTreeNode("E");
				        parent.add(node);
				        error = rule_E(node);
			      }
		    		
	    }
	  return error;
  }

  private static boolean rule_E(DefaultMutableTreeNode parent) {
	  
    boolean error;
    DefaultMutableTreeNode node;
    node = new DefaultMutableTreeNode("A");
    parent.add(node);
    error = rule_A(node);
    while (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("+") || currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("-")) {
      if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("+")) {
        node = new DefaultMutableTreeNode("+");
        parent.add(node);
        currentToken++;
        node = new DefaultMutableTreeNode("A");
        parent.add(node);
        error = rule_A(node);
      } else if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("-")) {
        node = new DefaultMutableTreeNode("-");
        parent.add(node);
        currentToken++;
        node = new DefaultMutableTreeNode("A");
        parent.add(node);
        error = rule_A(node);
      }
    }
    return error;
  }

  private static boolean rule_A(DefaultMutableTreeNode parent) {
    boolean error;
    DefaultMutableTreeNode node = new DefaultMutableTreeNode("B");
    parent.add(node);
    error = rule_B(node);
    while (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("*") || currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("/")) {
      if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("*")) {
        node = new DefaultMutableTreeNode("*");
        parent.add(node);
        currentToken++;
        node = new DefaultMutableTreeNode("B");
        parent.add(node);

        error = rule_B(node);

      } else if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("/")) {
        node = new DefaultMutableTreeNode("/");
        parent.add(node);
        node = new DefaultMutableTreeNode("B");
        parent.add(node);
        currentToken++;
        error = rule_B(node);
      }
    }
    return error;
  }

  private static boolean rule_B(DefaultMutableTreeNode parent) {
    boolean error;
    DefaultMutableTreeNode node;
    if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("-")) {
      node = new DefaultMutableTreeNode("-");
      parent.add(node);
      currentToken++;
    }
    node = new DefaultMutableTreeNode("C");
    parent.add(node);
    error = rule_C(node);
    return error;
  }

  private static boolean rule_C(DefaultMutableTreeNode parent) {
    boolean error = false;
    DefaultMutableTreeNode node;
    if (currentToken < tokens.size() && tokens.get(currentToken).getToken().equals("INTEGER")) {
      node = new DefaultMutableTreeNode("integer" + "(" + tokens.get(currentToken).getWord() + ")");
      parent.add(node);
      currentToken++;
    } else if (currentToken < tokens.size() && tokens.get(currentToken).getToken().equals("IDENTIFIER")) {
      node = new DefaultMutableTreeNode("identifier" + "(" + tokens.get(currentToken).getWord() + ")");
      parent.add(node);
      currentToken++;
    } else if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("(")) {
      node = new DefaultMutableTreeNode("(");
      parent.add(node);
      currentToken++;
      //
      node = new DefaultMutableTreeNode("expression");
      parent.add(node);
      error = rule_R(node);
      //
      node = new DefaultMutableTreeNode(")");
      parent.add(node);
      currentToken++;      
    } else {
      return true;
    }
    return false;
  }

}
