package com.github.uglyog.batchprocessor.commands

import com.github.uglyog.batchprocessor.PipelineProcessingError
import org.junit.After
import org.junit.Before
import org.junit.Test

public class ValidateInputFileTest {

    ValidateInputFile command
    List files

    @Before
    void setup() {
        command = new ValidateInputFile()
        files = []
    }

    @After
    void cleanup() {
        files.each {
            it.delete()
        }
    }

    @Test(expected = PipelineProcessingError)
    void 'fails if the file does not exist'() {
        command.execute([args:['/does/not/exist(I hope ;-))']])
    }

    @Test(expected = PipelineProcessingError)
    void 'fails if the file is not readable'() {
        def file = File.createTempFile(ValidateInputFileTest.getSimpleName(), ".tmp")
        file.setReadable(false, false)
        files << file
        command.execute([args:[file.toString()]])
    }

    @Test(expected = PipelineProcessingError)
    void 'fails if the file is not a file'() {
        def file = File.createTempDir(ValidateInputFileTest.getSimpleName(), ".tmp")
        files << file
        command.execute([args:[file.toString()]])
    }

    @Test
    void 'adds the input file to the execution context'() {
        def file = File.createTempFile(ValidateInputFileTest.getSimpleName(), ".tmp")
        files << file
        assert command.execute([args:[file.toString()]]).inputFile == file
    }
}
