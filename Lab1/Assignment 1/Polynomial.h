/*
Filip Todorovic
Assignment #1: Polynomial
Due: 1/26/2014
Instructor: Michael M. Stark
*/
#include <iostream>
#include <string>
#include <sstream>
#include <cassert>
#include <cstdarg>
#include <cstdio>
#include <cstdlib>


using namespace std;

//------------------------------------------------------------------------------
// Class Polynomial: An array of values which are interpreted as a polynomial
//		with array index being the degree and array value being the constant
// Assumptions:
//		- Polynomial array cannot contain negative degrees
//      - Assume valid user input of integer values
//      - First user input value must be positive and indicative of the number
//        of values that follow
//		- Indeces with values of 0 are considered empty
//		- Arrays are resized if inserted degree is larger than array size
//		- Arrays only increase in size and never shrink
//		- Valid operand types are Polynomial and int
//      - Polynomial operations include: +, -, *, +=, -=, *=, =, ==, !=
//      - First value of user input must be the number of values that will be
//        placed within a Polynomial object, followed by those values which are
//        placed in ascending order of index in the array, beginning at index 0
//------------------------------------------------------------------------------

class Polynomial {
//------------------------------------------------------------------------------
// Operator <<
// Operator to output the values of a Polynomial, with "x" as the variable
friend ostream& operator <<(ostream& output, const Polynomial& arr) {

	int tempSize = arr.size-1;
	int counter = 0;
	// properly add signs and degrees where necessary
	for (int i = tempSize; i >= 0; i--) {
		// disregard indeces with value of 0
		if (arr.pArray[i] != 0) {
			if (arr.pArray[i] > 1) {         // positive coefficients (add'+')
				if (i == 0) {
					if (counter == 0) {output << " " << arr.pArray[i]; counter++;}
					else output << " + " << arr.pArray[i]; 
				}
				else if (i == 1) {
					if (counter == 0) {output << arr.pArray[i] << "x"; counter++;}
					else output << " + " << arr.pArray[i] << "x"; 
				}
				else {
					if (counter == 0) {output << " " << arr.pArray[i] << "x^" << i; counter++;}
					else output << " + " << arr.pArray[i] << "x^" << i;
				}
			}
			else if (arr.pArray[i] < -1) {   // negative coefficients
				if (i == 0) {
					output << " " << arr.pArray[i];
				}
				else if (i == 1) {
					if (counter == 0) {output << arr.pArray[i] << "x"; counter++;}
					else output << " - " << -arr.pArray[i] << "x";
				}
				else {
					if (counter == 0) {output << arr.pArray[i] << "x^" << i; counter++;}
					else output << " - " << -arr.pArray[i] << "x^" << i;
				}
			}
			else if (arr.pArray[i] == 1) {   // values of 1 only output '+'
				if (i == 0) {
					if (counter == 0) { output << arr.pArray[i]; counter++;}
					else output << " + " << arr.pArray[i];
				}
				else if (i == 1) {
					if (counter == 0) { output << " x"; counter++;}
					else output << " + " << "x";
				}
				else {
					if (counter == 0) { output << " x^" << i; counter++; }
					else output << " + " << "x^" << i;
				}
			}
			else if (arr.pArray[i] == -1) {  // values of -1 only output a '-'
				if (i == 0) {
					output << " " << arr.pArray[i];
				}
				else if (i == 1) {
					if (counter == 0) { output << " -" << "x"; counter++; }
					else output << " - " << "x";
				}
				else {
					if (counter == 0) { output << " -" << "x^" << i; counter++; }
					else output << " - " << "x^" << i;
				}
			}
		}
	}
	return output;
}
//------------------------------------------------------------------------------
// Operator >>
// Input operator to insert values into array
// First value of input is the number of values that will follow
// Values after first are placed into array at ascending indeces, beginning at 0
// and ending at the first value minus 1
friend istream& operator >>(istream& input, Polynomial& arr) {
	
	int coeff = 0;   // holds the coefficient multiplied by the variable
	int power = 0;   // holds the degree of a variable

	// read input, inserting values into poly and power, then into arr, if 
	// successful
	while (true) {
		input >> coeff >> power;
		if (!input.eof()) {
			arr.set_coeff(power,coeff);
		}
		else break;
	}
	return input;
}

public:
	// CONSTRUCTORS
	Polynomial();                              // default constructor
	Polynomial(unsigned, ... );                // constructor
	~Polynomial();						       // destructor
	Polynomial(const Polynomial&);			   // copy constructor

	// MEMBER FUNCTIONS
	int get_coeff(unsigned) const;		// retrieve a coefficient at an index
	int set_coeff(unsigned power = 0, int coeff = 0); // insert into pArray
	int get_degree() const;				// retrieve degree of polynomial
	
	
	// ARITHMETIC OPERATORS
	Polynomial operator+(const Polynomial&) const;// addition (Polynomial)
	Polynomial operator+(const int) const;        // addition (int)
	Polynomial operator-(const Polynomial&) const;// subtraction (Polynomial)
	Polynomial operator-(const int) const;        // subtraction (int)
	Polynomial operator*(const Polynomial&) const;// multiplication (Polynomial)
	Polynomial operator*(const int) const;        // multiplication (int)
	Polynomial operator-() const;                 // unary negation

	// ASSIGNMENT OPERATORS
	Polynomial& operator+=(const Polynomial&); // addition assignment
	Polynomial& operator-=(const Polynomial&); // subtraction assignment
	Polynomial& operator*=(const Polynomial&); // multiplication assignment
	Polynomial& operator=(const Polynomial&);  // assignment operator

	// EQUALITY OPERATORS
	bool operator==(const Polynomial&) const; // equality operator
	bool operator!=(const Polynomial&) const; // inequality operator

private:
	int *pArray;  // array to hold polynomial

	// ATTRIBUTES OF POLYNOMIAL ARRAY
	unsigned size;     // size of pArray
	int degree;        // degree of the polynomial
	bool full;         // true if array is filled

	// PRIVATE MEMBER FUNCTIONS
	void makeEmpty();					// deallocates memory from object
	void resize(int);		    		// increase the size of pArray
	int set_degree();					// set degree of a polynomial
	bool isFull() const;				// check if array is full
};
