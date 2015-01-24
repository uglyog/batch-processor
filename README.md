# batch-processor
Simple batch file processor written in Groovy

## To build the batch processor, execute

```
$ ./gradle check install
```

This will run all the tests and build an application bundle.

## To execute the batch process

The application bundle has a start script in `./build/install/batchprocessor/bin`.

so execute

```
$ ./build/install/batchprocessor/bin/batchprocessor
Batch Processor Version 0.0.1

usage: batchprocessor [options] inputfile targetdirectory
 -help   print this message
```

## NOTE on Java versions

Java 1.7 and Java 1.8 less than 1.8.31 have a defect which causes groovy to fail with a verification error.
Java 1.8.0.31+ fixes this issue.
