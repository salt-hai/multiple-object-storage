package com.hengtacc.object.storage.core;

import com.hengtacc.object.storage.core.wrapper.adapter.DiskFileWrapperAdapter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * test for wrapper
 *
 * @author Kuang HaiBo 2024/5/16 10:54
 */
public class FileWrappersTest {

	static Logger log = LoggerFactory.getLogger(FileWrappersTest.class);

	@Test
	void testFileWrapperAdapter() {
		log.info("=================");
		log.info("==> test");
		log.info("=================");
		Assertions.assertInstanceOf(DiskFileWrapperAdapter.class,
				FileWrappers.determineAdapter(new File("/data/test.png")));
	}

}
