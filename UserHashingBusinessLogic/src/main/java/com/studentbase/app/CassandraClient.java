package com.studentbase.app;

import org.apache.log4j.Logger;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;

public class CassandraClient {
	
	//logger
    final static Logger LOG = Logger.getLogger(CassandraClient.class);

    private static final Cluster cluster = connect("127.0.0.1");

    /**
     * Open connection to Cassandra cluster
     * @param node IP where Cassandra deployed
     * @return New client connection
     */
	public static Cluster connect(String node) {
    	try {
    		
    		Cluster cluster = Cluster.builder()
				.addContactPoint(node)
				.build();
	 
    		Metadata metadata = cluster.getMetadata();
	 
    		LOG.info("Connected to cluster: " + metadata.getClusterName());
	 
    		for ( Host host : metadata.getAllHosts() ) {
    			LOG.info("Datatacenter: "+ host.getDatacenter() + ", Host: " + host.getAddress() + ", Rack: " + host.getRack());
    		}
		
    		return cluster;
    	} catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException(
					"There was an error building Cassandra cluster");
    	}
	 }
	
	/**
	 * Return instance of cluster
	 * @return
	 */
	public static Cluster getCluster() {
		return cluster;
	}

	/**
	 * Close Cassandra client connection
	 */
	 public void close() {
		 LOG.info("Close Cassandra client connection");
		 cluster.close();
	 }
}
