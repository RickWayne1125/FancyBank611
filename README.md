# Fancy Bank 611

# CS611-5
## FancyBank
---------------------------------------------------------------------------
| Name                      | BU-Email        | BUID      |
| ------------------------- | --------------- | --------- |
| Rui(Richard) Wei          | rickwei@bu.edu  | U02377614 |
| Prithviraj Pankaj Khelkar | pkhelkar@bu.edu | U41575882 |
| Taoyu Chen                | mirack@bu.edu   | U82740711 |
| Yuxuan Zhang              |                 |           |


## Files
---------------------------------------------------------------------------

```shell
.
├── API
├── Account	# Account pa
│   ├── Loan
│   └── Security
├── Bank
├── BoughtStock
├── DataBase
├── Frontend
├── Main.java
├── Money
├── Person
│   ├── Customer
│   ├── Manager
├── Stock
├── Transact
├── Utils
│   ├── Config.java
│   ├── DAO.java
│   ├── Helpers.java
│   ├── IO.java
│   ├── MessageType.java
│   ├── Tests.java
│   └── TextColors.java
└── fancybank.db
```

## Notes
---------------------------------------------------------------------------
1. <Files to be parsed should be stored in ConfigFiles, for parser class to

[//]: # (   read class>)

[//]: # (2. <Bonus Done>)

[//]: # (3. <Notes to grader>)

## How to compile and run
---------------------------------------------------------------------------
1. Navigate to the main directory of the project after unzipping the files
2. Run the following instructions:

For Linux/OSX users, please use commands below:

```shell
sh build_run.sh # This is used to compile the code, and it should generate a jar package in the main folder
java -jar FancyBank611.jar # This is to run the program
```

For Windows users, please use commands below in git bash(or any other way that you can run `bash`) instead:

```bash
bash build_run.sh # This is used to compile the code, and it should generate a jar package in the main folder
java -jar FancyBank611.jar # This is to run the program
```

If the method above failed, please compile manually in powershell:

```shell
rm -r bin
mkdir bin
cmd /r dir "*.java" /s /B > sources.txt
javac -encoding utf-8 -d bin -cp ".;lib/sqlite-jdbc-3.36.0.3.jar;lib/forms_rt-7.0.3.jar" "@sources.txt"
jar -cvfm 'FancyBank611.jar' 'MANIFEST.MF' -C 'bin' .
java -jar FancyBank611.jar
```

## Input/Output Example

---------------------------------------------------------------------------