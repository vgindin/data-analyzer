## Description
The file at src/main/test/resources/transaction_data.csv is a sample data file. Write an application that reads a file
in this format. Using the data in each record, calculate the sum of all values in the `transactionAmount` field in the 
file. 
Also, calculate the sum of all values in the `transactionAmount` field per `transactionDate`.
 
The application should support a command line option for the output format. By default, the application should write out 
a summary report in the text format below and exit:

```
==== Run Summary ====
Input filename: src/main/test/resources/transaction_data.csv
Total records processed: X
Total transaction amount: N
Transaction amount per day:
yyyy-mm-dd: N1
yyyy-mm-dd: N2
...
yyyy-mm-dd: NX

==== End Run Summary ====
```

Alternatively, the application should support a command line argument that writes out the summary report in JSON as below:
```
{
    "Input filename": "src/main/test/resources/transaction_data.csv",
    "Total records processed": X,
    "Total transaction amount": N,
    "Daily Transaction Amounts": {
        "yyyy-mm-dd": N1,
        "yyyy-mm-dd": N2,
        ...,
        "yyyy-mm-dd": NX
    }
}
```

Your application should also accept a command line argument specifying the input file to process.  

## Further Instructions
* You may write your solution in Java (8+), Scala, or Kotlin
* You may use any open-source libraries that you wish
* You may use any resources that would normally be available to you, including books and Internet resources 
(StackOverflow, etc), and any IDE you are comfortable with.
* You may *not* ask other people to help you with the implementation, nor copy other implementations from the web. The
work you deliver should be your work alone
* Please add instructions for running your application in this file, below under the "Running" section

## Evaluation Criteria
* Pretend you are writing a production application that non-technical users will run. Your target user knows 
just enough Unix command line to run your application and navigate the filesystem. Keep this user profile in mind and
code defensively to handle any errors or edge cases that might come up based on the end user profile.
* One of the most important criteria for production code is maintainability. Do your best to write the code so that 
other developers can read and understand it so that they can more easily maintain it. You will be evaluated on code 
readability, including things like picking good names and logical code organization
* Another important criteria of a production application is proving that it works. How do you prove that your
implementation works correctly?
* You are not being measured on the time it takes you to complete this project, you should have ample time and we do
not intend to put you under time pressure
* As part of your submission, document your design and the edge cases you considered and how you handled them. Put this
 information in the `design.md` file.

* Good Luck! If you have any questions, please email Radu at [radu.ionita@anonossoftware.com](mailto:radu.ionita@anonossoftware.com)

## Submission
To submit your work on this project, please email a .zip archive of your project directory to [radu.ionita@anonossoftware.com](mailto:radu.ionita@anonossoftware.com).
Include your name in the subject line. Thank you!

## Running
Please replace this sentence with instructions for running your application.
