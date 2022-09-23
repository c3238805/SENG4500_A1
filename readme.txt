/*  
    /============================\
    |  SENG4500 Assignment 1     | 
    |  Name: Ni Zeng            | 
    |  Student Number : c3238805 |
    \============================/   */


The assignment Folder contains:

    AssessmentItemCoverSheet.pdf   (CoverSheet)
    ass1.SENG4500.pdf  (assignment specification)
    LinkedList.java     (doubly LinkedList)
    Node.java            (doubly LinkedList's Node)
    TaxNode.java           (class to store TaxNode)
    TaxClient.java
    TaxServer.java


Instruction to Run TaxServer & TaxClient

    1. first run TaxServer.java 
    2. the TaxServer console will ask if want a different port than 4500. (if enter "N", the program will ask to specify the port number).
    3. after the TaxServer establishes its connection, the client can now try to connect with the TaxServer.

    4.  now run TaxClient.java
    5.  the TaxClient console will ask if want to specify a different host and port.
    6.  specify the host and port(if not specified, will use the default host and port).
    7.  start to connect with TaxServer by entering: TAX
    8.  Available command: "TAX" , "STORE","QUERY", "BYE" , "END" and Integer

    Note: 
        When running a "STORE" command,
        The end income is allowed to be an empty value. (press Enter). 
        other values are not allowed to be empty.(start income,base income,per dollar tax).
        




