package com.github.uglyog.batchprocessor.commands

import com.github.uglyog.batchprocessor.PipelineProcessingError
import org.junit.After
import org.junit.Before
import org.junit.Test

class ValidateTargetDirectoryTest {

    ValidateTargetDirectory command
    List files

    @Before
    void setup() {
        command = new ValidateTargetDirectory()
        files = []
    }

    @After
    void cleanup() {
        files.each {
            it.delete()
        }
    }

    @Test(expected = PipelineProcessingError)
    void 'fails if the target directory does not exist'() {
        command.execute([args:['', '/also/does/not/exist(I hope ;-))']])
    }

    @Test(expected = PipelineProcessingError)
    void 'fails if the target directory is not writeable'() {
        def file = File.createTempDir(ValidateTargetDirectoryTest.getSimpleName(), ".tmp")
        file.setWritable(false, false)
        files << file
        command.execute([args:['', file.toString()]])
    }

    @Test(expected = PipelineProcessingError)
    void 'fails if the target directory is not a directory'() {
        def file = File.createTempFile(ValidateTargetDirectoryTest.getSimpleName(), ".tmp")
        files << file
        command.execute([args:['', file.toString()]])
    }

    @Test
    void 'adds the target directory to the execution context'() {
        def file = File.createTempDir(ValidateTargetDirectoryTest.getSimpleName(), ".tmp")
        files << file
        assert command.execute([args:['', file.toString()]]).targetDirectory == file
    }
}
