// Evaluator for Lego formulas
package lego;

import lego.parser.*;

// data structure to store values of free variables
class Env {
}

public class Eval {
    public static boolean eval(Formula f) {
	return eval(f, new Env());
    }
    public static boolean eval(Formula f, Env e) {
	return true;
    }
}
