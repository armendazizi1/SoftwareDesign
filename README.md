# Cell Trees Traversal

A  program that given a cell of an excel sheet traverses the whole cell tree and outputs the content of the cells it found. 


### Prerequisites

You need to have java installed on your machine


## Running the program

From the project folder enter the following command on terminal/command line:


```
java -jar target/muck-0.0.1-SNAPSHOT.jar
```

You also need to pass the following command options
```
 -f,--filepath <arg>    Path of the excel file
 
 -s,--sheetname <arg>   Name of the sheet(surrounded by single-quotes)
 -c,--cellname <arg>    Name of the cell
```
 
 Example:
 
 
```
 java -jar target/muck-0.0.1-SNAPSHOT.jar -f /Users/[yourUserName]/Desktop/sample.xlsx -s "Sheet1" -c A12
```
 


## Built With

* [Java](https://www.oracle.com/technetwork/java/javase/overview/java8-2100321.html) - The programming language used
* [Maven](https://maven.apache.org/) - Dependency Management





## Authors

* **Gloria Sassone**  
* **Armend Azizi**  

