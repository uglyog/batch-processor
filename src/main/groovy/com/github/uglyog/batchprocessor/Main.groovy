package com.github.uglyog.batchprocessor

/**
 * Main script to parse parameters and setup pipeline
 */

import org.fusesource.jansi.Ansi
import org.fusesource.jansi.AnsiConsole

AnsiConsole.out().println(Ansi.ansi().bold().a('Batch Processor').boldOff().a(' Version 0.0.1'))

def cli = new CliBuilder(usage:'batchprocessor [options] inputfile targetdirectory')
cli.help('print this message')
def options = cli.parse(args)

if (options.arguments().empty || options.help || ProcessingPipeline.optionsInvalid(options)) {
    cli.usage()
} else {
    new ProcessingPipeline(options).executePipeline()
}
