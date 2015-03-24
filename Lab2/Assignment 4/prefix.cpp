/*
Filip Todorovic
Assignment Lab 4: Recursion
Due: 11/9/2013
*/

#include "prefix.h"

//-----------------------------------------------------------------------------
// constructor
// initialize the array to the empty string 
// char arrays are terminated with '\0' so operator<< works properly

Prefix::Prefix() {
   expr[0] = '\0'; 
} 

//-----------------------------------------------------------------------------
// setPrefix
// Set a prefix expression made of single digit operands, operators +,-,*,/
// and no blanks or tabs contained in a file. The expression is valid in
// that each operator has two valid operands.

void Prefix::setPrefix(ifstream& infile) { 
    infile >> expr;
}
//-----------------------------------------------------------------------------
// evaluatePrefix 
// Evaluate a prefix expr made of single digit operands and operators +,-,*,/

int Prefix::evaluatePrefix() const {
    if (expr[0] == '\0') 
        return 0;

    int index = -1;                               // to walk through expr
    return evaluatePrefixHelper(index);
}
//-----------------------------------------------------------------------------
// evaluatePrefixHelper
// Helper to iterate through the array recursively and calculating the value
// of the prefix expression. 'index' is the index of the prefix array.

int Prefix::evaluatePrefixHelper(int& index) const {
    char symbol = expr[++index];

	// Recursion continues until end of line is reached
	if (symbol != '\0') {
		// Find an operator and perform operation on the operands
		if (symbol == '+') {
			int left = evaluatePrefixHelper(index);
			int right =  evaluatePrefixHelper(index);
			return  (left + right);
		}
		else if (symbol == '*') {
			int left = evaluatePrefixHelper(index);
			int right = evaluatePrefixHelper(index);
			return (left * right);
		}
		else if (symbol == '-') {
			int left = evaluatePrefixHelper(index);
			int right = evaluatePrefixHelper(index);
			return (left - right);
		}
		else if (symbol == '/') {
			int left = evaluatePrefixHelper(index);
			int right = evaluatePrefixHelper(index);
			
			// handle divide by 0;
			if (right != 0) {
				return left / right;
			} else {
				cout << "Divide by zero in expression ";
				return 0;
			}
		}
		else if (isdigit(symbol)) {
			return (symbol - '0');
		}
	}
	return (symbol - '0');
}

//-----------------------------------------------------------------------------
// outputAsPostfix, toPostfix
// Convert prefix expression to postfix and output

void Prefix::outputAsPostfix(ostream& out) const {
    if (expr[0] == '\0') 
        return;

    int index = -1;                           // to walk through expr
    int count = -1;                           // used to build postfix array
    char postfix[MAXSIZE+1];                  // holds postfix expression
    toPostfix(index, postfix, count);
    postfix[++count] = '\0';
    out << postfix;
}
//-----------------------------------------------------------------------------
// toPostfix
// Helper to convert the current prefix expression into a postfix expression
// and inserts the new postfix expression into an array. 'postfix' is an empty
// array, 'index' is the index of the prefix array, 'count' is the number of 
// elements input into 'prefix'
void Prefix::toPostfix(int& index, char postfix[], int& count) const {
    char symbol = expr[++index];
	
	// Recursion continues until end of line is reached
	if (symbol != '\0') {
		// Find operator, wait until their associated operands are input, then
		// insert operator
		if (symbol == '+' || symbol == '*' || symbol == '-' || symbol == '/') {
			toPostfix(index, postfix, count);
			toPostfix(index, postfix, count);
			postfix[++count] = symbol;
		}
		else { 
			// Operands are input before their operators
			postfix[++count] = symbol;
		}
	}
}

//-----------------------------------------------------------------------------
// operator<<
// display prefix expression as a string (char array)

ostream& operator<<(ostream& output, const Prefix& expression) {
    output << expression.expr;
    return output;
}
