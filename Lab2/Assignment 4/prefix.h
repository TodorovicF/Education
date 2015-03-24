 
// FILE: prefix.h
// PROVIDES: Prefix object provides functionality to evalute prefix expressions
//			 and convert to postfix.
//-----------------------------------------------------------------------------
// TYPES: Expressions consist of ints and four operators which are read from a
//        text file and placed into an array of fixed size MAXSIZE.
//-----------------------------------------------------------------------------
// CONSTRUCTOR for the prefix class: 
//     Prefix()
//         Initializes the first element in the array to the end-of-line
//         character
//-----------------------------------------------------------------------------
// FUNCTIONS:
//     friend ostream& operator<<(ostream&, const Prefix&)
//         Prints the characters within an array. 'Prefix' is the address of 
//         the array containing a prefix expression.
//         
//     void setPrefix(ifstream& infile)
//         infile holds the data within data4.txt. A single line is placed into
//         expr[] as an array of chars.
//         
//     int evaluatePrefix() const
//         Evaluates a single prefix expression. The helper function
//         is called to evaluate the expression and return an int value.
//         
//     void outputAsPostfix(ostream&) const
//         'index' and 'count' ints hold the index value of the 'postfix' array
//         and the 'count' of the number of chars in the array. Uses helper 
//         function to input a postfix version of the prefix expression into 
//         the 'postfix' array.
//-----------------------------------------------------------------------------
// PRIVATE MEMBER VARIBALES:
//     char expr[MAXSIZE+1]
//         Array to hold the prefix expression. Holds a single line read from 
//         data4.txt. Each line is a single prefix expression.
//-----------------------------------------------------------------------------
// PRIVATE MEMBER FUNCTIONS:
//     int evaluatePrefixHelper(int&) const
//         A single prefix expression is evaluated and the result is returned.
//
//     void toPostfix(int&, char [], int&) const
//         Converts the current prefix expression into a postfix 
//         expression and inputs the new expression into the char array
//         parameter.
//-----------------------------------------------------------------------------
//-------------------------------Assumptions-----------------------------------
//-----------------------------------------------------------------------------
//  -- Each line within data4.txt contains only one expression of integers
//     and operators
//  -- All expressions are valid prefix expressions
//  -- All operands are >= 0 and <= 9
//  -- Accepted operators are: /, +, -, *
//  -- Divide-by-zero expressions evaluate to 0
//-----------------------------------------------------------------------------

#ifndef PREFIX_H
#define PREFIX_H
#include <iostream>
#include <fstream>
using namespace std;

int const MAXSIZE = 100;

class Prefix {
    friend ostream& operator<<(ostream&, const Prefix&);

public:
    Prefix();
    void setPrefix(ifstream& infile);
    int evaluatePrefix() const;
    void outputAsPostfix(ostream&) const;
	
private:
    char expr[MAXSIZE+1];
    int evaluatePrefixHelper(int&) const;
    void toPostfix(int&, char [], int&) const;
	
};

#endif
