package com.github.uglyog.batchprocessor.commands

import org.junit.After
import org.junit.Before
import org.junit.Test

class GenerateOutputFilesTest {

    GenerateOutputFiles command
    def context
    def options

    @Before
    void setup() {
        command = new GenerateOutputFiles()

        context = [
            options: options,
            results: ['index.html': [
                title: 'Test Title',
                navigation: 'NAV',
                thumbnails: 'thumbs'
            ]],
            targetDirectory: File.createTempDir(GenerateOutputFilesTest.getSimpleName(), '.tmp'),
            inputFile: File.createTempFile(GenerateOutputFilesTest.getSimpleName(), '.xml')
        ]
    }

    @After
    void cleanup() {
        context.targetDirectory.eachFile() {
            it.delete()
        }
        context.targetDirectory.delete()
        context.inputFile.delete()
    }

    @Test
    void 'writes out the template file with results from the processing context'() {
        command.execute(context)

        def file  = new File(context.targetDirectory, 'index.html')
        assert file.exists()
        def expected = getClass().getResourceAsStream('/test-output.html').text
        assert file.text == expected
    }

}
