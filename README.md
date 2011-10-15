#Scala Letter Frequency

A simple Scala program that calculates the frequency
of letters in the ispell dictionary files.

##Results

This is the output of the program:

Total letters: 1017741
a: 	84144		8.27%
b: 	20084		1.97%
c: 	44569		4.38%
d: 	34378		3.38%
e: 	111379		10.94%
f: 	12925		1.27%
g: 	26626		2.62%
h: 	25393		2.50%
i: 	87883		8.64%
j: 	1870		0.18%
k: 	9244		0.91%
l: 	58331		5.73%
m: 	30627		3.01%
n: 	70276		6.91%
o: 	67744		6.66%
p: 	30201		2.97%
q: 	1806		0.18%
r: 	73923		7.26%
s: 	77032		7.57%
t: 	69772		6.86%
u: 	34826		3.42%
v: 	10384		1.02%
w: 	8000		0.79%
x: 	3177		0.31%
y: 	20827		2.05%
z: 	2320		0.23%

##Running

I used SBT to build/run the program. If you are on OSX and have Home
Brew installed, you can just do this:

    $ brew install sbt

Then in the root of this source, you can do

    $ sbt run

It should download all the dependencies (for SBT, this program doesn't
require anything other than the native Scala libs) and then run.
