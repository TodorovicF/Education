#include "Polynomial.h"

//------------------------------------------------------------------------------
// DefaultConstructor
// Create a Polynomial object with empty array
Polynomial::Polynomial() {
	// Empty array will still have size of 1 with value 0 at index 0
	degree = 0;
	size = 1;
	full = false;
	pArray = new int[size];
	pArray[0] = 0;
}

//------------------------------------------------------------------------------
// Constructor
// Create a Polynomial with array size equal to n and place all consecutive 
// values in ascending order of index
Polynomial::Polynomial(unsigned n, ... ) {
	
	if (n == 0) {        // create zero polynomial if 0 values
		pArray = new int[1];
		pArray[0] = 0;
		degree = 0;
		size = 1;
	} else {             // allocate memory for pArray to size n
		pArray = new int[n];
		degree = n-1;
		size = n;
	}
	full = false;
	assert(pArray != NULL);   // out of memory

	int coeff = 0;       // coefficient to be placed in array

	// initialize the list of values
	va_list args;
	va_start(args, n);

	// loop through the list, storing values at their proper index
	if (n > 0) {
		for (unsigned i = 0; i < size; i++) {
			coeff = va_arg(args, int);        // store coefficient
			pArray[i] = coeff;
		}
	}
	va_end(args);

	// set attributes of the object
	set_degree();
	isFull();
}
//------------------------------------------------------------------------------
// Destructor
// Calls makeEmpty to deallocate memory
Polynomial::~Polynomial() {
	makeEmpty();
}
//------------------------------------------------------------------------------
// Copy Constructor
// Create identical Polynomial object
Polynomial::Polynomial(const Polynomial& arr){
	
	// allocate memory for pArray and set attributes
	pArray = new int[arr.size];
	size = arr.size;
	degree = arr.degree;
	full = arr.full;
	assert(pArray != NULL);    // out of memory

	// insert values
	for (unsigned i = 0; i < size; i++) {
		pArray[i] = arr.pArray[i];
	}
}
//------------------------------------------------------------------------------
// makeEmpty
// Deallocate memory from pArray
void Polynomial::makeEmpty() {
	delete[] pArray;
	pArray = NULL;
}
//------------------------------------------------------------------------------
// resize
// Increase the size of pArray if new inserted element would fall out of bounds
// New size will be the new value's degree
void Polynomial::resize(int power) {

	// allocate memory for larger array
	int newSize = power+1;
	int* oldArray = pArray;
	pArray = new int[newSize];
	assert(pArray != NULL);

	// initialize array
	for (int i = 0; i < newSize; i++) {
		pArray[i] = 0;
	}

	// place old values into new, larger array
	for (unsigned i = 0; i < size; i++) {
		pArray[i] = oldArray[i];
	}

	// deallocate old memory and reset attributes of Polynomial
	delete[] oldArray;
	size = newSize;
	set_degree();
}
//------------------------------------------------------------------------------
// getCoeff
// Returns the coefficient at a given degree
int Polynomial::get_coeff(unsigned power) const {

	// check if power out of bounds
	if (size > power && power >= 0) {
		return pArray[power];
	}
	else {
		// do nothing if out of bounds or negative index attempted
		return 0;
	}
}
//------------------------------------------------------------------------------
// setCoeff
// Inserts a coefficient at a certain index equal to its degree
int Polynomial::set_coeff(unsigned power, int coeff) {

	// resize pArray if power is out of bounds
	if (power >= degree) {
		resize(power);
	}

	// insert value and reset attributes
	pArray[power] = coeff;
	set_degree();
	isFull();

	return power;
}
//------------------------------------------------------------------------------
// getDegree
// Returns the degree of a Polynomial
int Polynomial::get_degree() const {
	return degree;
}
//------------------------------------------------------------------------------
// setDegree
// Sets the degree attribute of a Polynomial object
int Polynomial::set_degree() {

	// Find largest index containing a value and set the degree of the object.
	// For newly created objects, degree will be equal to size-1, but objects
	// may be altered from there, so iterating through the array will be
	// necessary.
	if (size != 0) {
		for (unsigned i = 0; i < size; i++) {
			if (pArray[i] != 0) {
				degree = i;
			}
		}
	}
	else degree = 0;

	return degree;
}
//------------------------------------------------------------------------------
// isFull
// Attribute to hold true if pArray is full (for testing purposes)
bool Polynomial::isFull() const {

	// array is not full if any index contains a value of 0
	for (unsigned i = 0; i < size; i++) {
		if (pArray[i] == 0) {
			return false;
		}
	}
	return true;
}
//------------------------------------------------------------------------------
// Operator + (Poly)
// Arithmetic operator to add one Polynomial with another
Polynomial Polynomial::operator+(const Polynomial& arr) const {

	// variables to hold left and right operand size and the largest of the two
	int left = size;
	int right = arr.size;
	int largestSize = 0;

	// check which array is larger
	if (right > left) {
		largestSize = right;
	}
	else {
		largestSize = left;
	}

	// temporary Polynomial to hold new values
	Polynomial newPoly;              
	newPoly.resize(largestSize);

	// add values from each index
	for (int i = 0; i < right; i++) {
		newPoly.pArray[i] += arr.pArray[i];
	}
	for (int i = 0; i < left; i++) {
		newPoly.pArray[i] += pArray[i];
	}
	
	// set attributes
	newPoly.set_degree();
	newPoly.isFull();

	return newPoly;
}
//------------------------------------------------------------------------------
// Operator - (Poly)
// Arithmetic operator to subtract one Polynomial from another
Polynomial Polynomial::operator-(const Polynomial& right) const {

	
	int largestSize = 0;   // variable to hold largest size
	int rightSize = right.size;

	// set largest size
	if (right.size > size) {
		largestSize = right.size;
	}
	else {
		largestSize = size;
	}

	// temporary Polynomial to hold new values
	Polynomial newPoly;           
	newPoly.resize(largestSize);     

	// instantiate pArray
	for (int i = 0; i < largestSize; i++) {
		newPoly.pArray[i] = 0;
	}

	// copy polynomials from one array, then add polynomials from other array
	for (unsigned i = 0; i < size; i++) {
		newPoly.pArray[i] = pArray[i];
	}
	for (int i = 0; i < rightSize; i++) {
		newPoly.pArray[i] -= right.pArray[i];
	}

	// set attributes
	newPoly.set_degree();
	newPoly.isFull();

	return newPoly;
}
//------------------------------------------------------------------------------
// Operator *
// Arithmetic operator to multiply one Poylnomial with another
Polynomial Polynomial::operator*(const Polynomial& arr) const {

	// create temp Poly object and variables to determine attributes of temp
	int left = size;
	int right = arr.size;
	int largestPower = 0;
	
	// set variables of temp object
	largestPower = ((degree + arr.degree) + 1);
	Polynomial temp;               // temporary Polynomial to hold new values
	temp.resize(largestPower);     

	// multiply pArrays of left and right operands and insert values into
	// temp, adding all variables of like power
	for (int i = 0; i < left; i++) {
		if (pArray[i] != 0) {
			for (int j = 0; j < right; j++) {
				if (arr.pArray[j] != 0) {
					temp.pArray[i+j] += (pArray[i] * arr.pArray[j]);
				}
			}
		}
	}
	// set full and degree attributes
	temp.isFull();
	temp.set_degree();

	return temp;
}
//------------------------------------------------------------------------------
// Operator + (int)
// Arithmetic operator to add a value to the constant of degree 0
Polynomial Polynomial::operator+(const int coeff) const {

	Polynomial temp(*this);

	temp.pArray[0] += coeff;
	return temp;
}
//------------------------------------------------------------------------------
// Operator - (int)
// Arithmetic operator to subtract a value from the constant of degree 0
Polynomial Polynomial::operator-(const int coeff) const {

	Polynomial temp(*this);
	
	temp.pArray[0] -= coeff;
	return temp;
}
//------------------------------------------------------------------------------
// Operator * (int)
// Arithmetic operator to subtract a value from the constant of degree 0
Polynomial Polynomial::operator*(const int coeff) const {

	Polynomial temp(*this);
	// multiply all non-zero values by the right operand
	for (unsigned i = 0; i < size; i++) {
		if (temp.pArray[i] != 0) {
			temp.pArray[i] *= coeff;
		}
	}
	return temp;
}
//------------------------------------------------------------------------------
// Operator - (Unary Negation)
// Operator to set a polynomials values to negative if positive and vice-versa
Polynomial Polynomial::operator-() const {
	
	Polynomial temp(*this);       // temporary Polynomial to hold new values    
	// change sign of constant to its opposite
	for (unsigned i = 0; i < size; i++) {
		temp.pArray[i] = -pArray[i];
	}

	return temp;
}
//------------------------------------------------------------------------------
// Operator +=
// Assignment operator to add and assign one Polynomial to another
Polynomial& Polynomial::operator+=(const Polynomial& right) {

	int rightSize = right.size;
	int leftSize = size;

	// if right operand is larger, resize left
	if (rightSize > leftSize) {
		resize(rightSize);
	}
	// add right operand's values into left's
	for (int i = 0; i < rightSize; i++) {
		pArray[i] += right.pArray[i];
	}
	// reset attributes
	isFull();
	set_degree();

	return *this;
}
//------------------------------------------------------------------------------
// Operator -=
// Assignment operator to subtract and assign one Polynomial to another
Polynomial& Polynomial::operator-=(const Polynomial& right) {

	int rightSize = right.size;
	int leftSize = size;

	//  if right operand is larger, resize left
	if (rightSize > leftSize) {
		resize(rightSize);
	}
	// subtract left operand's values from right's
	for (int i = 0; i < rightSize; i++) {
		pArray[i] -= right.pArray[i];
	}

	// reset attributes
	isFull();
	set_degree();
	
	return *this;
}
//------------------------------------------------------------------------------
// Operator *=
// Assignment operator to multiply and assign one Polynomial to another
Polynomial& Polynomial::operator*=(const Polynomial& right) {

	int newSize = (size + right.size); // size of Polynomial after operation
	int leftSize = size;                 // hold size value of left operand
	int rightSize = right.size;          // hold size value of right operand

	// array will always be resized unless right's degree is 0
	

	// if either operand's degrees are 0
	if (right.degree == 0) { // if right degree is 0
		for (unsigned i = 0; i < leftSize; i++) {
			pArray[i] *= right.pArray[0];
		}
		// reset attributes
		set_degree();
		isFull();
		return *this;
	}
	else if (degree == 0) { // if left degree is 0
		if (pArray[0] == 0) return *this;
		else {
			resize(rightSize);
			int val = pArray[0]; // hold single constant to avoid overwriting
			for (int i = 0; i < rightSize; i++) {
				pArray[i] = right.pArray[i] * val;
			}
			// reset attributes
			set_degree();
			isFull();
			return *this;
		}
	}
	resize(newSize);

	Polynomial temp;       // temporary Polynomial to hold new values
	temp.resize(newSize);

	// find a coeff in left pArray and multiply it by all filled indeces of 
	// right pArray. Continue through left until all left coeffs are multiplied
	// by right
	for (int i = 0; i < size; i++) {
		temp.pArray[i] = pArray[i];
	}
	for (int i = 0; i < newSize; i++) {
		pArray[i] = 0;
	}

	for (int i = 0; i < leftSize; i++) {
		if (temp.pArray[i] != 0) {
			for (int j = 0; j < rightSize; j++) {
				if (right.pArray[j] != 0) {
					int newValue = (temp.pArray[i] * right.pArray[j]);
					pArray[i+j] += newValue;
				}
			}
		}
	}

	// reset attributes
	set_degree();
	isFull();

	return *this;
}
//------------------------------------------------------------------------------
// Operator ==
// Check if one Polynomial is equal to another
bool Polynomial::operator==(const Polynomial& right) const {

	// depending on which array is largest, iterate through both and check if
	// polynomials are the same.
	if (right.size > size) { // check left
		for (unsigned i = 0; i < size; i++) {
			if (right.pArray[i] != pArray[i]) {
				return false;
			}
		}
	}
	else if (size > right.size) { // check right
		for (unsigned i = 0; i < right.size; i++) {
			if (right.pArray[i] != pArray[i]) {
				return false;
			}
		}
	}
	return true;
}
//------------------------------------------------------------------------------
// Operator !=
// Check if one Polynomoial is not equal to another
bool Polynomial::operator!=(const Polynomial& right) const {
	return !(*this == right);
}
//------------------------------------------------------------------------------
// Operator = (Poly)
// Set one Polynomial equal to another
Polynomial& Polynomial::operator=(const Polynomial& right) {

	// check if not setting equal to self
	if (&right != this) {
		// deallocate old memory and allocate new memory to pArray with size 
		// of right operand
		delete []pArray;
		size = right.size;
		degree = right.degree;
		full = right.full;
		pArray = new int[size];
		assert(pArray != NULL);  // out of memory

		// copy right operand's values into left's pArray
		for (unsigned i = 0; i < size; i++) {
			pArray[i] = right.pArray[i];
		}
	}
	return *this;
}
