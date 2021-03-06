package br.unifesp.ict.seg.geniesearchapi.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import br.unifesp.ict.seg.geniesearchapi.infrastructure.GenieMethodRepository;
import br.unifesp.ict.seg.geniesearchapi.infrastructure.util.GenieSearchAPIConfig;

public class GenieMethodClearFilesTest {

	private GenieMethodRepository genieMethodRepository = new GenieMethodRepository();

	@Before
	public void initialize() throws IOException {
		GenieSearchAPIConfig.loadProperties();
	}

	@Test
	public void clearMethodFiles() throws Exception {
		String fqn = "org.javathena.core.utiles.Functions.parseIntToByte";
		String params = "(int)";
		String returnType = "byte";
		GenieMethod genieMethod = genieMethodRepository.findByInterfaceElements(fqn, params, returnType);
		assertTrue(genieMethod.isAllowsExecution());

		// Without files
		boolean cleaned = genieMethod.clearMethodFiles();
		assertTrue(cleaned);
		assertFalse(genieMethod.isContainsSlicedFile());
		assertFalse(Paths.get(GenieSearchAPIConfig.getExtractTempPath() + "", genieMethod.getEntityId() + "").toFile().isDirectory());
		assertFalse(genieMethod.isContainsCompiledJar());

		// With files
		genieMethod.execute("4");
		assertTrue(genieMethod.isContainsSlicedFile());
		assertTrue(Paths.get(GenieSearchAPIConfig.getExtractTempPath() + "", genieMethod.getEntityId() + "").toFile().isDirectory());
		assertTrue(genieMethod.isContainsCompiledJar());

		// Without files
		cleaned = genieMethod.clearMethodFiles();
		assertTrue(cleaned);
		assertFalse(genieMethod.isContainsSlicedFile());
		assertFalse(Paths.get(GenieSearchAPIConfig.getExtractTempPath() + "", genieMethod.getEntityId() + "").toFile().isDirectory());
		assertFalse(genieMethod.isContainsCompiledJar());

		// Slice
		genieMethod.slice();
		assertTrue(genieMethod.isContainsSlicedFile());
		assertFalse(Paths.get(GenieSearchAPIConfig.getExtractTempPath() + "", genieMethod.getEntityId() + "").toFile().isDirectory());
		assertFalse(genieMethod.isContainsCompiledJar());

		// Compiled jar
		genieMethod.generateJar();
		assertTrue(genieMethod.isContainsSlicedFile());
		assertTrue(Paths.get(GenieSearchAPIConfig.getExtractTempPath() + "", genieMethod.getEntityId() + "").toFile().isDirectory());
		assertTrue(genieMethod.isContainsCompiledJar());
	}
}
