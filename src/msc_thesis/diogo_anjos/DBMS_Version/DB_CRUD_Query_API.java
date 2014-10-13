package msc_thesis.diogo_anjos.DBMS_Version;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

import javax.naming.NameAlreadyBoundException;

import msc_thesis.diogo_anjos.DBMS_Version.exceptions.ThereIsNoDataPoint_PKwithThisLocaionException;
import msc_thesis.diogo_anjos.simulator.EnergyMeasureTupleDTO;
import msc_thesis.diogo_anjos.util.DButil;
import msc_thesis.diogo_anjos.util.DataPoint_PK;

public class DB_CRUD_Query_API {

	private final String className = "BD_CRUD_Query_API"; //debug purposes
	private final Connection database = DButil.connectToDB("localhost", "5432", "lumina_db", "postgres", "root", className);;
	
	/*
	 *  INSERT a the given record into DBMS_EMS_Schema.DataPointReading
	 */
	public void insertInto_DatapointReadingTable(EnergyMeasureTupleDTO reading){	
		String queryStatement = "";		
		String measure_ts = reading.getMeasureTS();
		
		DataPoint_PK dpPK = null;
		try{
			dpPK = DataPoint_PK.getDataPoint_PKByLocation(reading.getMeterLocation());
		}catch(ThereIsNoDataPoint_PKwithThisLocaionException e){
			e.printStackTrace();
			System.exit(1); //non-zero status program = program terminate with errors 
		}
	
		Map<Integer,Double> datapointPKToConsumptionValueMap = new TreeMap<Integer,Double>();
		datapointPKToConsumptionValueMap.put(dpPK.getPh1_PK(), reading.getPh1Consumption());
		datapointPKToConsumptionValueMap.put(dpPK.getPh2_PK(), reading.getPh2Consumption());
		datapointPKToConsumptionValueMap.put(dpPK.getPh3_PK(), reading.getPh3Consumption());
		
		
	   try {
		   for(Integer pk : datapointPKToConsumptionValueMap.keySet()){
			   queryStatement =  "INSERT INTO \"DBMS_EMS_Schema\".\"DataPointReading\"(measure_timestamp, measure, datapoint_fk)"
					   			+ "VALUES ('"+measure_ts+"',"+datapointPKToConsumptionValueMap.get(pk)+","+pk+")";
			   DButil.executeUpdate(queryStatement, database);
		   }
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 *  TRUNCATE ALL records from table DBMS_EMS_Schema.DataPointReading
	 */
	public void truncateAll_DatapointReadingTable() {
		String queryStatement = "TRUNCATE TABLE \"DBMS_EMS_Schema\".\"DataPointReading\"";
		try{
			DButil.executeUpdate(queryStatement, database);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
}
