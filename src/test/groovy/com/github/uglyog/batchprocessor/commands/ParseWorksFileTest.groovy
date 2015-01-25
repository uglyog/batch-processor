package com.github.uglyog.batchprocessor.commands

import org.junit.After
import org.junit.Before
import org.junit.Test

public class ParseWorksFileTest {

    ParseWorksFile command
    def context

    @Before
    void setup() {
        command = new ParseWorksFile()
        context = [
            inputFile: File.createTempFile(GenerateOutputFileTest.getSimpleName(), '.xml'),
            data: [:]
        ]
    }

    @After
    void cleanup() {
        context.inputFile.delete()
    }

    @Test
    void 'builds a map indexed by make and model'() {
        context.inputFile.write(getClass().getResourceAsStream('/example-works-1.xml').text)

        command.execute(context)

        assert context.data.makes.keySet() == ['MAKE1', 'MAKE2'] as Set
        assert context.data.makes.MAKE1.keySet() == ['MODEL1', 'MODEL2'] as Set
        assert context.data.makes.MAKE1.MODEL1.collect{it.id} == ['001', '004']
        assert context.data.makes.MAKE1.MODEL2.collect{it.id} == ['003']
        assert context.data.makes.MAKE2.keySet() == ['MODEL1'] as Set
        assert context.data.makes.MAKE2.MODEL1.collect{it.id} == ['002']
    }

    @Test
    void 'handles empty and missing values'() {
        context.inputFile.write(getClass().getResourceAsStream('/example-works-2.xml').text)

        command.execute(context)

        assert context.data.makes.keySet() == ['MAKE1', 'MAKE2', 'Unknown'] as Set
        assert context.data.makes.MAKE1.keySet() == ['MODEL1'] as Set
        assert context.data.makes.MAKE1.MODEL1.collect{it.id} == ['001']
        assert context.data.makes.MAKE2.keySet() == ['Unknown'] as Set
        assert context.data.makes.MAKE2.Unknown.collect{it.id} == ['002']
        assert context.data.makes.Unknown.keySet() == ['MODEL2', 'Unknown'] as Set
        assert context.data.makes.Unknown.MODEL2.collect{it.id} == ['003']
        assert context.data.makes.Unknown.Unknown.collect{it.id} == ['004', '005']
    }


}
