package com.bank;

	import javax.sql.DataSource;
	import java.sql.Connection;
	import org.springframework.boot.CommandLineRunner;
	import org.springframework.stereotype.Component;

	@Component
	public class DataSourceCheck implements CommandLineRunner {

	    private final DataSource dataSource;

	    public DataSourceCheck(DataSource dataSource) {
	        this.dataSource = dataSource;
	    }

	    @Override
	    public void run(String... args) throws Exception {
	        try (Connection conn = dataSource.getConnection()) {
	            System.out.println("✅ Connected to DB: " + conn.getMetaData().getURL());
	            System.out.println("✅ Driver: " + conn.getMetaData().getDriverName());
	            System.out.println("✅ AutoCommit: " + conn.getAutoCommit());
	            System.out.println("✅ Isolation Level: " + conn.getTransactionIsolation());
	        }
	    }
	}


