UTEID: hcg359;
FIRSTNAME: Charles;
LASTNAME: Gong;
CSACCOUNT: hcgong;
EMAIL: hcgong@cs.utexas.edu;

[Project directory structure]
CS361_Lab1: {
	CS361_Lab1_Part1: {
		src: {
			Instruction.java,
			ObjectManager.java,
			ReferenceMonitor.java,
			SecureSystem.java,
			SecurityLevel.java,
			instructionList,
			test
		}
	},
	CS361_Lab1_Part2: {
		src: {
			CovertChannel.java,
			Instruction.java,
			ObjectManager.java,
			ReferenceMonitor.java,
			SecureSystem.java,
			SecurityLevel.java,
			inputfilename,
			pride_and_prejudice
		}
	}
}


[Program 1]
[Description]
[Part 1]
My SecureSystem program uses BLP modeling to implement a secure system to read and write objects. In SecureSystem.java, I read instructions from a file in the parseFile() function and sent them into the ReferenceMonitor. Then, in ReferenceMonitor.java the executeInstruction() function checks to see if the operation is allowed. If it is allowed, it uses the ObjectManager to perform operations on the objects. Compile the program with “javac *.java” within /src and run the program with “java SecureSystem INPUTFILE” where INPUTFILE = instructionList, or test.

[Part 2]
My CovertChannel program takes advantage of the BLP model and the behavior of operations to send data from a high level subject to a lower level subject. In CovertChannel.java, I read data from a file and convert it into bits. Instructions are then generated to indicate to the low level subject that the high level subject read either a 0 or 1. Instructions are fed to the ReferenceMonitor and that determines whether the instruction succeeds. The RUN instruction executes the subject’s run() method, which generates a byte with the bits it is given. I implemented a Byte inner class within Subject, which handles the output of a byte when it is full. Compile the program with “javac *.java” within /src and run the program with “java CovertChannel INPUTFILE” or “java CovertChannel -v INPUTFILE” where INPUTFILE = inputfilename, pride_and_prejudice, or metamorphosis.


[Finish]
[Part 1]
I finished all of part 1.

[Part 2]
I finished all of part 1 but did not record bandwidth.

[Part 2, Machine Information]
Machine type: macOS Sierra
Clock speed: 2.5 GHz 

[Part 2, Results Summary]
[No.]	[DocumentName] 		[Size] 	 	[Bandwidth]
1	Pride and Prejudice	XXX bytes	YYY bits/ms
2	Metamorphosis		XXX bytes	YYY bits/ms

[Part 1, Test Cases]
[Input of test 1]
write hal hobj 
read lal hobj
read hal 
write lyle lobj 10
read hal lobj 
write lyle hobj 20
write hal lobj 200
read hal hobj
read lyle lobj
read lyle hobj
foo lyle lobj
Hi lyle, This is hal
The missile launch code is 1234567

[Output of test 1]
Reading from file: instructionList

Bad Instruction
The current state is:
   lobj: 0
   hobj: 0
   lyle read: 0
   hal read: 0

Bad Instruction
The current state is:
   lobj: 0
   hobj: 0
   lyle read: 0
   hal read: 0

Bad Instruction
The current state is:
   lobj: 0
   hobj: 0
   lyle read: 0
   hal read: 0

lyle writes 10 to lobj
The current state is:
   lobj: 10
   hobj: 0
   lyle read: 0
   hal read: 0

hal reads lobj
The current state is:
   lobj: 10
   hobj: 0
   lyle read: 0
   hal read: 10

lyle writes 20 to hobj
The current state is:
   lobj: 10
   hobj: 20
   lyle read: 0
   hal read: 10

hal writes 200 to lobj
The current state is:
   lobj: 10
   hobj: 20
   lyle read: 0
   hal read: 10

hal reads hobj
The current state is:
   lobj: 10
   hobj: 20
   lyle read: 0
   hal read: 20

lyle reads lobj
The current state is:
   lobj: 10
   hobj: 20
   lyle read: 10
   hal read: 20

lyle reads hobj
The current state is:
   lobj: 10
   hobj: 20
   lyle read: 0
   hal read: 20

Bad Instruction
The current state is:
   lobj: 10
   hobj: 20
   lyle read: 0
   hal read: 20

Bad Instruction
The current state is:
   lobj: 10
   hobj: 20
   lyle read: 0
   hal read: 20

Bad Instruction
The current state is:
   lobj: 10
   hobj: 20
   lyle read: 0
   hal read: 20
   
[Input of test 2]
write hobj hal 5
write lyle hobj 50
read hal hobj
write hal hobj 100
write hal lobj 100
read lyle lobj
write lyle lobj 5
read hal lobj
write lyle lobj 2000
read lyle lobj
read hal lobj
read hal hobj

[Output of test 2]
Reading from file: test

Bad Instruction
The current state is:
   lobj: 0
   hobj: 0
   lyle read: 0
   hal read: 0

lyle writes 50 to hobj
The current state is:
   lobj: 0
   hobj: 50
   lyle read: 0
   hal read: 0

hal reads hobj
The current state is:
   lobj: 0
   hobj: 50
   lyle read: 0
   hal read: 50

hal writes 100 to hobj
The current state is:
   lobj: 0
   hobj: 100
   lyle read: 0
   hal read: 50

hal writes 100 to lobj
The current state is:
   lobj: 0
   hobj: 100
   lyle read: 0
   hal read: 50

lyle reads lobj
The current state is:
   lobj: 0
   hobj: 100
   lyle read: 0
   hal read: 50

lyle writes 5 to lobj
The current state is:
   lobj: 5
   hobj: 100
   lyle read: 0
   hal read: 50

hal reads lobj
The current state is:
   lobj: 5
   hobj: 100
   lyle read: 0
   hal read: 5

lyle writes 2000 to lobj
The current state is:
   lobj: 2000
   hobj: 100
   lyle read: 0
   hal read: 5

lyle reads lobj
The current state is:
   lobj: 2000
   hobj: 100
   lyle read: 2000
   hal read: 5

hal reads lobj
The current state is:
   lobj: 2000
   hobj: 100
   lyle read: 2000
   hal read: 2000

hal reads hobj
The current state is:
   lobj: 2000
   hobj: 100
   lyle read: 2000
   hal read: 100
