package com.github.uglyog.batchprocessor.commands

import org.junit.After
import org.junit.Before
import org.junit.Test

class GenerateOutputFileTest {

    GenerateOutputFile command
    def context
    def options

    @Before
    void setup() {
        command = new GenerateOutputFile()

        context = [
            options: options,
            results: [
                title: 'Test Title',
                navigation: 'NAV',
                thumbnails: 'thumbs'
            ],
            targetDirectory: File.createTempDir(GenerateOutputFileTest.getSimpleName(), '.tmp'),
            inputFile: File.createTempFile(GenerateOutputFileTest.getSimpleName(), '.xml')
        ]
    }

    @After
    void cleanup() {
        context.targetDirectory.delete()
        context.inputFile.delete()
    }

    @Test
    void 'writes out the template file with results from the processing context'() {
        command.execute(context)

        def file  = new File(context.targetDirectory, context.inputFile.name.replace('.xml', '.html'))
        assert file.exists()
        def expected = getClass().getResourceAsStream('/test-output.html').text
        assert file.text == expected
    }

}
