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
$ ./build/install/batchprocessor/bin/batchprocessor <arguments>
Batch Processor Version 0.0.1
```
