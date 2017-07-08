package datacvg.gather.embeddedDB;

/*-
 * See the file LICENSE for redistribution information.
 *
 * Copyright (c) 2002,2008 Oracle.  All rights reserved.
 *
 * $Id: HelloDatabaseWorld.java,v 1.27 2008/05/27 15:30:30 mark Exp $
 */


import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;

import com.sleepycat.bind.EntryBinding;
import com.sleepycat.bind.serial.ClassCatalog;
import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.collections.StoredSortedMap;
import com.sleepycat.collections.TransactionWorker;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.EnvironmentLockedException;

/**
 * @author Mark Hayes
 */
public class DBBehaivor implements TransactionWorker {

	//private static final String[] INT_NAMES = { "Hello", "Database", "World", };
	private static boolean create = true;

	private Environment env;
	private ClassCatalog catalog;
	//private String dir = null;
	private Database db;
	private SortedMap<String, Object> map;

	public DBBehaivor(String dir) throws Exception {
		super();
		File dbHome = new File(dir);
		if (!dbHome.exists()) {
			dbHome.mkdir();
		}
		EnvironmentConfig envConfig = new EnvironmentConfig();
		envConfig.setTransactional(true);
		envConfig.setAllowCreate(true);
		try {
			this.env = new Environment(new File(dir), envConfig);
//			this.env = env;
			open();
		} catch (EnvironmentLockedException e) {
			throw e;
		} catch (DatabaseException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}

	/** Creates the database for this application */
	@SuppressWarnings("unused")
	private DBBehaivor(Environment env) throws Exception {

		this.env = env;
		open();
	}

	/** Performs work within a transaction. */
	public void doWork() throws Exception {

		writeAndRead();
	}

	/** Opens the database and creates the Map. */
	@SuppressWarnings("unchecked")
	private void open() throws Exception {

		// use a generic database configuration
		DatabaseConfig dbConfig = new DatabaseConfig();
		dbConfig.setTransactional(true);
		if (create) {
			dbConfig.setAllowCreate(true);
		}

		// catalog is needed for serial bindings (java serialization)
		Database catalogDb = env.openDatabase(null, "catalog", dbConfig);
		catalog = new StoredClassCatalog(catalogDb);

		// use Integer tuple binding for key entries
		/*
		 * TupleBinding<Integer> keyBinding =
		 * TupleBinding.getPrimitiveBinding(Integer.class);
		 */
		TupleBinding keyBinding = TupleBinding
				.getPrimitiveBinding(String.class);
		// use String serial binding for data entries
		EntryBinding<Object> dataBinding = new SerialBinding<Object>(catalog,
				Object.class);

		this.db = env.openDatabase(null, "helloworld", dbConfig);

		// create a map view of the database
		this.map = new StoredSortedMap<String, Object>(db, keyBinding,
				dataBinding, true);
	}

	/** Closes the database. */
	public void close() throws Exception {

		if (catalog != null) {
			catalog.close();
			catalog = null;
		}
		if (db != null) {
			db.close();
			db = null;
		}
		if (env != null) {
			env.close();
			env = null;
		}
	}

	public void put(String key, Object value) {
		this.map.put(key, value);
	}

	public boolean contains(String key) {
		return this.map.containsKey(key);
	}

	public Object get(String key) {
		return this.map.get(key);
	}

	public void all() {
		Iterator<Map.Entry<String, Object>> iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			@SuppressWarnings("unused")
			Map.Entry<String, Object> entry = iter.next();
		}
	}

	/** Writes and reads the database via the Map. */
	private void writeAndRead() {

	}
}
